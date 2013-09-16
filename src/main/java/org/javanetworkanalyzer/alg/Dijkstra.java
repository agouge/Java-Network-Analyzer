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

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.javanetworkanalyzer.data.VDijkstra;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DirectedSubgraph;
import org.jgrapht.graph.EdgeReversedGraph;
import org.jgrapht.graph.Subgraph;
import org.jgrapht.graph.UndirectedSubgraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home-brewed implementation of Dijkstra's algorithm.
 *
 * @param <V> Vertices
 * @param <E> Edges
 *
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
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Dijkstra.class);

    private Subgraph shortestPathTree;

    /**
     * Constructs a new {@link Dijkstra} object.
     *
     * @param graph The graph.
     */
    public Dijkstra(Graph<V, E> graph) {
        super(graph);
        queue = createPriorityQueue();
    }

    /**
     * Do a Dijkstra search from the given start node to all other nodes.
     */
    // TODO: Add a unit test for this.
    @Override
    public Subgraph calculate(V startNode) {

        init(startNode);

        while (!queue.isEmpty()) {
            // Extract the minimum element.
            V u = queue.poll();
//            shortestPathTree.addVertex(u);
            // Do any pre-relax step.
            if (preRelaxStep(startNode, u)) {
                break;
            }
            // Relax all the outgoing edges of u.
            for (E e : outgoingEdgesOf(u)) {
                relax(startNode, u, e, queue);
            }
        }

        LOGGER.info("------ " + startNode.getID() + " ------ "
                + shortestPathTree.toString());

        return shortestPathTree;
    }

    @Override
    protected void init(V startNode) {
        for (V node : graph.vertexSet()) {
            node.reset();
        }
        startNode.setSource();
        if (graph instanceof DirectedGraph) {
            shortestPathTree = new DirectedSubgraph<V, E>(
                    (DirectedGraph<V, E>) graph, new HashSet<V>(), null);
        } else if (graph instanceof UndirectedGraph) {
            shortestPathTree = new UndirectedSubgraph<V, E>(
                    (UndirectedGraph<V, E>) graph, new HashSet<V>(), null);
        }

        queue.clear();
        queue.add(startNode);
    }

    /**
     * Any work to be done using vertex u before relaxing the outgoing edges of
     * u. Must return true if the search should be stopped.
     *
     * @param u Vertex u.
     *
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
            LOGGER.debug("\tNew SP: d({},{}) = {}",
                    startNode.getID(), v.getID(), u.getDistance() + uvWeight);
            shortestPathSoFarUpdate(startNode, u, v, uvWeight, e, queue);
        } else if (Math.abs(v.getDistance() - (u.getDistance() + uvWeight))
                < TOLERANCE) {
            LOGGER.debug("\tMultiple SP: d({},{}) = {}",
                    startNode.getID(), v.getID(), u.getDistance() + uvWeight);
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
        // Remove all the edges from the shortest path tree
        for (V pred : (Set<V>) v.getPredecessors()) {
            // TODO: Get all edges?
            E edgeToRemove = (E) shortestPathTree.getEdge(pred, v);
            boolean removedEdge = shortestPathTree.removeEdge(edgeToRemove);
            LOGGER.debug("Removed edge {} [{}]", edgeToRemove, removedEdge);
        }
        // Add this edge to the SPT
        shortestPathTree.addVertex(u);
        shortestPathTree.addVertex(v);
        addSPTEdge(u, v, e);
        // Reset the predecessors and add u as a predecessor
        v.getPredecessors().clear();
        v.addPredecessor(u);
        // Set the distance
        v.setDistance(u.getDistance() + uvWeight);
        // Update the queue.
        queue.remove(v);
        queue.add(v);
    }

    private void addSPTEdge(V u, V v, E e) {
        boolean edgeAdded;
        try {
            edgeAdded = shortestPathTree.addEdge(u, v, e);
        } catch (AssertionError ex) {
            // In undirected graphs, sometimes u and v are switched.
            edgeAdded = shortestPathTree.addEdge(v, u, e);
        }
        LOGGER.debug("Added edge ({},{}) [{}]", u.getID(), v.getID(), edgeAdded);
    }

    /**
     * Updates to be performed if the path to v through u is a new multiple
     * shortest path.
     *
     * @param u Vertex u
     * @param v Vertex v
     */
    protected void multipleShortestPathUpdate(V u, V v, E e) {
        // There is no need to set the distance on v since it this is a
        // multiple shortest path.
        // Add this edge to the SPT.
        addSPTEdge(u, v, e);
        v.addPredecessor(u);
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
     *
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
     * Performs a Dijkstra search from the source, stopping once the all the
     * targets are found.
     *
     * @param source  Source
     * @param targets Targets
     *
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
     * Performs a Dijkstra search from each source to the given target using a
     * {@link #oneToOne}.
     *
     * Note: This is not very efficient since a separate search is required from
     * each start node. TODO: Optimize!
     *
     * @param sources Sources
     * @param target  Target
     *
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
     *
     * Note: Using oneToMany rather than manyToOne is more efficient since we
     * don't have to create an edge-reversed graph.
     *
     * @param sources Sources
     * @param targets Targets
     *
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
