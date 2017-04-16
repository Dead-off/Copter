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

    public final static Gyroscope INSTANCE = new RealGyroscope();

    private RealGyroscope() {

    }

    @Override
    public RotationAngles getData() {
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
        if (xyz.length != 2) {
            return RotationAngles.ZERO;
        }
        try {
            double x = Double.parseDouble(xyz[0]);
            double y = Double.parseDouble(xyz[1]);
            double z = Double.parseDouble(xyz[2]);
            System.out.println(x + " " + y + " " + z);
            return new RotationAngles(x,y,z);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RotationAngles.ZERO;
    }
}