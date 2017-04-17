package engine;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.wiringpi.Gpio;

import static com.pi4j.wiringpi.Gpio.PWM_MODE_MS;

public class PiPWMEngine implements Engine {

    private final static int RANGE = 1000;
    private final static int ZERO_POWER = RANGE / 20;
    private static final GpioController GPIO = GpioFactory.getInstance();

    static {
        Gpio.pwmSetClock(19200000 / (RANGE * 50));//19.2e6/(clock*range) - hz pwm output
        Gpio.pwmSetMode(PWM_MODE_MS);
    }

    private final GpioPinPwmOutput pin;

    public PiPWMEngine(GpioPinPwmOutput pin) {
        this.pin = pin;
        this.pin.setMode(PinMode.PWM_OUTPUT);
        this.pin.setPwmRange(RANGE);
        setPower(0);
    }

    @Override
    public void setPower(double power) {
        int pwmImpulseDuration = (int) Math.round(ZERO_POWER * power + ZERO_POWER);
        this.pin.setPwm(pwmImpulseDuration);
    }

    @Override
    public void shutDown() {
        GPIO.unprovisionPin(pin);
    }
}
