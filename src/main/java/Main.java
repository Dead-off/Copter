import bootstrap.Arguments;
import bootstrap.ArgumentsParser;
import com.martiansoftware.jsap.JSAPException;
import control.UserController;
import facade.Copter;
import facade.QuadCopter;
import bootstrap.FactoryConfigurator;
import bootstrap.MainFactory;

public class Main {

    public static void main(String[] args) {
        Arguments arguments;
        try {
            arguments = new ArgumentsParser().parse(args);
        } catch (JSAPException e) {
            System.out.println("incorrect arguments format");
            e.printStackTrace();
            return;
        }

        FactoryConfigurator configurator = new FactoryConfigurator(MainFactory.INSTANCE, arguments);
        try {
            configurator.configure();
        } catch (IllegalArgumentException e) {
            System.out.println("error in cl args, check helps");
            e.printStackTrace();
            return;
        }
        UserController userController = MainFactory.INSTANCE.getUserController();
        Copter copter = new QuadCopter();
        copter.init();
        userController.setCopter(copter);
        userController.run();


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
