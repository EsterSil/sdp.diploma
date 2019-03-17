package fibers.expluotation.tests.echo.server.fibers;

import fibers.expluotation.tests.echo.server.SocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class FibersSocketServer {


    private final static Logger logger = LoggerFactory.getLogger("FiberSocketServer");
    private static boolean isInterrupted = false;

    public static void main(String[] args) throws IOException {
        var listener = ServerSocketChannel.open();
        logger.info("Server started");
        listener.bind(new InetSocketAddress(args[0], Integer.parseInt(args[1])));
        Fiber fiber = Fiber.schedule(() -> handle(listener));
        fiber.awaitTermination();

    }

    private static void handle(ServerSocketChannel listener) {
        while (!isInterrupted) {
            try {
                SocketChannel client = listener.accept();
                logger.info("connection accepted");
                Fiber.schedule(() -> SocketUtils.handleClient(client));
            } catch (ClosedChannelException e) {
                logger.error("listener closed");
                isInterrupted = true;
            } catch (IOException e) {
                e.printStackTrace();
                isInterrupted = true;
            }
        }
    }

}