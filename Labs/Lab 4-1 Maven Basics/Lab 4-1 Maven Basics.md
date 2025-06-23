# Lab 4-1 Maven Basics

## Lab Objectives 

In this lab, you will use Maven to generate and run a basic Hello World Application

## Part 1: Setup

- First, confirm that Maven is installed on your computer by running the following command at a terminal. 
- Note, the lab was prepared on a Linux computer so your output many look a bit different on Windows or MacOS.
- The important part of this step is that the `mvn` command is recognized.
- Note the versions of Java and your default platform encoding.
- In this case, the version of Java in use is Java 21 and the platform encoding is UTF-8

```console
$ mvn -v
Apache Maven 3.9.6 (Red Hat 3.9.6-7)
Maven home: /usr/share/maven
Java version: 21.0.6, vendor: Red Hat, Inc., runtime: /usr/lib/jvm/java-21-openjdk
Default locale: en_CA, platform encoding: UTF-8
OS name: "linux", version: "6.14.3-200.fc41.x86_64", arch: "amd64", family: "unix"

```

- If Maven is not installed on your computer, download it and follow the installation instructions here [Maven Download and Install](https://maven.apache.org/install.html)

- Ensure that your javac and java versions are the same. 
- It can  happen that there are several versions of Java installed and the java JVM is using a version older than the compiler tools javac are using.

```console
$ javac -version
javac 21.0.6
$ java -version
openjdk version "21.0.6" 2025-01-21
OpenJDK Runtime Environment (Red_Hat-21.0.6.0.7-1) (build 21.0.6+7)
OpenJDK 64-Bit Server VM (Red_Hat-21.0.6.0.7-1) (build 21.0.6+7, mixed mode, sharing)

```

## Part 2: Set up the project

- Create a new directory to run the project in
- In the example code shown, it is `/working/maven`
- Copy the file `start.pom.xml` from the lab `Assets` folder to the directory you just created
- Rename the file to `pom.xml`

- Your directory should look like this:

```console
$ ls -l
total 4
-rwxr--r--+ 1 rod rod 426 Nov 16  2022 pom.xml
```

## Part 3: Examine the pom.xml file

- Open the pom.xml file in VS Code.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>exgnosis</groupId>
    <artifactId>minimal</artifactId>
    <version>DEMO.1</version>

</project>
```

- You can change the groupId and artifactID to other values if you want.
- The groupID can be thought of as being like a Java top level package
- The applicationID can be thought of as the name of the application.

## Part 4: Run Maven validate

- The Maven validate commands checks to see that the pom is a valid xml file. 
- At the terminal, run `mvn validate` to ensure your pom file is good to go.


```console
 mvn  validate
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< exgnosis:minimal >--------------------------
[INFO] Building minimal DEMO.1
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.455 s
[INFO] Finished at: 2025-06-23T12:27:46-04:00
[INFO] ------------------------------------------------------------------------

```

- A common error is to have blank lines at the start of the pom.xml file
- These will produce errors like this

```console
[ERROR] [ERROR] Some problems were encountered while processing the POMs:
[FATAL] Non-parseable POM /home/rod/working/maven/pom.xml: processing instruction can not have PITarget with reserved xml name (position: START_DOCUMENT seen \r\n\r\n<?xml ... @3:7)  @ line 3, column 7
```

- Invalid your pom file by removing the last `</project>` tag, then rerun `mvn validate` to see how Maven responds to an invalid pom file.
- You should see the following

```console
$ mvn  validate
[INFO] Scanning for projects...
[ERROR] [ERROR] Some problems were encountered while processing the POMs:
[FATAL] Non-readable POM /home/rod/working/maven/pom.xml: no more data available - expected end tag </project> to close start tag <project> from line 2, parser stopped on END_TAG seen ...<version>DEMO.1</version>\r\n\r\n... @11:1 @ 
 @ 
[ERROR] The build could not read 1 project -> [Help 1]
[ERROR]   
[ERROR]   The project  (/home/rod/working/maven/pom.xml) has 1 error
[ERROR]     Non-readable POM /home/rod/working/maven/pom.xml: no more data available - expected end tag </project> to close start tag <project> from line 2, parser stopped on END_TAG seen ...<version>DEMO.1</version>\r\n\r\n... @11:1
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/ProjectBuildingException

```

- Replace the tag and rerun `mvn validate` to ensure your pom file is valid once again.
- 
## Part 5: Creating a jar file

- By default, Maven produces a jar file when `mvn package` is run. 
- The jar file is not a stand-alone jar file (stand-alone jar files are also called fat jars file or uber-jar files). U
  - ber-jar files have all the dependencies, sometimes even application servers, bundled together in the jar file. 
  - The Maven default is not to include the dependencies in the jar file. I
- In the next lab, you will see how to create an uber-jar file.
- The result of the mvn packaging produces a target directory that contains the jar file. 
- Confirm this for yourself by running `mvn package`

- What you might see, if they are not cached locally on your system is a whole lot of files being downloaded which Maven needs to execute this command.

```console
$ mvn package
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< exgnosis:minimal >--------------------------
[INFO] Building minimal DEMO.1
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-compiler-plugin/3.11.0/maven-compiler-plugin-3.11.0.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-compiler-plugin/3.11.0/maven-compiler-plugin-3.11.0.pom (9.8 kB at 8.8 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-compiler-plugin/3.11.0/maven-compiler-plugin-3.11.0.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-compiler-plugin/3.11.0/maven-compiler-plugin-3.11.0.jar (66 kB at 597 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-surefire-plugin/3.2.2/
```

- If you run `mvn package` a second time, you won't see these because they are now cached locally.

```console
 mvn package
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< exgnosis:minimal >--------------------------
[INFO] Building minimal DEMO.1
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ minimal ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /home/rod/working/maven/src/main/resources
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ minimal ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ minimal ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /home/rod/working/maven/src/test/resources
[INFO] 
[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ minimal ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.2.2:test (default-test) @ minimal ---
[INFO] No tests to run.
[INFO] 
[INFO] --- jar:3.3.0:jar (default-jar) @ minimal ---
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.535 s
[INFO] Finished at: 2025-06-23T13:22:38-04:00
[INFO] ------------------------------------------------------------------------

```

` There are two warnings. 
- The second is just Maven telling us that there is nothing in the jar file since it didn’t find any code to compile.
- But it still created the jar file

```java
$ ls -l target
total 4
drwxr-xr-x. 1 rod rod   28 Jun 23 13:19 maven-archiver
-rw-r--r--. 1 rod rod 1291 Jun 23 13:19 minimal-DEMO.1.jar
```

- The first warning is telling us that the character encoding has not been specified and so the default encoding of your machine is used. 
- This can create cross-platform problems if you are importing a project that has used a different encoding that the default on your machine.
- To fix this, you need to add a property element that tells Maven what encoding to use regardless of the default platform encoding. 
- The pom with this addition is in the lab assets folder in the repo with the name `final.pom.xml`.

## Part 6: Cleaning the build

- Before starting this section, confirm that you have a jar file in the target directory. 
- The Maven clean command removes the target directory to clean out all the results of a previous build, including any of the intermediate files that may have been generated. 
- Run the `mvn clean` command as shown below, and then verify that the target directory no longer exists.


```console
$ mvn clean
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< exgnosis:minimal >--------------------------
[INFO] Building minimal DEMO.1
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.2.0:clean (default-clean) @ minimal ---
[INFO] Deleting /home/rod/working/maven/target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.138 s
[INFO] Finished at: 2025-06-23T14:06:03-04:00
[INFO] ------------------------------------------------------------------------

```

## End Lab