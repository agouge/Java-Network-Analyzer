/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 * It is part of the OrbisGIS tool ecosystem.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * Java Network Analyzer is distributed under GPL 3 license.
 *
 * Copyright (C) 2012-2014 CNRS (IRSTV CNRS FR 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC CNRS UMR 6285)
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
 * 
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.VBFS;
import org.javanetworkanalyzer.model.EdgeSPT;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import java.util.LinkedList;
import java.util.Set;

/**
 * Root Breadth First Search (BFS) class.
 * <p/>
 * The {@link #calculate} method can be overridden in subclasses in order to
 * do graph analysis (e.g., calculating betweenness centrality).
 *
 * @param <V> The data structure to hold node information during the execution
 *            of BFS.
 * @author Adam Gouge
 * @param <E> edge
 */
public class BFS<V extends VBFS, E extends EdgeSPT>
        extends GraphSearchAlgorithm<V, E> {

    /**
     * BFS queue.
     */
    private final LinkedList<V> queue;

    /**
     * Constructor.
     *
     * @param graph The graph.
     */
    public BFS(Graph<V, E> graph) {
        super(graph);
        queue = new LinkedList<V>();
    }

    /**
     * Does a breadth first search from the given start node to all other nodes.
     *
     * @param startNode Start node
     */
    @Override
    public void calculate(V startNode) {

        init(startNode);

        // While the queue is not empty ...
        while (!queue.isEmpty()) {
            V current = dequeueStep(queue);

            // For every neighbor of the current node ...
            Set<E> outgoingEdges = outgoingEdgesOf(current);
            for (E e : outgoingEdges) {
                V neighbor = Graphs.getOppositeVertex(graph, e, current);
                // If this neighbor is found for the first time ...
                if (neighbor.getDistance() < 0) {
                    enqueueAndUpdateDistance(current, neighbor, queue);
                    firstTimeFoundStep(current, neighbor);
                }
                // If this is a shortest path from startNode to neighbor
                // via current ...
                if (neighbor.getDistance() == current.getDistance() + 1) {
                    shortestPathStep(current, neighbor, e);
                }
            }
        }
    }

    @Override
    protected void init(V startNode) {
        super.init(startNode);
        for (V node : graph.vertexSet()) {
            node.reset();
        }
        startNode.setSource();
        queue.clear();
        queue.add(startNode);
    }

    /**
     * Dequeues a node from the given queue.
     *
     * @param queue The queue.
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
        // Update the distance.
        neighbor.setDistance(current.getDistance() + 1);
        // Enqueue the neighbor.
        queue.add(neighbor);
    }

    /**
     * Work to be done after {@link BFS#enqueueAndUpdateDistance}.
     *
     * @param current  Current node
     * @param neighbor Neighbor node
     */
    protected void firstTimeFoundStep(final V current, final V neighbor) {
        // Empty on purpose
    }

    /**
     * Work to be done if this is a shortest path from the start node to
     * neighbor via current.
     *
     * @param current  Current node
     * @param neighbor Neighbor node
     * @param e        Edge (current, neighbor)
     */
    protected void shortestPathStep(V current, V neighbor, E e) {
        // Set the predecessor.
        neighbor.addPredecessor(current);
        neighbor.addPredecessorEdge(e);
    }
}
