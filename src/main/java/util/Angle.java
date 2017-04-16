package util;

public class Angle {

    private final static double DEGREES_360 = 360;

    private final double degrees;

    public Angle(double degrees) {
        this.degrees = degrees;
    }

    public double getDegrees() {
        return degrees;
    }

    public Angle add(Angle other) {
        return new Angle(this.getDegrees() + other.getDegrees());
    }

    public Angle subtract(Angle other) {
        return new Angle(this.getDegrees() - other.getDegrees());
    }

    public Angle getNormalized() {
        double result = getDegrees();
        while (result <= -180) {
            result += DEGREES_360;
        }
        while (result > 180) {
            result -= DEGREES_360;
        }
        return new Angle(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Angle angle = (Angle) o;

        return Double.compare(angle.degrees, degrees) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(degrees);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return String.valueOf(getDegrees());
    }
}
