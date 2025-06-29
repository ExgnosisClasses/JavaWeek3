# Lab 2-2 Interface Design

## Lab Objectives

- This lab walks through an interface design for a Java accounting application that supports a RESTful service.
- In the next lab, we will look at the internal architecture of the application

## Part 1: Scoping the problem

- This is going to be an application that currently pays invoices and processes payments, although it may later need to handle other forms of accounting transactions like credit notes, returns and others that will be determined later.
- This application will be migrated to a RESTful microservice

## Part 2: The Imperative Interface

- It is tempting to write an interface that looks something like this

```java
interaface AccountingService {
    boolean payInvoice(String invNumber, int amount);
    boolean procPayment(String invNumber, int amount);
        }
```

- Where the boolean return value indicates whether the operation was successful or not.
- There are a number of problems with this approach.
- This is an imperative interface - the methods are operations to be performed
- There is nothing inherently wrong with an imperative interface
- In fact, there are design problems where it is the most effective solution
- In this case, we would not be using REST but a different kind of protocol like gRPC (general remote procedure call)


- This interface violates the Open-Close principle
- If we need to extend the functionality, like adding a `issueCreditNote()` method, then we need to change the interface, which breaks the contract between our service and existing clients

- Another problem, is that our design is not based on what works in the existing system, we are using programming primitives line boolean and int to represent business objects

## Part 3: Design Analysis

- Good OO practices are based on the idea of iconicity - our system should resemble what it automates.
- Looking at the real world situation, our hypothetical process looks more like this
  - A document comes in to the accounting department
  - It is examined to see if it is an invoice or payment
  - Based on the document type, it is forwarded to Accounts Payable or Accounts Receivable to be dealt with
  - The department that performs the work then returns either receipt for a payment or an acknowledgement of the invoice received. 

## Part 4: The Interfaces

- There are two basic types of documents, which we will call a transaction and a report, which we can define with two interfaces
- These are marker interfaces used to create some abstract types

```java
package accounting;

public interface Transaction {}
```

```java
package accounting;

public interface Report {}
```

- Now we can write a declarative interface that looks like this


```java
package accounting;

public interface AccountingService {
    Report process(Transaction T);

}

```

- This interface has the following improvements over the imperative one in this problem
- It follows the Open Close principle, as we will see later
- It's easier to wrap this in a REST web interface because it is expressed in terms of the domain objects
- Note that this is also an implementation of the Command design pattern

## Part 5: The Domain Objects

- Now we can define an Invoice class.
- To keep the lab simple, we will use only a couple of fields

```java
package accounting;

public class InvoiceReport implements Report {
  private String invNumber = null;

  InvoiceReport(String id) {
    this.invNumber = id;
  }

  public String toString() {
    return "Processed Invoice #" + this.invNumber;
  }

  public String getInvNumber() {
    return this.invNumber;
  }

}
```

- And the Payment class

```java
package accounting;

public class Payment  implements Transaction {
  private String paymentID = null;

  Payment(String number) {
    this.paymentID = number;
  }
  public String toString() {
    return "Payment #"+this.paymentID;
  }
  public String getPaymentID() {
    return this.paymentID;
  }

}

```

- The important thing to note is that we can add more Transaction types without breaking the interface

- We now define three Report types
  - In addition to a PaymentReport and an InvoiceReport, we need an ErrorReport to return to the client when things go wrong.

```java
package accounting;

public class PaymentReport implements Report {
    private String id;
    PaymentReport(String id) {
        this.id = id;
    }
    public String toString() {
        return "Processed Payment #" + this.id;
    }
}
```


```java
package accounting;

public class InvoiceReport implements Report {
    private String invNumber = null;

    InvoiceReport(String id) {
        this.invNumber = id;
    }

    public String toString() {
        return "Processed Invoice #" + this.invNumber;
    }

}

```

```java
package accounting;

public class ErrorReport implements Report {
    public String toString() {
        return "A processing error occurred - Transaction cancelled";
    }

}
```

## Part 6: Application Architecture

- The accounting department in the real world has two parts
  - Accounts payable and accounts receivable
- This suggests we need two classes that correspond to these
- Each will take a transaction and return a report

- First the AccountsReceivable

```java
package accounting;

public class AccountsReceivable implements AccountingService {

  @Override
  public Report process(Transaction t) {
    // check to see if the Transaction one the AP module is responsible for

    if (t instanceof Payment) {
      String id  =  ((Payment)t).getPaymentID();
      // if there is a null ID, return an error
      // This simulates corrupt data
      if (null == id) {
        return new ErrorReport();
      }
      return new PaymentReport(id);
    } else {
      return new ErrorReport();
    }
  }
}
```

```java
package accounting;

public class AccountsPayable implements AccountingService {

    public Report process(Transaction t) {
        // check to see if the Transaction one the AP module is responsible for
    
        if (t instanceof Invoice) {
            String id  =  ((Invoice)t).getInvNumber();
            // if there is a null ID, return an error
            // This simulates corrupt data
            if (null == id) {
                return new ErrorReport();
            }
            return new InvoiceReport(id);
        } else {
            return new ErrorReport();
        }
    }
}

```

## Part 7: The Facade

- The last step is to provide a single point of contact for a client. 
- The client should not have to get a reference to the AccountsPayable or AccountsReceivables objects
- One reason is that it produces a coupling between the client and the internal structure of the accounting service.
- Instead, we use the Facade design pattern to provide a unified interface to the different accounting departments


- In the code, we also see an example of DIP.
- We are injecting the dependencies into the AccountingDepartment object
- We are also creating the dependency if it doesn't exist.


- The role of the facade class is to identify the type of request, forward it to the correct object and relay the result of processing that request back to the client
- An error report is generated if the request is not recognized
- Notice that we have avoided throwing exceptions because we are expecting this will be deployed in a microservice eventually.

```java
package accounting;

public class AccountingDepartment implements AccountingService{

    private AccountsPayable payables = null;
    private AccountsReceivable receivables = null;

    AccountingDepartment(AccountsPayable p, AccountsReceivable r) {
        if (p == null) {
            this.payables = new AccountsPayable();
        } else this.payables = p;

        if (r == null ) {
            this.receivables = new AccountsReceivable();
        } else {
            this.receivables = r;
        }
        System.out.println("Accounting Department open for business");
    }

    @Override
    public Report process(Transaction t) {
        if (t instanceof Invoice) {
            return payables.process(t);
        } else if (t instanceof Payment) {
            return receivables.process(t);
        }
        return new ErrorReport();
    }
}

```

## Part 8: Run the application

- Create a runner class and test out the accounting department with a valid payment and invoice, an unrecognized transaction and several corrupted transactions

```java
package accounting;

public class Runner {

    public static void main(String[] args) {
       System.out.println("Creating the Accounting department");
       AccountingDepartment acc = new AccountingDepartment(new AccountsPayable(), new AccountsReceivable());
       Invoice i = new Invoice("33183");
       Payment p = new Payment("990");

       // Create and process an invvoice and payment
       System.out.println(acc.process(i));
       System.out.println(acc.process(p));

       // Create a bad transacion
       System.out.println(acc.process(new BadTrans()));

       // Create a couple of malformed transactions
       i = new Invoice(null);
       p = new Payment(null);
       System.out.println(acc.process(i));
       System.out.println(acc.process(p));
   }
    }
    
class BadTrans implements Transaction {}
```
```console
Creating the Accounting department
Accounting Department open for business
Processed Invoice #33183
Processed Payment #990
A processing error occurred - Transaction cancelled
A processing error occurred - Transaction cancelled
A processing error occurred - Transaction cancelled
```


## Challenge

Add the capability to process a utility payment.

## End Lab
