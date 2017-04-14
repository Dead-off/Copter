package facade;

import bootstrap.MainFactory;
import engine.CopterController;
import proto.CopterDirection;
import sensors.Gyroscope;
import sensors.SensorThread;
import util.*;

import java.util.concurrent.atomic.AtomicReference;

public class QuadCopter implements Copter {

    private final CopterController copterController;
    private final PowerCalculator powerCalculator;
    private final Gyroscope gyroscope;
    private final OffsetCalculator offsetCalculator;

    private final double AVERAGE_POWER = 0.35;

    private final AtomicReference<RotationAngles> lastGyroscopeData = new AtomicReference<>();
    private final AtomicReference<RotationAngles> lastClientOffset = new AtomicReference<>();
    private volatile double power = 0;

    public QuadCopter() {
        this.copterController = MainFactory.INSTANCE.getCopterModulesFactory().getCopterController();
        this.gyroscope = MainFactory.INSTANCE.getCopterModulesFactory().getGyroscope();
        this.powerCalculator = new QuadPowerCalculator();
        this.offsetCalculator = new OffsetCalculatorImpl();
    }

    @Override
    public void init() {
        SensorThread<RotationAngles> gyroscopeThread = new SensorThread<>(this.gyroscope);
        gyroscopeThread.addObserver(new GyroscopeObserver());
        gyroscopeThread.startReading();
    }

    private void receivedGyroscopeData(RotationAngles data) {
        lastGyroscopeData.set(data);
        calculateEnginesPowerAndSet();
    }

    @Override
    public void handleDirectionChange(CopterDirection.Direction newDirection) {
        lastClientOffset.set(offsetCalculator.calculateOffset(newDirection));
        this.power = newDirection.getPower();
        calculateEnginesPowerAndSet();
    }

    private void calculateEnginesPowerAndSet() {

        RotationAngles gyroscopeData = getGyroscopeData();
        RotationAngles clientOffset = getClientOffset();

        RotationAngles sum = new RotationAngles(
                gyroscopeData.getX().subtract(clientOffset.getX()),
                gyroscopeData.getY().subtract(clientOffset.getY()),
                gyroscopeData.getZ().subtract(clientOffset.getZ())

        );
        QuadEnginePowerContainer calculatePower = powerCalculator.calculateEnginesPower(sum, power);
        synchronized (this) {
            copterController.setEnginesPower(calculatePower);
        }
    }

    private RotationAngles getGyroscopeData() {
        return getOrElse(lastGyroscopeData, RotationAngles.ZERO);
    }

    private <T> T getOrElse(AtomicReference<T> reference, T defaultValue) {
        T result = reference.get();
        return result == null ? defaultValue : result;
    }

    private RotationAngles getClientOffset() {
        return getOrElse(lastClientOffset, RotationAngles.ZERO);
    }

    @Override
    public void resetOffset() {
        lastClientOffset.set(new RotationAngles(0, 0, 0));
    }

    @Override
    public void clientConnectionLost() {
        resetOffset();
        this.power = AVERAGE_POWER;
    }

    private class GyroscopeObserver implements Observer<RotationAngles> {

        @Override
        public void notify(RotationAngles arg) {
            receivedGyroscopeData(arg);
        }
    }
}
