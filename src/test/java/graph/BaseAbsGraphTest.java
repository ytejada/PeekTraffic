package graph;

import base.PeekTrafficBaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseAbsGraphTest extends PeekTrafficBaseTest {

    /*  Factory which will provide VertexContainer instances as {@link EdgeContainer}   */
    protected final BaseAbsGraph.EdgeContainerFactory<String, String> mEdgeContainerFactory = new BaseAbsGraph.EdgeContainerFactory<String, String>() {
        @Override
        public BaseAbsGraph.EdgeContainer<String, String> newEdgeContainer() {
            return new BaseEdgeContainer();
        }
    };

    /**
     * Check that the a BaseAbsGraph does not support operation without a EdgeContainerFactory Factory
     */
    @Test
    public void testBAseGraphConstructor() {
        BaseAbsGraph graph = new BaseAbsGraph() {
            @Override
            public boolean containsEdge(Object sourceVertex, Object targetVertex) {
                return false;
            }
        };
    }


    /**
     * Check that the a BaseAbsGraph does not support operation without a EdgeContainerFactory Factory
     */
    @Test
    public void testAddVertexNullEdgeContainerFactory() {
        BaseAbsGraph graph = new BaseGraphAdapter();
        try {
            graph.addVertex("v1");
            fail("Expected to have an exception when no EdgeContainerFactory was set for the Graph");
        } catch (IllegalStateException ise) {

        }
    }

    @Test
    public void testAddExistingVertex() {
        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        graph.addVertex("V1");
        final BaseAbsGraph.EdgeContainer beforeEdges = graph.getEdges("V1");

        //add same vertex again and check for same edges
        graph.addVertex("V1");
        final BaseAbsGraph.EdgeContainer afterEdges = graph.getEdges("V1");
        assertEquals("The edgeContainer returned after adding an already existing vertex should be the same", beforeEdges, afterEdges);
    }

    /**
     * Check that the a BaseAbsGraph does not support operation without a EdgeContainerFactory Factory
     */
    @Test
    public void testyAddConnectionNullEdgeContainerFactory() {
        BaseAbsGraph graph = new BaseGraphAdapter();
        try {
            graph.addConnection("V1", "V2", "E1");
            fail("Expected to have an exception when no EdgeContainerFactory was set for the Graph");
        } catch (IllegalStateException ise) {

        }
    }

    @Test
    public void testGetEmptyVertexSet() {
        BaseAbsGraph graph = new BaseGraphAdapter();
        graph.setEdgeContainerFactory(mEdgeContainerFactory);
        assertTrue("A newly created graph should not contain any vertex", graph.vertexSet().isEmpty());
    }

    @Test
    public void testGetNotEmptyVertexSet() {
        BaseAbsGraph graph = new BaseGraphAdapter();
        graph.setEdgeContainerFactory(mEdgeContainerFactory);
        assertTrue("A newly created graph should not contain any vertex", graph.vertexSet().isEmpty());
        graph.addVertex("V1");
        assertEquals("The size of the returned vertex set after having added one vertex should be 1", 1, graph.vertexSet().size());
    }

    @Test
    public void testGetEdgesNullVertex() {

        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        final BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges(null);
        assertNull("Returned EdgeContainer from the graph should be null for non existing vertex", edges);
    }

    @Test
    public void testGetEdgesNonExistentVertex() {

        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        final BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges("V1");
        assertNull("Returned EdgeContainer from the graph should be null for non existing vertex", edges);
    }

    @Test
    public void testGetEdgesExistentVertex() {

        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        graph.addVertex("V1");
        final BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges("V1");
        assertNotNull("Returned EdgeContainer from the graph should be null for non existing vertex", edges);
        assertTrue("Expected empty edge container when retrieving edges for null Vertex", edges.isEmpty());
    }

    @Test
    public void testAddConnectionOk() {
        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        graph.addConnection("V1", "V2", "E1");
        final BaseAbsGraph.EdgeContainer edges = graph.getEdges("V1");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V2,E1) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 1, edges.size());
        assertTrue("The Edge V2 should be within the returned Edges after having added the tuple (V1,V2,E1)", edges.getEdges().contains("E1"));
    }

    @Test
    public void testAddTwoConnectionsOk() {
        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        graph.addConnection("V1", "V2", "E1");
        final BaseAbsGraph.EdgeContainer edges = graph.getEdges("V1");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V2,E1) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 1, edges.size());
        assertTrue("The Edge V2 should be within the returned Edges after having added the tuple (V1,V2,E1)", edges.getEdges().contains("E1"));

        //Repeat the process adding a new connection
        graph.addConnection("V1", "V3", "E2");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V3,V3) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 2, edges.size());
        assertTrue("The Edge V3 should be within the returned Edges after having added the tuple (V1,V3,E1)", edges.getEdges().contains("E1"));
        assertTrue("The Edge V2 should be within the returned Edges after having added both tuples (V1,V2,E1) and (V1,V3,E2)", edges.getEdges().contains("E2"));

    }

    @Test
    public void testAddConnectionNullSourceVertex() {
        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        try {
            graph.addConnection(null, "V2", "V2");
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("Expected exception IllegalArgumentException after when adding a null source vertex");
    }

    @Test
    public void testAddConnectionNullTargetVertex() {
        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        try {
            graph.addConnection("V1", null, "V2");
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("Expected exception IllegalArgumentException after when adding a null source vertex");
    }

    @Test
    public void testAddConnectionNullEdge() {
        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        try {
            graph.addConnection("V1", "V2", null);
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("Expected exception IllegalArgumentException after when adding a null source vertex");
    }

    @Test
    public void testGetVertexFromEdge() throws Exception {

        BaseAbsGraph graph = new BaseGraphAdapter(mEdgeContainerFactory);
        graph.addConnection("V1", "V2", "E1");
        final BaseAbsGraph.EdgeContainer edges = graph.getEdges("V1");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V2,E1) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 1, edges.size());
        assertEquals("The Edge V should be within the returned Edges after having added the tuple (V1,V2,E1)", "V2", edges.getVertexForEdge("E1"));

    }
}