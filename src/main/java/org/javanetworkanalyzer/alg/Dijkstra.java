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

import org.javanetworkanalyzer.data.VSearch;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

/**
 * Home-brewed implementation of Dijkstra's algorithm.
 *
 * @param <V> Vertices
 * @param <E> Edges
 *
 * @author Adam Gouge
 */
public class Dijkstra<V extends VSearch<V, Double>, E>
        extends GraphSearchAlgorithm<V, E> {

    /**
     * Dijkstra queue.
     */
    private final PriorityQueue<V> queue;
    /**
     * Tolerance to be used when determining if two potential shortest paths
     * have the same length.
     */
    protected static final double TOLERANCE = 0.000000001;

    /**
     * Constructs a new {@link Dijkstra} object.
     *
     * @param graph     The graph.
     * @param startNode The start node.
     */
    public Dijkstra(Graph<V, E> graph) {
        super(graph);
        queue = createPriorityQueue();
    }

    /**
     * Do a Dijkstra search from the given start node.
     */
    @Override
    public void calculate(V startNode) {

        init(startNode);

        while (!queue.isEmpty()) {
            // Extract the minimum element.
            V u = queue.poll();
            // Do any pre-relax step.
            preRelaxStep(startNode, u);
            // Relax all the outgoing edges of u.
            for (E e : outgoingEdgesOf(u)) {
                relax(u, e, queue);
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
     * Any work to be done using vertex u before relaxing the outgoing edges of
     * u. In this class, it is empty on purpose.
     *
     * @param u Vertex u.
     */
    protected void preRelaxStep(V startNode, V u) {
    }

    /**
     * Relaxes the edge outgoing from u and updates the queue appropriately.
     *
     * @param u     Vertex u.
     * @param e     Edge e.
     * @param queue The queue.
     */
    protected void relax(V u, E e, PriorityQueue<V> queue) {
        // Get the target vertex.
        V v = Graphs.getOppositeVertex(graph, e, u);
        // Get the weight.
        double uvWeight = graph.getEdgeWeight(e);
        // If a smaller distance estimate is available, make the necessary
        // updates.
        if (smallerEstimateExists(u, v, uvWeight)) {
            updateNeighbor(u, v, uvWeight, queue);
        }
    }

    /**
     * Returns {@code true} iff the current distance estimate on v is greater
     * than OR EQUAL TO (this corresponds to multiple shortest paths) the length
     * of the path to u plus w(u,v).
     *
     * @param u        Vertex u
     * @param v        Vertex v
     * @param uvWeight w(u,v)
     *
     * @return {@code true} iff a smaller distance estimate exists.
     */
    protected boolean smallerEstimateExists(V u, V v, Double uvWeight) {
        // The TOLERANCE takes care of the "or equal to" part.
        // (If the distance to v is "just a little bit" less than
        // the distance to u plus w(u,v), then we consider this to be
        // a new shortest path to v through u.)
        // Without the tolerance, this would just be "greater".
        if (v.getDistance() > u.getDistance() + uvWeight - TOLERANCE) {
            return true;
        }
        return false;
    }

    /**
     * Sets the predecessor of v to be u and updates the distance estimate on v
     * to equal the distance to u plus w(u,v).
     *
     * @param u        Vertex u
     * @param v        Vertex v
     * @param uvWeight w(u,v)
     * @param queue    Queue
     */
    protected void updateNeighbor(V u, V v, Double uvWeight,
                                  PriorityQueue<V> queue) {
        // Set the predecessor and the distance.
        v.addPredecessor(u);
        v.setDistance(u.getDistance() + uvWeight);
        // Update the queue.
        queue.remove(v);
        queue.add(v);
    }

    /**
     * Creates the priority queue used in Dijkstra's algorithm.
     *
     * @return The priority queue used in Dijkstra's algorithm.
     */
    private PriorityQueue<V> createPriorityQueue() {
        return new PriorityQueue<V>(
                graph.vertexSet().size(),
                new Comparator<V>() {
            @Override
            public int compare(V v1, V v2) {
                return Double.compare(
                        v1.getDistance(),
                        v2.getDistance());
            }
        });
    }
}
