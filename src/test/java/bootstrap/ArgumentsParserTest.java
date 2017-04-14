package bootstrap;

import com.martiansoftware.jsap.JSAPException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgumentsParserTest {

    private ArgumentsParser parser;

    @Before
    public void init() {
        this.parser = new ArgumentsParser();
    }

    @Test
    public void parseTest() throws JSAPException {
        String[] args = new String[]{"-c", "GPIO", "-u", "REMOTE", "-p", "1234", "-s", "MOCK"};
        Arguments actual = parser.parse(args);
        Arguments expected = new Arguments("REMOTE", "GPIO", 1234, "MOCK");
        assertEquals(expected, actual);

        args = new String[]{"-u", "pew", "-c", "pewpew", "-s", "REAL"};
        actual = parser.parse(args);
        expected = new Arguments("pew", "pewpew", ArgumentsParser.DEFAULT_PORT, "REAL");
        assertEquals(expected, actual);
    }

    @Test(expected = JSAPException.class)
    public void parseTestException() throws JSAPException {
        String[] args = new String[]{"-u", "REMOTE", "-p", "1234"};
        parser.parse(args);
    }
}
