package graph;

import Log.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Graph subclass which contains no information about the edges which links two vertices.
 * Instead only the two involved Vertex are stored asuming the edge information the same as the target vertex.
 * For this instead of using a complex edge container that stores edge and vertex information, a simple Vertex container
 * is used, storing only the involved vertices
 *
 * @param <V> Type for the Vertex to store.
 */
public class NoEdgeInfoGraph<V> extends BaseAbsGraph<V, V> {

    /*  Factory which will provide VertexContainer instances as {@link EdgeContainer}   */
    final EdgeContainerFactory<V, V> mEdgeContainerFactory = new EdgeContainerFactory<V, V>() {
        @Override
        public BaseAbsGraph.EdgeContainer<V, V> newEdgeContainer() {
            return new VertexContainer();
        }
    };

    /**
     * Constructs a new Graph wich will contain no specific information about the edges
     */
    public NoEdgeInfoGraph() {
        super();
        super.setEdgeContainerFactory(mEdgeContainerFactory);
    }

    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {
        final VertexContainer edges = (VertexContainer) super.getEdges(sourceVertex);
        if (edges == null || edges.isEmpty()) {
            return false;
        } else {
            return edges.containsEdge(targetVertex);
        }
    }

    /**
     * Adds a new connection defined by the tuple({@code sourceVertex, targetEdge, edge}). Note that all the input
     * parameters shouldn't  be null. and the target Vertex and the edge should be the same element.
     *
     * @param sourceVertex Source vertex
     * @param targetEdge   Target vertex
     * @param edge         Edge which connects previous vertices.
     */
    @Override
    public void addConnection(V sourceVertex, V targetEdge, V edge) {
        if (targetEdge != edge) {
            throw new IllegalArgumentException("This type of graph does not support adding Edge information that it' s different from the target Vertex");
        }
        super.addConnection(sourceVertex, targetEdge, edge);
    }

    /**
     * Adds a new Connection between the given two vertices to the graph.
     *
     * @param sourceVertex
     * @param targetEdge
     */
    public void addConnection(V sourceVertex, V targetEdge) {
        super.addConnection(sourceVertex, targetEdge, targetEdge);
    }


    /**
     * A {@link graph.NoEdgeInfoGraph.VertexContainer} which assumes that edge and Vertex information to be the same.
     * In this waz there is no difference between storing an edge or a vertex. Adding and edge with value V1 and then
     * retrieving an edge for the vertex V1 will return V1.
     */
    class VertexContainer implements BaseAbsGraph.EdgeContainer<V, V> {

        private Set<V> mEdges = new HashSet<V>();

        VertexContainer() {

        }

        @Override
        public void addEdge(V targetVertex, V edge) {
            mEdges.add(edge);
        }

        @Override
        public boolean containsEdge(final V edge) {
            return mEdges.contains(edge);
        }

        @Override
        public boolean isEmpty() {
            return mEdges.isEmpty();
        }

        @Override
        public Set<V> getEdges() {
            return mEdges;
        }

        @Override
        public void remove(Collection<V> edgesToRemove) {
            mEdges.removeAll(edgesToRemove);
        }

        @Override
        public int size() {
            return mEdges.size();
        }

        @Override
        public V getVertexForEdge(V edge) {
            if (mEdges.contains(edge)) {
                return edge;
            }
            return null;
        }

        @Override
        public Iterator<V> iterator() {
            return mEdges.iterator();
        }

        @Override
        public java.lang.String toString() {
            return "EdgeContainer{" +
                    "mEdges=" + mEdges +
                    '}';
        }


    }

    /**
     * Remove all connections of the graph which are not bidirectional. A Vertex A has a bidirectional connection with
     * Vertex B only if Vertex B it's also connected to A.
     */
    public void removeNonBidirectionalConnections() {

        //For each source vertex edge, check that there is another connection between that target vertex and the source.
        //If not remove those edges for the the current edge.
        for (V sourceVertex : super.vertexSet()) {

            //Store edges to remove after looping over all edges
            final Set<V> edgesToRemove = new HashSet<V>();
            EdgeContainer<V, V> vertexEdges = getEdges(sourceVertex);

            //Check for each edge, that the graph contains another edge for it.
            for (final V targetVertex : vertexEdges) {


                //If the target vertex for the current edge is not connected to the source vertex mark to remove it.
                final VertexContainer targetVertexConnections = (VertexContainer) super.getEdges(targetVertex);
                if (targetVertexConnections == null || targetVertexConnections.isEmpty()) {
                    edgesToRemove.add(targetVertex);
                    Log.debug("Found edge" + sourceVertex + "->" + targetVertex + " which is no bidirectional. Adding for later removal");
                }
            }
            if (!edgesToRemove.isEmpty()) {
                Log.debug("Removing " + edgesToRemove.size() + " edges");
                vertexEdges.remove(edgesToRemove);
            }

        }

    }

    @Override
    public java.lang.String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("Graph{\n");
        for (V vertex : super.vertexSet()) {
            buf.append(vertex);
            buf.append("->");
            buf.append(getEdges(vertex));
            buf.append("\n");
        }
        return buf.toString();


    }
}
