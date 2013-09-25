package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.VBFS;
import org.javanetworkanalyzer.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link BFS} on the Cormen graph.
 *
 * @author Adam Gouge
 */
public class BFSCormenTest {

    private DirectedPseudoG<VBFS, Edge> graph;
    private BFS<VBFS, Edge> bfs;
    private TraversalGraph<VBFS, Edge> sPT;
    private VBFS v1;
    private VBFS v2;
    private VBFS v3;
    private VBFS v4;
    private VBFS v5;
    private static final double TOLERANCE = 0.0;


    @Test
    public void testD() throws NoSuchMethodException {

        bfs = new BFS<VBFS, Edge>(graph);

        bfs.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 2);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v2);
        assertEquals(3, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 2);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(3, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(3, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.outDegreeOf(v1) == 2);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 0);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v4);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 3);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v5);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.outDegreeOf(v1) == 2);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 0);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }

    @Test
    public void testR() throws NoSuchMethodException {

        bfs = new BFS<VBFS, Edge>(new EdgeReversedG<VBFS, Edge>(graph));

        bfs.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(3, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);

        bfs.calculate(v2);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(3, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 2);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 0);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v3, v4));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 3);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v4);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(3, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v5);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }

    @Test
    public void testU() throws NoSuchMethodException {

        bfs = new BFS<VBFS, Edge>(new AsUndirectedG<VBFS, Edge>(graph));

        bfs.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 7);
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.getAllEdges(v5, v3).size() == 2);
        assertTrue(sPT.outDegreeOf(v1) == 3);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);

        bfs.calculate(v2);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 8);
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.getAllEdges(v2, v4).size() == 2);
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.getAllEdges(v3, v5).size() == 2);
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 4);
        assertTrue(sPT.outDegreeOf(v3) == 2);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 7);
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v3, v4));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.getAllEdges(v3, v5).size() == 2);
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 4);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v4);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.getAllEdges(v4, v2).size() == 2);
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 5);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v5);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 8);
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.getAllEdges(v5, v3).size() == 2);
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.getAllEdges(v4, v2).size() == 2);
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 4);
    }

    @Before
    public void setUp() {

        graph = new DirectedPseudoG<VBFS, Edge>(VBFS.class, Edge.class);

        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(5, 1);
        graph.addEdge(2, 4);
        graph.addEdge(4, 2);
        graph.addEdge(3, 5);
        graph.addEdge(2, 3);
        graph.addEdge(4, 3);
        graph.addEdge(5, 3);
        graph.addEdge(4, 5);

        v1 = graph.getVertex(1);
        v2 = graph.getVertex(2);
        v3 = graph.getVertex(3);
        v4 = graph.getVertex(4);
        v5 = graph.getVertex(5);
    }
}

