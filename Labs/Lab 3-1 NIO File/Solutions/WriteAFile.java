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
