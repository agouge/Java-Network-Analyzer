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
import org.javanetworkanalyzer.model.TraversalGraph;
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

        DFS<VDFS, Edge> dfs = new DFS<VDFS, Edge>(graph, true);
        TraversalGraph<VDFS, Edge> traverse = dfs.calculate(v1);

        assertTrue(v1.getDiscoveryTime() == 1);
        assertTrue(v1.getFinishingTime() == 8);
        assertTrue(v2.getDiscoveryTime() == 2);
        assertTrue(v2.getFinishingTime() == 7);
        assertTrue(v3.getDiscoveryTime() == 3);
        assertTrue(v3.getFinishingTime() == 6);
        assertTrue(v4.getDiscoveryTime() == 4);
        assertTrue(v4.getFinishingTime() == 5);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);

        assertTrue(traverse.getRoot().equals(v1));
        assertTrue(traverse.edgeSet().size() == 3);
        assertTrue(traverse.containsEdge(v1, v2));
        assertTrue(traverse.containsEdge(v2, v3));
        assertTrue(traverse.containsEdge(v3, v4));
    }

    @Test
    public void testDFSDirectedFromVertexTwo() {

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        DFS<VDFS, Edge> dfs = new DFS<VDFS, Edge>(graph, true);
        TraversalGraph<VDFS, Edge> traverse = dfs.calculate(v2);

        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 1);
        assertTrue(v2.getFinishingTime() == 6);
        assertTrue(v3.getDiscoveryTime() == 2);
        assertTrue(v3.getFinishingTime() == 5);
        assertTrue(v4.getDiscoveryTime() == 3);
        assertTrue(v4.getFinishingTime() == 4);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);

        assertTrue(traverse.getRoot().equals(v2));
        assertTrue(traverse.edgeSet().size() == 2);
        assertTrue(traverse.containsEdge(v2, v3));
        assertTrue(traverse.containsEdge(v3, v4));
    }

    @Test
    public void testDFSDirectedFromVertexThree() {

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        DFS<VDFS, Edge> dfs = new DFS<VDFS, Edge>(graph, true);
        TraversalGraph<VDFS, Edge> traverse = dfs.calculate(v3);

        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 3);
        assertTrue(v2.getFinishingTime() == 4);
        assertTrue(v3.getDiscoveryTime() == 1);
        assertTrue(v3.getFinishingTime() == 6);
        assertTrue(v4.getDiscoveryTime() == 2);
        assertTrue(v4.getFinishingTime() == 5);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);

        assertTrue(traverse.getRoot().equals(v3));
        assertTrue(traverse.edgeSet().size() == 2);
        assertTrue(traverse.containsEdge(v3, v4));
        assertTrue(traverse.containsEdge(v4, v2));
    }

    @Test
    public void testDFSDirectedFromVertexFour() {

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        DFS<VDFS, Edge> dfs = new DFS<VDFS, Edge>(graph, true);
        TraversalGraph<VDFS, Edge> traverse = dfs.calculate(v4);

        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 2);
        assertTrue(v2.getFinishingTime() == 5);
        assertTrue(v3.getDiscoveryTime() == 3);
        assertTrue(v3.getFinishingTime() == 4);
        assertTrue(v4.getDiscoveryTime() == 1);
        assertTrue(v4.getFinishingTime() == 6);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);

        assertTrue(traverse.getRoot().equals(v4));
        assertTrue(traverse.edgeSet().size() == 2);
        assertTrue(traverse.containsEdge(v4, v2));
        assertTrue(traverse.containsEdge(v2, v3));
    }

    @Test
    public void testDFSDirectedFromVertexFive() {

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        DFS<VDFS, Edge> dfs = new DFS<VDFS, Edge>(graph, true);
        TraversalGraph<VDFS, Edge> traverse = dfs.calculate(v5);

        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 3);
        assertTrue(v2.getFinishingTime() == 6);
        assertTrue(v3.getDiscoveryTime() == 4);
        assertTrue(v3.getFinishingTime() == 5);
        assertTrue(v4.getDiscoveryTime() == 2);
        assertTrue(v4.getFinishingTime() == 7);
        assertTrue(v5.getDiscoveryTime() == 1);
        assertTrue(v5.getFinishingTime() == 10);
        assertTrue(v6.getDiscoveryTime() == 8);
        assertTrue(v6.getFinishingTime() == 9);

        assertTrue(traverse.getRoot().equals(v5));
        assertTrue(traverse.edgeSet().size() == 4);
        assertTrue(traverse.containsEdge(v5, v4));
        assertTrue(traverse.containsEdge(v4, v2));
        assertTrue(traverse.containsEdge(v2, v3));
        assertTrue(traverse.containsEdge(v5, v6));
    }

    @Test
    public void testDFSDirectedFromVertexSix() {

        DirectedPseudoG<VDFS, Edge> graph = prepareGraph();
        DFS<VDFS, Edge> dfs = new DFS<VDFS, Edge>(graph, true);
        TraversalGraph<VDFS, Edge> traverse = dfs.calculate(v6);

        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == -1);
        assertTrue(v2.getFinishingTime() == -1);
        assertTrue(v3.getDiscoveryTime() == -1);
        assertTrue(v3.getFinishingTime() == -1);
        assertTrue(v4.getDiscoveryTime() == -1);
        assertTrue(v4.getFinishingTime() == -1);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == 1);
        assertTrue(v6.getFinishingTime() == 2);

        assertTrue(traverse.getRoot().equals(v6));
        assertTrue(traverse.edgeSet().size() == 0);
    }
}
