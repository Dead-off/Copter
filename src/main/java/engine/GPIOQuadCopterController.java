package engine;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

import static com.pi4j.wiringpi.Gpio.PWM_MODE_MS;
import static com.pi4j.wiringpi.Gpio.PWM_OUTPUT;

public class GPIOQuadCopterController implements CopterController {

    private final GpioController GPIO;
    private final GpioPinPwmOutput leftFrontEngine;
    private final GpioPinPwmOutput leftBackEngine;
    private final GpioPinPwmOutput rightFrontEngine;
    private final GpioPinPwmOutput rightBackEngine;

    GPIOQuadCopterController(GpioController GPIO) {
        this.GPIO = GPIO;
        this.leftFrontEngine = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26);
        this.leftBackEngine = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26);
        //todo check can use so gpio pins, set ports, set props from t() func
        this.rightFrontEngine = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26);
        this.rightBackEngine = GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26);
    }

    public GPIOQuadCopterController() {
        this(GpioFactory.getInstance());
    }

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {


        final GpioPinDigitalOutput pin = GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.HIGH);
        System.out.println("on");
        pin.setShutdownOptions(true, PinState.LOW);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        pin.low();
        GPIO.shutdown();
    }

    public void t() {
//        pin0.setPwmRange();
//        GpioFactory.getDefaultProvider().setP
//        Gpio.pinMode(26, PWM_OUTPUT);
//        Gpio.pwmSetMode(PWM_MODE_MS);
//        Gpio.pwmSetClock(384);//19.2e6/(clock*range) max value of clock*range == 2^24 (4096*4096)
//        Gpio.pwmSetRange(1000);//value==range - output 1, value == 0 output 0
//        int value = 50+(int)Math.round(powerContainer.getLeftBackEnginePower()*50);
//        System.out.println(value);
//        System.out.println("pulse on 50hz " + (value/1000));
//        Gpio.pwmWrite(26, value);
////        pin
////        pin0.setPwm((int)Math.round(1024*powerContainer.getLeftBackEnginePower()));
    }

    @Override
    public void close() {
        GPIO.shutdown();
        //todo release
//        GPIO.unprovisionPin(pin0);
    }
}
