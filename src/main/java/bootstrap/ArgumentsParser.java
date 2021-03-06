package bootstrap;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;

public class ArgumentsParser {

    private final static String ENGINES_CONTROL_OPTION = "copterControl";
    private final static String USER_CONTROL_OPTION = "userControl";
    private final static String PORT = "port";
    private final static String SENSOR_FACTORY_OPTION = "sensors";
    final static int DEFAULT_PORT = 9276;

    public Arguments parse(String[] args) throws JSAPException {
        JSAP jsap = getJSAP();
        JSAPResult config = jsap.parse(args);
        if (!config.success()) {
            System.out.println(jsap.getHelp());
            throw new JSAPException("incorrect CL params");
        }
        return new Arguments(config.getString(USER_CONTROL_OPTION)
                , config.getString(ENGINES_CONTROL_OPTION)
                , config.getInt(PORT)
                , config.getString(SENSOR_FACTORY_OPTION));
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

        FlaggedOption port = new FlaggedOption(PORT)
                .setStringParser(JSAP.INTEGER_PARSER)
                .setRequired(false)
                .setShortFlag('p')
                .setLongFlag(JSAP.NO_LONGFLAG)
                .setDefault(String.valueOf(DEFAULT_PORT));
        port.setHelp("port for remote server");

        FlaggedOption sensors = new FlaggedOption(SENSOR_FACTORY_OPTION)
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('s')
                .setLongFlag(JSAP.NO_LONGFLAG);
        sensors.setHelp("type of sensor control factory. Values REAL or MOCK");

        result.registerParameter(copterControl);
        result.registerParameter(userControl);
        result.registerParameter(port);
        result.registerParameter(sensors);

        return result;
    }


}
