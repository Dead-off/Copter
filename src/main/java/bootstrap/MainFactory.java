package bootstrap;

import control.UserController;
import engine.EnginesFactory;
import sensors.SensorFactory;

public class MainFactory {

    public static final MainFactory INSTANCE = new MainFactory();

    private EnginesFactory copterModulesFactory;
    private SensorFactory sensorFactory;
    private UserController userController;

    private MainFactory() {
    }

    public EnginesFactory getCopterModulesFactory() {
        if (copterModulesFactory == null) {
            throw new RuntimeException("Main factory is not configured!");
        }
        return copterModulesFactory;
    }

    public SensorFactory getSensorFactory() {
        return sensorFactory;
    }

    public void setSensorFactory(SensorFactory sensorFactory) {
        this.sensorFactory = sensorFactory;
    }

    public boolean isConfigured() {
        return copterModulesFactory != null && userController != null && sensorFactory != null;
    }

    public void setCopterModulesFactory(EnginesFactory copterModulesFactory) {
        this.copterModulesFactory = copterModulesFactory;
    }

    public UserController getUserController() {
        if (userController == null) {
            throw new RuntimeException("Main factory is not configured!");
        }
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}
