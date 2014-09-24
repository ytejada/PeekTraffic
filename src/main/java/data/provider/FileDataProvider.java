package data.provider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Abstract class which generates Data of type T from the given File source. For each line defined in the given File,
 * the subclass {@link #dataFromString(String)} method will be called for building up valid data upon the given String.
 */
public abstract class FileDataProvider<T> {

    final String mSourceFile;

    /**
     * Constructs a new FileDataProvider which will take the parsing information from the given source file.
     *
     * @param mSourceFile
     */
    public FileDataProvider(final String mSourceFile) {
        this.mSourceFile = mSourceFile;
    }

    /**
     * Returns a T data parsed from the given String.
     *
     * @param aStringToken
     * @return
     */
    public abstract T dataFromString(final String aStringToken);

    /**
     * Retuns a List<T> representing all the parsed data the source file contained. If any error occurs, then an empty
     * List will be returned instead.
     */
    public List<T> getAllData() {

        FileInputStream inputStream = null;
        Scanner sc = null;
        final List<T> data = new ArrayList<T>();

        //make use of Scanner instead of directly reading the file content. This will avoid loading the whole file in memory
        try {
            inputStream = new FileInputStream(mSourceFile);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                data.add(dataFromString(line));
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
        return Collections.emptyList();
    }
}
