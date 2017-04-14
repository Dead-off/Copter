package sensors;

public class MockSensorFactory implements SensorFactory {

    @Override
    public Gyroscope getGyroscope() {
        return new RandomGyroscope();
    }
}
