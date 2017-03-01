package engine;

public class QuadEnginesFactory implements EnginesControlFactory {

    public QuadEnginesFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new GPIOQuadCopterController();
    }
}
