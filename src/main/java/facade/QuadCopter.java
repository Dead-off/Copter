package facade;

import bootstrap.MainFactory;
import engine.CopterController;
import engine.PowerCalculator;
import engine.QuadEnginePowerContainer;
import engine.QuadroPowerCalculator;
import sensors.RotationAngles;
import proto.CopterDirection;
import util.Observer;

public class QuadCopter implements Copter {

    private final CopterController copterController;
    private final PowerCalculator powerCalculator;

    public QuadCopter() {
        this.copterController = MainFactory.INSTANCE.getEnginesControlFactory().getCopterController();
        this.powerCalculator = new QuadroPowerCalculator();
    }

    private void receivedGyroscopeData(RotationAngles data) {

    }

    @Override
    public void handleDirectionChange(CopterDirection.Direction newDirection) {
        QuadEnginePowerContainer calculatePower = powerCalculator.calculateEnginesPower(newDirection);
        copterController.setEnginesPower(calculatePower);
    }

    @Override
    public void correct(CopterDirection.Direction.Correct correct) {
        switch (correct) {
            case BACKWARD: {
                powerCalculator.decrementForward();
                break;
            }
            case FORWARD: {
                powerCalculator.incrementForward();
                break;
            }
            case LEFT: {
                powerCalculator.incrementLeft();
                break;
            }
            case RIGHT: {
                powerCalculator.decrementLeft();
                break;
            }
            case ROTATE_CCW: {
                powerCalculator.incrementRotate();
                break;
            }
            case ROTATE_CW: {
                powerCalculator.decrementRotate();
                break;
            }
        }
    }

    @Override
    public void clientConnectionLost() {
        copterController.close();
    }

    private class GyroscopeObserver implements Observer<RotationAngles> {

        @Override
        public void notify(RotationAngles arg) {
            receivedGyroscopeData(arg);
        }
    }
}
