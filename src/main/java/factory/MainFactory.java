package factory;

import engine.EnginesControlFactory;

public class MainFactory {

    public static final MainFactory INSTANCE = new MainFactory();

    private EnginesControlFactory enginesControlFactory;

    private MainFactory() {
    }

    public EnginesControlFactory getEnginesControlFactory() {
        if (enginesControlFactory == null) {
            throw new RuntimeException("Main factory is not configured!");
        }
        return enginesControlFactory;
    }

    public void setEnginesControlFactory(EnginesControlFactory enginesControlFactory) {
        this.enginesControlFactory = enginesControlFactory;
    }
}
