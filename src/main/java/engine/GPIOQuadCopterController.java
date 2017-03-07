package engine;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

import static com.pi4j.wiringpi.Gpio.PWM_MODE_MS;

public class GPIOQuadCopterController implements CopterController {

    private final static int DEFAULT_RANGE = 1000;

    private final GpioController GPIO;
    private final GpioPinPwmOutput leftFrontPin;
    private final GpioPinPwmOutput leftBackPin;
    private final GpioPinPwmOutput rightFrontPin;
    private final GpioPinPwmOutput rightBackPin;

    GPIOQuadCopterController(GpioController GPIO) {
        this.GPIO = GPIO;
        this.leftFrontPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26);//GPIO 32 + 34 ground
        this.leftBackPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_21);//GPIO 29 + 30 ground
        this.rightFrontPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_25);//GPIO 37 + 39 Ground
        this.rightBackPin = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_00);//GPIO 11 + 9 Ground

        init();
    }

    public GPIOQuadCopterController() {
        this(GpioFactory.getInstance());
    }

    private void init() {
        Gpio.pwmSetMode(PWM_MODE_MS);
        this.leftBackPin.setMode(PinMode.PWM_OUTPUT);
        this.leftFrontPin.setMode(PinMode.PWM_OUTPUT);
        this.rightFrontPin.setMode(PinMode.PWM_OUTPUT);
        this.rightBackPin.setMode(PinMode.PWM_OUTPUT);
        this.leftFrontPin.setPwmRange(DEFAULT_RANGE);
        this.leftBackPin.setPwmRange(DEFAULT_RANGE);
        this.rightFrontPin.setPwmRange(DEFAULT_RANGE);
        this.rightBackPin.setPwmRange(DEFAULT_RANGE);
        Gpio.pwmSetClock(384);//19.2e6/(clock*range) - hz pwm output
    }

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {
        this.leftFrontPin.setPwm((int) Math.round(50 * powerContainer.getLeftFrontEnginePower() + 50));
        this.leftBackPin.setPwm((int) Math.round(50 * powerContainer.getLeftBackEnginePower() + 50));
        this.rightBackPin.setPwm((int) Math.round(50 * powerContainer.getRightBackEnginePower() + 50));
        this.rightFrontPin.setPwm((int) Math.round(50 * powerContainer.getRightFrontEnginePower() + 50));
    }

    @Override
    public void close() {
        GPIO.shutdown();
        GPIO.unprovisionPin(leftBackPin);
        GPIO.unprovisionPin(leftFrontPin);
        GPIO.unprovisionPin(rightBackPin);
        GPIO.unprovisionPin(rightFrontPin);
    }
}
