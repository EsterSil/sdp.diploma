package fibers.expluotation.tests.echo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketUtils {

    public static void echoClient(SocketChannel channel) {
        try (channel) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
            int n = channel.read(byteBuffer);
            if (n != -1) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    channel.write(byteBuffer);
                }
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendTo(SocketChannel socketChannel, String message) {
        byte[] bytes = message.getBytes();
        try (socketChannel) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.clear();
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            if (socketChannel.isConnected()) {
                int n = socketChannel.read(byteBuffer);
                if (n > 0) {
                    System.out.println(new String(byteBuffer.array()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
