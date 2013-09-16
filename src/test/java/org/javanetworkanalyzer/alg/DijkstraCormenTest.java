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
import org.javanetworkanalyzer.graphcreators.CormenGraphPrep;
import org.javanetworkanalyzer.graphcreators.GraphPrep;
import org.javanetworkanalyzer.model.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedSubgraph;
import org.jgrapht.graph.Subgraph;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests {@link Dijkstra} on all possible configurations of the Cormen graph.
 *
 * @author Adam Gouge
 */
public class DijkstraCormenTest extends DijkstraTest {

    private static final GraphPrep CORMEN = new CormenGraphPrep();

    @Override
    public GraphPrep getGraphPrep() {
        return CORMEN;
    }

    private Edge getEdge(KeyedGraph<VDijkstra, Edge> g,
                         int source, int target) {
        return g.getEdge(g.getVertex(source), g.getVertex(target));
    }

    @Test
    public void testWDSPT() throws NoSuchMethodException {

        DirectedWeightedPseudoG<VDijkstra, Edge> g = CORMEN.weightedDirected();

        Dijkstra<VDijkstra, Edge> dijkstra = new Dijkstra<VDijkstra, Edge>(g);

        DirectedSubgraph<VDijkstra, Edge> sPT;

        VDijkstra v1 = g.getVertex(1);
        VDijkstra v2 = g.getVertex(2);
        VDijkstra v3 = g.getVertex(3);
        VDijkstra v4 = g.getVertex(4);
        VDijkstra v5 = g.getVertex(5);

        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v1);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 1, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 5)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 2)));
        assertTrue(sPT.containsEdge(getEdge(g, 2, 3)));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v2);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 2, 3)));
        assertTrue(sPT.containsEdge(getEdge(g, 2, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 5)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 1)));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 2);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);


        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v3);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 3, 5)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 1)));
        assertTrue(sPT.containsEdge(getEdge(g, 1, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 2)));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);


        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v4);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 4, 5)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 1)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 2)));
        assertTrue(sPT.containsEdge(getEdge(g, 2, 3)));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v5);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 5, 3)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 1)));
        assertTrue(sPT.containsEdge(getEdge(g, 1, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 2)));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }

    @Test
    public void testWRSPT() throws Exception {

        WeightedEdgeReversedG<VDijkstra, Edge> g = CORMEN.weightedReversed();

        Dijkstra<VDijkstra, Edge> dijkstra = new Dijkstra<VDijkstra, Edge>(g);

        DirectedSubgraph<VDijkstra, Edge> sPT;

        VDijkstra v1 = g.getVertex(1);
        VDijkstra v2 = g.getVertex(2);
        VDijkstra v3 = g.getVertex(3);
        VDijkstra v4 = g.getVertex(4);
        VDijkstra v5 = g.getVertex(5);

        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v1);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 1, 5)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 3)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 2)));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);

        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v2);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 2, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 1)));
        assertTrue(sPT.containsEdge(getEdge(g, 1, 5)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 3)));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);


        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v3);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 3, 2)));
        assertTrue(sPT.containsEdge(getEdge(g, 2, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 1)));
        assertTrue(sPT.containsEdge(getEdge(g, 3, 5)));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 2);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 0);


        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v4);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 4, 1)));
        assertTrue(sPT.containsEdge(getEdge(g, 1, 5)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 3)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 2)));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        sPT = (DirectedSubgraph<VDijkstra, Edge>) dijkstra.calculate(v5);
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(getEdge(g, 5, 4)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 1)));
        assertTrue(sPT.containsEdge(getEdge(g, 4, 2)));
        assertTrue(sPT.containsEdge(getEdge(g, 5, 3)));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }
}
