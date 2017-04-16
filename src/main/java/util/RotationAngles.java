package util;

public class RotationAngles {

    /*
    система координат - правая
    OZ - вверх
    OY - вперед
    OX - вправо
    Положительные углы - поворот по часовой стрелке вокруг соответствуещй оси, если смотреть по положительному направлению
    Например для OX x=45 градусов значит, что коптер находится под наклоном назад
    если выполнить поворот системы координат, то получится, что OZ указывает вверх и назад, OY - вперед и вверх
    */
    private final Angle x;
    private final Angle y;
    private final Angle z;

    public final static RotationAngles ZERO = new RotationAngles(0, 0, 0);

    public RotationAngles(Angle x, Angle y, Angle z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RotationAngles(double x, double y, double z) {
        this(new Angle(x), new Angle(y), new Angle(z));
    }

    public Angle getX() {
        return x;
    }

    public Angle getY() {
        return y;
    }

    public Angle getZ() {
        return z;
    }

    public RotationAngles getNormalized() {
        return new RotationAngles(x.getNormalized(), y.getNormalized(), z.getNormalized());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RotationAngles that = (RotationAngles) o;

        if (!x.equals(that.x)) return false;
        if (!y.equals(that.y)) return false;
        return z.equals(that.z);
    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        result = 31 * result + z.hashCode();
        return result;
    }

    public RotationAngles add(RotationAngles other) {
        return new RotationAngles(x.add(other.getX()), y.add(other.getY()), z.add(other.getZ()));
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
