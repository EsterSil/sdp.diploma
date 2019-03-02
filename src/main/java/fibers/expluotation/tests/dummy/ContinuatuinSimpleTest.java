package fibers.expluotation.tests.dummy;

public class ContinuatuinSimpleTest {
    public static void main(String[] args) {
        ContinuationScope scope = new ContinuationScope("test");
        Continuation continuation = new Continuation(scope, () -> {
            System.out.println("A");
            Continuation.yield(scope);
            System.out.println("B");
            Continuation.yield(scope);
            System.out.println("C");

        });
        while (!continuation.isDone()) {
            System.out.println("RUN");
            continuation.run();
        }
    }
}
