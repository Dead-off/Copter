package util;

public class QuadPowerCalculator implements PowerCalculator {

    static final double DEFAULT_POWER = 0.7;
    static final double P = 0.25 / 180;
    static final double D = 0.05;

    private RotationAngles previous = null;
    private long lastTime;

    @Override
    public synchronized QuadEnginePowerContainer calculateEnginesPower(RotationAngles angles, double power) {
        angles = angles.getNormalized();
        if (previous == null) {
            previous = angles;
            lastTime = System.currentTimeMillis();
        }
        long currentTime = System.currentTimeMillis();
        Builder builder = new Builder(currentTime - lastTime);
        builder.rotate(angles.getZ(), previous.getZ());
        builder.moveLeftRight(angles.getY(), previous.getY());
        builder.moveBackForward(angles.getX(), previous.getX());
        previous = angles;
        lastTime = currentTime;
        return builder.build(power);
    }

    private class Builder {
        //        cw - clock wise ccw - counter clock wise
        double leftFront = DEFAULT_POWER;//cw
        double leftBack = DEFAULT_POWER;//ccw
        double rightFront = DEFAULT_POWER;//ccw
        double rightBack = DEFAULT_POWER;//cw

        private final long deltaTime;

        private Builder(long deltaTime) {
            this.deltaTime = deltaTime;
        }

        void rotate(Angle angle, Angle previous) {
            double multiplier = getMultiplier(angle, previous);
            if (multiplier == 0) {
                return;
            }
            boolean needRotateLeft = (angle.getDegrees() < 0);
            if (needRotateLeft) {
                rightFront /= multiplier;
                leftBack /= multiplier;
                leftFront *= multiplier;
                rightBack *= multiplier;
            } else {
                rightFront *= multiplier;
                leftBack *= multiplier;
                leftFront /= multiplier;
                rightBack /= multiplier;
            }
        }

        void moveLeftRight(Angle angle, Angle previous) {
            double multiplier = getMultiplier(angle, previous);
            if (multiplier == 0) {
                return;
            }
            boolean needLeft = (angle.getDegrees() > 0);
            if (needLeft) {
                rightFront *= multiplier;
                leftBack /= multiplier;
                leftFront /= multiplier;
                rightBack *= multiplier;
            } else {
                rightFront /= multiplier;
                leftBack *= multiplier;
                leftFront *= multiplier;
                rightBack /= multiplier;
            }
        }

        void moveBackForward(Angle angle, Angle previous) {
            double multiplier = getMultiplier(angle, previous);
            if (multiplier == 0) {
                return;
            }
            boolean needForward = (angle.getDegrees() > 0);
            if (needForward) {
                rightFront /= multiplier;
                leftBack *= multiplier;
                leftFront /= multiplier;
                rightBack *= multiplier;
            } else {
                rightFront *= multiplier;
                leftBack /= multiplier;
                leftFront *= multiplier;
                rightBack /= multiplier;
            }
        }

        private double getMultiplier(Angle angle, Angle previous) {
            double velocity = 1000.0 * (previous.getDegrees() - angle.getDegrees()) / deltaTime;//degrees/second
            if (velocity < 0) {
                //Квадриик висел под углом, должен был начать выправляться, но на деле завалился ещё сильнее
                //по правильному наверно стоит в таком случае увеличивать корректировочный коэифиент скорости вплоть до 2
                //при скорости стремящейся к минус бесконечности, но пока пусть будет просто игнор скорости.
                velocity = 0;
            }
            double velocityCorrect = 1.0 / (D * velocity + 1);//velocity=[0, +inf] -> correct = [1,0]
            return 1 + Math.abs(P * angle.getDegrees()) * velocityCorrect;
        }

        private double getValueBetweenZeroAndOne(double value) {
            if (value > 1) {
                return 1.0;
            } else if (value < 0) {
                return 0.0;
            }
            return value;
        }

        QuadEnginePowerContainer build(double power) {

            return new QuadEnginePowerContainer(
                    getValueBetweenZeroAndOne(leftFront * power),
                    getValueBetweenZeroAndOne(leftBack * power),
                    getValueBetweenZeroAndOne(rightFront * power),
                    getValueBetweenZeroAndOne(rightBack * power));
        }
    }
}
