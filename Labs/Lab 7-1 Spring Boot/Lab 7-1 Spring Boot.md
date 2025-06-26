# Lab 7-1: Spring Boot

## Lab Objectives

In this lab you will use Gradle and Spring boot to create a simple command line application

## Part 1: Set up gradle

- Like you did in lab 5-1, create a new directory called `simple-boot-app`
- Locate to the directory and start VS Code

- Set up the `settings.gradle` file like this

```text
rootProject.name = 'simple-boot-app'
```

- And the `build.gradle` file like this

```text
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.5'
    id 'io.spring.dependency-management' version '1.1.5'
}

group = 'lab7'
version = '1.0.0'

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
}

```
#### id 'org.springframework.boot' version '3.2.5'

- Applies the Spring Boot plugin
- Simplifies the creation and execution of Spring Boot applications
- Adds special tasks like:
  - bootRun – runs the app with embedded Tomcat if necessary
  - bootJar – creates an executable fat JAR with all dependencies
- Automatically configures default settings and sensible conventions

#### id 'io.spring.dependency-management' version '1.1.5'

- Applies Spring's Dependency Management plugin
- Lets you declare dependencies without specifying versions, because the plugin injects the correct version from Spring Boot’s BOM


- Create a directory structure in the project that looks like this

```text
simple-boot-app
└── src
    └── main
        └── java
            └── lab7    
```

## Part 2: Add the code

- Add the following file into the `lab7` package

```java
package lab7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private MessageService service;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Spring Boot app says: " + service.getMessage());
    }
}

@Component
class MessageService {
    public String getMessage() {
        return "Hello from Spring Boot";
    }
}
```

- Notice that Spring is instantiating a MessageService bean automatically

## Part 3: Build and Run

- At the terminal in the `simple-boot-app` directory
- Run `gradle build`

```console
$gradle build

BUILD SUCCESSFUL in 737ms
4 actionable tasks: 4 executed

```

- Run `gradle bootRun`

```consol
 gradle bootRun

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

2025-06-26T19:27:01.524-04:00  INFO 3863657 --- [           main] lab7.Application                         : Starting Application using Java 21.0.6 with PID 3863657 (/home/rod/working/simple-boot-app/build/classes/java/main started by rod in /home/rod/working/simple-boot-app)
2025-06-26T19:27:01.526-04:00  INFO 3863657 --- [           main] lab7.Application                         : No active profile set, falling back to 1 default profile: "default"
2025-06-26T19:27:01.753-04:00  INFO 3863657 --- [           main] lab7.Application                         : Started Application in 0.375 seconds (process running for 0.508)
Spring Boot app says: Hello from Spring Boot

BUILD SUCCESSFUL in 1s
3 actionable tasks: 1 executed, 2 up-to-date
```

## End Lab