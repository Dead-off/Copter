import proto.CopterDirection;

public interface QuadCopter {

    void handleDirectionChange(CopterDirection newDirection);

    void clientConnectionLost();

}
