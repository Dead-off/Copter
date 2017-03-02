package control;

import facade.Copter;
import proto.CopterDirection;

import java.io.InputStream;
import java.util.Scanner;

public class CommandLineController implements UserController {

    private Copter copter;
    private final InputStream inputStream;

    public CommandLineController() {
        this.inputStream = System.in;
    }

    CommandLineController(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        final Scanner scanner = new Scanner(inputStream);
        while (true) {
            System.out.println("type f/b:[0-100], l/r:[0-100], cw/ccw:[0-100], p:[0-100]");
            System.out.println("e.g. max power, fly forward 60% and rotate cw 50%");
            System.out.println("f:60 cw:50 p:100");
            System.out.println("exit for exit and s for fast set all direction to zero and power to 40%");
            String command = scanner.nextLine();
            if ("exit".equalsIgnoreCase(command)) {
                break;
            }
            try {
                CopterDirection.Direction direction = parseCommand(command);
                copter.handleDirectionChange(direction);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private CopterDirection.Direction parseCommand(String command) throws IllegalArgumentException {
        CopterDirection.Direction.Builder builder = CopterDirection.Direction.newBuilder();
        if ("s".equalsIgnoreCase(command)) {
            return builder.setPower(0.3).build();
        }
        for (String s : command.split(" ")) {
            String[] dv = s.split(":");
            if (dv.length != 2) {
                throw new IllegalArgumentException("incorrect command format");
            }
            double value;
            String direction = dv[0];
            try {
                value = Double.parseDouble(dv[1]) / 100;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("incorrect command format");
            }
            switch (direction) {
                case "f":
                    builder.setForward(value);
                    break;
                case "b":
                    builder.setBackward(value);
                    break;
                case "l":
                    builder.setLeft(value);
                    break;
                case "r":
                    builder.setRight(value);
                    break;
                case "cw":
                    builder.setRotateRight(value);
                    break;
                case "ccw":
                    builder.setRotateLeft(value);
                    break;
                case "p":
                    builder.setPower(value);
                    break;
                default:
                    throw new IllegalArgumentException("incorrect command format");
            }
        }
        return builder.build();
    }

    @Override
    public void setCopter(Copter copter) {
        this.copter = copter;
    }

    @Override
    public Copter getCopter() {
        return copter;
    }
}
