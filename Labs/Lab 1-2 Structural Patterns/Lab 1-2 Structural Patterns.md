# Lab 1-2: Structural Patterns

## Lab Objectives

In this lab you will implement several of the structural patterns

## Part 1: The Original Class

- First, define the interface for the original class
- Then create an implementation class.

```java
interface Original {
    String meth1(String s);
    String meth2(String s);
}

class OriginalImp implements Original {

    @Override
    public String meth1(String s) {
        return "Original Method 1" + s;
    }

    @Override
    public String meth2(String s) {
        return "Original Method 2" + s;
    }

}
```

- Create a runner class and test out the class methods

```java
public class Runner {
    public static void main(String[] args) {
        Original orig = new OriginalImp();
        System.out.println(orig.meth1("Original"));
        System.out.println(orig.meth2("Original"));
    }

}
```
```console
Original Method 1 Original
Original Method 2 Original
```

## Part 2: Define the decorator

- Assume that we now need to add a new method to the original class for some reason.
- We cannot use inheritance, possibly because we don't have access to the original code, so we can't make the changes at compile time.
- Or we may need to add one of several possible new methods in different contexts.
- In this case, we would like to avoid creating a class that contains all these alternatives to try and avoid code bloat.

- First create the `Decorator` interface.  
- It extends the original interface so ensure all the original functionality of the original class is preserved
- Add the new method to the `Decorator` interface

```java
interface Decorator  extends Original {
    String newMeth(String s);
}
```

## Part 3: Create the Decorator Implementation

- By definition, a decorator extends an existing class's interface
- To implement this, the decorator object keeps a copy of the object it decorates
- The only constructor we need is one that takes a reference to the original object

```java
class DecImp implements Decorator {
    private Original myOrig;

    DecImp(Original o) {
        this.myOrig = o;
    }
}
```

## Part 4: Implement the Methods

- For the existing methods in the decorated class, we just forward the method invocation on the decorator to the decorated object and relay the response to the client.
- And we add the new method

```java
   @Override
    public String meth1(String s) {
        return this.myOrig.meth1(s);
    }

    @Override
    public String meth2(String s) {
         return this.myOrig.meth2(s);
    }

    @Override
    public String newMeth(String s) {
        return "New Method " + s ;
    }
    
```

## Part 5: Test the Decorator

- Modify the Runner class to test your decorated object

```java
public class Runner {
    public static void main(String[] args) {
        Original orig = new OriginalImp();
        System.out.println(orig.meth1("Original"));
        System.out.println(orig.meth2("Original"));

        Decorator decorated = new DecImp(orig);
        System.out.println(decorated.meth1("Decorated"));
        System.out.println(decorated.meth2("Decorated"));
        System.out.println(decorated.newMeth("Decorated"));
    }

}
```
```console
Original Method 1 Original
Original Method 2 Original
Original Method 1 Decorated
Original Method 2 Decorated
New Method Decorated
```

## Part 6: The Flyweight Pattern

- In this section, we will assume that we have a limited number of resources that are expensive to create. 
- Assume these are `Resource` objects representing some sort of resource

- We start by creating a `ResourcePool` class that pre-creates a fixed number of these objects.
- We set some foxed values for the default and maximum number of objects created.
- The pool of objects is just an array of resources.
- We also add an array of booleans that indicate if the corresponding object is in use or not.
- The number of resources not in use will this recorded in the `available` variable

```java 
class ResourcePool {
    private  Resource [] pool = null;
    private  boolean [] free = null;
    private int poolSize = 0;
    private int available = 0;
    public static final int MAX_POOL_SIZE = 10;
    public static final int DEFAULT_POOL_SIZE = 4;
```

- The constructor creates the arrays after checking that the requested size is valid
- The arrays are then populated with constructed resource objects
- We also have a constructor with no arguments that uses the default size

```java
     ResourcePool(int size) {
    if (size < 1) this.poolSize = ResourcePool.DEFAULT_POOL_SIZE;
    else if (size > ResourcePool.MAX_POOL_SIZE) this.poolSize = ResourcePool.MAX_POOL_SIZE;
    else {
        this.poolSize = size;
        this.available = size;

        pool = new Resource[this.poolSize];
        free = new boolean[this.poolSize];

        for (int i = 0; i < this.poolSize; i++) {
            pool[i] = new Resource();
            free[i] = true;
        }
    }
}

ResourcePool() {
    this(DEFAULT_POOL_SIZE);
}
```

## Part 7: Accessing the pool

- We will create two methods
- The `getResouce()` method 
  - Returns `null` if there are no available resources
  - Loops through the `free` array to find a Resource not in use
  - If found, it marks it as in use, decreases the available number
  - Return the reference to the resource
- The `releaseResource(Resource r)`
  - Searches the `pool` for a reference that is the same as `r`
  - If found, it sets the `free` value to `true` and increments the availability count
  - If not found, it does nothing "This isn't one of mine"
- We also add a method that tells clients how many resources are avaialble

```java
   public Resource getResource() {
        Resource r = null;
        if (this.available == 0) return r;
        for (int i = 0; i < this.poolSize; i++) {
            if (this.free[i]) {
                this.free[i] = false;
                r = this.pool[i];
                this.available--;
                break;
            }
        }
        return r;

    }

    public void releaseResource(Resource r) {
        for (int i = 0; i < this.poolSize; i++) {
            if (r == this.pool[i]) {
                this.free[i] = true;
                this.available++;
                break;
            }
        }

    }
    public int availableResources() {
        return this.available;

    }

```

## Part 8: Test your code

- Create a pool with the default size.
- Check the availability count
- Get a resource
- Check the availability count
- Release the resource
- Check the availability count
- Try to allocate more resources than the pool has.

```java
public class Runner {
    public static void main(String[] args) {
        ResourcePool r = new ResourcePool();
        System.out.println("Available: " + r.availableResources());
        Resource res = r.getResource();
        System.out.println("Resource " + res);
        System.out.println("Available: " + r.availableResources());
        r.releaseResource(res);
        System.out.println("Available: " + r.availableResources());

        for (int i = 0; i < 5; i++) {
            System.out.println("i=" + i + " Resource = " + r.getResource());
        }           
    }
}

```

## Challenge 1

- One of the problems with this code is that there is nothing to prevent the creation of multiple resource pools.
- Change the code so that the ResourcePool is a singleton class.
- Note that you will have to use a fixed size pool and remove the size parameter from the constructors because we create it once and then the size can't change.

## Challenge 2

- There is a problem when a client gets a resource then releases it but continues to use the resource. 
- Another client could then get a reference to the resource and we wun into something like a race condition
- Give the resource object a dummy operation like `run()`
- Wrap it in a decorator that remembers if the resource is free.
- When the `run()` operations is called on a resource that is marked as free in the pool, have it throw the unchecked `UnsupportedOperationException`

