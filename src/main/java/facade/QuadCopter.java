package facade;

import engine.CopterController;
import engine.PowerCalculator;
import engine.QuadEnginePowerContainer;
import engine.QuadroPowerCalculator;
import bootstrap.MainFactory;
import proto.CopterDirection;

public class QuadCopter implements Copter {

    private final CopterController copterController;
    private final PowerCalculator powerCalculator;

    public QuadCopter() {
        this.copterController = MainFactory.INSTANCE.getEnginesControlFactory().getCopterController();
        this.powerCalculator = new QuadroPowerCalculator();
    }

    @Override
    public void handleDirectionChange(CopterDirection.Direction newDirection) {
        QuadEnginePowerContainer calculatePower = powerCalculator.calculateEnginesPower(newDirection);
        copterController.setEnginesPower(calculatePower);
    }

    @Override
    public void clientConnectionLost() {
        copterController.close();
    }
}
