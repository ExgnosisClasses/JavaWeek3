# Lab 6-3: Dependencies

## Lab Objectives

In this lab, you will configure a dependency injection between the ITGuru bean and a new bean called `TechGuide` which implements a `Manual` (as in user manual) interface.

## Lab Setup

- This lab builds on the previous lab, no additional setup is required. 
- You can start coding from the final state of the code from the last lab.

## Part 1: Modifying the Java Components

- In the previous labs, the `Consultant` objects just returned a string. 
- In this lab you will implement a `TechGuide` type that the `ITGuru `objects do a lookup on to produce their advice.
- There will be a constructor injection used to create a link between the two objects. 
- This will be indicated by an `@Autowired` annotation. 
- In terms of lifecycles, each `ITGuru` bean is a reference to a unique instance, but the `TechGuide` is a singleton object which means that all the `ITGuru` objects use the same instance of the `TechGuide` bean

- Create the `Manual` interface, remember this is needed by Spring

```java
package lab6;

public interface Manual {
    String lookup();
}
```

- Add the `TechGuide` implementation class to be used as a bean

```java
package lab6;

import org.springframework.stereotype.Component;

@Component
public class TechGuide  implements Manual{

    @Override
    public String lookup() {
        return "Just a sec.. asking ChatGPT";
    }
}

```

- Modify the `ITGuru` class
- There are three changes to be made to the class
- The first is to add a private instance variable of type `Manual` and a constructor that takes a reference to a `Manual` and initializes the variable to the reference passed.
- The second is to change the `giveAdvice(`) method to return the results of `lookup()` on the Manual.
- The third is to add the` @Autowired` annotation to the constructor to tell Spring to resolve the dependency when the `ITGuru` bean is created

```java 
package lab6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ITGuru implements Consultant {

    private Manual myManual;

    @Autowired
    ITGuru(Manual m) {
        this.myManual = m;
    }

	@Override
	public String getAdvice() {
		return this.myManual.lookup();
	}
}
```

## Part 2: Run

- Create two `ITGuru` beans and show that the `getAdvice()` method works on both of them.\

```java
package lab6;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("lab6/config.xml");

        Consultant dilbert = context.getBean("ITGuru", Consultant.class);
        Consultant ratbert = context.getBean("ITGuru", Consultant.class);
        System.out.println("Dilbert says " + dilbert.getAdvice());
        System.out.println("Ratbert says " + ratbert.getAdvice());


    
        context.close();
    }
}
```

```Console
Dilbert says Just a sec.. asking ChatGPT
Ratbert says Just a sec.. asking ChatGPT
```

## Challenge

- In the project, the `ITGuru` class asked for a bean that implemented the `Manua`l interface.  
- Spring knew to create the `TechGuide` bean because it was the only class that implemented the `Manual` interface.
- But what if there are two classes that implement the `Manual` interface?
- Copy the `TechGuide` file and rename the copy UserGuide. 
- Now there are two implementations of the `Manual` interface.
- Run the code again and see what happens. Can you explain the results?
- Change the constructor in `ITGuru` and the private instance variable to be of type `TechGuide` and then rerun the code. Can you explain the results?


## End Lab

