# Lab 5-1: Gradle Hello World

## Lab Objectives

In this lab you will use Gradle to build a simple Hello World Java application

## Lab Setup

- Make sure gradle is installed on your system

```console
$ gradle -version

------------------------------------------------------------
Gradle 8.11
------------------------------------------------------------

Build time:    2024-11-11 13:58:01 UTC
Revision:      b2ef976169a05b3c76d04f0fa76a940859f96fa4

Kotlin:        2.0.20
Groovy:        3.0.22
Ant:           Apache Ant(TM) version 1.10.14 compiled on August 16 2023
Launcher JVM:  21.0.6 (Red Hat, Inc. 21.0.6+7)
Daemon JVM:    /usr/lib/jvm/java-21-openjdk (no JDK specified, using current Java home)
OS:            Linux 6.14.3-200.fc41.x86_64 amd64

```

- If not, install it from here [gradle install](https://gradle.org/install/)


## Part 1: Directory Structure

- Create a directory `hw` to hold the project.
- Create the following directory structure

```text
hw/
└── src/
    └── main/
        └── java/
            └── lab5/
```

## Part 2: Add the code

- In the `lab5` package, add the `HelloWorld.java` file

```java
package lab5;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World from the Gradle build tool!");
    }
}
```

## Part 3: Add the configuration

- First, in the root of the `hw` directory, add the `setting.gradle` file.
- This sets the root project name which is the name of the directory the project is in, not the name of the application
- There is only the single line in the file.

```text
rootProject.name = 'hw'
```

- In the same directory, add the `build.gradle` file with the following contents
- Modify the JavaVersion to match your verison of Java

```text
plugins {
    id 'java'
    id 'application'
}

group = 'lab5'
version = '1.0'

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass = 'lab5.HelloWorld'
}

```

- Your directory structure should now look like this

```text
hw/
├── build.gradle
├── settings.gradle
└── src/
    └── main/
        └── java/
            └── lab5/
                └── HelloWorld.java
```

## Part 4: Build and Run

- Open a terminal in VS Code 
- Make sure you are in the `hw` directory
- Run `gradle build`
- You should see the output showing you that the build worked

```console
~/hw$ gradle build

BUILD SUCCESSFUL in 463ms
5 actionable tasks: 5 executed
```

- Now run with `gradle run`
- You should see the app output

```console
~/hw$ gradle run

> Task :run
Hello World from the Gradle build tool!

BUILD SUCCESSFUL in 401ms
2 actionable tasks: 1 executed, 1 up-to-date
```

- Take a moment to examine the build directory.
- Note what artifacts have been created by Gradle.


## End Lab