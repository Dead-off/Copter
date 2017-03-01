package engine;

import org.junit.Assert;
import org.junit.Test;
import proto.CopterDirection;

public class PowerCalculatorTest extends Assert {

    private final static double EPS = 0.000001;
    private final static double DEFAULT_POWER = QuadroPowerCalculator.DEFAULT_POWER;
    private final static double MULTIPLIER = QuadroPowerCalculator.MAX_MULTIPLIER;

    @Test
    public void calculatePowerEngineTest() {
        PowerCalculator calculator = new QuadroPowerCalculator();

        CopterDirection.Direction.Builder directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setPower(0);
        QuadEnginePowerContainer actual = calculator.calculateEnginesPower(directionBuilder.build());
        QuadEnginePowerContainer expected = new QuadEnginePowerContainer(0, 0, 0, 0);
        assertTrue(actual.equalsWithEps(expected, EPS));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setPower(1);
        actual = calculator.calculateEnginesPower(directionBuilder.build());
        expected = new QuadEnginePowerContainer(DEFAULT_POWER, DEFAULT_POWER, DEFAULT_POWER, DEFAULT_POWER);
        assertTrue(actual.equalsWithEps(expected, EPS));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setPower(0.7);
        directionBuilder.setRotateRight(0.6);
        directionBuilder.setForward(0.5);
        actual = calculator.calculateEnginesPower(directionBuilder.build());
        expected = new QuadEnginePowerContainer(
                DEFAULT_POWER * 0.7 / ((1 + MULTIPLIER * 0.5) * (1 + MULTIPLIER * 0.6))
                , DEFAULT_POWER * (1 + MULTIPLIER * 0.5) * (1 + MULTIPLIER * 0.6) * 0.7
                , DEFAULT_POWER * 0.7 * (1 + MULTIPLIER * 0.6) / (1 + MULTIPLIER * 0.5)
                , DEFAULT_POWER * 0.7 * (1 + MULTIPLIER * 0.5) / (1 + MULTIPLIER * 0.6));
        assertTrue(expected.equalsWithEps(actual, EPS));

        directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setPower(0.9);
        directionBuilder.setLeft(1.0);
        actual = calculator.calculateEnginesPower(directionBuilder.build());
        expected = new QuadEnginePowerContainer(
                DEFAULT_POWER * 0.9 / (1 + MULTIPLIER)
                , DEFAULT_POWER * 0.9 / (1 + MULTIPLIER)
                , DEFAULT_POWER * 0.9 * (1 + MULTIPLIER)
                , DEFAULT_POWER * 0.9 * (1 + MULTIPLIER));
        assertTrue(expected.equalsWithEps(actual, EPS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatePowerEngineTestIllegalDirection() {
        PowerCalculator calculator = new QuadroPowerCalculator();
        CopterDirection.Direction.Builder directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setLeft(0.3);
        directionBuilder.setRight(0.3);
        calculator.calculateEnginesPower(directionBuilder.build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatePowerEngineTestNANDirection() {
        PowerCalculator calculator = new QuadroPowerCalculator();
        CopterDirection.Direction.Builder directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setLeft(Double.NaN);
        calculator.calculateEnginesPower(directionBuilder.build());
    }
}
