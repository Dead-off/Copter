import bootstrap.Arguments;
import bootstrap.ArgumentsParser;
import com.martiansoftware.jsap.JSAPException;
import control.UserController;
import facade.Copter;
import facade.QuadCopter;
import bootstrap.FactoryConfigurator;
import bootstrap.MainFactory;

public class Main {

    public static void main(String[] args) {
        Arguments arguments;
        try {
            arguments = new ArgumentsParser().parse(args);
        } catch (JSAPException e) {
            System.out.println("incorrect arguments format");
            e.printStackTrace();
            return;
        }

        FactoryConfigurator configurator = new FactoryConfigurator(MainFactory.INSTANCE, arguments);
        try {
            configurator.configure();
        } catch (IllegalArgumentException e) {
            System.out.println("error in cl args, check helps");
            e.printStackTrace();
            return;
        }
        UserController userController = MainFactory.INSTANCE.getUserController();
        Copter copter = new QuadCopter();
        copter.init();
        userController.setCopter(copter);
        userController.run();
    }
}
