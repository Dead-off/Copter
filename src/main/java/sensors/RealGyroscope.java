package sensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import util.RotationAngles;

import java.io.IOException;

public class RealGyroscope implements Gyroscope {

    public final static Gyroscope INSTANCE = new RealGyroscope();
    private final I2CDevice device;

    private RealGyroscope() {
        I2CDevice deviceLocal = null;
        try {
            I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
            deviceLocal = bus.getDevice(0x69);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.device = deviceLocal;
    }

    @Override
    public RotationAngles getData() {
        if (device == null) {
            return RotationAngles.ZERO;
        }
        byte[] gyroData = new byte[6];
        try {
            gyroData[0] = (byte) device.read(0x28);
            gyroData[1] = (byte) device.read(0x29);
            gyroData[2] = (byte) device.read(0x2a);
            gyroData[3] = (byte) device.read(0x2b);
            gyroData[4] = (byte) device.read(0x2c);
            gyroData[5] = (byte) device.read(0x2d);
        } catch (IOException e) {
            e.printStackTrace();
            return RotationAngles.ZERO;
        }
        int xAngle = 256 * (int) gyroData[0] + (int) gyroData[1];
        int yAngle = 256 * (int) gyroData[2] + (int) gyroData[3];
        int zAngle = 256 * (int) gyroData[4] + (int) gyroData[5];
        System.out.println("x=" + xAngle);
        System.out.println("y=" + yAngle);
        System.out.println("z=" + zAngle);
        return RotationAngles.ZERO;
    }
}