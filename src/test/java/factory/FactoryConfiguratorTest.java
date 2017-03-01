package factory;

import org.junit.Test;

public class FactoryConfiguratorTest {

    @Test
    public void configureFactoryFromCLArgsTest() {
        MainFactory.INSTANCE.getEnginesControlFactory();
    }
}
