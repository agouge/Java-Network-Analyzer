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
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.SearchInfo;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.MyIntDeque;
import java.util.Map;

/**
 * Root Breadth First Search (BFS) class.
 *
 * The {@link #calculate()} method can be overridden in subclasses in order to
 * do graph analysis (e.g., calculating betweenness centrality).
 *
 * @param <T> The data structure to hold node information during the execution
 *            of BFS.
 *
 * @author Adam Gouge
 */
public class BFS<T extends SearchInfo<Integer>>
        extends GraphSearchAlgorithm {

    /**
     * Stores information calculated during the execution of BFS in
     * {@link BFS#calculate()}.
     */
    protected final Map<Integer, T> nodeMap;

    /**
     * Constructs a new {@link BFS} object.
     *
     * @param graph     The graph.
     * @param startNode The start node.
     * @param nodeMap   Maps nodes to their info.
     */
    public BFS(Graph graph,
               int startNode,
               final Map<Integer, T> nodeMap) {
        super(graph, startNode);
        this.nodeMap = nodeMap;
    }

    /**
     * Do the breadth first search.
     */
    protected void calculate() {

        nodeMap.get(startNode).setSource();

        // Create the BFS traversal queue and enqueue startNode.
        MyIntDeque queue = new MyIntDeque();
        queue.push(startNode);

        // While the queue is not empty ...
        while (!queue.isEmpty()) {
            // ... dequeue a node
            int current = queue.pop();
            final T currentNBInfo = nodeMap.get(current);

            // For every neighbor of the current node ...
            for (EdgeIterator outgoingEdges =
                    GeneralizedGraphAnalyzer.outgoingEdges(graph, current);
                    outgoingEdges.next();) {
                int neighbor = outgoingEdges.adjNode();
                final T neighborNBInfo = nodeMap.get(neighbor);

                // If this neighbor is found for the first time ...
                if (neighborNBInfo.getDistance() < 0) {
                    // then update the distance
                    int updatedSteps = currentNBInfo.getDistance() + 1;
                    neighborNBInfo.setDistance(updatedSteps);
                    // set the predecessor
                    neighborNBInfo.addPredecessor(current);
                    // and enqueue it
                    queue.push(neighbor);
                }
            }
        }
    }
}
