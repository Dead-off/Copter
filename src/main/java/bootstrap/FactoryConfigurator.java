package bootstrap;

import control.CommandLineController;
import control.NetworkController;
import control.UserController;
import engine.EnginesControlFactory;
import engine.PrintControllerFactory;
import engine.QuadEnginesFactory;

public class FactoryConfigurator {

    private final MainFactory factory;
    private final Arguments arguments;

    public FactoryConfigurator(MainFactory factory, Arguments arguments) {
        this.factory = factory;
        this.arguments = arguments;
    }

    public void configure() {
        EnginesControlFactory enginesControlFactory = parseEnginesFactory(arguments.outputController);
        UserController userController = parseUserController(arguments.userController);
        if (userController == null) {
            throw new IllegalArgumentException("Illegal user controller argument!");
        }
        if (enginesControlFactory == null) {
            throw new IllegalArgumentException("Illegal user controller argument!");
        }
        factory.setEnginesControlFactory(enginesControlFactory);
        factory.setUserController(userController);
    }

    private EnginesControlFactory parseEnginesFactory(String type) {
        switch (type) {
            case "GPIO": {
                return new QuadEnginesFactory();
            }
            case "PRINT": {
                return new PrintControllerFactory();
            }
        }
        return null;
    }

    private UserController parseUserController(String type) {
        switch (type) {
            case "REMOTE": {
                return new NetworkController(arguments.port);
            }
            case "CL": {
                return new CommandLineController();
            }
        }
        return null;
    }
}
