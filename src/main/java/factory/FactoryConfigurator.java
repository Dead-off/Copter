package factory;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import control.CommandLineController;
import control.NetworkController;
import control.UserController;
import engine.EnginesControlFactory;
import engine.PrintControllerFactory;
import engine.QuadEnginesFactory;

public class FactoryConfigurator {

    private final static String ENGINES_CONTROL_OPTION = "copterControl";
    private final static String USER_CONTROL_OPTION = "userControl";

    private final MainFactory factory;

    public FactoryConfigurator(MainFactory factory) {
        this.factory = factory;
    }

    public void configureFactoryFromCLArgs(String[] args) throws JSAPException {
        JSAP jsap = getJSAP();
        JSAPResult config = jsap.parse(args);
        if (!config.success()) {
            badParams(jsap);
        }
        String enginesFactoryType = config.getString(ENGINES_CONTROL_OPTION);
        String userControlFactoryType = config.getString(USER_CONTROL_OPTION);
        EnginesControlFactory enginesControlFactory = parseEnginesFactory(enginesFactoryType);
        UserController userController = parseUserController(userControlFactoryType);
        if (userController == null || enginesControlFactory == null) {
            badParams(jsap);
        }
        factory.setEnginesControlFactory(enginesControlFactory);
        factory.setUserController(userController);
    }

    private void badParams(JSAP jsap) throws JSAPException {
        System.out.println(jsap.getHelp());
        throw new JSAPException("incorrect CL params");
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
                return new NetworkController();
            }
            case "CL": {
                return new CommandLineController();
            }
        }
        return null;
    }

    private JSAP getJSAP() throws JSAPException {
        JSAP result = new JSAP();

        FlaggedOption copterControl = new FlaggedOption(ENGINES_CONTROL_OPTION)
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('c')
                .setLongFlag(JSAP.NO_LONGFLAG);
        copterControl.setHelp("type copter control interface. Values: GPIO or PRINT");

        FlaggedOption userControl = new FlaggedOption(USER_CONTROL_OPTION)
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('u')
                .setLongFlag(JSAP.NO_LONGFLAG);
        userControl.setHelp("type user control interface. Values: REMOTE or CL");

        result.registerParameter(copterControl);
        result.registerParameter(userControl);

        return result;
    }

}
