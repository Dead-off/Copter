package util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AngleTest {

    @Test
    public void addTest() {

        Angle expected = new Angle(0);
        Angle actual = new Angle(0).add(new Angle(0));
        assertEquals(expected, actual);

        actual = new Angle(50).add(new Angle(-50));

        assertEquals(expected, actual);
        expected = new Angle(370);
        actual = new Angle(180).add(new Angle(190));
        assertEquals(expected, actual);
    }

    @Test
    public void subtractTest() {
        Angle expected = new Angle(0);
        Angle actual = new Angle(10).subtract(new Angle(10));
        assertEquals(expected, actual);

        expected = new Angle(30);
        actual = new Angle(60).subtract(new Angle(30));
        assertEquals(expected, actual);

        expected = new Angle(-10);
        actual = new Angle(-5).subtract(new Angle(5));
        assertEquals(expected, actual);
    }

    @Test
    public void getNormalizedTest() {
        Angle expected = new Angle(180);
        Angle actual = new Angle(180).getNormalized();
        assertEquals(expected, actual);

        expected = new Angle(-179.9);
        actual = new Angle(-179.9).getNormalized();
        assertEquals(expected, actual);


        expected = new Angle(0);
        actual = new Angle(-360).getNormalized();
        assertEquals(expected, actual);

        expected = new Angle(0);
        actual = new Angle(360).getNormalized();
        assertEquals(expected, actual);

        expected = new Angle(35);
        actual = new Angle(395).getNormalized();
        assertEquals(expected, actual);


        expected = new Angle(70);
        actual = new Angle(430).getNormalized();
        assertEquals(expected, actual);


        expected = new Angle(90);
        actual = new Angle(1170).getNormalized();
        assertEquals(expected, actual);

        expected = new Angle(-5);
        actual = new Angle(-725).getNormalized();
        assertEquals(expected, actual);
    }

}
