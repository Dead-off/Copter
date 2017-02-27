package engine;

public class QuadEnginePowerContainer {

    //   cw and ccw for top view
    private final double leftFrontEnginePower;//cw
    private final double leftBackEnginePower;//ccw
    private final double rightFrontEnginePower;//ccw
    private final double rightBackEnginePower;//cw

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("left front:").append(leftFrontEnginePower);
        sb.append(", left-back:").append(leftBackEnginePower);
        sb.append(", right-front:").append(rightFrontEnginePower);
        sb.append(", right-back:").append(rightBackEnginePower);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuadEnginePowerContainer that = (QuadEnginePowerContainer) o;

        if (Double.compare(that.leftFrontEnginePower, leftFrontEnginePower) != 0) return false;
        if (Double.compare(that.leftBackEnginePower, leftBackEnginePower) != 0) return false;
        if (Double.compare(that.rightFrontEnginePower, rightFrontEnginePower) != 0) return false;
        return Double.compare(that.rightBackEnginePower, rightBackEnginePower) == 0;
    }

    public boolean equalsWithEps(Object o, double eps) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuadEnginePowerContainer that = (QuadEnginePowerContainer) o;
        if (Math.abs(that.leftBackEnginePower - leftBackEnginePower) > eps) return false;
        if (Math.abs(that.leftFrontEnginePower - leftFrontEnginePower) > eps) return false;
        if (Math.abs(that.rightBackEnginePower - rightBackEnginePower) > eps) return false;
        if (Math.abs(that.rightFrontEnginePower - rightFrontEnginePower) > eps) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(leftFrontEnginePower);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(leftBackEnginePower);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rightFrontEnginePower);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rightBackEnginePower);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
