package sensors;

public class RealGyroscope implements Gyroscope {

    public final static Gyroscope INSTANCE = new RealGyroscope();

    private RealGyroscope() {

    }

    @Override
    public RotationAngles getData() {
        return null;
    }
}
