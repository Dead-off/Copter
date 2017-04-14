package util;

public class QuadPowerCalculator implements PowerCalculator {

    static final double DEFAULT_POWER = 0.7;
    static final double MAX_MULTIPLIER = 0.15;

    @Override
    public QuadEnginePowerContainer calculateEnginesPower(RotationAngles angles, double power) {
        angles = angles.getNormalized();
        Builder builder = new Builder();
        builder.rotate(angles.getZ());
        builder.moveLeftRight(angles.getY());
        builder.moveBackForward(angles.getX());
        return builder.build(power);
    }

    private class Builder {
        //        cw - clock wise ccw - counter clock wise
        double leftFront = DEFAULT_POWER;//cw
        double leftBack = DEFAULT_POWER;//ccw
        double rightFront = DEFAULT_POWER;//ccw
        double rightBack = DEFAULT_POWER;//cw

        void rotate(Angle angle) {
            double multiplier = getMultiplier(angle);
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

        void moveLeftRight(Angle angle) {
            double multiplier = getMultiplier(angle);
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

        void moveBackForward(Angle angle) {
            double multiplier = getMultiplier(angle);
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

        private double getMultiplier(Angle angle) {
            return 1+Math.abs(MAX_MULTIPLIER*angle.getDegrees() / 180);
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
