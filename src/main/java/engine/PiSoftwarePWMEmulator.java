package engine;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.concurrent.atomic.AtomicInteger;

public class PiSoftwarePWMEmulator implements Engine {

    private final Thread thread;
    private final AtomicInteger value = new AtomicInteger();

    private final static int CYCLE_MCS = 20000;
    private final static GpioController GPIO = GpioFactory.getInstance();
    private final static int CYCLE_NANOS = CYCLE_MCS * 1000;
    private final static int ZERO_POWER = 1000000 / CYCLE_MCS;

    private final int DELAY_SHIFT_MS = 2;

    public PiSoftwarePWMEmulator(final GpioPinDigitalOutput pin) {
        this.setPower(0);
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
            GPIO.unprovisionPin(pin);
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

    @Override
    public void setPower(double power) {
        value.set((int) Math.round(ZERO_POWER * power + ZERO_POWER));
    }

    @Override
    public void shutDown() {
        thread.interrupt();
    }
}
