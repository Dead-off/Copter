package util;

import org.junit.Assert;
import org.junit.Test;
import proto.CopterDirection;

public class DirectionCorrectorImplTest extends Assert {

    @Test
    public void isCorrectDirectionMessageTest() {
        CopterDirection.Direction.Builder directionBuilder = CopterDirection.Direction.newBuilder();
        DirectionCorrector corrector = new DirectionCorrectorImpl();

        assertTrue(corrector.isCorrectDirectionMessage(directionBuilder.build()));

        directionBuilder.setPower(0.7);
        directionBuilder.setBackward(0.7);
        assertTrue(corrector.isCorrectDirectionMessage(directionBuilder.build()));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setPower(1.0).setLeft(1.0);
        assertTrue(corrector.isCorrectDirectionMessage(directionBuilder.build()));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setLeft(-0.001);
        assertFalse(corrector.isCorrectDirectionMessage(directionBuilder.build()));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setLeft(1.0);
        assertTrue(corrector.isCorrectDirectionMessage(directionBuilder.build()));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setForward(1.1);
        assertFalse(corrector.isCorrectDirectionMessage(directionBuilder.build()));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setLeft(0.5).setRotateRight(0.5);
        assertTrue(corrector.isCorrectDirectionMessage(directionBuilder.build()));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setRotateLeft(0.1).setRotateRight(0.3);
        assertFalse(corrector.isCorrectDirectionMessage(directionBuilder.build()));
    }

}
