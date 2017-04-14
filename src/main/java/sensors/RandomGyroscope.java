package sensors;

import util.Angle;
import util.RotationAngles;

import java.util.Random;

public class RandomGyroscope implements Gyroscope {

    private final static double MAX_ANGLE = 30;
    private final static double MIN_ANGLE = 30;
    private final static double MAX_DELTA_ON_STEP = 10;
    private final Random random = new Random();
    private RotationAngles lastAngles = new RotationAngles(new Angle(0), new Angle(0), new Angle(0));

    @Override
    public RotationAngles getData() {
        double newXAngle = generateAngle(lastAngles.getX().getDegrees());
        double newYAngle = generateAngle(lastAngles.getY().getDegrees());
        double newZAngle = generateAngle(lastAngles.getZ().getDegrees());
        RotationAngles result = new RotationAngles(
                new Angle(newXAngle), new Angle(newYAngle), new Angle(newZAngle));
        lastAngles = result;
        return result;
    }

    private double generateAngle(double previous) {
        double generatedValue = 2 * MAX_DELTA_ON_STEP * random.nextDouble() - MAX_DELTA_ON_STEP;
        double result = previous + generatedValue;
        if (result > MAX_ANGLE) {
            return MAX_ANGLE;
        }
        if (result < MIN_ANGLE) {
            return MIN_ANGLE;
        }
        return result;
    }
}
