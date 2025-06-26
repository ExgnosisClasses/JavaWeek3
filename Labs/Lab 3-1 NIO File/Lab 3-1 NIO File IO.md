# Lab 3-1: Reading and writing a file with NIO

## Part 1: Reading a file

- For this section, we will be using the file `SampleText.txt` located in the `Assets` folder

### Step 1: Create the buffers and channels

- Create the channel and buffer
- Note that we are a try-with-resources block so that if the program ends for any reason, the file will be closed automatically.
- Once we have a rando access file, we need to wrap it in a channel so we can use nio
- We also allocate a buffer to use with the channel

```java
  public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("SampleText.txt", "r");
             FileChannel channel = file.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024); // 1KB buffer
           // ...
        } catch (Exception e) {
            e.printStackTrace();
        }

```


### Step 2: Read the file

- Now we just start a while loop and
  - Read a buffer full of data
  - Flip the buffer pointer so we are pointing at the start of the buffer
  - Print the buffer contents
  - And so one until we reach the end of the file

```java
public class NIOFileReadExample {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("SampleText.txt", "r");
             FileChannel channel = file.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024); // 1KB buffer

            while (channel.read(buffer) > 0) {
                buffer.flip(); // Switch from write to read mode
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear(); // Prepare for the next read
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

- Run the code and compare the output with the file contents

## Part 2: Write a file

- In this section, you will write a string to file using NIO.

- First, set up the String and buffer to be used

```java
public class NIOWriteExample  {
    public static void main(String[] args) {
        String text = "Java Bootcamp is more fun than a party";
        ByteBuffer buffer = ByteBuffer.allocate(128);
```

- Then put the sting into the buffer.
- Since this is a buffer of bytes, we need to convert the Java String object to an array of bytes
- Once we do that, we flip() the buffer so that we are pointing at the first element

```java
ublic class NIOWriteExample  {
  public static void main(String[] args) {
    String text = "Java Bootcamp is more fun than a party";
    ByteBuffer buffer = ByteBuffer.allocate(128);
    buffer.put(text.getBytes(StandardCharsets.UTF_8));

    buffer.flip();
```

- Then create the output file and channel, and write the buffer.

```java
public class NIOWriteExample  {
    public static void main(String[] args) {
        String text = "Java Bootcamp is more fun than a party";
        ByteBuffer buffer = ByteBuffer.allocate(128);
        buffer.put(text.getBytes(StandardCharsets.UTF_8));

        buffer.flip();
        
        try (FileOutputStream f = new FileOutputStream("Output.txt")) {
            FileChannel ch = f.getChannel();
            ch.write(buffer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

```

## Challenge

- Create a program that uses NIO to copy a file.

## End Lab
