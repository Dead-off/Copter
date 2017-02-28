import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws Exception {
//        List<Integer> iters = new ArrayList<>();
//        Lock l = new ReentrantLock();
//        l.lock();
//        for (int i = 0; i < 2; i++) {
//            AtomicInteger atomicInteger = new AtomicInteger();
//            Thread t = new Thread(() -> {
//                while (true) {
//                    atomicInteger.incrementAndGet();
//                    try {
////                        Thread.sleep(1, 600000);
//
//                        l.tryLock(800, TimeUnit.MICROSECONDS);//it work
//                    } catch (InterruptedException e) {
//                        return;
//                    }
//                    if (Thread.interrupted()) {
//                        return;
//                    }
//                }
//            });
//
//            t.start();
//            Thread.sleep(5000);
//            t.interrupt();
//            t.join();
//            iters.add(atomicInteger.get());
//        }
//        System.out.println(iters);
    }
}
