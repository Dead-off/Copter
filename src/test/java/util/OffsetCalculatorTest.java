package util;

import org.junit.Test;
import proto.CopterDirection;

import static org.junit.Assert.assertEquals;

public class OffsetCalculatorTest {

    private final static int FULL_ANGLE = OffsetCalculatorImpl.ANGLE_FOR_FULL_VALUE;

    @Test
    public void calculateOffsetTest() {
        OffsetCalculator calculator = new OffsetCalculatorImpl();
        CopterDirection.Direction direction = CopterDirection.Direction.newBuilder()
                .setLeft(0.5)
                .setForward(1.0)
                .setPower(0)
                .build();
        RotationAngles actual = calculator.calculateOffset(direction);
        RotationAngles expected = new RotationAngles(-FULL_ANGLE, -FULL_ANGLE / 2, 0);
        assertEquals(expected, actual);


        direction = CopterDirection.Direction.newBuilder()
                .setRight(0.5)
                .setForward(1.0)
                .setPower(1)
                .setRotateLeft(0.7)
                .build();
        actual = calculator.calculateOffset(direction);
        expected = new RotationAngles(-FULL_ANGLE, FULL_ANGLE / 2, FULL_ANGLE * 0.7);
        assertEquals(expected, actual);
    }
}
