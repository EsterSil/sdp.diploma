package fibers.expluotation.tests.echo.server.fibers;

import fibers.expluotation.tests.echo.server.SocketUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class FibersSocketServer {


    private static boolean isInterrupted = false;

    public static void main(String[] args) throws IOException {
        var listener = ServerSocketChannel.open();
        listener.bind(new InetSocketAddress("localhost", 9090), 500);
        Fiber fiber = Fiber.schedule(() -> handle(listener));
        fiber.awaitTermination();

    }

    private static void handle(ServerSocketChannel listener) {
        while (!isInterrupted) {
            try {
                SocketChannel client = listener.accept();
                Fiber.schedule(() -> SocketUtils.echoClient(client));
            } catch (ClosedChannelException e) {
                System.out.println("listener closed");
            } catch (IOException e) {
                e.printStackTrace();
                isInterrupted = true;
            }
        }
    }

}