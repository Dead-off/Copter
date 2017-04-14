package util;

import proto.CopterDirection;

public interface OffsetCalculator {

    RotationAngles calculateOffset(CopterDirection.Direction direction);

}
