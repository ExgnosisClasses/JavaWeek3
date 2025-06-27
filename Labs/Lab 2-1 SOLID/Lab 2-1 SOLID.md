# Lab 2-1: Solid

## Part 1: Single Responsibility Principle

- A class should have one and only one reason to change.
- Meaning in practical terms:
  - Each class should have only one job.
  - If a class has multiple reasons to change, it is violating SRP.

- Create a project and create a package called `srp`
- Create the following class.

- `UserManager` is responsible for:
  - User management (adding users).
  - Generating user reports.
  - These are two different responsibilities, violating SRP.
  
```java
class UserManager {
    public void addUser(String username, String password) {
        // Logic to add user to the database
        System.out.println("User '" + username + "' added to the database.");
    }

    public void generateReport(String username) {
        // Logic to generate a report for the user
        System.out.println("Report generated for user: " + username);
    }
}

```

- Test the code to ensure it works.
- A Runner class is shown
- The code for this section is in the file `non-srp.java1` in the Solutions/srp directory

```java
package srp;

public class Runner {
 public static void main(String[] args) {
    UserManager userManager = new UserManager();
    userManager.addUser("alice", "password123");
    userManager.generateReport("alice");
    
 }
}
```

```console
User 'alice' added to the database.
Report generated for user: alice
```

### Refactoring.

- To refactor code means to change the structure of the code without changing its functionality
- To refactor this example, we need to have two classes.
  - UserService for user management
  - UserReportService for report generation:
- Enter and run the following code to ensure we wee the same output as before
- It is also in the file `-srp.java1` in the Solutions/srp

```java
package srp;

public class Runner {
 public static void main(String[] args) {
    UserService userService = new UserService();
    UserReportService reportService = new UserReportService();

    userService.addUser("alice", "password123");
    reportService.generateReport("alice");
 }
}


class UserReportService {

    public void generateReport(String username) {
        System.out.println("Report generated for user: " + username);
    }
}


class UserService {

    public void addUser(String username, String password) {
        System.out.println("User '" + username + "' added to the database.");
    }
}

```

## Open Close Principle

- This will be illustrated in the next lab

## Liskov Substitution Principle

- This is the one that most people struggle with since it's not immediately obvious what it means. 
- Objects of a superclass should be replaceable with objects of its subclasses without altering the correctness of the program.
- If S is a subclass of T, you should be able to substitute T with S without unexpected behavior.


### Examole that Violates LSP

Create a Java project with the following violating LSP example:

```java
class Bird {
    public void fly() {
        System.out.println("Flying high in the sky.");
    }
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        // Ostriches cannot fly
        throw new UnsupportedOperationException("Ostriches can't fly!");
    }
}
```

- We expect that Bird can be substituted by any subclass.
- Substituting Bird with Ostrich breaks the program since `fly()` throws an exception.
- This violates LSP.
- The code is in `non-lsp.java` in the Solutions/lsp directory

- Test by running this class

```java
package lsp;

public class Runner {

    public static void main(String[] args) {
        Bird myBird = new Ostrich();
        myBird.fly(); // Expecting it to fly, but throws exception
    }
}
```
```console
Exception in thread "main" java.lang.UnsupportedOperationException: Ostriches can't fly!
        at lsp.Ostrich.fly(Runner.java:21)
        at lsp.Runner.main(Runner.java:7)
```


### Refactoring

- We start the refactoring by noting there are two kinds of birds
  - Those that can fly
  - And those that cannot
- The original formulation of the code did not take that into account
- If we have `fly()` defined in the bird class, it will be inherited by non-flying birds, which is not an accurate modeling of the problem
- This is the value of the LSP: violations of the rule often are symptoms of an error in our analysis of the problem domain.

- Start by creating a `Bird` class that is the inheritance root for both flying birds and non-flying birds.
- And a specialized subclass for flying birds


```java
class Bird {
    public void eat() {
        System.out.println("Peck peck peck...");
    }
}

class FlyingBird extends Bird {
  @Override
  public void fly() {
    System.out.println("Flying high in the sky.");
  }
}
```

- And subclass a concrete subtype for each

```java
class Ostrich extends Bird {
    public void run() {
        System.out.println("Running fast on the ground.");
    }
}

class Owl extends FlyingBird {
    public void run() {
        System.out.println("Running fast on the ground.");
    }
}
```

- The Runner class can now look like this where we add a test to see if a `Bird` object is a `FlyingBird` before calling `fly()` on it
- This code is in the file `lsp1.java` 

```java
package lsp;

public class Runner {

    public static void main(String[] args) {
        Bird owl = new Owl();
        owl.eat();
        if (owl instanceof FlyingBird) {
            ((FlyingBird) owl).fly();
        }

    }
}

```

### More Modern Approach

- A more modern approach is to not use inheritance at all but to use interfaces
- In this case, we would define interfaces for the different functions different subgroups could manifest
- For example, flying is a bundle of functionality that can be added to some birds and some mammals (bats).
- In this case, we define a `Flyer` interface like this
- And a Runner interface

```java
interface Flyer {
    void fly();
}

interface Runner {
  void run();
}
```

- Then any bird that is a flyer has to implement the `Flyer` interface.
- Now we only have one level of inheritance, but each concrete class can have a different bundles of functionality without needing superclasses.
- These bundles of functionality are often referred to as mix-ins since we mix functionality into a class.

```java
class Ostrich extends Bird implements Runner {
    @Override
    public void run() {
        System.out.println("Running fast on the ground.");
    }
}

class Owl extends Bird implements Flyer, Runner {
    @Override
    public void fly() {
        System.out.println("Flying high in the sky");
    }
    @Override
    public void run() {
        System.out.println("Running with a sort of a waddle on the ground.");
    }
}
```

- Run the code to ensure it works.
- The code is in `lsp2.java`

```console
Flying high in the sky
Running with a sort of a waddle on the ground.
Running fast on the ground.
```

## Note

- Interface Segregation and Dependency Inversion Principle will be covered in the next few labs

## End Lab