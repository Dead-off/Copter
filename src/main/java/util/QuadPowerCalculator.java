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
            return 1 + Math.abs(P * angle.getDegrees());
            //            return 1 + Math.abs(P * angle.getDegrees() + D * deltaTime * (angle.getDegrees() - previous.getDegrees()));
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
