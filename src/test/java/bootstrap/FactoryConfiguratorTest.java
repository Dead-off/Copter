package bootstrap;

import com.martiansoftware.jsap.JSAPException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FactoryConfiguratorTest {

    @Test
    public void configureFactoryFromCLArgsTest() throws JSAPException {
        MainFactory configureFactory = MainFactory.INSTANCE;
        Arguments arguments = new Arguments("CL", "GPIO", 0, "REAL");
        FactoryConfigurator factoryConfigurator = new FactoryConfigurator(configureFactory, arguments);
        factoryConfigurator.configure();
        assertTrue(configureFactory.isConfigured());
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectCLArgs() {
        MainFactory configureFactory = MainFactory.INSTANCE;
        Arguments arguments = new Arguments("CL", "g", 0, "REAL");
        FactoryConfigurator factoryConfigurator = new FactoryConfigurator(configureFactory, arguments);
        factoryConfigurator.configure();
    }
}
