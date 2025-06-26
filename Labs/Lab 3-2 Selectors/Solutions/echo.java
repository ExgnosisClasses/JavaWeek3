import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

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
