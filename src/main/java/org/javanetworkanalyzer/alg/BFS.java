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
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.SearchInfo;
import java.util.LinkedList;
import org.jgrapht.Graph;

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
     * BFS queue.
     */
    private final LinkedList<V> queue;

    /**
     * Constructs a new {@link BFS} object.
     *
     * @param graph     The graph.
     * @param startNode The start node.
     * @param nodeMap   Maps nodes to their info.
     */
    public BFS(Graph<V, E> graph) {
        super(graph);
        queue = new LinkedList<V>();
    }

    /**
     * Do the breadth first search.
     */
    @Override
    public void calculate(V startNode) {

        init(startNode);

        // While the queue is not empty ...
        while (!queue.isEmpty()) {
            V current = dequeueStep(queue);

            // For every neighbor of the current node ...
            for (final V neighbor : successorListOf(current)) {
                // If this neighbor is found for the first time ...
                if (neighbor.getDistance() < 0) {
                    enqueueAndUpdateDistance(current, neighbor, queue);
                    firstTimeFoundStep(current, neighbor);
                }
                // If this is a shortest path from startNode to neighbor
                // via current ...
                if (neighbor.getDistance() == current.getDistance() + 1) {
                    shortestPathStep(current, neighbor);
                }
            }
        }
    }

    @Override
    protected void init(V startNode) {
        startNode.setSource();
        queue.clear();
        queue.add(startNode);
    }

    /**
     * Dequeues a node from the given queue.
     *
     * @param queue The queue.
     *
     * @return The newly dequeued node.
     */
    protected V dequeueStep(LinkedList<V> queue) {
        return queue.poll();
    }

    /**
     * Enqueue neighbor and set neighbor's distance to be one more than
     * current's distance.
     *
     * @param current  Current node
     * @param neighbor Neighbor node
     * @param queue    The queue
     */
    protected void enqueueAndUpdateDistance(final V current,
                                            final V neighbor,
                                            LinkedList<V> queue) {
        // Enqueue the neighbor.
        queue.add(neighbor);
        // Update the distance.
        neighbor.setDistance(current.getDistance() + 1);
    }

    /**
     * Work to be done after {@link BFS#enqueueAndUpdateDistance}.
     *
     * @param current  Current node
     * @param neighbor Neighbor node
     */
    protected void firstTimeFoundStep(final V current, final V neighbor) {
        // Set the predecessor.
        neighbor.addPredecessor(current);
    }

    /**
     * Work to be done if this is a shortest path from the start node to
     * neighbor via current.
     *
     * @param current  Current node
     * @param neighbor Neighbor node
     */
    protected void shortestPathStep(V current, V neighbor) {
        // This is empty here on purpose.
    }
}
