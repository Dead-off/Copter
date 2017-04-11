package util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AngleTest {

    @Test
    public void addTest() {

        Angle expected = new Angle(0);
        Angle actual = new Angle(0);
        assertEquals(expected, actual);

        actual = new Angle(50).add(new Angle(-50));

        assertEquals(expected, actual);
        expected = new Angle(370);
        actual = new Angle(180).add(new Angle(190));
        assertEquals(expected, actual);

    }
}
