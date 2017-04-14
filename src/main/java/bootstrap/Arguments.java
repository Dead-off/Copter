package bootstrap;

public class Arguments {

    public final String userController;
    public final String outputController;
    public final String sensorFactory;
    public final int port;

    public Arguments(String userController, String outputController, int port, String sensorFactory) {
        this.userController = userController;
        this.outputController = outputController;
        this.port = port;
        this.sensorFactory = sensorFactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arguments arguments = (Arguments) o;

        if (port != arguments.port) return false;
        if (!userController.equals(arguments.userController)) return false;
        return outputController.equals(arguments.outputController);
    }

    @Override
    public int hashCode() {
        int result = userController.hashCode();
        result = 31 * result + outputController.hashCode();
        result = 31 * result + port;
        return result;
    }
}
