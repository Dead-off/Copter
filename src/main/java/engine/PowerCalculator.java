package engine;

import proto.CopterDirection;

public interface PowerCalculator {

    QuadEnginePowerContainer calculateEnginesPower(CopterDirection.Direction direction);

    void incrementRotate();

    void decrementRotate();

    void incrementForward();

    void decrementForward();

    void incrementLeft();

    void decrementLeft();

}
