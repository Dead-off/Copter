package engine;

public class QuadEnginesFactory implements EnginesControlFactory {

    public static final QuadEnginesFactory INSTANCE = new QuadEnginesFactory();

    private QuadEnginesFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new GPIOQuadCopterController();
    }
}
