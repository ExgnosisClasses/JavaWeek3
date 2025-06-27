# Lab 6-2: Bean Lifecycle

## Lab Objectives

In this lab, you will explore how Spring manages the lifcycle of beans

## Lab Setup

- This is a continuation of the previous lab, so you can start with code that you finished that lab with

## Part 1: Default behavior

- By default, Spring uses the Singleton pattern when managing beans
- If a new request for a bean is made, if an instance already exists, Spring returns that
- To see this, modify the App class to create two `ITGuru` objects and print out their addresses
- This code is in `App1.java` in the Solutions directory

```java
public class App {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("lab6/config.xml");

        Consultant dilbert = context.getBean("ITGuru", Consultant.class);
        Consultant ratbert = context.getBean("ITGuru", Consultant.class);
        System.out.println("Address of Dilbert = " + dilbert);
        System.out.println("Address of Ratbert = " + ratbert);


        context.close();
    }
}
```

- And we see that they have the same address.

```console
Address of Dilbert = lab6.ITGuru@4562e04d
Address of Ratbert = lab6.ITGuru@4562e04d
```

## Part 2: Modify the ITGuru class

- The only change to be made to the class definition is to add the scope annotation as shown below
- This tells Spring to create a new instance of the ITGuru bean every time one is requested

```java
package lab6;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ITGuru implements Consultant {

    @Override
    public String getAdvice() {
        return "Turn it off and on again";
    }
}
```

- Rerunning the App code produces

```console
Address of Dilbert = lab6.ITGuru@a1153bc
Address of Ratbert = lab6.ITGuru@1aafa41
```

