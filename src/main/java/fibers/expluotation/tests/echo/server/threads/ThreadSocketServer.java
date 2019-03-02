package fibers.expluotation.tests.echo.server.threads;

import fibers.expluotation.tests.echo.server.SocketUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ThreadSocketServer {

    private static boolean isInterrupted = false;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel listener = getServerSocketChannel();
        Thread serverThread = new Thread(() -> handle(listener));
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ServerSocketChannel getServerSocketChannel() throws IOException {
        var listener = ServerSocketChannel.open();
        listener.bind(new InetSocketAddress("localhost", 8080), 500);
        return listener;
    }

    private static void handle(ServerSocketChannel listener) {
        while (!isInterrupted) {
            try {
                SocketChannel client = listener.accept();
                Thread thread = new Thread(() -> SocketUtils.echoClient(client));
                thread.start();
            } catch (ClosedChannelException e) {
                System.out.println("listener closed");
            } catch (IOException e) {
                e.printStackTrace();
                isInterrupted = true;
            }
        }
    }


}