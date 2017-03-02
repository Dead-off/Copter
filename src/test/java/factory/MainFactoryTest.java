package factory;

import control.CommandLineController;
import control.UserController;
import engine.EnginesControlFactory;
import facade.Copter;
import org.junit.Test;
import proto.CopterDirection;

import static org.junit.Assert.assertTrue;

public class MainFactoryTest {

    @Test
    public void setEnginesTest() {
        EnginesControlFactory actual = () -> null;
        MainFactory.INSTANCE.setEnginesControlFactory(actual);
        MainFactory.INSTANCE.setUserController(new CommandLineController());
        assertTrue(MainFactory.INSTANCE.isConfigured());
    }

    @Test(expected = RuntimeException.class)
    public void getNonConfiguredTest() {
        MainFactory.INSTANCE.setEnginesControlFactory(null);
        MainFactory.INSTANCE.getEnginesControlFactory();
    }

}
