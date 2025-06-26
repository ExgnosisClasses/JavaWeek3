import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
