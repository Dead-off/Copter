package bootstrap;

import control.UserController;
import engine.EnginesControlFactory;

public class MainFactory {

    public static final MainFactory INSTANCE = new MainFactory();

    private EnginesControlFactory enginesControlFactory;
    private UserController userController;

    private MainFactory() {
    }

    public EnginesControlFactory getEnginesControlFactory() {
        if (enginesControlFactory == null) {
            throw new RuntimeException("Main factory is not configured!");
        }
        return enginesControlFactory;
    }

    public boolean isConfigured() {
        return enginesControlFactory != null && userController != null;
    }

    public void setEnginesControlFactory(EnginesControlFactory enginesControlFactory) {
        this.enginesControlFactory = enginesControlFactory;
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
