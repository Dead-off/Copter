package engine;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.concurrent.atomic.AtomicInteger;

public class SoftwarePWMEmulator {

    private final Thread thread;
    private final AtomicInteger value = new AtomicInteger();

    private final int CYCLE_MCS = 20000;
    private final int CYCLE_NANOS = CYCLE_MCS*1000;

    private final int DELAY_SHIFT_MS = 2;

    public SoftwarePWMEmulator(final GpioPinDigitalOutput pin) {
        this.value.set(5);
        pin.low();
        thread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    int pulseDuration = value.get() * CYCLE_MCS;
                    pin.high();
                    mwait(System.nanoTime() + pulseDuration);
                    pin.low();
                    long deadLine = (CYCLE_NANOS - pulseDuration) + System.nanoTime();
                    long availableMsDelay = (CYCLE_NANOS - pulseDuration) / 1000000 - DELAY_SHIFT_MS;
                    if (availableMsDelay > 0) {
                        Thread.sleep(availableMsDelay);
                    }
                    mwait(deadLine);
                }
            } catch (InterruptedException e) {
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
