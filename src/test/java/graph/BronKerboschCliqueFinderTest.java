package graph;

import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BronKerboschCliqueFinderTest {


    private static final String V1 = "v1";
    private static final String V2 = "v2";
    private static final String V3 = "v3";
    private static final String V4 = "v4";
    private static final String V5 = "v5";
    private static final String V6 = "v6";
    private static final String V7 = "v7";
    private static final String V8 = "v8";

    private void createGraph(NoEdgeInfoGraph g) {
        g.addVertex(V1);
        g.addVertex(V2);
        g.addVertex(V3);
        g.addVertex(V4);
        g.addVertex(V5);
        g.addVertex(V6);
        g.addVertex(V7);
        g.addVertex(V8);

        // biggest clique:  { V1, V2, V3, V4 }
        addBidirectionalConnection(g, V1, V2);
        addBidirectionalConnection(g, V1, V3);
        addBidirectionalConnection(g, V1, V4);
        addBidirectionalConnection(g, V2, V3);
        addBidirectionalConnection(g, V2, V4);
        addBidirectionalConnection(g, V3, V4);

        // smaller clique:  { V5, V6, V7 }
        addBidirectionalConnection(g, V5, V6);
        addBidirectionalConnection(g, V5, V7);
        addBidirectionalConnection(g, V6, V7);

        // for fun, add an overlapping clique { V3, V4, V5 }
        addBidirectionalConnection(g, V3, V5);
        addBidirectionalConnection(g, V4, V5);

        // make V8 less lonely
        addBidirectionalConnection(g, V7, V8);
    }


    private static void addBidirectionalConnection(NoEdgeInfoGraph g, final String sourceVErtex, String targetVertex) {
        g.addConnection(sourceVErtex, targetVertex);
        g.addConnection(targetVertex, sourceVErtex);
    }

    @Test
    public void testFindBiggest() {
        NoEdgeInfoGraph g = new NoEdgeInfoGraph();
        createGraph(g);

        BronKerboschCliqueFinder<String, String> finder =
                new BronKerboschCliqueFinder<String, String>(g);

        Collection<Set<String>> cliques = finder.getBiggestMaximalCliques();

        assertEquals(1, cliques.size());

        Set<String> expected = new HashSet<String>();
        expected.add(V1);
        expected.add(V2);
        expected.add(V3);
        expected.add(V4);

        Set<String> actual = cliques.iterator().next();

        assertEquals(expected, actual);
    }

    @Test
    public void testFindAll() {
        NoEdgeInfoGraph g =
                new NoEdgeInfoGraph();
        createGraph(g);

        BronKerboschCliqueFinder<String, String> finder =
                new BronKerboschCliqueFinder<String, String>(g);

        Collection<Set<String>> cliques = finder.getAllMaximalCliques();

        assertEquals(4, cliques.size());

        Set<Set<String>> expected = new HashSet<Set<String>>();

        Set<String> set = new HashSet<String>();
        set.add(V1);
        set.add(V2);
        set.add(V3);
        set.add(V4);
        expected.add(set);

        set = new HashSet<String>();
        set.add(V5);
        set.add(V6);
        set.add(V7);
        expected.add(set);

        set = new HashSet<String>();
        set.add(V3);
        set.add(V4);
        set.add(V5);
        expected.add(set);

        set = new HashSet<String>();
        set.add(V7);
        set.add(V8);
        expected.add(set);

        // convert result from Collection to Set because we don't want
        // order to be significant
        Set<Set<String>> actual = new HashSet<Set<String>>(cliques);

        assertEquals(expected, actual);
    }
}