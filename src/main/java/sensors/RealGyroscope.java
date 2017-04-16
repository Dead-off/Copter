package sensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import util.RotationAngles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RealGyroscope implements Gyroscope {

    private final static String FILE_PATH = "/home/pi/Copter/gyro.data";
    private final static String SEPARATOR = ":";
    private final static double GAMMA = 0.90;

    public final static Gyroscope INSTANCE = new RealGyroscope();
    private RotationAngles lastAngles = RotationAngles.ZERO;

    private RealGyroscope() {

    }

    @Override
    public RotationAngles getData() {
        RotationAngles newAngles = getAngles();
        RotationAngles result = new RotationAngles(
                GAMMA * lastAngles.getX().getDegrees() + (1 - GAMMA) * newAngles.getX().getDegrees(),
                GAMMA * lastAngles.getY().getDegrees() + (1 - GAMMA) * newAngles.getY().getDegrees(),
                GAMMA * lastAngles.getZ().getDegrees() + (1 - GAMMA) * newAngles.getZ().getDegrees()
        );
        System.out.println(newAngles);
        System.out.println(result);
        System.out.println("--------------------------------------------");
        lastAngles = result;
        return result;
    }

    public RotationAngles getAngles() {
        List<String> content;
        try {
            content = Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
            return RotationAngles.ZERO;
        }
        if (content.size() == 0) {
            System.out.println("read error, file is empty");
            return RotationAngles.ZERO;
        }
        String data = content.get(0);
        String[] xyz = data.split(SEPARATOR);
        if (xyz.length != 3) {
            return RotationAngles.ZERO;
        }
        try {
            double x = Double.parseDouble(xyz[0]);
            double y = Double.parseDouble(xyz[1]);
            double z = Double.parseDouble(xyz[2]);
            return new RotationAngles(x, y, z);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RotationAngles.ZERO;
    }
}