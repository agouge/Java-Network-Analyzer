/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is distributed under the GPL 3 license. It is produced
 * by the "Atelier SIG" team of the <a href="http://www.irstv.fr">IRSTV
 * Institute</a>, CNRS FR 2488.
 *
 * Copyright 2013 IRSTV (CNRS FR 2488).
 *
 * Java Network Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Java Network Analyzer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Java Network Analyzer. If not, see <http://www.gnu.org/licenses/>.
 */
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.VDijkstra;
import org.javanetworkanalyzer.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link Dijkstra} on the Cormen graph.
 *
 * @author Adam Gouge
 */
public class DijkstraCormenTest {

    private DirectedWeightedPseudoG<VDijkstra, Edge> graph;
    private Dijkstra<VDijkstra, Edge> dijkstra;
    private TraversalGraph<VDijkstra, Edge> sPT;
    private VDijkstra v1;
    private VDijkstra v2;
    private VDijkstra v3;
    private VDijkstra v4;
    private VDijkstra v5;
    private static final double TOLERANCE = 0.0;

    @Test
    public void testWDLimitedByRadius() throws NoSuchMethodException {
        dijkstra = new Dijkstra<VDijkstra, Edge>(graph);
        final double radius = 12.0;
        dijkstra.calculate(v3, radius);
        assertEquals(11, v1.getDistance(), TOLERANCE);
        assertTrue(v2.getDistance() > radius);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertTrue(v2.getDistance() > radius);
        assertEquals(4, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph(radius);
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 2);
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v5, v1));
    }

    @Test
    public void testWD() throws NoSuchMethodException {

        dijkstra = new Dijkstra<VDijkstra, Edge>(graph);

        dijkstra.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(8, v2.getDistance(), TOLERANCE);
        assertEquals(9, v3.getDistance(), TOLERANCE);
        assertEquals(5, v4.getDistance(), TOLERANCE);
        assertEquals(7, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        dijkstra.calculate(v2);
        assertEquals(11, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(4, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 2);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);


        dijkstra.calculate(v3);
        assertEquals(11, v1.getDistance(), TOLERANCE);
        assertEquals(19, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(16, v4.getDistance(), TOLERANCE);
        assertEquals(4, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        dijkstra.calculate(v4);
        assertEquals(9, v1.getDistance(), TOLERANCE);
        assertEquals(3, v2.getDistance(), TOLERANCE);
        assertEquals(4, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        dijkstra.calculate(v5);
        assertEquals(7, v1.getDistance(), TOLERANCE);
        assertEquals(15, v2.getDistance(), TOLERANCE);
        assertEquals(6, v3.getDistance(), TOLERANCE);
        assertEquals(12, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }

    @Test
    public void testWR() throws NoSuchMethodException {

        dijkstra = new Dijkstra<VDijkstra, Edge>(
                new WeightedEdgeReversedG<VDijkstra, Edge>(graph));

        dijkstra.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(11, v2.getDistance(), TOLERANCE);
        assertEquals(11, v3.getDistance(), TOLERANCE);
        assertEquals(9, v4.getDistance(), TOLERANCE);
        assertEquals(7, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);

        dijkstra.calculate(v2);
        assertEquals(8, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(19, v3.getDistance(), TOLERANCE);
        assertEquals(3, v4.getDistance(), TOLERANCE);
        assertEquals(15, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);


        dijkstra.calculate(v3);
        assertEquals(9, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(4, v4.getDistance(), TOLERANCE);
        assertEquals(6, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 2);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 0);


        dijkstra.calculate(v4);
        assertEquals(5, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(16, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(12, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v5);
        assertEquals(7, v1.getDistance(), TOLERANCE);
        assertEquals(4, v2.getDistance(), TOLERANCE);
        assertEquals(4, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }

    @Test
    public void testWU() throws NoSuchMethodException {

        dijkstra = new Dijkstra<VDijkstra, Edge>(
                new AsUndirectedG<VDijkstra, Edge>(graph));

        dijkstra.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(7, v2.getDistance(), TOLERANCE);
        assertEquals(8, v3.getDistance(), TOLERANCE);
        assertEquals(5, v4.getDistance(), TOLERANCE);
        assertEquals(7, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.outDegreeOf(v1) == 2);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        dijkstra.calculate(v2);
        assertEquals(7, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(4, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 2);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        dijkstra.calculate(v3);
        assertEquals(8, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(3, v4.getDistance(), TOLERANCE);
        assertEquals(4, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 2);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        dijkstra.calculate(v4);
        assertEquals(5, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(3, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 3);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        dijkstra.calculate(v5);
        assertEquals(7, v1.getDistance(), TOLERANCE);
        assertEquals(4, v2.getDistance(), TOLERANCE);
        assertEquals(4, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 3);
    }

    @Test
    public void testD() throws NoSuchMethodException {

        dijkstra = new Dijkstra<VDijkstra, Edge>(
                new AsUnweightedDirectedG<VDijkstra, Edge>(graph));

        dijkstra.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v2);
        assertEquals(3, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(3, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(3, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v4);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v5);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra = new Dijkstra<VDijkstra, Edge>(
                new EdgeReversedG<VDijkstra, Edge>(
                        new AsUnweightedDirectedG<VDijkstra, Edge>(graph)));

        dijkstra.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(3, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v2);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(3, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v4);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(3, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v5);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra = new Dijkstra<VDijkstra, Edge>(
                new AsUnweightedG<VDijkstra, Edge>(graph));

        dijkstra.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v2);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v4);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        dijkstra.calculate(v5);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = dijkstra.reconstructTraversalGraph();
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

        graph = new DirectedWeightedPseudoG<VDijkstra, Edge>(
                VDijkstra.class, Edge.class);

        graph.addEdge(1, 2).setWeight(10);
        graph.addEdge(1, 4).setWeight(5);
        graph.addEdge(5, 1).setWeight(7);
        graph.addEdge(2, 4).setWeight(2);
        graph.addEdge(4, 2).setWeight(3);
        graph.addEdge(3, 5).setWeight(4);
        graph.addEdge(2, 3).setWeight(1);
        graph.addEdge(4, 3).setWeight(9);
        graph.addEdge(5, 3).setWeight(6);
        graph.addEdge(4, 5).setWeight(2);

        v1 = graph.getVertex(1);
        v2 = graph.getVertex(2);
        v3 = graph.getVertex(3);
        v4 = graph.getVertex(4);
        v5 = graph.getVertex(5);
    }
}
