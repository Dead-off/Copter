package control;

import facade.Copter;
import org.junit.Test;
import proto.CopterDirection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class NetworkControllerTest {

    @Test
    public void runTest() {
        NetworkController controller = new NetworkController(15127);
        final AtomicReference<CopterDirection.Direction> directionReference = new AtomicReference<>();
        controller.setCopter(new Copter() {
            @Override
            public void handleDirectionChange(CopterDirection.Direction newDirection) {
                directionReference.set(newDirection);
            }

            @Override
            public void clientConnectionLost() {

            }
        });
        Thread serverThread = new Thread(controller::run);
        CopterDirection.Direction expected = CopterDirection.Direction.newBuilder()
                .setForward(1)
                .setLeft(0.3)
                .setRotateLeft(0.7)
                .setPower(0.8).build();
        serverThread.start();
        final Thread currentThread = Thread.currentThread();
        controller.setOnChannelRead(currentThread::interrupt);
        controller.setOnServerStart(currentThread::interrupt);
        try {
            Thread.sleep(1000);
            fail();
        } catch (InterruptedException ignored) {
        }
        try (Socket socket = new Socket("127.0.0.1", 15127);
             OutputStream os = socket.getOutputStream()) {
            os.write(expected.toByteArray());
            os.flush();
            try {
                Thread.sleep(1000);
                fail();
            } catch (InterruptedException ignored) {
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        serverThread.interrupt();
        CopterDirection.Direction actual = directionReference.get();
        assertEquals(expected, actual);
    }
}
