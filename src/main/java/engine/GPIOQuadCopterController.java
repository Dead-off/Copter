package engine;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import util.QuadEnginePowerContainer;

import java.util.concurrent.atomic.AtomicInteger;

import static com.pi4j.wiringpi.Gpio.PWM_MODE_MS;

public class GPIOQuadCopterController implements CopterController {

    private final static int DEFAULT_RANGE = 1000;
    private final static int ZERO_POWER = DEFAULT_RANGE / 20;

    private final GpioController GPIO;
    private final SoftwarePWMEmulator leftFrontPin;
    private final SoftwarePWMEmulator rightFrontPin;

    private final GpioPinPwmOutput leftBackPin;
    private final GpioPinPwmOutput rightBackPin;

    GPIOQuadCopterController(GpioController GPIO) {
        this.GPIO = GPIO;
        this.rightBackPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26);
        this.leftBackPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_23);
        this.leftFrontPin = new SoftwarePWMEmulator(GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_15), GPIO);
        this.rightFrontPin = new SoftwarePWMEmulator(GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_29), GPIO);

        init();
    }

    public GPIOQuadCopterController() {
        this(GpioFactory.getInstance());
    }

    private void init() {

        this.leftBackPin.setMode(PinMode.PWM_OUTPUT);
        this.leftBackPin.setPwmRange(DEFAULT_RANGE);
        this.leftBackPin.setPwm(ZERO_POWER);

        this.rightBackPin.setMode(PinMode.PWM_OUTPUT);
        this.rightBackPin.setPwmRange(DEFAULT_RANGE);
        this.rightBackPin.setPwm(ZERO_POWER);

        this.leftBackPin.setPwm(ZERO_POWER);
        this.rightBackPin.setPwm(ZERO_POWER);

        Gpio.pwmSetMode(PWM_MODE_MS);
        Gpio.pwmSetClock(19200000 / (DEFAULT_RANGE * 50));//19.2e6/(clock*range) - hz pwm output
    }

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {
        int v = (int) Math.round(ZERO_POWER * powerContainer.getLeftFrontEnginePower() + ZERO_POWER);
        this.leftFrontPin.setPwm(calculateValue(powerContainer.getLeftFrontEnginePower()));
        this.leftBackPin.setPwm(calculateValue(powerContainer.getLeftBackEnginePower()));
        this.rightFrontPin.setPwm(calculateValue(powerContainer.getRightFrontEnginePower()));
        this.rightBackPin.setPwm(calculateValue(powerContainer.getRightBackEnginePower()));
    }

    private int calculateValue(double enginePower) {
        return (int) Math.round(ZERO_POWER * enginePower + ZERO_POWER);
    }

    @Override
    public void close() {
        this.leftFrontPin.close();
        this.rightFrontPin.close();
        GPIO.shutdown();
        GPIO.unprovisionPin(rightBackPin);
        GPIO.unprovisionPin(leftBackPin);
    }

    public static class SoftwarePWMEmulator {

        private final Thread thread;
        private final AtomicInteger value = new AtomicInteger();
        private final GpioController GPIO;
        private final GpioPinDigitalOutput pin;

        private final int CYCLE_MCS = 20000;
        private final int CYCLE_NANOS = CYCLE_MCS * 1000;

        private final int DELAY_SHIFT_MS = 2;

        public SoftwarePWMEmulator(final GpioPinDigitalOutput pin, final GpioController GPIO) {
            this.pin = pin;
            this.GPIO = GPIO;
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
            GPIO.unprovisionPin(pin);
        }

        void setPwm(int v) {
            value.set(v);
        }
    }
}
