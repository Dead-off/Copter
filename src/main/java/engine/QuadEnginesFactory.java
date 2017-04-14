package engine;

public class QuadEnginesFactory implements EnginesFactory {

    @Override
    public CopterController getCopterController() {
        return new GPIOQuadCopterController();
    }
}
