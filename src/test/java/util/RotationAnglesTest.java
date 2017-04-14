package util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotationAnglesTest {

    @Test
    public void getNormalizedTest() {
        RotationAngles expected = new RotationAngles(10, 0, 50);
        RotationAngles actual = new RotationAngles(370, 0, -310).getNormalized();
        assertEquals(expected, actual);

        expected = new RotationAngles(10, 0, 180);
        actual = new RotationAngles(370, 360, -180).getNormalized();
        assertEquals(expected, actual);
    }
}
