package engine;

public class PrintControllerFactory implements EnginesFactory {

    public PrintControllerFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new PrintCopterController();
    }

}
