package facade;

import bootstrap.MainFactory;
import engine.CopterController;
import engine.PowerCalculator;
import util.QuadEnginePowerContainer;
import util.QuadPowerCalculator;
import sensors.Gyroscope;
import sensors.RotationAngles;
import proto.CopterDirection;
import sensors.SensorThread;
import util.Observer;

import java.util.concurrent.atomic.AtomicReference;

public class QuadCopter implements Copter {

    private final CopterController copterController;
    private final PowerCalculator powerCalculator;
    private final Gyroscope gyroscope;

    private final AtomicReference<RotationAngles> lastGyroscopeData = new AtomicReference<>();
    private final AtomicReference<RotationAngles> lastClientOffset = new AtomicReference<>();

    public QuadCopter() {
        this.copterController = MainFactory.INSTANCE.getCopterModulesFactory().getCopterController();
        this.gyroscope = MainFactory.INSTANCE.getCopterModulesFactory().getGyroscope();
        this.powerCalculator = new QuadPowerCalculator();
    }

    @Override
    public void init() {
        SensorThread<RotationAngles> gyroscopeThread = new SensorThread<>(this.gyroscope);
        gyroscopeThread.addObserver(new GyroscopeObserver());
        gyroscopeThread.startReading();
    }

    private void receivedGyroscopeData(RotationAngles data) {
        lastGyroscopeData.set(data);
    }

    @Override
    public void handleDirectionChange(CopterDirection.Direction newDirection) {
        //todo calculate client offset
        QuadEnginePowerContainer calculatePower = powerCalculator.calculateEnginesPower(newDirection);
        copterController.setEnginesPower(calculatePower);
    }

    private void calculateEnginesPowerAndSet() {
        // TODO: 12.04.17 calculate engines power by angles
    }

    @Override
    public void correct(CopterDirection.Direction.Correct correct) {
        switch (correct) {
            case BACKWARD: {
                powerCalculator.decrementForward();
                break;
            }
            case FORWARD: {
                powerCalculator.incrementForward();
                break;
            }
            case LEFT: {
                powerCalculator.incrementLeft();
                break;
            }
            case RIGHT: {
                powerCalculator.decrementLeft();
                break;
            }
            case ROTATE_CCW: {
                powerCalculator.incrementRotate();
                break;
            }
            case ROTATE_CW: {
                powerCalculator.decrementRotate();
                break;
            }
        }
    }

    @Override
    public void clientConnectionLost() {
        copterController.close();
    }

    private class GyroscopeObserver implements Observer<RotationAngles> {

        @Override
        public void notify(RotationAngles arg) {
            receivedGyroscopeData(arg);
        }
    }
}
