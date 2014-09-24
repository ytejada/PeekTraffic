package main;

import Log.Log;
import data.provider.FaceBookDataProvider;
import data.provider.FacebookDataImpl;
import data.provider.FileDataProvider;
import graph.BronKerboschCliqueFinder;
import graph.NoEdgeInfoGraph;

import java.io.File;
import java.util.*;

/**
 * Main Class for the main.PeakTraffic code challenge. It contains a main method which executes the program. This main method
 * takes as argument one String representing the path to a file containing the input data. For information about the
 * behaviour of the program or the format of this input file's data {@see https://www.codeeval.com/public_sc/49/}
 */
public class PeakTraffic {


    private static final int SET_SIZE_THRESHOLD = 3;

    /**
     * Possible return values for the {@link PeakTraffic} program. This return values contain an integer which defines
     * the return code of the program execution, and a String with the message.
     */
    public enum PeakTrafficResult {
        OK(0, "OK"),
        NO_FILE_PROVIDED(10, "No path provided to a file with the data to process. Please provide a path to a valid file"),
        TOO_MANY_ARGUMENTS(20, "More than one parameter was provided. This program does only accept one input file"),
        NO_VALID_PATH(30, "The provided path does not exist");

        final int mReturnCode;
        final String mMessage;

        PeakTrafficResult(int returnCode, final String message) {
            mReturnCode = returnCode;
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }

        public int getReturnCode() {
            return mReturnCode;
        }
    }

    public static void main(String[] args) {

        //Validate input arguments
        final PeakTrafficResult validationResult = validateInput(args);
        if (validationResult != PeakTrafficResult.OK) {
            Log.error(validationResult.getMessage());
            System.exit(validationResult.getReturnCode());
        }
        final File sourceFile = new File(args[0]);

        //Create the Data provider which will parse the the file into valid data
        FileDataProvider provider = new FaceBookDataProvider(sourceFile.getPath());
        final List<FacebookDataImpl> allData = provider.getAllData();

        //Create an empty graph and add the connections described by the data
        final NoEdgeInfoGraph<String> g = new NoEdgeInfoGraph<String>();
        for (FacebookDataImpl d : allData) {
            g.addConnection(d.getSourceUser(), d.getTargetUser());

        }

        //At this point we have a biDirectional graph. Remove all the vertex connections which are not bidirectional,
        //converting the graph to uniDirectional. This is compulsorz for current impl.
        g.removeNonBidirectionalConnections();

        Log.debug(g.toString());

        //Find all maximal cliques on the graph
        BronKerboschCliqueFinder<String, String> finder = new BronKerboschCliqueFinder<String, String>(g);
        final Collection<Set<String>> result = finder.getAllMaximalCliques();

        //Sort the retrieved result and print them
        final List<List<String>> sortedResult = sortClusters(result);
        for (List<String> set : sortedResult) {
            if (set.size() >= SET_SIZE_THRESHOLD) {
                System.out.println(prettyPrint(set));
            } else {
                Log.debug("Got cluster \'" + prettyPrint(set) + "\', but did not reach the minimum length");
            }
        }
    }

    /**
     * Validates the input parameters for the main method of {@link PeakTraffic} program. Possible return types are
     * defined by {@link PeakTraffic.PeakTrafficResult}
     *
     * @param args
     * @return
     */
    public static PeakTrafficResult validateInput(String[] args) {
        if (args == null || args.length == 0) {
            return PeakTrafficResult.NO_FILE_PROVIDED;
        } else if (args.length > 1) {
            return PeakTrafficResult.TOO_MANY_ARGUMENTS;
        } else {
            final String path = args[0];
            if (!new File(path).exists()) {
                return PeakTrafficResult.NO_VALID_PATH;
            }
        }
        return PeakTrafficResult.OK;
    }


    /**
     * Returns an alphabetically sorted representation of the given {@code Set<String>} collection. Each Set will be
     * sorted internally, and then the collection will be sorted taking into account the alphabetically order of the
     * Set's within the collection.
     *
     * @param result
     */
    public static List<List<String>> sortClusters(Collection<Set<String>> result) {
        //Main container with the result
        final List<List<String>> container = new ArrayList<List<String>>(result.size());
        for (Set<String> curSet : result) {
            //Convert each set into a list to sort internally
            final List<String> currentList = new ArrayList<String>(curSet);
            Collections.sort(currentList);
            container.add(currentList);
        }

        //At this point we have a List containing alphabetically sorted list. Let's sort the top level list,
        // comparing the first element of each list
        final Comparator<List<String>> comparator = new Comparator<List<String>>() {
            @Override
            public int compare(List<String> strings, List<String> strings2) {
                return strings.get(0).compareTo(strings2.get(0));
            }
        };
        Collections.sort(container, comparator);
        return container;
    }


    /**
     * Returns a String representation of the given collection to be printed as result. The returned String will contain
     * all the collection elements separated by the {@code ", "} string.
     *
     * @param collection
     * @param <T>
     */
    public static <T> String prettyPrint(final Collection<T> collection) {
        if (collection.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        final String SEPARATOR = ", ";
        for (final T t : collection) {
            sb.append(t);
            sb.append(SEPARATOR);
        }

        //Remove the last separato we have added
        sb.delete(sb.length() - SEPARATOR.length(), sb.length());
        return sb.toString();
    }
}