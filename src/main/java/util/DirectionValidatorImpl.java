package util;

import proto.CopterDirection;

public class DirectionValidatorImpl implements DirectionValidator {

    @Override
    public boolean isCorrectDirectionMessage(CopterDirection.Direction direction) {
        if (direction.getPower() > 1 || direction.getPower() < 0) return false;
        double[] backForward = new double[]{direction.getForward(), direction.getBackward()};
        double[] leftRight = new double[]{direction.getLeft(), direction.getRight()};
        double[] rotate = new double[]{direction.getRotateLeft(), direction.getRotateRight()};
        if (!checkArrayPair(backForward)) {
            return false;
        }
        if (!checkArrayPair(leftRight)) {
            return false;
        }
        if (!checkArrayPair(rotate)) {
            return false;
        }
        return true;
    }

    private boolean checkArrayPair(double[] directionPair) {
        if (directionPair.length != 2) {
            return false;
        }
        if (directionPair[0] != 0 && directionPair[1] != 0) {
            return false;
        }
        for (double value : directionPair) {
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                return false;
            }
            if (value < 0 || value > 1) {
                return false;
            }
        }
        return true;
    }
}
