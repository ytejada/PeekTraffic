package data.provider;

import data.FacebookData;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class FaceBookDataProviderTest {

    @Test
    public void testDataFromString() throws Exception {


        //Create a new file with the test content
        final PrintWriter writer;
        try {
            writer = new PrintWriter("./src/test/resources/inputFacebook.txt", "UTF-8");
            writer.println("Thu Dec 11 17:53:01 PST 2008    a@facebook.com    b@facebook.com");
            writer.println("Thu Dec 11 17:53:12 PST 2008    f@facebook.com    e@facebook.com");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Exception raised when crating the test file");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail("Exception raised when crating the test file");
        }


        //Create facebook data from data above
        final FacebookData originalData = new FacebookDataImpl("Thu Dec 11 17:53:01 PST 2008", "a@facebook.com", "b@facebook.com");

        //Retrieve the data contained on the first line of the file, and compare with the original one.
        try {
            final FaceBookDataProvider provider = new FaceBookDataProvider("./src/test/resources/inputFacebook.txt");
            assertNotNull("Returned data cannot be null", provider.getAllData());
            assertEquals("Data retrieved from a non valid file should not be empty", 2, provider.getAllData().size());
            assertEquals("First element delivered by the provide does not match.", originalData, provider.getAllData().get(0));

        } catch (Exception e) {
            fail("No exception was expected when getting elements from a valid file");
        }
    }
}