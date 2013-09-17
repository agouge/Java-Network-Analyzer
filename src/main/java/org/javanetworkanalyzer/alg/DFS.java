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
import org.javanetworkanalyzer.model.ShortestPathTree;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.Subgraph;

/**
 * Root Depth First Search (DFS) class.
 *
 * @param <V> The data structure to hold node information during the execution
 *            of DFS.
 *
 * @author Adam Gouge
 */
public class DFS<V extends VDFS, E> extends GraphSearchAlgorithm<V, E> {

    /**
     * For discovery and finishing times.
     */
    private int time = 0;

    /**
     * Constructor. By default, does not calculate SPTs.
     *
     * @param graph   The graph.
     */
    public DFS(Graph<V, E> graph) {
        this(graph, false);
    }

    /**
     * Constructor. The user can specify whether SPTs are calculated.
     *
     * @param graph     The graph.
     * @param returnSPT True iff the SPT is to be calculated.
     */
    public DFS(Graph<V, E> graph, boolean returnSPT) {
        super(graph, returnSPT);
    }

    /**
     * Does the depth first search for all nodes in the graph.
     */
    public void calculate() {
        for (V node : graph.vertexSet()) {
            if (node.getDiscoveryTime() < 0) {
                visit(node);
            }
        }
    }

    /**
     * Does a depth first search from the given start node.
     *
     * @param startNode Start node
     */
    @Override
    public ShortestPathTree<V, E> calculate(V startNode) {
        visit(startNode);

        if (returnSPT) {
            return reconstructSPT(startNode);
        }
        return null;
    }

    @Override
    protected void init(V startNode) {
        // Empty on purpose.
    }

    /**
     * Visit the given node, updating its predecessor and discovery and
     * finishing times.
     *
     * @param node The node.
     */
    protected void visit(V node) {

        time++;

        node.setDiscoveryTime(time);

        for (V neighbor : successorListOf(node)) {
            if (neighbor.getDiscoveryTime() < 0) {
                neighbor.addPredecessor(node);
                visit(neighbor);
            }
        }

        time++;

        node.setFinishingTime(time);
    }
}
