import org.junit.Test;

import static main.PeakTraffic.PeakTrafficResult;
import static main.PeakTraffic.PeakTrafficResult.*;
import static main.PeakTraffic.validateInput;
import static org.junit.Assert.assertEquals;

public class PeakTrafficTest {

    @Test
    public void testValidateInputWrongArgsCount() {
        final String[] args = new String[]{"one", "two"};
        final PeakTrafficResult validationResult = validateInput(args);
        assertEquals("Expected too many arguments error when providing two input values", TOO_MANY_ARGUMENTS, validationResult);
    }


    @Test
    public void testValidateInputNullArgs() {
        final PeakTrafficResult validationResult = validateInput(null);
        assertEquals("Expected too many arguments error when providing two input values", NO_FILE_PROVIDED, validationResult);
    }

    @Test
    public void testValidateInputNoArgs() {
        final String[] args = new String[]{};
        final PeakTrafficResult validationResult = validateInput(args);
        assertEquals("Expected too many arguments error when providing two input values", NO_FILE_PROVIDED, validationResult);
    }

    @Test
    public void testValidateInputWrongFile() {
        final String[] args = new String[]{"one"};
        final PeakTrafficResult validationResult = validateInput(args);
        assertEquals("Expected too many arguments error when providing two input values", NO_VALID_PATH, validationResult);
    }

    @Test
    public void testValidateInputOk() {
        final String[] args = new String[]{"./src/test/resources/inputFacebook.txt"};
        final PeakTrafficResult validationResult = validateInput(args);
        assertEquals("Expected too many arguments error when providing two input values", OK, validationResult);
    }

}