package facade;

import engine.CopterController;
import engine.PrintCopterController;
import facade.CopterModulesFactory;
import sensors.Gyroscope;
import sensors.RandomGyroscope;

public class PrintControllerFactory implements CopterModulesFactory {

    public PrintControllerFactory() {
    }

    @Override
    public CopterController getCopterController() {
        return new PrintCopterController();
    }

    @Override
    public Gyroscope getGyroscope() {
        return new RandomGyroscope();
    }
}
