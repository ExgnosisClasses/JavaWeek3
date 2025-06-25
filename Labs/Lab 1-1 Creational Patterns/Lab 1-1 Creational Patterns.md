# Lab 1-1: Creational Patterns

## Part 1: Singleton

- This is the simplest of the patterns to implement

- We want to ensure that there is one instance of a `Singleton` class.
- We make the constructor for the `Singleton` private so that `Singleton` objects can only be created by methods in the `Singleton` class.

```java
public class Singleton {

    private Singleton();

}
```
- There is a private static variable that holds the reference to the one `Singleton` object
- There is a public static method `getSingleton()` that either
  - Creates a `Singleton` object if the reference is null and then assignes the newly created object to the static variable.
  - Returns a reference to the object 

```java
public class Singleton {

    private static Singleton theOne = null;
    
    public static Singleton getSingleton() {
        if (Singleton.theOne == null) {
            Singleton.theOne = new Singleton();
            return Singleton.theOne;
        }
    }

    private Singleton(){};

}
```

- Add a `main()` method that executes several calls to `getSingleton()` and prints out their address to ensure there is only one instance of type `Singleton`

``` java 
   public static void main(String[] args) {
        Singleton a = Singleton.getSingleton();
        Singleton b = Singleton.getSingleton();

        System.out.println("Address of a " + a);
        System.out.println("Address of b " + b);

        
    }
```

- You should see output similar to the following:

```console
Address of a Singleton@2a139a55
Address of b Singleton@2a139a55
```

## Part 2: Factory Method

- In this section, we will create a shape factory.
- We want to create shapes (Circle, Square, etc.) without using new directly in the client code. 
- Often used when we don't know what type we will need at compile time so we can't use the new operator in the code conveniently
- Instead, we use a factory method to encapsulate the creation of different shapes and at runtime can return the required shape.
- This is a widespread technique in a lot of Java applications

- Start with a marker interface to group objects into shapes and create several shape types

```java

interface Shape{}

class Circle implements Shape{}
class Square implements Shape{}

```

- Implement a factory class

```java
public static Shape createShape(String type) {
        switch (type.toLowerCase()) {
            case "circle":
                return new Circle();
            case "square":
                return new Square();
            default:
            return null;
        }
      
    }
```

- Use the main() method to use the factory method to create a circle, square and a triangle

```java
   public static void main(String[] args) {

        System.out.println("Circle " + FactoryMethod.createShape("circle"));
        System.out.println("Square " + FactoryMethod.createShape("Square"));
        System.out.println("Triangle " + FactoryMethod.createShape("Triangle"));
              
    }

```

- Your output should look like this:

```console
Circle Circle@6d06d69c
Square Square@135fbaa4
Triangle null
```

## End Lab
