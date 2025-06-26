# Lab 3-2: NIO Selectors

## Lab Objectives

- In this lab, you will create an echo server
- This will listen on port 5000 and accept any number of connections
- For each connection, the server will listen for new lines of text
- Then it will write the text back to the connection

## Lab Setup

- You will need telnet to do this lab. 
- Check to see if you have telnet

```console
$ telnet
telnet> 
telnet> ^C
$
```

- If you don't have telnet installed, check to see how to install it or enable it on your system.

## Part 1: Set up the Server

- There are a number of steps to start the server.
- The code below documents the steps you need to follow to create the initialization logic

```java
public class EchoServerWithSelector {
    public static void main(String[] args) throws IOException {

        // Opens a Selector
        Selector selector = Selector.open();

        // Opens a ServerSocketChannel,  a NIO equivalent of a server socket
        // used to listen for incoming TCP connections.
        ServerSocketChannel serverSocket = ServerSocketChannel.open();

        // Listens on port 5000
        serverSocket.bind(new InetSocketAddress(5000));

        // Sets the channel to non-blocking mode.
        serverSocket.configureBlocking(false);

        // Tells the selector to look for incoming connection requests.
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Echo server started on port 5000...");

        // Allocate the buffer
        ByteBuffer buffer = ByteBuffer.allocate(256);
```

## Part 3: The event loop

- This is the heart of the application where the echo server is waiting for incoming text
- Added here with explanations of what each line does

```java
public class EchoServerWithSelector {
    public static void main(String[] args) throws IOException {

        // Opens a Selector
        Selector selector = Selector.open();

        // Opens a ServerSocketChannel,  a NIO equivalent of a server socket
        // used to listen for incoming TCP connections.
        ServerSocketChannel serverSocket = ServerSocketChannel.open();

        // Listens on port 5000
        serverSocket.bind(new InetSocketAddress(5000));

        // Sets the channel to non-blocking mode.
        serverSocket.configureBlocking(false);

        // Tells the selector to look for incoming connection requests.
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Echo server started on port 5000...");

        // Allocate the buffer
        ByteBuffer buffer = ByteBuffer.allocate(256);

        // This line blocks until at least one channel is ready for an I/O operation 
        // (e.g., a new connection or incoming data).
        while (true) {
            selector.select(); // blocking until at least one event
            
            // Retrieves the set of keys representing the channels that are ready for some operation.
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();


            while (iter.hasNext()) {
                SelectionKey key = iter.next();

                // If the key is "acceptable", that means a new client connection is waiting on the ServerSocketChannel.
                // register(...) accepts the client socket, configures it as non-blocking, and 
                // registers it with the selector for read events (OP_READ).
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                }

                // If the key is "readable", that means a client has sent data, and it's ready to be read.
                // echoData(...) will read the data from the client channel and echo it back using the shared buffer.
                if (key.isReadable()) {
                    echoData(key, buffer);
                }

                // Removes the current key from the set of selected keys
                // to prevent redundant handling.
                iter.remove(); 
            }
        }
    }
```

## Part 4: Register and Echo

- These are two methods that register the client and echo the data
- Following is the line by line breakdown


```java
   private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {

        // Accepts the incoming client connection.
        // Returns a SocketChannel that represents this connection.
        // Configures the SocketChannel to be non-blocking.
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);

        // Registers the new client channel with the selector, 
        // asking to be notified when the channel is ready to read data.
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("New client connected: " + client.getRemoteAddress());
    }

    private static void echoData(SelectionKey key, ByteBuffer buffer) throws IOException {

        // Retrieves the SocketChannel associated with the key.
        // This is the channel that is ready for reading.
        SocketChannel client = (SocketChannel) key.channel();

        // Resets the buffer to prepare for reading new data.
        //Reads bytes from the client into the buffer.
        //Returns the number of bytes read.
        buffer.clear();
        int read = client.read(buffer);
        
        // A return value of -1 means the client has closed the connection.
        // The channel is closed, and the function returns.
        if (read == -1) {
            client.close();
            System.out.println("Client disconnected.");
            return;
        }

        // Flips the buffer from write mode to read mode so its contents can be sent back to the client.
        // Writes the buffer’s contents back to the client — this is the "echo".
        buffer.flip();
        client.write(buffer);
    }
}

```

## Part 5: Run and test

- Start the server by running the class
- You should see the message on the terminal in VS Code

```console
Echo server started on port 5000...
```

- Open a terminal and connect to the server using telnet
- Enter a few lines of text and see it echo

```console
$ telnet localhost 5000
Trying ::1...
Connected to localhost.
Escape character is '^]'.
This is client 1
This is client 1
```

- Open another terminal and do the same

```console
$ telnet localhost 5000
Trying ::1...
Connected to localhost.
Escape character is '^]'.
This is client 2
This is client 2
```

- Check the output of the terminal in VS Code.

```console
Echo server started on port 5000...
New client connected: /[0:0:0:0:0:0:0:1]:59610
New client connected: /[0:0:0:0:0:0:0:1]:51482
```

- Close each client terminal window to disconnect from the client from the server.
- Check the VS Code terminal

```console
Echo server started on port 5000...
New client connected: /[0:0:0:0:0:0:0:1]:59610
New client connected: /[0:0:0:0:0:0:0:1]:51482
Client disconnected.
Client disconnected.
```

- End the server program.  

## End Lab

