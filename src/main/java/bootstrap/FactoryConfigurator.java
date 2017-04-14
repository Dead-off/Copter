package bootstrap;

import control.CommandLineController;
import control.NetworkController;
import control.UserController;
import engine.EnginesFactory;
import engine.PrintControllerFactory;
import engine.QuadEnginesFactory;
import sensors.MockSensorFactory;
import sensors.RealSensorFactory;
import sensors.SensorFactory;

public class FactoryConfigurator {

    private final MainFactory factory;
    private final Arguments arguments;

    public FactoryConfigurator(MainFactory factory, Arguments arguments) {
        this.factory = factory;
        this.arguments = arguments;
    }

    public void configure() {
        EnginesFactory enginesFactory = parseEnginesFactory(arguments.outputController);
        UserController userController = parseUserController(arguments.userController);
        SensorFactory sensorFactory = parseSensorType(arguments.sensorFactory);
        if (userController == null) {
            throw new IllegalArgumentException("Illegal user controller argument!");
        }
        if (enginesFactory == null) {
            throw new IllegalArgumentException("Illegal engines controller argument!");
        }
        if (sensorFactory == null) {
            throw new IllegalArgumentException("Illegal sensor argument!");
        }
        factory.setCopterModulesFactory(enginesFactory);
        factory.setUserController(userController);
        factory.setSensorFactory(sensorFactory);
    }

    private EnginesFactory parseEnginesFactory(String type) {
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

    private SensorFactory parseSensorType(String type) {
        switch (type) {
            case "REAL": {
                return new RealSensorFactory();
            }
            case "MOCK": {
                return new MockSensorFactory();
            }
        }
        return null;
    }
}
