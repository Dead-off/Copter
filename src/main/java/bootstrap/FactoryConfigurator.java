package bootstrap;

import control.CommandLineController;
import control.NetworkController;
import control.UserController;
import facade.CopterModulesFactory;
import facade.PrintControllerFactory;
import facade.QuadEnginesFactory;

public class FactoryConfigurator {

    private final MainFactory factory;
    private final Arguments arguments;

    public FactoryConfigurator(MainFactory factory, Arguments arguments) {
        this.factory = factory;
        this.arguments = arguments;
    }

    public void configure() {
        CopterModulesFactory copterModulesFactory = parseEnginesFactory(arguments.outputController);
        UserController userController = parseUserController(arguments.userController);
        if (userController == null) {
            throw new IllegalArgumentException("Illegal user controller argument!");
        }
        if (copterModulesFactory == null) {
            throw new IllegalArgumentException("Illegal user controller argument!");
        }
        factory.setCopterModulesFactory(copterModulesFactory);
        factory.setUserController(userController);
    }

    private CopterModulesFactory parseEnginesFactory(String type) {
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
