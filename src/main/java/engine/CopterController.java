package engine;

import util.QuadEnginePowerContainer;

public interface CopterController {

    void setEnginesPower(QuadEnginePowerContainer powerContainer);

    void close();

}
