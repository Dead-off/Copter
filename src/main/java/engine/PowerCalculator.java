package engine;

import proto.CopterDirection;

public interface PowerCalculator<T> {

    T calculateEnginesPower(CopterDirection.Direction direction);

}
