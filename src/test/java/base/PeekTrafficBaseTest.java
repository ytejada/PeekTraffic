package base;

import data.provider.FileDataProvider;
import graph.BaseAbsGraph;

import java.util.*;

/**
 * Created by sirtxonan on 23.09.14.
 */
public class PeekTrafficBaseTest {


    public PeekTrafficBaseTest() {
    }

    /**
     * Base Graph with no EdgeContainerFactory.
     */
    protected final class BaseGraphAdapter extends BaseAbsGraph<String, String> {

        public BaseGraphAdapter() {
            super(null);
        }

        public BaseGraphAdapter(final EdgeContainerFactory containerFactory) {
            super(containerFactory);
        }

        @Override
        public boolean containsEdge(String sourceVertex, String targetVertex) {
            return false;
        }
    }


    /**
     * Basic edge container which stores all the information in a Map<String,String>. It stores Edges and vertices information the map.
     */
    protected class BaseEdgeContainer implements BaseAbsGraph.EdgeContainer<String, String> {

        private Map<String, String> mEdges = new HashMap<String, String>();

        public BaseEdgeContainer() {

        }

        @Override
        public void addEdge(String targetVertex, String edge) {
            mEdges.put(edge, targetVertex);
        }

        @Override
        public boolean containsEdge(final String edge) {
            return mEdges.containsKey(edge);
        }

        @Override
        public boolean isEmpty() {
            return mEdges.isEmpty();
        }

        @Override
        public Set<String> getEdges() {
            return mEdges.keySet();
        }

        @Override
        public void remove(Collection<String> edgesToRemove) {
            for (final String key : edgesToRemove) {
                mEdges.remove(key);
            }
        }

        @Override
        public int size() {
            return mEdges.size();
        }

        @Override
        public String getVertexForEdge(String edge) {
            return mEdges.get(edge);

        }

        @Override
        public Iterator<String> iterator() {
            return mEdges.keySet().iterator();
        }

        @Override
        public java.lang.String toString() {
            return "EdgeContainer{" +
                    "mEdges=" + mEdges +
                    '}';
        }
    }

    protected class DirectParsingDataProviderAdapter extends FileDataProvider<String> {


        /**
         * Constructs a new FileDataProvider which will take the parsing information from the given source file.
         *
         * @param mSourceFile
         */
        public DirectParsingDataProviderAdapter(String mSourceFile) {
            super(mSourceFile);
        }

        @Override
        public String dataFromString(String aStringToken) {
            return aStringToken;
        }
    }
}
