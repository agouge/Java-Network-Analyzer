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

import com.graphhopper.sna.data.DFSInfo;
import com.graphhopper.sna.model.DirectedPseudoG;
import com.graphhopper.sna.model.Edge;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Tests the DFS algorithm from all possible start vertices on a directed graph.
 *
 * @author Adam Gouge
 */
// TODO: Write the tests for undirected graphs as well.
public class DFSRootNodeTest extends DFSTest {

    @Test
    public void testDFSDirectedFromVertexOne() {

        DirectedPseudoG<DFSInfo, Edge> graph = prepareGraph();
        new DFS<DFSInfo, Edge>(graph).calculate(graph.getVertex(1));
        DFSInfo[] vertices = indexVertices(graph);

        assertTrue(vertices[0].getDiscoveryTime() == 1);
        assertTrue(vertices[0].getFinishingTime() == 8);
        assertTrue(vertices[1].getDiscoveryTime() == 2);
        assertTrue(vertices[1].getFinishingTime() == 7);
        assertTrue(vertices[2].getDiscoveryTime() == 3);
        assertTrue(vertices[2].getFinishingTime() == 6);
        assertTrue(vertices[3].getDiscoveryTime() == 4);
        assertTrue(vertices[3].getFinishingTime() == 5);
        assertTrue(vertices[4].getDiscoveryTime() == -1);
        assertTrue(vertices[4].getFinishingTime() == -1);
        assertTrue(vertices[5].getDiscoveryTime() == -1);
        assertTrue(vertices[5].getFinishingTime() == -1);
    }

    @Test
    public void testDFSDirectedFromVertexTwo() {

        DirectedPseudoG<DFSInfo, Edge> graph = prepareGraph();
        new DFS<DFSInfo, Edge>(graph).calculate(graph.getVertex(2));
        DFSInfo[] vertices = indexVertices(graph);

        assertTrue(vertices[0].getDiscoveryTime() == -1);
        assertTrue(vertices[0].getFinishingTime() == -1);
        assertTrue(vertices[1].getDiscoveryTime() == 1);
        assertTrue(vertices[1].getFinishingTime() == 6);
        assertTrue(vertices[2].getDiscoveryTime() == 2);
        assertTrue(vertices[2].getFinishingTime() == 5);
        assertTrue(vertices[3].getDiscoveryTime() == 3);
        assertTrue(vertices[3].getFinishingTime() == 4);
        assertTrue(vertices[4].getDiscoveryTime() == -1);
        assertTrue(vertices[4].getFinishingTime() == -1);
        assertTrue(vertices[5].getDiscoveryTime() == -1);
        assertTrue(vertices[5].getFinishingTime() == -1);
    }

    @Test
    public void testDFSDirectedFromVertexThree() {

        DirectedPseudoG<DFSInfo, Edge> graph = prepareGraph();
        new DFS<DFSInfo, Edge>(graph).calculate(graph.getVertex(3));
        DFSInfo[] vertices = indexVertices(graph);

        assertTrue(vertices[0].getDiscoveryTime() == -1);
        assertTrue(vertices[0].getFinishingTime() == -1);
        assertTrue(vertices[1].getDiscoveryTime() == 3);
        assertTrue(vertices[1].getFinishingTime() == 4);
        assertTrue(vertices[2].getDiscoveryTime() == 1);
        assertTrue(vertices[2].getFinishingTime() == 6);
        assertTrue(vertices[3].getDiscoveryTime() == 2);
        assertTrue(vertices[3].getFinishingTime() == 5);
        assertTrue(vertices[4].getDiscoveryTime() == -1);
        assertTrue(vertices[4].getFinishingTime() == -1);
        assertTrue(vertices[5].getDiscoveryTime() == -1);
        assertTrue(vertices[5].getFinishingTime() == -1);
    }

    @Test
    public void testDFSDirectedFromVertexFour() {

        DirectedPseudoG<DFSInfo, Edge> graph = prepareGraph();
        new DFS<DFSInfo, Edge>(graph).calculate(graph.getVertex(4));
        DFSInfo[] vertices = indexVertices(graph);

        assertTrue(vertices[0].getDiscoveryTime() == -1);
        assertTrue(vertices[0].getFinishingTime() == -1);
        assertTrue(vertices[1].getDiscoveryTime() == 2);
        assertTrue(vertices[1].getFinishingTime() == 5);
        assertTrue(vertices[2].getDiscoveryTime() == 3);
        assertTrue(vertices[2].getFinishingTime() == 4);
        assertTrue(vertices[3].getDiscoveryTime() == 1);
        assertTrue(vertices[3].getFinishingTime() == 6);
        assertTrue(vertices[4].getDiscoveryTime() == -1);
        assertTrue(vertices[4].getFinishingTime() == -1);
        assertTrue(vertices[5].getDiscoveryTime() == -1);
        assertTrue(vertices[5].getFinishingTime() == -1);
    }

    @Test
    public void testDFSDirectedFromVertexFive() {

        DirectedPseudoG<DFSInfo, Edge> graph = prepareGraph();
        new DFS<DFSInfo, Edge>(graph).calculate(graph.getVertex(5));
        DFSInfo[] vertices = indexVertices(graph);

        assertTrue(vertices[0].getDiscoveryTime() == -1);
        assertTrue(vertices[0].getFinishingTime() == -1);
        assertTrue(vertices[1].getDiscoveryTime() == 3);
        assertTrue(vertices[1].getFinishingTime() == 6);
        assertTrue(vertices[2].getDiscoveryTime() == 4);
        assertTrue(vertices[2].getFinishingTime() == 5);
        assertTrue(vertices[3].getDiscoveryTime() == 2);
        assertTrue(vertices[3].getFinishingTime() == 7);
        assertTrue(vertices[4].getDiscoveryTime() == 1);
        assertTrue(vertices[4].getFinishingTime() == 10);
        assertTrue(vertices[5].getDiscoveryTime() == 8);
        assertTrue(vertices[5].getFinishingTime() == 9);
    }

    @Test
    public void testDFSDirectedFromVertexSix() {

        DirectedPseudoG<DFSInfo, Edge> graph = prepareGraph();
        new DFS<DFSInfo, Edge>(graph).calculate(graph.getVertex(6));
        DFSInfo[] vertices = indexVertices(graph);

        assertTrue(vertices[0].getDiscoveryTime() == -1);
        assertTrue(vertices[0].getFinishingTime() == -1);
        assertTrue(vertices[1].getDiscoveryTime() == -1);
        assertTrue(vertices[1].getFinishingTime() == -1);
        assertTrue(vertices[2].getDiscoveryTime() == -1);
        assertTrue(vertices[2].getFinishingTime() == -1);
        assertTrue(vertices[3].getDiscoveryTime() == -1);
        assertTrue(vertices[3].getFinishingTime() == -1);
        assertTrue(vertices[4].getDiscoveryTime() == -1);
        assertTrue(vertices[4].getFinishingTime() == -1);
        assertTrue(vertices[5].getDiscoveryTime() == 1);
        assertTrue(vertices[5].getFinishingTime() == 2);
    }
}
