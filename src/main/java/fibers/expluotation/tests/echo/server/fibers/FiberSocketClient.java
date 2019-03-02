package fibers.expluotation.tests.echo.server.fibers;

import fibers.expluotation.tests.echo.server.SocketUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FiberSocketClient {
    private final static String greeting = "Hello, newborn fiber! ";

    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("localhost", 9090);

        List<Fiber<?>> fibers = IntStream.range(0, 200)
                .mapToObj(i -> Fiber.schedule(() -> {
                    try {
                        SocketChannel socketChannel = SocketChannel.open(address);
                        SocketUtils.sendTo(socketChannel, greeting + i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }))
                .collect(Collectors.toList());
        fibers.forEach(Fiber::awaitTermination);
    }
}
