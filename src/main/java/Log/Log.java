package Log;

/**
 * Basic Log class. Provides debug and error logging through the standard output.
 */
public class Log {

    private static final boolean DEBUG = false;


    /**
     * Prints in the standard output the given String if degub mode it's enabled
     *
     * @param message
     */
    public static void debug(final String message) {
        if (DEBUG)
            System.out.println(message);
    }

    /**
     * Prints in the error output the given String.
     *
     * @param message
     */
    public static void error(final String message) {
        System.err.println(message);
    }
}
