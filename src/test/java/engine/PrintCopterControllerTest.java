package engine;

import org.junit.Test;
import util.QuadEnginePowerContainer;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class PrintCopterControllerTest {

    @Test
    public void setEnginesPowerTest() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintCopterController printController = new PrintCopterController(os);
        printController.setEnginesPower(new QuadEnginePowerContainer(
                0.1, 0.2, 0.3, 0.4));
        String expected = "0.1  0.3\n0.2  0.4\n-------------------------------\n";
        String actual = new String(os.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(expected, actual);
        printController.close();
    }
}
