package graph;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoEdgeInfoGraphTest {

    @Test
    public void testAddExistingVertex() {
        BaseAbsGraph graph = new NoEdgeInfoGraph();
        graph.addVertex("V1");
        final BaseAbsGraph.EdgeContainer beforeEdges = graph.getEdges("V1");

        //add same vertex again and check for same edges
        graph.addVertex("V1");
        final BaseAbsGraph.EdgeContainer afterEdges = graph.getEdges("V1");
        assertEquals("The edgeContainer returned after adding an already existing vertex should be the same", beforeEdges, afterEdges);
    }


    @Test
    public void testGetEmptyVertexSet() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        assertTrue("A newly created graph should not contain any vertex", graph.vertexSet().isEmpty());
    }

    @Test
    public void testGetNotEmptyVertexSet() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        assertTrue("A newly created graph should not contain sany vertex", graph.vertexSet().isEmpty());
        graph.addVertex("V1");
        assertEquals("The size of the returned vertex set after having added one vertex should be 1", 1, graph.vertexSet().size());
    }

    @Test
    public void testGetEdgesNullVertex() {

        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        final BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges(null);
        assertNull("Returned EdgeContainer from the graph should be null for non existing vertex", edges);
    }

    @Test
    public void testGetEdgesNonExistentVertex() {

        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        final BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges("V1");
        assertNull("Returned EdgeContainer from the graph should be null for non existing vertex", edges);
    }

    @Test
    public void testGetEdgesExistentVertex() {

        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addVertex("V1");
        final BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges("V1");
        assertNotNull("Returned EdgeContainer from the graph should be null for non existing vertex", edges);
        assertTrue("Expected empty edge container when retrieving edges for null Vertex", edges.isEmpty());
    }


    @Test
    public void testAddConnectionOnlyVertex() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2");
        final BaseAbsGraph.EdgeContainer edges = graph.getEdges("V1");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V2,V2) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 1, edges.size());
        assertTrue("The Edge V2 should be within the returned Edges after having added the tuple (V1,V2,V2)", edges.getEdges().contains("V2"));
    }

    @Test
    public void testAddConnectionOk() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2", "V2");
        final BaseAbsGraph.EdgeContainer edges = graph.getEdges("V1");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V2,V2) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 1, edges.size());
        assertTrue("The Edge V2 should be within the returned Edges after having added the tuple (V1,V2,V2)", edges.getEdges().contains("V2"));
    }

    @Test
    public void testAddTwoConnectionsOk() {
        BaseAbsGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2", "V2");
        final BaseAbsGraph.EdgeContainer edges = graph.getEdges("V1");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V2,V2) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 1, edges.size());
        assertTrue("The Edge V2 should be within the returned Edges after having added the tuple (V1,V2,V2)", edges.getEdges().contains("V2"));

        //Repeat the process adding a new connection
        graph.addConnection("V1", "V3", "V3");
        assertNotNull("Returned edge container should not be null as the tuple (V1,V3,V3) was added.", edges);
        assertEquals("Expected  edge container of size 1 when retrieving edges for null Vertex", 2, edges.size());
        assertTrue("The Edge V3 should be within the returned Edges after having added the tuple (V1,V3,V3)", edges.getEdges().contains("V3"));
        assertTrue("The Edge V2 should be within the returned Edges after having added both tuples (V1,V2,V2) and (V1,V3,V3)", edges.getEdges().contains("V2"));

    }

    @Test
    public void testAddConnectionNullSourceVertex() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        try {
            graph.addConnection(null, "V2", "V2");
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("Expected exception IllegalArgumentException after when adding a null source vertex");
    }

    @Test
    public void testAddConnectionNullTargetVertex() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        try {
            graph.addConnection("V1", null, "V2");
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("Expected exception IllegalArgumentException after when adding a null source vertex");
    }

    @Test
    public void testAddConnectionNullEdge() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        try {
            graph.addConnection("V1", "V2", null);
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("Expected exception IllegalArgumentException after when adding a null source vertex");
    }

    @Test
    public void testAddConnectionEdgeAndTargetVertexNotMatching() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        try {
            graph.addConnection("V1", "V2", "V3");
        } catch (IllegalArgumentException iae) {
            return;
        }
        fail("Expected exception IllegalArgumentException after when adding a null source vertex");
    }


    @Test
    public void testContainsEdgeEmptyGraph() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        assertFalse("An empty NoEdgeInfoGraph must not contain any type of edge", graph.containsEdge("V1", "V2"));
    }

    @Test
    public void testContainsEdgeOk() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2");
        assertTrue("NoEdgeInfoGraph should contain an edge V2 after having added connection V1,V2", graph.containsEdge("V1", "V2"));
    }

    @Test
    public void testNotContainsEdge() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2");
        assertFalse("NoEdgeInfoGraph should not contain the given edge", graph.containsEdge("V1", "E1"));
    }

    @Test
    public void testContainsEdgeOnlyVertexAdded() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addVertex("V1");
        assertFalse("NoEdgeInfoGraph should not contain the given edge", graph.containsEdge("V1", "E1"));
    }

    @Test
    public void testContainsEdgeNullSourceVertex() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        assertFalse("NoEdgeInfoGraph should not contain the given edge", graph.containsEdge(null, "E1"));
    }

    @Test
    public void testContainsEdgeNullTargetVertex() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        assertFalse("NoEdgeInfoGraph should not contain the given edge", graph.containsEdge("V1", null));
    }

    @Test
    public void testContainsEdgeNullInput() {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        assertFalse("NoEdgeInfoGraph should not contain the given edge", graph.containsEdge(null, null));
    }

    @Test
    public void testRemoveNonBidirectionalConnectionsAllBidirectionalConnection() throws Exception {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2");
        graph.addConnection("V2", "V1");
        graph.removeNonBidirectionalConnections();
        assertTrue(graph.containsEdge("V1", "V2"));
    }

    @Test
    public void testRemoveNonBidirectionalConnectionsOnlyUnidirectionalConnections() throws Exception {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2");
        graph.addConnection("V1", "V3");
        graph.addConnection("V3", "V2");
        graph.removeNonBidirectionalConnections();
        assertFalse("Graph should not contain V1,V2 connection " + graph, graph.containsEdge("V1", "V2"));
        assertFalse(graph.containsEdge("V1", "V3"));
        assertFalse(graph.containsEdge("V3", "V2"));
    }

    @Test
    public void testGetVertexFromEdgeOK() throws Exception {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2");
        BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges("V1");
        assertEquals("Expected to get V1 as vertex, when retrieving the vertex from the edge.", "V2", edges.getVertexForEdge("V2"));

    }

    @Test
    public void testGetVertexFromEdgeEmpty() throws Exception {
        NoEdgeInfoGraph graph = new NoEdgeInfoGraph();
        graph.addConnection("V1", "V2");
        BaseAbsGraph.EdgeContainer<String, String> edges = graph.getEdges("V1");
        assertNull("Expected to get V1 as vertex, when retrieving the vertex from the edge.", edges.getVertexForEdge("V4"));

    }
}