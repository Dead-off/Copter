package facade;

import engine.CopterController;
import sensors.Gyroscope;

public interface CopterModulesFactory {

    CopterController getCopterController();

    Gyroscope getGyroscope();

}
