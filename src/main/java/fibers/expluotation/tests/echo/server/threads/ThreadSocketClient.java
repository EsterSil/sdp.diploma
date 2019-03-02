package fibers.expluotation.tests.echo.server.threads;

import fibers.expluotation.tests.echo.server.SocketUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class ThreadSocketClient {
    private final static String greeting = "Hello, newborn fiber! ";

    public static void main(String[] args) {

        SocketAddress address = new InetSocketAddress("localhost", 8080);
        List<Thread> clients = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            String num = Integer.toString(i);
            try {
                SocketChannel socketChannel = SocketChannel.open(address);
                Thread thread = new Thread(() -> SocketUtils.sendTo(socketChannel, greeting + num));
                clients.add(thread);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error " + i);
            }
        }
        clients.forEach((ThreadSocketClient::joinThread));

    }

    private static void joinThread(Thread c) {
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
