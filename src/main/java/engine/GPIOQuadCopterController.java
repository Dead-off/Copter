package engine;

import com.pi4j.io.gpio.*;

public class GPIOQuadCopterController implements CopterController {

    //todo implementation
//    пока не очень ясно, как будут работать esc. Возможно потребуется ещё что-то типа GPIOWriter
//    который будет запускаться в отдельном потоке и переключать пины с заданными интервалом
//    можно тогда добавить package-private getter , который будет отдавать текущее состояние мотора (крутится/нет)
//    и юнитом это тестировать. То есть засетили - проверили, подождали N секунд - проверили, подождали ещё - проверили

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {
        final GpioController gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "MyLED", PinState.HIGH);
        pin.setShutdownOptions(true, PinState.LOW);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        pin.low();
        gpio.shutdown();
    }
}
