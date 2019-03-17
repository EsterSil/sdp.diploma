package fibers.expluotation.tests.echo.server.fibers;

import fibers.expluotation.tests.echo.server.SocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class FiberSocketClient {
    private final static String greeting = "";
    private final static Logger logger = LoggerFactory.getLogger("FiberSocketClient");
    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("localhost", Integer.parseInt(args[0]));

        List<Fiber<?>> fibers = new ArrayList<>();
        int bound = Integer.parseInt(args[1]);
        for (int i = 0; i < bound; i++) {
            logger.info("client "+ i + " started");

            String num = Integer.toString(i);
            for (int j = 0; j < Integer.parseInt(args[2]); j++) {
                int finalJ = j;
                Fiber schedule = Fiber.schedule(() -> {
                    SocketUtils.sendTo(address, greeting, num, finalJ);
                });
                fibers.add(schedule);
            }
        }
        fibers.forEach(Fiber::awaitTermination);
        logger.info("client end");
    }
}
