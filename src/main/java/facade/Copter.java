package facade;

import proto.CopterDirection;

public interface Copter {

    void handleDirectionChange(CopterDirection newDirection);

    void clientConnectionLost();

}
