package fibers.expluotation.tests.echo.server.threads;

import fibers.expluotation.tests.echo.server.SocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ThreadSocketServer {

    private static boolean isInterrupted = false;
    private final static Logger logger = LoggerFactory.getLogger("ThreadSocketServer");

    public static void main(String[] args) throws IOException {
        logger.info("Server started");

        ServerSocketChannel listener = getServerSocketChannel(args[0], Integer.parseInt(args[1]));
        Thread serverThread = new Thread(() -> handle(listener));
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ServerSocketChannel getServerSocketChannel(String address, int port) throws IOException {
        var listener = ServerSocketChannel.open();
        listener.bind(new InetSocketAddress(address, port));
        return listener;
    }

    private static void handle(ServerSocketChannel listener) {
        while (!isInterrupted) {
            try {
                SocketChannel client = listener.accept();
                logger.info("connection accepted");
                Thread thread = new Thread(() -> SocketUtils.handleClient(client));
                thread.start();
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