package engine;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.concurrent.atomic.AtomicInteger;

public class SoftwarePWMEmulator {

    private final Thread thread;
    private final AtomicInteger value = new AtomicInteger();

    private final int cycle = 20000;//microsec

    public SoftwarePWMEmulator(final GpioPinDigitalOutput pin) {
        this.value.set(5);
        pin.low();
        thread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    int pulseDuration = value.get() * 19000;
                    pin.high();
                    mwait(System.nanoTime() + pulseDuration);
                    pin.low();
                    long deadLine = (cycle-pulseDuration)*1000 + System.nanoTime();
                    long msDelay = (cycle-pulseDuration)/1000;
                    if (msDelay - 2 > 0) {
                        Thread.sleep(msDelay - 2);
                    }
                    mwait(deadLine);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            pin.low();
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        this.thread.start();
    }

    private void mwait(final long deadline) {
        while (true) {
            if (System.nanoTime() >= deadline) {
                return;
            }
        }
    }

    void close() {
        thread.interrupt();
    }

    void setPwm(int v) {
        value.set(v);
    }
}
