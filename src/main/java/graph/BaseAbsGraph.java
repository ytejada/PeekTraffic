package graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class implementation which represents a Graph and provides basic functionality. A graph it's just a set of Vertices
 * that are connected to other Vertices through some Edges.This structure it's represented by this abstract class through
 * a mapping between each vertex in the Graph to a {@link graph.BaseAbsGraph.EdgeContainer}. This Edge Container's
 * implementation will change the behaviour/nature of the Graph represented by each subclass, at least for the edge location,
 * or existence verification.
 */
public abstract class BaseAbsGraph<V, E> {// implements IGraph<V,E>{

    /*  Mapping of the Vertex to the Edge container. This defines the connections each edge has  */
    private Map<V, EdgeContainer<V, E>> vertices = new HashMap<V, EdgeContainer<V, E>>();

    /* EdgeContainer factory for creation of empty EdgeContainers  */
    private EdgeContainerFactory<V, E> mEdgeContainerFactory;

    /**
     * Interface defining base method for empty {@link graph.BaseAbsGraph.EdgeContainer} creation.
     *
     * @param <V> Type of the Vertices this Container handles
     * @param <E> Type of the Edges this Container handles
     */
    protected interface EdgeContainerFactory<V, E> {

        /**
         * Returns an empty EdgeContainer.
         */
        public EdgeContainer<V, E> newEdgeContainer();
    }


    /**
     * Creates a Base Graph which will use the given {@link EdgeContainerFactory} for creating the {@link EdgeContainer}
     * which will store the information between one Vertex and other connected vertices.
     *
     * @param edgeContainerFactory
     */
    public BaseAbsGraph(final EdgeContainerFactory<V, E> edgeContainerFactory) {
        mEdgeContainerFactory = edgeContainerFactory;
    }

    /**
     * Creates a BaseGraph without a defined {@link graph.BaseAbsGraph.EdgeContainerFactory}. Please do not forget to
     * add the factory afterward.
     */
    BaseAbsGraph() {
    }


    /**
     * Sets the {@link graph.BaseAbsGraph.EdgeContainerFactory} to be used by this Graph.
     *
     * @param edgeContainerFactory
     */
    void setEdgeContainerFactory(final EdgeContainerFactory<V, E> edgeContainerFactory) {
        mEdgeContainerFactory = edgeContainerFactory;
    }


    /**
     * Returns the Vertex set defined for this Graph.
     */
    public Set<V> vertexSet() {
        return vertices.keySet();
    }

    /**
     * Returns the {@link graph.BaseAbsGraph.EdgeContainer} for the given vertex. Note that the returned container will
     * be empty if no edges where added for the provided vertex, or null if the vertex does not exist within the graph.
     *
     * @param vertex
     * @return
     */
    protected EdgeContainer<V, E> getEdges(V vertex) {
        return vertices.get(vertex);
    }


    /**
     * Adds a new vertex to the graph if it did not exist before. Note that the vertex will be added to the vertex set of the graph, but this vertex
     * will have no Edges yet, therefore the {@link graph.BaseAbsGraph.EdgeContainer} for the vertex will be empty
     *
     * @param newVertex
     */
    public void addVertex(final V newVertex) {
        if (mEdgeContainerFactory == null) {
            throw new IllegalStateException("No EdgeContainerFactory is set for the Graph. Please make sure to add one");
        }
        if (!vertices.containsKey(newVertex)) {
            vertices.put(newVertex, mEdgeContainerFactory.newEdgeContainer());
        }
    }

    /**
     * Adds a new connection defined by the tuple({@code sourceVertex, targetEdge, edge}). Note that all the input
     * parameters shouldn't  be null.
     *
     * @param sourceVertex Source vertex
     * @param targetEdge   Target vertex
     * @param edge         Edge which connects previous vertices.
     */
    public void addConnection(final V sourceVertex, final V targetEdge, E edge) {
        if (mEdgeContainerFactory == null) {
            throw new IllegalStateException("No EdgeContainerFactory is set for the Graph. Please make sure to add one");
        }

        if (sourceVertex == null || edge == null || targetEdge == null) {
            throw new IllegalArgumentException("None of the provided parameter can be null");
        }
        EdgeContainer edges = vertices.get(sourceVertex);
        if (edges == null) {
            edges = mEdgeContainerFactory.newEdgeContainer();
        }
        edges.addEdge(targetEdge, edge);
        vertices.put(sourceVertex, edges);
    }


    /**
     * Returns if the Graph has an Edge between the provided two Vertices.
     *
     * @param sourceVertex
     * @param targetVertex
     * @return
     */
    public abstract boolean containsEdge(V sourceVertex, V targetVertex);

    /**
     * Interface for storing the Edges and the Vertex within the graph.
     * An EdgeContainer may store unidirectional or Bidirectional edges.
     *
     * @param <V> Type of the vertex this Container stores
     * @param <E> Type of the Edges this container stores.
     */
    public interface EdgeContainer<V, E> extends Iterable<E> {
        /**
         * Adds the given Edge to the container
         *
         * @param edge
         */
        public void addEdge(V targetVertex, E edge);

        /**
         * Returns if the container has an edge for the given Vertex
         *
         * @param vertex
         */
        public boolean containsEdge(final V vertex);

        /**
         * Given an edge returns the corresponding Vertex which is linked to it.
         *
         * @param edge
         * @return
         */
        public V getVertexForEdge(final E edge);

        /**
         * Returns if the EdgeContainer is emtpy or not.
         */
        public boolean isEmpty();

        /**
         * Returns a Set of Edges the container currrently holds.
         *
         * @return
         */
        public Set<E> getEdges();

        /**
         * Removes the give Edge collection from the container.
         *
         * @param edgesToRemove
         */
        public void remove(Collection<E> edgesToRemove);

        /**
         * Returns the number of elements the container holds.
         */
        public int size();

    }
}
