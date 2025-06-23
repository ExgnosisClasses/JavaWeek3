# Lab 4-2: Maven Archetypes

## Lab Objectives

In this lab, you will build a HelloWorld application using a Maven Archetype.


## Lab Setup

- You can use the same directory as the last lab, just empty it out first, including removing the pom.xml file
- Or you can start in a new empty directory

## Part 1: Create the project 

- At the command line, enter the following command that downloads the archetype
- It then creates the project structure, including the starter Java code.
- The command is in the file `command.txt` in the `Assets` folder.

```console
$ mvn archetype:generate -DgroupId=exgnosis -DartifactId=HelloWorld -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-install-plugin/3.1.1/maven-install-plugin-3.1.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-install-plugin/3.1.1/maven-install-plugin-3.1.1.pom (7.8 kB at 32 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-install-plugin/3.1.1/maven-install-plugin-3.1.1.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-install-plugin/3.1.1/maven-install-plugin-3.1.1.jar (31 kB at 977 kB/s)

<< All the rest of the downloads are not shown >>

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
INFO] Generating project in Batch mode
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Old (1.x) Archetype: maven-archetype-quickstart:1.0
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: basedir, Value: /home/rod/working/maven
[INFO] Parameter: package, Value: exgnosis
[INFO] Parameter: groupId, Value: exgnosis
[INFO] Parameter: artifactId, Value: HelloWorld
[INFO] Parameter: packageName, Value: exgnosis
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] project created from Old (1.x) Archetype in dir: /home/rod/working/maven/HelloWorld
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.511 s
[INFO] Finished at: 2025-06-23T14:15:41-04:00
[INFO] ------------------------------------------------------------------------

```

- Once the project is created, note that it has created a new directory `HelloWorld`
- Locate to that directory and open Visual Studio Code

## Part 2: Examine the code

- The archetype populated both the `main` and `test` directories under `src` with starter code.
- The `App.java` code looks like this


```java
package exgnosis;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}

```

- The `AppTest.java` code looks like this

```java
package exgnosis;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}

```

- Add a couple more tests as shown so we can get more interesting output later.

```java
   /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    public void testApp2()
    {
        assertTrue( true );
    }
    public void testApp3()
    {
        assertTrue( true );
    }
}

```

## Part 3: Compile the code

- Make sure you are in the same directory as the pom.xml file and run `mvn compile`

```console
mvn compile
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------< exgnosis:HelloWorld >-------------------------
[INFO] Building HelloWorld 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ HelloWorld ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /home/rod/working/maven/HelloWorld/src/main/resources
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ HelloWorld ---
[INFO] Changes detected - recompiling the module! :input tree
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 1 source file with javac [debug target 1.8] to target/classes
[WARNING] bootstrap class path not set in conjunction with -source 8
[WARNING] source value 8 is obsolete and will be removed in a future release
[WARNING] target value 8 is obsolete and will be removed in a future release
[WARNING] To suppress warnings about obsolete options, use -Xlint:-options.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.467 s
[INFO] Finished at: 2025-06-23T14:36:47-04:00
[INFO] ------------------------------------------------------------------------

```

- If you get the error 
- `source option 5 is no longer supported`
- Then you are probably using an older version of Maven which defaulted to assuming that the code was written to a Java 5 compliance

- However, examining the output, this version of Maven is assuming the source code is written to the Java 8 standard. 
- This could create problems with mispatched Java versions
- And there are the warning you saw before about the encoding

- Rectify these warning by adding the following lines into your pom file
- They are located in `command.txt` file in the `Assets` folder

```xml
 <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>??</maven.compiler.source>
    <maven.compiler.target>??   </maven.compiler.target>
  </properties>
```

- Replace the `??` with the version of Java you are using.
- On this demo machine, that would be `21`
- For your machine, refer to the first lab to see how to find your 
- Java version

- Your pom file should look like this

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>exgnosis</groupId>
  <artifactId>HelloWorld</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>HelloWorld</name>
  <url>http://maven.apache.org</url>
    
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
  </properties>
    
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

- Now the file compiles without errors


```console
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------< exgnosis:HelloWorld >-------------------------
[INFO] Building HelloWorld 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ HelloWorld ---
[INFO] skip non existing resourceDirectory /home/rod/working/maven/HelloWorld/src/main/resources
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ HelloWorld ---
[INFO] Changes detected - recompiling the module! :source
[INFO] Compiling 1 source file with javac [debug target 21] to target/classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.465 s
[INFO] Finished at: 2025-06-23T14:50:16-04:00
[INFO] ------------------------------------------------------------------------

```

## Part 4: Build the Application

- The project is still a Java project, so you can run it by right-clicking on the `App.java` file and selecting `Run Java`
- You will see the output in the VS Code terminal

- Run `mvn clean`
- Then run `mvn test`
- You should see the following

```console
$ mvn test
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------< exgnosis:HelloWorld >-------------------------
[INFO] Building HelloWorld 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ HelloWorld ---
[INFO] skip non existing resourceDirectory /home/rod/working/maven/HelloWorld/src/main/resources
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ HelloWorld ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ HelloWorld ---
[INFO] skip non existing resourceDirectory /home/rod/working/maven/HelloWorld/src/test/resources
[INFO] 
[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ HelloWorld ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- surefire:3.2.2:test (default-test) @ HelloWorld ---
[INFO] Using auto detected provider org.apache.maven.surefire.junit.JUnit3Provider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running exgnosis.AppTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.012 s -- in exgnosis.AppTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.596 s
[INFO] Finished at: 2025-06-23T15:02:43-04:00
[INFO] ------------------------------------------------------------------------
```

- Note that to run the test; Maven knew it had to compile the code
- Change the return value of one of the tests to false and run the tests again

```java
  /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    public void testApp2()
    {
        assertTrue( true );
    }
    // failing test
    public void testApp3()
    {
        assertTrue( false );
    }

```

This shows up as:

```console
INFO] Results:
[INFO] 
[ERROR] Failures: 
[ERROR]   exgnosis.AppTest#testApp3 AssertionFailedError
[INFO] 
[ERROR] Tests run: 3, Failures: 1, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.618 s
[INFO] Finished at: 2025-06-23T15:06:49-04:00
[INFO] ------------------------------------------------------------------------

```

- Fix the test so it passes
- Rerun `mvn test` to confirm

- Now run `mvn package`
- Note that tests are run before the jar file is created

```console
$ mvn package
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------< exgnosis:HelloWorld >-------------------------
[INFO] Building HelloWorld 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ HelloWorld ---
[INFO] skip non existing resourceDirectory /home/rod/working/maven/HelloWorld/src/main/resources
[INFO] 
[INFO] --- compiler:3.11.0:compile (default-compile) @ HelloWorld ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ HelloWorld ---
[INFO] skip non existing resourceDirectory /home/rod/working/maven/HelloWorld/src/test/resources
[INFO] 
[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ HelloWorld ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- surefire:3.2.2:test (default-test) @ HelloWorld ---
[INFO] Using auto detected provider org.apache.maven.surefire.junit.JUnit3Provider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running exgnosis.AppTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.012 s -- in exgnosis.AppTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jar:3.3.0:jar (default-jar) @ HelloWorld ---
[INFO] Building jar: /home/rod/working/maven/HelloWorld/target/HelloWorld-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.716 s
[INFO] Finished at: 2025-06-23T15:08:48-04:00
[INFO] ------------------------------------------------------------------------

```

- Confirm the jar file is there. and record its name
- Try to run the jar file


```console
$ ls target/*jar
target/HelloWorld-1.0-SNAPSHOT.jar

$ java -jar target/HelloWorld-1.0-SNAPSHOT.jar
no main manifest attribute, in target/HelloWorld-1.0-SNAPSHOT.jar
```

The file will not execute because it is not a stand-alone jar file.
- We will fix this in the next lab do not delete your work!

## ENd Lab

```