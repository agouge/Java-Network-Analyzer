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
import org.javanetworkanalyzer.model.AsUndirectedG;
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

        assertTrue(graph.getVertex(1).getDiscoveryTime() == 1);
        assertTrue(graph.getVertex(1).getFinishingTime() == 8);
        assertTrue(graph.getVertex(2).getDiscoveryTime() == 2);
        assertTrue(graph.getVertex(2).getFinishingTime() == 7);
        assertTrue(graph.getVertex(3).getDiscoveryTime() == 3);
        assertTrue(graph.getVertex(3).getFinishingTime() == 6);
        assertTrue(graph.getVertex(4).getDiscoveryTime() == 4);
        assertTrue(graph.getVertex(4).getFinishingTime() == 5);
        assertTrue(graph.getVertex(5).getDiscoveryTime() == 9);
        assertTrue(graph.getVertex(5).getFinishingTime() == 12);
        assertTrue(graph.getVertex(6).getDiscoveryTime() == 10);
        assertTrue(graph.getVertex(6).getFinishingTime() == 11);
    }

    @Test
    public void testDFSUndirected() throws NoSuchMethodException {

        // Note: The traversal order is a little different than if we had
        // directly constructed a PseudoG, but traversal order is in general
        // not unique in DFS.
        AsUndirectedG<VDFS, Edge> graph =
                new AsUndirectedG<VDFS, Edge>(prepareGraph());

        new DFS<VDFS, Edge>(graph).calculate();

        // Note: Cannot use graph.getVertex(int) because AsUndirectedGraph
        // is not a keyed graph!
        assertTrue(graph.getVertex(1).getDiscoveryTime() == 1);
        assertTrue(graph.getVertex(1).getFinishingTime() == 12);
        assertTrue(graph.getVertex(2).getDiscoveryTime() == 2);
        assertTrue(graph.getVertex(2).getFinishingTime() == 11);
        assertTrue(graph.getVertex(3).getDiscoveryTime() == 4);
        assertTrue(graph.getVertex(3).getFinishingTime() == 5);
        assertTrue(graph.getVertex(4).getDiscoveryTime() == 3);
        assertTrue(graph.getVertex(4).getFinishingTime() == 10);
        assertTrue(graph.getVertex(5).getDiscoveryTime() == 6);
        assertTrue(graph.getVertex(5).getFinishingTime() == 9);
        assertTrue(graph.getVertex(6).getDiscoveryTime() == 7);
        assertTrue(graph.getVertex(6).getFinishingTime() == 8);
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
}
