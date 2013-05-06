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
import org.jgrapht.Graph;

/**
 * Root Depth First Search (DFS) class.
 *
 * @param <V> The data structure to hold node information during the execution
 *            of DFS.
 *
 * @author Adam Gouge
 */
public class DFS<V extends DFSInfo, E> extends GraphSearchAlgorithm<V, E> {

    /**
     * For discovery and finishing times.
     */
    private int time = 0;

    /**
     * Constructs a new {@link DFS} object.
     *
     * @param graph   The graph.
     * @param nodeMap Maps nodes to their info.
     */
    public DFS(Graph<V, E> graph) {
        super(graph);
    }

    /**
     * Does the depth first search for all nodes in the graph.
     */
    protected void calculate() {
        for (V node : graph.vertexSet()) {
            if (node.getDiscoveryTime() < 0) {
                visit(node);
            }
        }
    }

    /**
     * Does a depth first search from the given node.
     */
    @Override
    public void calculate(V node) {
        visit(node);
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
