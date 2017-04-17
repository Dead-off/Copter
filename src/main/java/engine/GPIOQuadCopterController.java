package engine;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import util.QuadEnginePowerContainer;

public class GPIOQuadCopterController implements CopterController {

    private static final GpioController GPIO = GpioFactory.getInstance();

    private final Engine leftFrontEngine;
    private final Engine rightFrontEngine;
    private final Engine leftBackEngine;
    private final Engine rightBackEngine;

    public GPIOQuadCopterController() {
        this.rightBackEngine = new PiPWMEngine(GPIO.provisionPwmOutputPin(RaspiPin.GPIO_26));
        this.leftBackEngine = new PiPWMEngine(GPIO.provisionPwmOutputPin(RaspiPin.GPIO_23));
        this.leftFrontEngine = new PiSoftwarePWMEmulator(GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_15));
        this.rightFrontEngine = new PiSoftwarePWMEmulator(GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_29));
    }

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {
        this.leftFrontEngine.setPower(powerContainer.getLeftFrontEnginePower());
        this.leftBackEngine.setPower(powerContainer.getLeftBackEnginePower());
        this.rightFrontEngine.setPower(powerContainer.getRightFrontEnginePower());
        this.rightBackEngine.setPower(powerContainer.getRightBackEnginePower());
    }

    @Override
    public void close() {
        leftFrontEngine.shutDown();
        leftBackEngine.shutDown();
        rightBackEngine.shutDown();
        rightFrontEngine.shutDown();
        GPIO.shutdown();
    }
}
