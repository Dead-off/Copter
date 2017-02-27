package engine;

public interface EnginesControlFactory {

    CopterController getCopterController();

    PowerCalculator getPowerCalculator();

}
