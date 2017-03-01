package engine;

public class PrintControllerFactory implements EnginesControlFactory<QuadEnginePowerContainer>{

    public static final PrintControllerFactory INSTANCE = new PrintControllerFactory();

    private PrintControllerFactory() {
    }

    @Override
    public CopterController<QuadEnginePowerContainer> getCopterController() {
        return new PrintCopterController();
    }
}
