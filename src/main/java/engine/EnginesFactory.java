package engine;

import engine.CopterController;
import sensors.Gyroscope;

public interface EnginesFactory {

    CopterController getCopterController();

}
