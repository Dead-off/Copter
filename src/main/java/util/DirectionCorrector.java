package util;

import proto.CopterDirection;

public interface DirectionCorrector {

    boolean isCorrectDirectionMessage(CopterDirection.Direction direction);

}
