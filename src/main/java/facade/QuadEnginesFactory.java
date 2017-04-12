package facade;

import engine.CopterController;
import engine.GPIOQuadCopterController;
import facade.CopterModulesFactory;

public class QuadEnginesFactory implements CopterModulesFactory {

    public QuadEnginesFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new GPIOQuadCopterController();
    }
}
