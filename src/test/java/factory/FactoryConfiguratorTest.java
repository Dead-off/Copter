package factory;

import com.martiansoftware.jsap.JSAPException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FactoryConfiguratorTest {

    @Test
    public void configureFactoryFromCLArgsTest() throws JSAPException {
        MainFactory configureFactory = MainFactory.INSTANCE;
        FactoryConfigurator factoryConfigurator = new FactoryConfigurator(configureFactory);
        factoryConfigurator.configureFactoryFromCLArgs(new String[]{"-c", "GPIO", "-u", "CL"});
        assertTrue(configureFactory.isConfigured());
    }

    @Test(expected = JSAPException.class)
    public void incorrectCLArgs() throws JSAPException {
        MainFactory configureFactory = MainFactory.INSTANCE;
        FactoryConfigurator factoryConfigurator = new FactoryConfigurator(configureFactory);
        factoryConfigurator.configureFactoryFromCLArgs(new String[]{"-c", "g", "-u", "CL"});
    }
}
