package facade;

import engine.CopterController;
import engine.QuadEnginePowerContainer;
import engine.QuadEnginesFactory;
import proto.CopterDirection;

public class QuadCopter implements Copter {

    private final CopterController<QuadEnginePowerContainer> copterController;

    public QuadCopter() {
        this.copterController = QuadEnginesFactory.INSTANCE.getCopterController();
    }

    @Override
    public void handleDirectionChange(CopterDirection newDirection) {

    }

    @Override
    public void clientConnectionLost() {

    }
}
