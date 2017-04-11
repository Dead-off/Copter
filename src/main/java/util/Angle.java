package util;

public class Angle {

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
}
