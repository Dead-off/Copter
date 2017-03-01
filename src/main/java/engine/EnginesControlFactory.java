package engine;

public interface EnginesControlFactory<T> {

    CopterController<T> getCopterController();

}
