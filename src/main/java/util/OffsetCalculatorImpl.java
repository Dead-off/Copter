package util;

import proto.CopterDirection;

public class OffsetCalculatorImpl implements OffsetCalculator {

    final static int ANGLE_FOR_FULL_VALUE = 10;
    private final DirectionValidator directionValidator;

    public OffsetCalculatorImpl() {
        this.directionValidator = new DirectionValidatorImpl();
    }

    @Override
    public RotationAngles calculateOffset(CopterDirection.Direction direction) {
        if (!directionValidator.isCorrectDirectionMessage(direction)) {
            throw new IllegalArgumentException("direction values are incorrect!");
        }
        double forwardBackward = direction.getBackward() - direction.getForward();
        double leftRight = direction.getRight() - direction.getLeft();
        double rotate = direction.getRotateLeft() - direction.getRotateRight();
        return new RotationAngles(
                forwardBackward * ANGLE_FOR_FULL_VALUE,
                leftRight * ANGLE_FOR_FULL_VALUE,
                rotate * ANGLE_FOR_FULL_VALUE);
    }
}
