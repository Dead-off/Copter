package engine;

public class QuadEnginePowerContainer {

    private final double leftFrontEnginePower;
    private final double leftBackEnginePower;
    private final double rightFrontEnginePower;
    private final double rightBackEnginePower;

    public QuadEnginePowerContainer(double leftFrontEnginePower, double leftBackEnginePower, double rightFrontEnginePower, double rightBackEnginePower) {
        this.leftFrontEnginePower = leftFrontEnginePower;
        this.leftBackEnginePower = leftBackEnginePower;
        this.rightFrontEnginePower = rightFrontEnginePower;
        this.rightBackEnginePower = rightBackEnginePower;
    }

    public double getLeftFrontEnginePower() {
        return leftFrontEnginePower;
    }

    public double getLeftBackEnginePower() {
        return leftBackEnginePower;
    }

    public double getRightFrontEnginePower() {
        return rightFrontEnginePower;
    }

    public double getRightBackEnginePower() {
        return rightBackEnginePower;
    }
}
