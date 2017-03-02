package facade;

import proto.CopterDirection;

public interface Copter {

    void handleDirectionChange(CopterDirection.Direction newDirection);

    void clientConnectionLost();

}
