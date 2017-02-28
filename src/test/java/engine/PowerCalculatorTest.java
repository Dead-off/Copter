package engine;

import org.junit.Assert;
import org.junit.Test;
import proto.CopterDirection;

public class PowerCalculatorTest extends Assert {

    private final static double EPS = 0.000001;
    private final static double DEFAULT_POWER = PowerCalculatorImpl.DEFAULT_POWER;
    private final static double MULTIPLIER = PowerCalculatorImpl.MAX_MULTIPLIER;

    @Test
    public void calculatePowerEngineTest() {
        PowerCalculator calculator = new PowerCalculatorImpl();

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
        double defaultPower = PowerCalculatorImpl.DEFAULT_POWER;
        actual = calculator.calculateEnginesPower(directionBuilder.build());
        expected = new QuadEnginePowerContainer(
                defaultPower * 0.7 / ((1 + MULTIPLIER * 0.5) * (1 + MULTIPLIER * 0.6))
                , defaultPower * (1 + MULTIPLIER * 0.5) * (1 + MULTIPLIER * 0.6) * 0.7
                , defaultPower * 0.7 * (1 + MULTIPLIER * 0.6) / (1 + MULTIPLIER * 0.5)
                , defaultPower * 0.7 * (1 + MULTIPLIER * 0.5) / (1 + MULTIPLIER * 0.6));
        assertEquals(expected.getLeftFrontEnginePower(), actual.getLeftFrontEnginePower(), EPS);
        assertEquals(expected.getLeftBackEnginePower(), actual.getLeftBackEnginePower(), EPS);
        assertEquals(expected.getRightBackEnginePower(), actual.getRightBackEnginePower(), EPS);
        assertEquals(expected.getRightFrontEnginePower(), actual.getRightFrontEnginePower(), EPS);

    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatePowerEngineTestIllegalDirection() {
        PowerCalculator calculator = new PowerCalculatorImpl();
        CopterDirection.Direction.Builder directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setLeft(0.3);
        directionBuilder.setRight(0.3);
        calculator.calculateEnginesPower(directionBuilder.build());
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculatePowerEngineTestNANDirection() {
        PowerCalculator calculator = new PowerCalculatorImpl();
        CopterDirection.Direction.Builder directionBuilder = CopterDirection.Direction.newBuilder();
        directionBuilder.setLeft(Double.NaN);
        calculator.calculateEnginesPower(directionBuilder.build());
    }
}
