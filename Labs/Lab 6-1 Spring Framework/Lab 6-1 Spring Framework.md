# Lab 6-1: Spring Framework

## Lab Objectives

In this lab, you will use the Spring Framework to manage a simple application for you

## Part 1: Lab Setup

- We are going to let Maven set the Spring environment up for us.
- Go to a new directory and create the project at the terminal with the following maven command.
- The command is found in the file `Setup.txt` in the `Assets` folder
- The project creation looks like this

```console
$ mvn archetype:generate \
  -DgroupId=lab6 \
  -DartifactId=spring \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 \
  -DinteractiveMode=false
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------< org.apache.maven:standalone-pom >-------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] >>> archetype:3.4.0:generate (default-cli) > generate-sources @ standalone-pom >>>
[INFO] 
[INFO] <<< archetype:3.4.0:generate (default-cli) < generate-sources @ standalone-pom <<<
[INFO] 
[INFO] 
[INFO] --- archetype:3.4.0:generate (default-cli) @ standalone-pom ---
[INFO] Generating project in Batch mode
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/archetypes/maven-archetype-quickstart/1.4/maven-archetype-quickstart-1.4.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/archetypes/maven-archetype-quickstart/1.4/maven-archetype-quickstart-1.4.jar (7.1 kB at 16 kB/s)
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Archetype: maven-archetype-quickstart:1.4
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: lab6
[INFO] Parameter: artifactId, Value: spring
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Parameter: package, Value: lab6
[INFO] Parameter: packageInPathFormat, Value: lab6
[INFO] Parameter: package, Value: lab6
[INFO] Parameter: groupId, Value: lab6
[INFO] Parameter: artifactId, Value: spring
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] Project created from Archetype in dir: /home/rod/working/maven/spring
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.151 s
[INFO] Finished at: 2025-06-23T20:58:30-04:00
[INFO] ------------------------------------------------------------------------

```

- Locate to the `spring` directory and start VS Code

#### Edits to the pom file

- Open the pom file and edit the following so that Maven uses your version of Java rather than Java 7 (same as 1.7)
- In the following, it has been changed to Java 21

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
  </properties>
```

- Now add the Spring dependencies. 

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
    <version>5.3.34</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-beans</artifactId>
    <version>5.3.34</version>
</dependency>

```
- These are also in the `Setup.txt` file
- The final configured `pom.xml` file is also in the `Assets` directory
- Run `mvn validate` to ensure sure there are no errors

## Part 2: The Non-spring Project

- We often start a project developing our POJOs before defining the Spring Framework.  
-  Create a `Consultant` interface as shown that defines a single method that “gives advice.

```java
package lab6;

public interface Consultant {
    String getAdvice();
}
```

- Create an `ITGuru` class that implements consultant.

```java
package lab6;

public class ITGuru implements Consultant{

    @Override
    public String getAdvice() {
        return "Turn it off and on again";
    
    }
}
```

- And a `PRWhiz` class that also implements `Consultant`


```java
package lab6;

public class PRWhiz implements Consultant {

	@Override
	public String getAdvice() {
		return "Don't let then see you sweat";
	}

}
```

- The `main()` method creates a couple of consultants and prints out their advice.
- Replace the generated HelloWorld code with the following

```java

package lab6;

public class App {

	public static void main(String[] args) {
		 Consultant dilbert = new ITGuru();
		  System.out.println("ITGuru says " + dilbert.getAdvice());
		  
		  Consultant ratbert = new PRWhiz();
		  System.out.println("PRWhiz says " + ratbert.getAdvice());

	}

}

```

- Run the code to ensure it works.
- The full code is available in the Part 1 directory in the Assets directory

```consul
ITGuru says Turn it off and on again
PRWhiz says Don't let then see you sweat
```

## Part 3: Spring Framework

- In this part of the lab, we let Spring manage the classes for us

#### Add the configuration file

- As noted in the slides, we need to provide Spring with some direction
- The `config.xml` file contains the directive that Spring is to look through the `lab6` package for annotations that show Spring what needs to be managed
- A copy of this file is in the Assets directory

```xml
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context" xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
<!--  add entry to enable component scanning  -->
<context:component-scan base-package="lab6"/>
</beans>
```

- This file needs to be placed in the package `lab6` at the same level as the `App.java` file
- To make sure that all the dependencies are installed, run 

```console
mvn clean install
```

#### Add Annotations

- Annotate the ITGuru class with `@Component` so Spring knows objects of this class are beans

```java
package lab6;
import org.springframework.stereotype.Component;

@Component
public class ITGuru implements Consultant {

	@Override
	public String getAdvice() {
		return "Turn it off and on again";
	}
}

```

- Do the same for the PRWhizz class, but this time rename the bean to `SpinDoctor`

```java

package lab6;

import org.springframework.stereotype.Component;

@Component("SpinDoctor")
public class PRWhiz implements Consultant {

	@Override
	public String getAdvice() {
		return "Don't let them see you sweat";
	}
}

```

- Now modify the runner class to create a Spring Context like this

```java
package lab6;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("lab6/config.xml");
   
   
        context.close();
    }
}
```
- Delegate the creation of the beans to the context
- What we are asking Spring to do is to find a class it manages, a class annotated with `@component` that implements a `Consultant` interface and
  - Return a reference to an existing bean of that type
  - Or, if one doesn't already exist, create one
- Once the context is closed, all managed instances are destroyed

- By specifying the interface and bean name, we can easily swap out the implementing class for a new one, as long as it implements the `Consultant` interface and is marked with the `@Component(beanname)` annotation
- The complete code looks like this

```java
package lab6;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("lab6/config.xml");

        Consultant dilbert = context.getBean("ITGuru", Consultant.class);
        Consultant ratbert = context.getBean("SpinDoctor", Consultant.class);

        System.out.println("Dilbert says " + dilbert.getAdvice());
        System.out.println("Ratbert says " + ratbert.getAdvice());
        context.close();
    }
}
```
- Running this should produce the same result as before
- Full code is in the Part 2 directory in the Solutions directory

### Don't delete your work, you will need it in the next lab

## End Lab
