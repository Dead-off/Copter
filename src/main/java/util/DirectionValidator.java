package util;

import proto.CopterDirection;

public interface DirectionValidator {

    boolean isCorrectDirectionMessage(CopterDirection.Direction direction);

}
