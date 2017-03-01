package factory;

import engine.EnginesControlFactory;
import org.junit.Test;
import sun.applet.Main;

public class MainFactoryTest {

    @Test
    public void setEnginesTest() {
        EnginesControlFactory actual = ()->null;
        MainFactory.INSTANCE.setEnginesControlFactory(actual);
        EnginesControlFactory expected = MainFactory.INSTANCE.getEnginesControlFactory();
    }

    @Test(expected = RuntimeException.class)
    public void getNonConfiguredTest() {
        MainFactory.INSTANCE.getEnginesControlFactory();
    }

}
