package fibers.expluotation.tests.echo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class SocketUtils {
    private final static Logger logger = LoggerFactory.getLogger("Utils");

    public static void handleClient(SocketChannel channel) {
        try (channel) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
            //while(true) {
                int n = channel.read(byteBuffer);
                if (n != -1) {
                    byteBuffer.flip();
                    process(byteBuffer);
                    byteBuffer.clear();
//                    while (byteBuffer.hasRemaining()) {
//                        channel.write(byteBuffer);
//                    }
                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void process(ByteBuffer byteBuffer) {
        logger.info("got " + Arrays.toString(byteBuffer.array()));
    }

    public static void sendTo(SocketAddress address, String message, String client, int j ) {
        byte[] bytes = (message + "client "+client +" sent " +j).getBytes();
        try (SocketChannel socketChannel = SocketChannel.open(address)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.clear();
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            logger.info("client "+client +" sent " +j);
            byteBuffer.clear();
            if (socketChannel.isConnected()) {
                //int n = socketChannel.read(byteBuffer);
                //if (n > 0) {
                    logger.info("client "+client +" got " + j);
                //}else {
                  //  logger.info("client " + client + " lost " + j);
                //}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
