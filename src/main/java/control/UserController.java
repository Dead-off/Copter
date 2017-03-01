package control;

import facade.Copter;
import proto.CopterDirection;

public interface UserController {

    CopterDirection getCopterDirection();

    Copter getControlledCopter();

}
