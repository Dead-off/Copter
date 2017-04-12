package sensors;

import util.Angle;

public class RotationAngles {

    /*
    система координат - правая
    OZ - вверх
    OY - вперед
    OX - вправо
    Положительные углы - поворот по часовой стрелке вокруг соответствуещй оси, если смотреть во положительному направлению
    Например для OX x=45 градусов значит, что коптер находится под наклоном назад
    если выполнить поворот системы координат, то получится, что OZ указывает вверх и назад, OY - вперед и вверх
    */
    private final Angle x;
    private final Angle y;
    private final Angle z;

    public RotationAngles(Angle x, Angle y, Angle z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
}
