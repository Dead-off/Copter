package engine;

import proto.CopterDirection;

public interface PowerCalculator {

    QuadEnginePowerContainer calculateEnginesPower(CopterDirection.Direction direction);

}
