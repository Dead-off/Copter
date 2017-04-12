package engine;

import facade.CopterModulesFactory;

public class QuadEnginesFactory implements CopterModulesFactory {

    public QuadEnginesFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new GPIOQuadCopterController();
    }
}
