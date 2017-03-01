import proto.CopterDirection;

public interface Copter<T> {

    void handleDirectionChange(CopterDirection newDirection);

    void clientConnectionLost();

}
