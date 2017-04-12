package facade;

import engine.CopterController;
import engine.PrintCopterController;
import facade.CopterModulesFactory;

public class PrintControllerFactory implements CopterModulesFactory {

    public PrintControllerFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new PrintCopterController();
    }
}
