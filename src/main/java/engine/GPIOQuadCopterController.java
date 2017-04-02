package engine;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

import static com.pi4j.wiringpi.Gpio.PWM_MODE_MS;

public class GPIOQuadCopterController implements CopterController {

    private final static int DEFAULT_RANGE = 1000;
    private final static int ZERO_POWER = DEFAULT_RANGE / 20;

    private final GpioController GPIO;
    private final GpioPinPwmOutput leftFrontPin;
    private final GpioPinPwmOutput rightBackPin;
    private final GpioPinDigitalOutput leftBackPinOrigin;
    private final GpioPinDigitalOutput rightFrontPinOrigin;

    private final SoftwarePWMEmulator leftBackPin;
    private final SoftwarePWMEmulator rightFrontPin;

    GPIOQuadCopterController(GpioController GPIO) {
        this.GPIO = GPIO;
        this.leftFrontPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26);
        this.rightBackPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_16);
        this.leftBackPinOrigin = GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_29);
        this.rightFrontPinOrigin = GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_23);
        this.leftBackPin = new SoftwarePWMEmulator(this.leftBackPinOrigin);
        this.rightFrontPin = new SoftwarePWMEmulator(this.rightFrontPinOrigin);

        init();
    }

    public GPIOQuadCopterController() {
        this(GpioFactory.getInstance());
    }

    private void init() {

        this.leftFrontPin.setMode(PinMode.PWM_OUTPUT);
        this.leftFrontPin.setPwmRange(DEFAULT_RANGE);
        this.leftFrontPin.setPwm(ZERO_POWER);

        this.rightBackPin.setMode(PinMode.PWM_OUTPUT);
        this.rightBackPin.setPwmRange(DEFAULT_RANGE);
        this.rightBackPin.setPwm(ZERO_POWER);

        this.leftBackPin.setPwm(ZERO_POWER);
        this.rightFrontPin.setPwm(ZERO_POWER);

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
        this.leftBackPin.close();
        this.rightFrontPin.close();
        GPIO.shutdown();
        GPIO.unprovisionPin(leftFrontPin);
        GPIO.unprovisionPin(leftBackPinOrigin);
        GPIO.unprovisionPin(rightFrontPinOrigin);
        GPIO.unprovisionPin(rightBackPin);
    }
}
