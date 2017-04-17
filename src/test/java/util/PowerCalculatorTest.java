package util;

import org.junit.Assert;
import org.junit.Test;

public class PowerCalculatorTest extends Assert {

    private final static double EPS = 0.000001;
    private final static double DEFAULT_POWER = QuadPowerCalculator.DEFAULT_POWER;
    private final static double MULTIPLIER = QuadPowerCalculator.P;

    @Test
    public void calculatePowerEngineTest() {
        PowerCalculator calculator = new QuadPowerCalculator();
        RotationAngles angles = new RotationAngles(10, 10, 10);
        QuadEnginePowerContainer actual = calculator.calculateEnginesPower(angles, 0);
        QuadEnginePowerContainer expected = new QuadEnginePowerContainer(0, 0, 0, 0);
        assertTrue(actual.equalsWithEps(expected, EPS));

        calculator = new QuadPowerCalculator();
        angles = new RotationAngles(360, 360, -360);
        actual = calculator.calculateEnginesPower(angles, 1);
        expected = new QuadEnginePowerContainer(DEFAULT_POWER, DEFAULT_POWER, DEFAULT_POWER, DEFAULT_POWER);
        assertTrue(actual.equalsWithEps(expected, EPS));

        calculator = new QuadPowerCalculator();
        actual = calculator.calculateEnginesPower(new RotationAngles(10, 0, 0), 0.8);
        expected = new QuadEnginePowerContainer(
                DEFAULT_POWER * 0.8 / (1 + MULTIPLIER * 10.0 / 180)
                , DEFAULT_POWER * 0.8 * (1 + MULTIPLIER * 10.0 / 180)
                , DEFAULT_POWER * 0.8 / (1 + MULTIPLIER * 10.0 / 180)
                , DEFAULT_POWER * 0.8 * (1 + MULTIPLIER * 10.0 / 180));
        assertTrue(actual.equalsWithEps(expected, EPS));

        calculator = new QuadPowerCalculator();
        actual = calculator.calculateEnginesPower(new RotationAngles(10, 5, -20), 0.9);
        double xCorrect = (1 + MULTIPLIER * 10.0 / 180);
        double yCorrect = (1 + MULTIPLIER * 5.0 / 180);
        double zCorrect = (1 + MULTIPLIER * 20.0 / 180);
        expected = new QuadEnginePowerContainer(
                DEFAULT_POWER * 0.9 * zCorrect / (xCorrect * yCorrect)
                , DEFAULT_POWER * 0.9 * xCorrect / (zCorrect * yCorrect)
                , DEFAULT_POWER * 0.9 * yCorrect / (zCorrect * xCorrect)
                , DEFAULT_POWER * 0.9 * xCorrect * yCorrect * zCorrect);
        assertTrue(expected.equalsWithEps(actual, EPS));

        calculator = new QuadPowerCalculator();
        actual = calculator.calculateEnginesPower(new RotationAngles(-180, 0, 0), 0.8);
        xCorrect = (1 + MULTIPLIER * 180 / 180);
        expected = new QuadEnginePowerContainer(
                DEFAULT_POWER * 0.8 / xCorrect
                , DEFAULT_POWER * 0.8 * xCorrect
                , DEFAULT_POWER * 0.8 / xCorrect
                , DEFAULT_POWER * 0.8 * xCorrect);
        assertTrue(expected.equalsWithEps(actual, EPS));
    }
}
