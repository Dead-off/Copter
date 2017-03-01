package engine;

public class PrintControllerFactory implements EnginesControlFactory {

    public static final PrintControllerFactory INSTANCE = new PrintControllerFactory();

    private PrintControllerFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new PrintCopterController();
    }
}
