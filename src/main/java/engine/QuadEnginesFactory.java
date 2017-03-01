package engine;

public class QuadEnginesFactory implements EnginesControlFactory<QuadEnginePowerContainer> {

    public static final QuadEnginesFactory INSTANCE = new QuadEnginesFactory();

    private QuadEnginesFactory() {
    }

    @Override
    public CopterController<QuadEnginePowerContainer> getCopterController() {
        return new GPIOQuadCopterController();
    }
}
