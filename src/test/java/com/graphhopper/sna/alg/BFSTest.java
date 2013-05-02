/**
 * GraphHopper-SNA implements a collection of social network analysis
 * algorithms. It is based on the <a
 * href="http://graphhopper.com/">GraphHopper</a> library.
 *
 * GraphHopper-SNA is distributed under the GPL 3 license. It is produced by the
 * "Atelier SIG" team of the <a href="http://www.irstv.fr">IRSTV Institute</a>,
 * CNRS FR 2488.
 *
 * Copyright 2012 IRSTV (CNRS FR 2488)
 *
 * GraphHopper-SNA is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * GraphHopper-SNA is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * GraphHopper-SNA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.graphhopper.sna.alg;

import com.graphhopper.sna.model.PseudoG;
import com.graphhopper.sna.data.UnweightedNodeBetweennessInfo;
import com.graphhopper.sna.model.Edge;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Tests the BFS algorithm to calculate distances from a given source vertex.
 *
 * @author Adam Gouge
 */
public class BFSTest {

    @Test
    public void testBFSFromVertexThree() {
        PseudoG<UnweightedNodeBetweennessInfo, Edge> graph = prepareGraph();

        new BFS<UnweightedNodeBetweennessInfo, Edge>(
                graph).calculate(graph.getVertex(3));

        assertTrue(graph.getVertex(3).getDistance() == 0);
        assertTrue(graph.getVertex(2).getDistance() == 1);
        assertTrue(graph.getVertex(4).getDistance() == 1);
        assertTrue(graph.getVertex(1).getDistance() == 2);
        assertTrue(graph.getVertex(5).getDistance() == 2);
        assertTrue(graph.getVertex(6).getDistance() == 2);
        assertTrue(graph.getVertex(7).getDistance() == 3);
        assertTrue(graph.getVertex(8).getDistance() == 3);
    }

    @Test
    public void testBFSFromVertexEight() {
        PseudoG<UnweightedNodeBetweennessInfo, Edge> graph = prepareGraph();

        new BFS<UnweightedNodeBetweennessInfo, Edge>(
                graph).calculate(graph.getVertex(8));

        assertTrue(graph.getVertex(8).getDistance() == 0);
        assertTrue(graph.getVertex(5).getDistance() == 1);
        assertTrue(graph.getVertex(6).getDistance() == 1);
        assertTrue(graph.getVertex(7).getDistance() == 1);
        assertTrue(graph.getVertex(4).getDistance() == 2);
        assertTrue(graph.getVertex(3).getDistance() == 3);
        assertTrue(graph.getVertex(2).getDistance() == 4);
        assertTrue(graph.getVertex(1).getDistance() == 5);
    }

    /**
     * Prepares the graph to be used in BFS tests.
     *
     * @return The graph.
     */
    protected PseudoG<UnweightedNodeBetweennessInfo, Edge> prepareGraph() {
        PseudoG<UnweightedNodeBetweennessInfo, Edge> graph = null;
        try {
            graph = new PseudoG<UnweightedNodeBetweennessInfo, Edge>(
                    UnweightedNodeBetweennessInfo.class, Edge.class);
            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
            graph.addEdge(3, 4);
            graph.addEdge(4, 5);
            graph.addEdge(4, 6);
            graph.addEdge(5, 6);
            graph.addEdge(5, 7);
            graph.addEdge(5, 8);
            graph.addEdge(6, 8);
            graph.addEdge(7, 8);
        } catch (NoSuchMethodException ex) {
        }
        return graph;
    }
}
