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
import com.graphhopper.util.EdgeIterator;
import java.util.LinkedList;
import org.jgrapht.Graph;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;

/**
 * Root Breadth First Search (BFS) class.
 *
 * The {@link #calculate()} method can be overridden in subclasses in order to
 * do graph analysis (e.g., calculating betweenness centrality).
 *
 * @param <V> The data structure to hold node information during the execution
 *            of BFS.
 *
 * @author Adam Gouge
 */
public class BFS<V extends SearchInfo<V, Integer>, E> 
    extends GraphSearchAlgorithm<V, E> {

    /**
     * Constructs a new {@link BFS} object.
     *
     * @param graph     The graph.
     * @param startNode The start node.
     * @param nodeMap   Maps nodes to their info.
     */
    public BFS(Graph<V, E> graph, V startNode) {
        super(graph, startNode);
    }

    /**
     * Do the breadth first search.
     */
    protected void calculate() {

        startNode.setSource();

        // Create the BFS traversal queue and enqueue startNode.
        // ToDo: Make sure this is FIFO!
        LinkedList<V> queue = new LinkedList<V>();
        queue.add(startNode);

        // While the queue is not empty ...
        while (!queue.isEmpty()) {
            // ... dequeue a node
            V current = queue.poll();

            // For every neighbor of the current node ...
            for (V neighbor : neighborIndex.neighborListOf(current)) {
                // If this neighbor is found for the first time ...
                if (neighbor.getDistance() < 0) {
                    // then update the distance
                    int updatedSteps = current.getDistance() + 1;
                    neighbor.setDistance(updatedSteps);
                    // set the predecessor
                    neighbor.addPredecessor(current);
                    // and enqueue it
                    queue.add(neighbor);
                }
            }
        }
    }
}
