package fibers.expluotation.tests.dummy;

import java.util.concurrent.locks.ReentrantLock;

public class FiberSimpleTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        System.out.println("A");
        Fiber.schedule(() -> {
            lock.lock();
            try {
                System.out.println("B");
            } finally {
                lock.unlock();
            }
            System.out.println("C");
        });
    }
}
