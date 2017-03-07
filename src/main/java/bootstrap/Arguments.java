package bootstrap;

public class Arguments {

    public final String userController;
    public final String outputController;
    public final int port;

    public Arguments(String userController, String outputController, int port) {
        this.userController = userController;
        this.outputController = outputController;
        this.port = port;
    }
}
