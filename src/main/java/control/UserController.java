package control;

import facade.Copter;

public interface UserController  {

    void run();

    void setCopter(Copter copter);

    Copter getCopter();

}
