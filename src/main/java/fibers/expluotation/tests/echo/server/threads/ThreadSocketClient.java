package fibers.expluotation.tests.echo.server.threads;

import fibers.expluotation.tests.echo.server.SocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ThreadSocketClient {
    private final static String greeting = "Hello, newborn fiber! ";

    private final static Logger logger = LoggerFactory.getLogger("ThreadSocketClient");

    public static void main(String[] args) {
        int threadnum = Integer.parseInt(args[0]);
        int iters = Integer.parseInt(args[1]);


        SocketAddress address = new InetSocketAddress("localhost", 8080);
        List<Thread> clients = new ArrayList<>();

        for (int i = 0; i < threadnum; i++) {
            logger.info("client "+ i + " started");
            String num = Integer.toString(i);
            for (int j = 0; j < iters; j++) {
                int finalJ = j;
                Thread thread = new Thread(() -> SocketUtils.sendTo(address, greeting , num, finalJ));
                clients.add(thread);
                thread.start();
            }
        }
        clients.forEach((ThreadSocketClient::joinThread));
        logger.info("client end");
    }

    private static void joinThread(Thread c) {
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
