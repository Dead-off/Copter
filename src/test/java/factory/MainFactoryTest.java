package factory;

import engine.EnginesControlFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainFactoryTest {

    @Test
    public void setEnginesTest() {
        EnginesControlFactory actual = () -> null;
        MainFactory.INSTANCE.setEnginesControlFactory(actual);
        EnginesControlFactory expected = MainFactory.INSTANCE.getEnginesControlFactory();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getNonConfiguredTest() {
        MainFactory.INSTANCE.setEnginesControlFactory(null);
        MainFactory.INSTANCE.getEnginesControlFactory();
    }

}
