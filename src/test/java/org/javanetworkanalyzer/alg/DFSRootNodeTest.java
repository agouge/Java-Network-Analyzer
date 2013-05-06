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

import org.javanetworkanalyzer.data.VDFS;
import org.javanetworkanalyzer.model.DirectedPseudoG;
import org.javanetworkanalyzer.model.Edge;
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

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        new DFS<VDFS, Edge>(graph).calculate(graph.getVertex(1));
        VDFS[] vertices = indexVertices(graph);

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

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        new DFS<VDFS, Edge>(graph).calculate(graph.getVertex(2));
        VDFS[] vertices = indexVertices(graph);

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

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        new DFS<VDFS, Edge>(graph).calculate(graph.getVertex(3));
        VDFS[] vertices = indexVertices(graph);

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

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        new DFS<VDFS, Edge>(graph).calculate(graph.getVertex(4));
        VDFS[] vertices = indexVertices(graph);

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

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        new DFS<VDFS, Edge>(graph).calculate(graph.getVertex(5));
        VDFS[] vertices = indexVertices(graph);

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

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        new DFS<VDFS, Edge>(graph).calculate(graph.getVertex(6));
        VDFS[] vertices = indexVertices(graph);

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
