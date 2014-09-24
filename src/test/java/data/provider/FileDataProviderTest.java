package data.provider;

import base.PeekTrafficBaseTest;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class FileDataProviderTest extends PeekTrafficBaseTest {


    @Test
    public void testFileDataProviderNotValidFile() {
        try {
            final FileDataProvider<String> provider = new PeekTrafficBaseTest.DirectParsingDataProviderAdapter("./src/test/resources/input2.txt");
            assertNotNull("Returned data cannot be null", provider.getAllData());
            assertTrue("Data retrieved from a non valid file should be empty", provider.getAllData().isEmpty());

        } catch (Exception e) {
            fail("No exception was expected when getting elements from a non valid file");
        }
    }

    @Test
    public void testFileDataProviderValidFile() {


        //Create a new file with the test content
        final PrintWriter writer;
        try {
            writer = new PrintWriter("./src/test/resources/input.txt", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Exception raised when crating the test file");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail("Exception raised when crating the test file");
        }

        try {
            final FileDataProvider<String> provider = new PeekTrafficBaseTest.DirectParsingDataProviderAdapter("./src/test/resources/input.txt");
            assertNotNull("Returned data cannot be null", provider.getAllData());
            assertEquals("Data retrieved from a non valid file should not be empty", 2, provider.getAllData().size());
            assertEquals("First element delivered by the provide does not match.", "The first line", provider.getAllData().get(0));

        } catch (Exception e) {
            fail("No exception was expected when getting elements from a valid file");
        }

    }
}