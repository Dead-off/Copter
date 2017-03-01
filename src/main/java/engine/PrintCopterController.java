package engine;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class PrintCopterController implements CopterController<QuadEnginePowerContainer>, Closeable {

    private final OutputStream out;

    public PrintCopterController() {
        out = System.out;
    }

    public PrintCopterController(OutputStream out) {
        this.out = out;
    }

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {
        try {
            out.write(powerContainer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
