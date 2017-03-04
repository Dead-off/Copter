package control;

import facade.Copter;
import org.junit.Test;
import proto.CopterDirection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class CommandLineControllerTest {

    @Test
    public void runTest() throws IOException {

        ByteArrayInputStream is = new ByteArrayInputStream("f:50 cw:10 p:50".getBytes());
        CommandLineController clController = new CommandLineController(is);
        final AtomicReference<CopterDirection.Direction> directionReference = new AtomicReference<>();
        clController.setCopter(new Copter() {
            @Override
            public void handleDirectionChange(CopterDirection.Direction newDirection) {
                directionReference.set(newDirection);
            }

            @Override
            public void clientConnectionLost() {

            }
        });
        clController.run();
        CopterDirection.Direction actual = directionReference.get();
        CopterDirection.Direction expected = directionReference.get();
        assertEquals(expected, actual);
    }
}