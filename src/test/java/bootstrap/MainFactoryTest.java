package bootstrap;

import control.CommandLineController;
import engine.PrintControllerFactory;
import org.junit.Test;
import sensors.RealSensorFactory;

import static org.junit.Assert.assertTrue;

public class MainFactoryTest {

    @Test
    public void setEnginesTest() {
        MainFactory.INSTANCE.setCopterModulesFactory(new PrintControllerFactory());
        MainFactory.INSTANCE.setUserController(new CommandLineController());
        MainFactory.INSTANCE.setSensorFactory(new RealSensorFactory());
        assertTrue(MainFactory.INSTANCE.isConfigured());
    }

    @Test(expected = RuntimeException.class)
    public void getNonConfiguredTest() {
        MainFactory.INSTANCE.setCopterModulesFactory(null);
        MainFactory.INSTANCE.getCopterModulesFactory();
    }

}
