package bootstrap;

import control.UserController;
import facade.CopterModulesFactory;

public class MainFactory {

    public static final MainFactory INSTANCE = new MainFactory();

    private CopterModulesFactory copterModulesFactory;
    private UserController userController;

    private MainFactory() {
    }

    public CopterModulesFactory getCopterModulesFactory() {
        if (copterModulesFactory == null) {
            throw new RuntimeException("Main factory is not configured!");
        }
        return copterModulesFactory;
    }

    public boolean isConfigured() {
        return copterModulesFactory != null && userController != null;
    }

    public void setCopterModulesFactory(CopterModulesFactory copterModulesFactory) {
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
