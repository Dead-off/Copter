package bootstrap;

import control.CommandLineController;
import facade.CopterModulesFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MainFactoryTest {

    @Test
    public void setEnginesTest() {
        CopterModulesFactory actual = () -> null;
        MainFactory.INSTANCE.setCopterModulesFactory(actual);
        MainFactory.INSTANCE.setUserController(new CommandLineController());
        assertTrue(MainFactory.INSTANCE.isConfigured());
    }

    @Test(expected = RuntimeException.class)
    public void getNonConfiguredTest() {
        MainFactory.INSTANCE.setCopterModulesFactory(null);
        MainFactory.INSTANCE.getCopterModulesFactory();
    }

}
