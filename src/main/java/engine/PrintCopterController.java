package engine;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class PrintCopterController implements CopterController {

    private final OutputStream out;

    public PrintCopterController() {
        out = System.out;
    }

    public PrintCopterController(OutputStream out) {
        this.out = out;
    }

    @Override
    public void setEnginesPower(QuadEnginePowerContainer powerContainer) {
        StringBuilder sb = new StringBuilder();
        sb.append(powerContainer.getLeftFrontEnginePower())
                .append("  ")
                .append(powerContainer.getRightFrontEnginePower())
                .append("\n")
                .append(powerContainer.getLeftBackEnginePower())
                .append("  ")
                .append(powerContainer.getRightBackEnginePower())
                .append("\n-------------------------------\n");
        try {
            out.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error in shutdown engine controller");
        }
    }
}
