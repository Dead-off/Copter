package sensors;

public class RealSensorFactory implements SensorFactory {

    @Override
    public Gyroscope getGyroscope() {
        return RealGyroscope.INSTANCE;
    }
}
