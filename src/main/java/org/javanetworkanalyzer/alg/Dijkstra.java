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

import org.javanetworkanalyzer.data.VDijkstra;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.EdgeReversedGraph;

import java.util.*;

/**
 * Home-brewed implementation of Dijkstra's algorithm.
 *
 * @param <V> Vertices
 * @param <E> Edges
 * @author Adam Gouge
 */
public class Dijkstra<V extends VDijkstra, E>
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
     * Constructor.
     *
     * @param graph The graph.
     */
    public Dijkstra(Graph<V, E> graph) {
        super(graph);
        queue = createPriorityQueue();
    }

    /**
     * Does a Dijkstra search from the given start node to all other nodes.
     * The shortest path "tree" we return may contain multiple shortest paths.
     *
     *
     * @param startNode Start node
     * @return The SPT if {@link #returnSPT} is true; null otherwise.
     */
    @Override
    public void calculate(V startNode) {

        init(startNode);

        while (!queue.isEmpty()) {
            // Extract the minimum element.
            V u = queue.poll();
            // Do any pre-relax step.
            if (preRelaxStep(startNode, u)) {
                break;
            }
            // Relax all the outgoing edges of u.
            Set<E> outgoing = outgoingEdgesOf(u);
            for (E e : outgoing) {
                relax(startNode, u, e, queue);
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
     * Any work to be done using vertex u before relaxing the outgoing edges of
     * u. Must return true if the search should be stopped.
     *
     * @param u Vertex u.
     * @return true if we should stop the Dijkstra search.
     */
    protected boolean preRelaxStep(V startNode, V u) {
        return false;
    }

    /**
     * Relaxes the edge outgoing from u and updates the queue appropriately.
     *
     * @param u     Vertex u.
     * @param e     Edge e.
     * @param queue The queue.
     */
    protected void relax(V startNode, V u, E e, PriorityQueue<V> queue) {
        // Get the target vertex.
        V v = Graphs.getOppositeVertex(graph, e, u);
        // Get the weight.
        double uvWeight = graph.getEdgeWeight(e);
        // If a smaller distance estimate is available, make the necessary
        // updates.
        if (v.getDistance() > u.getDistance() + uvWeight) {
            shortestPathSoFarUpdate(startNode, u, v, uvWeight, e, queue);
        } else if (Math.abs(v.getDistance() - (u.getDistance() + uvWeight))
                < TOLERANCE) {
            multipleShortestPathUpdate(u, v, e);
        }
    }

    /**
     * Updates to be performed if the path to v through u is the shortest
     * path to v found so far.
     *
     * @param u        Vertex u
     * @param v        Vertex v
     * @param uvWeight w(u,v)
     * @param e        Edge e
     * @param queue    Queue
     */
    protected void shortestPathSoFarUpdate(V startNode, V u, V v, Double uvWeight,
                                           E e, PriorityQueue<V> queue) {
        // Reset the predecessors and add u as a predecessor
        v.clear();
        v.addPredecessor(u);
        v.addPredecessorEdge(e);
        // Set the distance
        v.setDistance(u.getDistance() + uvWeight);
        // Update the queue.
        queue.remove(v);
        queue.add(v);
    }

    /**
     * Updates to be performed if the path to v through u is a new multiple
     * shortest path. There is no need to set the distance on v since this
     * is a multiple shortest path.
     *
     * @param u Vertex u
     * @param v Vertex v
     * @param e Edge e
     */
    protected void multipleShortestPathUpdate(V u, V v, E e) {
        // Add u to the list of predecessors.
        v.addPredecessor(u);
        v.addPredecessorEdge(e);
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

    /**
     * Performs a Dijkstra search from the source, stopping once the target is
     * found.
     *
     * @param source Source
     * @param target Target
     * @return The distance from the source to the target.
     */
    public double oneToOne(V source, final V target) {
        if (source == null || !graph.containsVertex(source)) {
            throw new IllegalArgumentException(
                    "Source vertex not found.");
        } else if (target == null || !graph.containsVertex(target)) {
            throw new IllegalArgumentException(
                    "Target vertex not found.");
        } else {
            // If source=target, then no search is necessary.
            if (source.equals(target)) {
                // So just set the distance.
                source.setSource();
                // and return it.
                return source.getDistance();
            } else {
                // Otherwise we have to search.
                new Dijkstra<V, E>(graph) {
                    @Override
                    protected boolean preRelaxStep(V startNode, V u) {
                        // If we have reached the target, then stop the search.
                        if (u.equals(target)) {
                            return true;
                        }
                        // Otherwise we have to keep going.
                        return false;
                    }
                }.calculate(source);
                // Return the distance to the target.
                return target.getDistance();
            }
        }
    }

    /**
     * Performs a Dijkstra search from the source, stopping once all the
     * targets are found.
     *
     * @param source  Source
     * @param targets Targets
     * @return A map of distances from the source keyed by the target vertex.
     */
    public Map<V, Double> oneToMany(V source, final Set<V> targets) {
        if (targets.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please specify at least one target.");
        } else {
            final Map<V, Double> distances = new HashMap<V, Double>();

            if (targets.size() == 1) {
                V target = targets.iterator().next();
                double distance = oneToOne(source, target);
                distances.put(target, distance);
            } else {

                // Use a copy of targets.
                final Set<V> remainingTargets = new HashSet<V>(targets);

                // Instead of looping through the targets and using oneToOne (which
                // would require one search per target), we do just one search until
                // all targets are found.
                new Dijkstra<V, E>(graph) {
                    @Override
                    protected boolean preRelaxStep(V startNode, V u) {
                        // If there are no more targets, then stop the search.
                        if (remainingTargets.isEmpty()) {
                            return true;
                        } else {
                            // If u is  a target, then remove it from the
                            // remaining targets and record its distance.
                            if (remainingTargets.remove(u)) {
                                distances.put(u, u.getDistance());
                            }
                            // If there are no more targets, then stop the search.
                            if (remainingTargets.isEmpty()) {
                                return true;
                            }
                        }
                        return false;
                    }
                }.calculate(source);
            }
            return distances;
        }
    }

    /**
     * Performs a Dijkstra search from each source to the given target by
     * reversing the graph and using a {@link #oneToMany} from the target
     * to all the sources. If the graph is undirected, there is no need
     * to reverse the graph.
     *
     * @param sources Sources
     * @param target  Target
     * @return A map of the distance to the target keyed by the source vertex.
     */
    public Map<V, Double> manyToOne(final Set<V> sources, V target) {
        if (sources.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please specify at least one source.");
        } else {
            // For directed graphs, we simply reverse the graph and do a 
            // oneToMany from the target.
            if (graph instanceof DirectedGraph) {
                EdgeReversedGraph<V, E> reversedGraph =
                        new EdgeReversedGraph<V, E>((DirectedGraph) graph);
                return new Dijkstra<V, E>(reversedGraph)
                        .oneToMany(target, sources);
            } // For undirected graphs, there is no need to reverse the graph.
            else {
                return oneToMany(target, sources);
            }
        }
    }

    /**
     * Performs a Dijkstra search from each source to each target using a
     * {@link #oneToMany} search from each source.
     * <p/>
     * Note: Using oneToMany rather than manyToOne is more efficient since we
     * don't have to create an edge-reversed graph.
     *
     * @param sources Sources
     * @param targets Targets
     * @return A map of maps of distances. The first V is keyed by the source
     *         and the second V is keyed by the target.
     */
    public Map<V, Map<V, Double>> manyToMany(final Set<V> sources,
                                             final Set<V> targets) {
        if (sources.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please specify at least one source.");
        } else {
            final Map<V, Map<V, Double>> distances =
                    new HashMap<V, Map<V, Double>>();
            for (V source : sources) {
                Map<V, Double> oneToMany = oneToMany(source, targets);
                distances.put(source, oneToMany);
            }
            return distances;
        }
    }
}
