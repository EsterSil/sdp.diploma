package fibers.expluotation.tests.dummy;

import java.util.concurrent.SynchronousQueue;

public class FiberProducerConsumerTest {
    public static void main(String[] args) throws Exception {
        var queue = new SynchronousQueue<Integer>();
        Fiber.schedule(() -> {
            int value = 0;
            while (true) {
                queue.put(value++);
            }
        });


        var result = Fiber.schedule(() -> {
            long total = 0L;
            for (int i = 0; i < 5; i++) {
                int value = queue.take();
                System.out.println(value);
                total += value;
            }
            return total;
        }).toFuture().get();
        System.out.println("result " + result);
    }
}
