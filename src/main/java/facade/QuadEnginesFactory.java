package facade;

import engine.CopterController;
import engine.GPIOQuadCopterController;
import sensors.Gyroscope;
import sensors.RealGyroscope;

public class QuadEnginesFactory implements CopterModulesFactory {

    @Override
    public Gyroscope getGyroscope() {
        return RealGyroscope.INSTANCE;
    }

    @Override
    public CopterController getCopterController() {
        return new GPIOQuadCopterController();
    }
}
