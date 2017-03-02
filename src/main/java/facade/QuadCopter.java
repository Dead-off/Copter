package facade;

import engine.CopterController;
import factory.MainFactory;
import proto.CopterDirection;

public class QuadCopter implements Copter {

    private final CopterController copterController;

    public QuadCopter() {
        this.copterController = MainFactory.INSTANCE.getEnginesControlFactory().getCopterController();
    }

    @Override
    public void handleDirectionChange(CopterDirection.Direction newDirection) {

    }

    @Override
    public void clientConnectionLost() {

    }
}
