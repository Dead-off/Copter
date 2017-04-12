package bootstrap;

import control.CommandLineController;
import facade.CopterModulesFactory;
import facade.PrintControllerFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MainFactoryTest {

    @Test
    public void setEnginesTest() {
        MainFactory.INSTANCE.setCopterModulesFactory(new PrintControllerFactory());
        MainFactory.INSTANCE.setUserController(new CommandLineController());
        assertTrue(MainFactory.INSTANCE.isConfigured());
    }

    @Test(expected = RuntimeException.class)
    public void getNonConfiguredTest() {
        MainFactory.INSTANCE.setCopterModulesFactory(null);
        MainFactory.INSTANCE.getCopterModulesFactory();
    }

}
