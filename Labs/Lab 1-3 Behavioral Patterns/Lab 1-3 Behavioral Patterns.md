# Lab 1-3 Behavioral Patterns

In the first part of this lab, you will implement a State pattern for a blender object that has two states, on and off. 
- Like most devices, it displays state-specific behavior

## Part 1: Create the state objects

- We only have to be concerned with the methods that are state-specific.
- We can often identify these in existing code because in the body of the method there is either a series of `if` statement or a switch statement selecting code to execute based on the current state

- We start by defining a `State` interface that specifies the state-specific methods.
- In this case, we name it `exec()`
- Then we create the state objects that implement state-specific code for the method defined in the interface

```java
interface State  {
    void exec();
}

class On implements State {

    @Override
    public void exec() {
        System.out.println("The device is on");
    }
    
}

class Off implements State {
    @Override
    public void exec() {
        System.out.println("The device is off");
    }    

}
```

## Part 2: Create the Device class

- It is a best practice to enumerate the states using an enum.
- This helps ensure we don't introduce invalid states
- We also keep track of the current state in the status variable
- There are three State variables
  - The on variable is a reference to an `On` state object
  - The off variable is a reference to an `Off` state object
  - the currentState variable references which of the on or off objects is currently in use, ie. the active state

```java
class Device {

    // These are the states the device can take
    private enum deviceState{ 
        ON,
        OFF;
    }

    // The state objects
    private State on = null;
    private State off = null;
    private State currentState = null;
    private deviceState status = deviceState.OFF;

```

- In the constructor, we create the two state objects
- Then we set the state to `OFF` since we are making it the state the blender initially starts in.

```java

    Device() {
        this.on = new On();
        this.off = new Off();
        this.currentState = this.off;
        this.status = deviceState.OFF;
    }
```

- We delegate execution of the `exec()` method to the current State object

```java
 public void exec() {
        this.currentState.exec();
    }
```

- Now add to methods to turn the blender off and on.

```java
 public void turnOn() {
        this.currentState = this.on;
        this.status = deviceState.ON;
    }

    public void turnOff() {
        this.currentState = this.off;
        this.status = deviceState.OFF;
    }

    public deviceState getStatus() {
        return this.status;
    }

```

- Test by creating a blender and turning it off and on

```java
ublic class Runner {
    public static void main(String[] args) {
        Device blender = new Device();
        blender.exec();
        blender.turnOn();
        blender.exec();
        blender.turnOff();
        blender.exec();
    }
}
```

```console
OFF
The device is off
The device is on
The device is off
```


## Part 3: The Visitor pattern

- In this section, we are going to have a Visitor object called a zookeeper that can provide the name of an animal when given a object a specific animal tpe.

- Start by creating a zookeeper Visitor object
- It has one method that animal objects can use to ask the zookeeper their name
- In our example, the zookeeper only knows about dogs and cats
- Otherwise, it just responds that it doesn't know
- Note that the zookeeper had to determine the type of object calling it so the method needs a reference to the calling object


```java
class Visitor {
    public String whatAmI(Object thing) {
        if (thing instanceof Dog) {
            return "I am a dog";
        } else if (thing instanceof Cat) {
            return "I am a cat";
        } else {
            return "I don't know";
        }
    }
}
```

## Part 4: Create the clients

- Create a Dog, Cat and Programmer class.
- Since each needs the logic in the zookeeper, pass a reference to the zookeeper object to each client class in their constructor. 
- Each object passes a reference to itself (the `this` keyword) when if calles the zookeeper


```java
class Cat {
    private Visitor info;
    
    Cat(Visitor info) {
       this.info = info;
    } 

    void printType() {
        System.out.println(this.info.whatAmI(this));
    }

}

class Dog {
    private Visitor info;
    
    Dog(Visitor info) {
       this.info = info;
    } 

    void printType() {
        System.out.println(this.info.whatAmI(this));
    }
}

class Programmer {
    private Visitor info;
    
    Programmer(Visitor info) {
       this.info = info;
    } 

    void printType() {
        System.out.println(this.info.whatAmI(this));
    }
}

```

## Part 5: Create a Runner and Tes

- Note that we do have to make sure the zookeeper object exists before we create the client objects

```java
public class Runner {
    public static void main(String[] args) {
        Visitor zookeeper = new Visitor();
        Cat c = new Cat(zookeeper);
        c.printType();
        Dog d = new Dog(zookeeper);
        d.printType();
        Programmer p = new Programmer(zookeeper);
        p.printType();
    }

}
```


## End LabS