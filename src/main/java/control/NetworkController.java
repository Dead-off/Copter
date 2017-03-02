package control;

import facade.Copter;

public class NetworkController implements UserController {

    private Copter copter;

    @Override
    public void run() {
        // TODO: 02.03.17 impl
    }

    @Override
    public void setCopter(Copter copter) {
        this.copter = copter;
    }

    @Override
    public Copter getCopter() {
        return copter;
    }
}
