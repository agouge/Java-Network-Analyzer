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
import org.jgrapht.graph.AsUndirectedGraph;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Tests the DFS algorithm on a(n) (un)directed graph. Note: Both the choice of
 * start vertex and the order in which neighbors are visited depend on the
 * underlying graph structure. (In the below examples, the start node seems to
 * be the first node added to the graph when the graph is created.) To specify
 * the start vertex, use {@link DFSRootNode}.
 *
 * @author Adam Gouge
 */
public class DFSTest {

    @Test
    public void testDFSDirected() {
        DirectedPseudoG<VDFS, Edge> graph =
                prepareGraph();

        new DFS<VDFS, Edge>(graph).calculate();

        VDFS[] vertices = indexVertices(graph);

        assertTrue(vertices[0].getDiscoveryTime() == 1);
        assertTrue(vertices[0].getFinishingTime() == 8);
        assertTrue(vertices[1].getDiscoveryTime() == 2);
        assertTrue(vertices[1].getFinishingTime() == 7);
        assertTrue(vertices[2].getDiscoveryTime() == 3);
        assertTrue(vertices[2].getFinishingTime() == 6);
        assertTrue(vertices[3].getDiscoveryTime() == 4);
        assertTrue(vertices[3].getFinishingTime() == 5);
        assertTrue(vertices[4].getDiscoveryTime() == 9);
        assertTrue(vertices[4].getFinishingTime() == 12);
        assertTrue(vertices[5].getDiscoveryTime() == 10);
        assertTrue(vertices[5].getFinishingTime() == 11);
    }

    @Test
    public void testDFSUndirected() {

        // Note: The traveral order is a little different than if we had
        // directly constructed a PseudoG, but traversal order is in general
        // not unique in DFS.
        AsUndirectedGraph<VDFS, Edge> graph =
                new AsUndirectedGraph<VDFS, Edge>(prepareGraph());

        new DFS<VDFS, Edge>(graph).calculate();

        // Note: Cannot use graph.getVertex(int) because AsUndirectedGraph
        // is not a keyed graph!
        for (VDFS node : graph.vertexSet()) {
            if (node.getID() == 1) {
                assertTrue(node.getDiscoveryTime() == 1);
                assertTrue(node.getFinishingTime() == 12);
            } else if (node.getID() == 2) {
                assertTrue(node.getDiscoveryTime() == 2);
                assertTrue(node.getFinishingTime() == 11);
            } else if (node.getID() == 3) {
                assertTrue(node.getDiscoveryTime() == 4);
                assertTrue(node.getFinishingTime() == 5);
            } else if (node.getID() == 4) {
                assertTrue(node.getDiscoveryTime() == 3);
                assertTrue(node.getFinishingTime() == 10);
            } else if (node.getID() == 5) {
                assertTrue(node.getDiscoveryTime() == 6);
                assertTrue(node.getFinishingTime() == 9);
            } else if (node.getID() == 6) {
                assertTrue(node.getDiscoveryTime() == 7);
                assertTrue(node.getFinishingTime() == 8);
            }
        }
    }

    /**
     * Prepares the graph to be used in BFS tests.
     *
     * @return The graph.
     */
    protected DirectedPseudoG<VDFS, Edge> prepareGraph() {
        DirectedPseudoG<VDFS, Edge> graph =
                new DirectedPseudoG<VDFS, Edge>(
                VDFS.class, Edge.class);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 2);
        graph.addEdge(5, 4);
        graph.addEdge(5, 6);
        graph.addEdge(6, 6);
        return graph;
    }

    /**
     * Facilitates obtaining references to vertices.
     *
     * @param graph Input graph.
     *
     * @return An array with the vertex with index i is in position i-1.
     */
    protected VDFS[] indexVertices(DirectedPseudoG<VDFS, Edge> graph) {
        int numberOfNodes = 6;
        VDFS[] vertices = new VDFS[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) {
            vertices[i] = graph.getVertex(i + 1);
        }
        return vertices;
    }
}
