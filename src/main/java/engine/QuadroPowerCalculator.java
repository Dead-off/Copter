package engine;

import proto.CopterDirection;
import util.DirectionValidator;
import util.DirectionValidatorImpl;

public class QuadroPowerCalculator implements PowerCalculator {

    static final double DEFAULT_POWER = 0.7;
    static final double MAX_MULTIPLIER = 0.15;
    static final double CORRECTOR_DELTA = 0.0025;
    static final double MAX_CORRECT_VALUE = 0.1;

    //values from [-MAX_CORRECT_VALUE, MAX_CORRECT_VALUE].
    //positive value is rotate CCW, fly forward and left
    private double rotateCorrector;
    private double forwardBackCorrector;
    private double leftRightCorrector;

    private final DirectionValidator directionValidator;

    public QuadroPowerCalculator() {
        this(0, 0, 0);
    }

    public QuadroPowerCalculator(double rotateCorrector, double forwardBackCorrector, double leftRightCorrector) {
        this.rotateCorrector = rotateCorrector;
        this.forwardBackCorrector = forwardBackCorrector;
        this.leftRightCorrector = leftRightCorrector;
        this.directionValidator = new DirectionValidatorImpl();
    }

    @Override
    public QuadEnginePowerContainer calculateEnginesPower(CopterDirection.Direction direction) {
        if (!directionValidator.isCorrectDirectionMessage(direction)) {
            throw new IllegalArgumentException("direction values are incorrect!");
        }
        CalculatorHelper helper = new CalculatorHelper();
        helper.correct();
        helper.rotate(direction.getRotateLeft(), direction.getRotateRight());
        helper.moveLeftRight(direction.getLeft(), direction.getRight());
        helper.moveBackForward(direction.getBackward(), direction.getForward());
        return helper.toContainer(direction.getPower());
    }

    @Override
    public void incrementRotate() {
        if (Math.abs(this.rotateCorrector) < MAX_CORRECT_VALUE) {
            this.rotateCorrector += CORRECTOR_DELTA;
        }
    }

    @Override
    public void decrementRotate() {
        if (Math.abs(this.rotateCorrector) < MAX_CORRECT_VALUE) {
            this.rotateCorrector -= CORRECTOR_DELTA;
        }
    }

    @Override
    public void incrementForward() {
        if (Math.abs(this.forwardBackCorrector) < MAX_CORRECT_VALUE) {
            this.forwardBackCorrector += CORRECTOR_DELTA;
        }
    }

    @Override
    public void decrementForward() {
        if (Math.abs(this.forwardBackCorrector) < MAX_CORRECT_VALUE) {
            this.forwardBackCorrector -= CORRECTOR_DELTA;
        }
    }

    @Override
    public void incrementLeft() {
        if (Math.abs(this.leftRightCorrector) < MAX_CORRECT_VALUE) {
            this.leftRightCorrector += CORRECTOR_DELTA;
        }
    }

    @Override
    public void decrementLeft() {
        if (Math.abs(this.leftRightCorrector) < MAX_CORRECT_VALUE) {
            this.leftRightCorrector -= CORRECTOR_DELTA;
        }
    }

    private class CalculatorHelper {
        //        cw - clock wise ccw - counter clock wise
        double leftFront = DEFAULT_POWER;//cw
        double leftBack = DEFAULT_POWER;//ccw
        double rightFront = DEFAULT_POWER;//ccw
        double rightBack = DEFAULT_POWER;//cw

        void rotate(double left, double right) {
            double multiplier = getMultiplier(left, right);
            if (multiplier == 0) {
                return;
            }
            boolean isRotateLeft = (left != 0);
            if (isRotateLeft) {
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

        private void correct() {
            this.leftBack += (forwardBackCorrector - leftRightCorrector - rotateCorrector);
            this.leftFront += (-forwardBackCorrector - leftRightCorrector + rotateCorrector);
            this.rightBack += (forwardBackCorrector + leftRightCorrector + rotateCorrector);
            this.rightFront += (-forwardBackCorrector + leftRightCorrector - rotateCorrector);
        }

        private double getMultiplier(double directionFirst, double directionSecond) {
            return 1 + MAX_MULTIPLIER * (directionFirst == 0 ? directionSecond : directionFirst);
        }

        void moveLeftRight(double left, double right) {
            double multiplier = getMultiplier(left, right);
            if (multiplier == 0) {
                return;
            }
            boolean isMoveLeft = (left != 0);
            if (isMoveLeft) {
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

        void moveBackForward(double back, double forward) {
            double multiplier = getMultiplier(back, forward);
            if (multiplier == 0) {
                return;
            }
            boolean isMoveForward = (forward != 0);
            if (isMoveForward) {
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

        QuadEnginePowerContainer toContainer(double power) {
            return new QuadEnginePowerContainer(
                    leftFront * power,
                    leftBack * power,
                    rightFront * power,
                    rightBack * power);
        }
    }
}
