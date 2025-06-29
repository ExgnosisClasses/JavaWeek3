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