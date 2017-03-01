package engine;

public class PrintControllerFactory implements EnginesControlFactory {

    public PrintControllerFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new PrintCopterController();
    }
}
