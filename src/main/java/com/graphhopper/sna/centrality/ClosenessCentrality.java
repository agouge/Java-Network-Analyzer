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

import com.graphhopper.coll.MyBitSet;
import com.graphhopper.coll.MyBitSetImpl;
import com.graphhopper.routing.AStar;
import com.graphhopper.routing.AStarBidirection;
import com.graphhopper.routing.AbstractRoutingAlgorithm;
import com.graphhopper.routing.DijkstraBidirection;
import com.graphhopper.routing.DijkstraBidirectionRef;
import com.graphhopper.routing.DijkstraSimple;
import com.graphhopper.routing.Path;
import com.graphhopper.routing.ch.PrepareContractionHierarchies;
import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.MyIntDeque;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.hash.TIntHashSet;

/**
 * Implementation of Freeman's original closeness centrality using several
 * different shortest path algorithms (BFS, Dijkstra, A*, Contraction
 * hierarchies, etc.). We use the GraphHopper implementations of all algorithms
 * except BFS.
 *
 * <p> The user choses the appropriate shortest path algorithm to use. Each
 * <code>calculate</code> method stores the results in a hash map, where the
 * keys are the vertices and the values are the closeness.
 *
 * <p> <i>Note</i>: For now, the {@link Graph} is <b>assumed to be
 * connected</b>.
 *
 * <p> Freeman, Linton, "A set of measures of centrality based upon
 * betweenness." <i>Sociometry</i> 40: 35–41, 1977. </p>
 *
 * @author Adam Gouge
 */
public class ClosenessCentrality {

    /**
     * The graph on which we will calculate the closeness centrality.
     */
    private Graph graph;

    /**
     * Constructs a {@link ClosenessCentrality} object.
     *
     * @param graph The graph on which we will calculate the closeness
     *              centrality.
     */
    public ClosenessCentrality(Graph graph) {
        this.graph = graph;
    }

    /**
     * Computes the closeness centrality indices of all vertices of an
     * <b>unweighted</b> graph by using a Breadth-First Search (BFS).
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public TIntDoubleHashMap calculateUsingBFS() {

        System.out.println(
                "Calculating closeness centrality using BFS.");

        long time = System.currentTimeMillis();

        // Closeness centrality
        TIntDoubleHashMap closenessCentrality = new TIntDoubleHashMap();
        // Recover the node set and an iterator on it.
        TIntHashSet nodeSet = GraphAnalyzer.nodeSet(graph);
        TIntIterator nodeIter = nodeSet.iterator();

        // Begin graph analysis.
        while (nodeIter.hasNext()) {

            // Start timing for this node.
            long start = System.currentTimeMillis();

            // Get the next node. (final so this node stays fixed)
            final int node = nodeIter.next();

            // SHORTEST PATHS COMPUTATION
//            System.out.println("Computing shortest paths for node " + node);
            PathLengthData pathLengthsData =
                    computeShortestPathsData(node, nodeSet);
//            System.out.println("Number of shortest path lengths accumulated: "
//                    + pathLengths.getCount());

            // Recover the eccentricity for this node.
            final double eccentricity = pathLengthsData.getMaxSteps();

            // Get the average path length for this node and store it.
            final double apl =
                    (pathLengthsData.getCount() > 0)
                    ? pathLengthsData.getAverageSteps() : 0.0;

            // Once we have the average path length for this node,
            // we have the closeness centrality for this node.
            final double closeness = (apl > 0.0) ? 1 / apl : 0.0;
            // Store it.
            closenessCentrality.put(node, closeness);

            // Stop timing for this node.
            long stop = System.currentTimeMillis();

            System.out.println("Node: " + node
                    + ", Closeness: " + closeness
                    + ", Time: " + (stop - start)
                    + " ms.");
        } // End node iteration.

        time = System.currentTimeMillis() - time;
        System.out.println("Closeness centrality computation (BFS) took "
                + time + " ms.");
        return closenessCentrality;
    }

    /**
     * Computes the shortest path lengths from the given node to all other nodes
     * in the unweighted graph and stores them in a {@link PathLengthData}
     * object.
     *
     * <p> This method uses a breadth-first traversal (BFS) through the graph,
     * starting from the specified node, in order to find all reachable nodes
     * and accumulate their distances. This only works if all edges are of
     * weight 1.
     *
     * @param startNode Start node of the shortest paths to be found.
     *
     * @return Data on the shortest path lengths from the current node to all
     *         other reachable nodes in the graph.
     */
    private PathLengthData computeShortestPathsData(
            int startNode,
            TIntHashSet nodeSet) {

//        System.out.println("BFS started for node " + aNode + ".");

        // Get an iterator on the node set.
        TIntIterator iterator = nodeSet.iterator();

        // Create the "queue" and add aNode to it.
        MyIntDeque queue = new MyIntDeque();
        queue.push(startNode);

        // Create the set of marked nodes and add startNode to it.
        MyBitSet marked = new MyBitSetImpl(nodeSet.size());
        marked.add(startNode);

        // Initialize the distances.
        TIntIntHashMap distances = new TIntIntHashMap();
        while (iterator.hasNext()) {
            int next = iterator.next();
//            System.out.println("Initializing the distance to "
//                    + next + " to " + Integer.MAX_VALUE);
            distances.put(next, Integer.MAX_VALUE);
        }
//        System.out.println("Resetting the distance to "
//                + startNode + " to be zero.");
        distances.put(startNode, 0);
//        printDistancesFromNode(distances, startNode);

        // Initialize the result to be returned.
        PathLengthData result = new PathLengthData();

        // The current child node to be considered.
        int currentNode;
        // The current distance from startNode.
        int currentDistance;

        // While the queue is not empty ...
        while (!queue.isEmpty()) {

            // ... dequeue a node.
            currentNode = queue.pop();
//            System.out.println("BFS for " + currentNode + " ");

            // Get the outgoing edges of the current node.
            EdgeIterator iter = graph.getOutgoing(currentNode);
            while (iter.next()) {

                // For every neighbor of the current node,
                // if the neighbor is not marked ...
                int neighbor = iter.node();
                if (!marked.contains(neighbor)) {

                    // ... then mark it,
                    marked.add(neighbor);

                    // ... enqueue it,
                    queue.push(neighbor);

                    // ... and set the distance.
                    currentDistance = distances.get(currentNode) + 1;
                    distances.put(neighbor, currentDistance);
//                    System.out.println("Set ("
//                            + currentNode + ", "
//                            + neighbor + ") = "
//                            + currentDistance);
                    result.addSPStep(currentDistance);
                }
            }
        }

        // TODO: If a node is unreachable, then make the average path length
        // infinite in order to make closeness centrality zero.
//        if (dist.containsValue(Integer.MAX_VALUE)) {
//            result.addSPStep(Integer.MAX_VALUE);
//        }
        return result;
    }

    /**
     * Calculates closeness centrality using {@link DijkstraSimple}.
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public TIntDoubleHashMap calculateUsingDijkstraSimple() {
        DijkstraSimple ds = new DijkstraSimple(graph);
        System.out.println(
                "Calculating closeness centrality using DijkstraSimple.");
        return calculate(ds);
    }

    /**
     * Calculates closeness centrality using {@link DijkstraBidirection}.
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public TIntDoubleHashMap calculateUsingDijkstraBidirection() {
        DijkstraBidirection db = new DijkstraBidirection(graph);
        System.out.println(
                "Calculating closeness centrality using DijkstraBidirection.");
        return calculate(db);
    }

    /**
     * Calculates closeness centrality using {@link DijkstraBidirectionRef}.
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public TIntDoubleHashMap calculateUsingDijkstraBidirectionRef() {
        DijkstraBidirectionRef dbr = new DijkstraBidirectionRef(graph);
        System.out.
                println(
                "Calculating closeness centrality using DijkstraBidirectionRef.");
        return calculate(dbr);
    }

    /**
     * Calculates closeness centrality using {@link AStar}.
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public TIntDoubleHashMap calculateUsingAStar() {
        AStar as = new AStar(graph);
        System.out.println("Calculating closeness centrality using AStar.");
        return calculate(as);
    }

    /**
     * Calculates closeness centrality using {@link AStarBidirection}.
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public TIntDoubleHashMap calculateUsingAStarBidirection() {
        AStarBidirection asb = new AStarBidirection(graph);
        System.out.println(
                "Calculating closeness centrality using AStarBidirection.");
        return calculate(asb);
    }

    /**
     * Calculates closeness centrality using contraction hierarchies.
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public TIntDoubleHashMap calculateUsingContractionHierarchies() {

        PrepareContractionHierarchies prepare =
                new PrepareContractionHierarchies().
                graph(graph);
        prepare.doWork();
        DijkstraBidirectionRef algo = prepare.createAlgo();
        return calculate(algo);
    }

    /**
     * Calculates closeness centrality indices of all vertices by calculating,
     * for each vertex, the shortest paths to every other vertex, using the
     * given {@link AbstractRoutingAlgorithm}; stores the results in a hash map,
     * where the keys are the vertices and the values are the closeness.
     *
     * @param algorithm The {@link AbstractRoutingAlgorithm} to use.
     *
     * @return A map with each vertex as key and each closeness centrality as
     *         value.
     */
    private TIntDoubleHashMap calculate(AbstractRoutingAlgorithm algorithm) {

        long time = System.currentTimeMillis();

        // Initiate the result Map.
        TIntDoubleHashMap result = new TIntDoubleHashMap();

        // Recover the set of nodes.
        TIntHashSet nodeSet = GraphAnalyzer.nodeSet(graph);

        // For tracking progress.
        long start, stop;
        int size = nodeSet.size();
        System.out.println("There are " + size + " nodes.");

        // Get an iterator over the node set.
        TIntIterator sourceIterator = nodeSet.iterator();

        // Fix a node.
        while (sourceIterator.hasNext()) {

            // Recover the source node.
            int source = sourceIterator.next();

            // Get an iterator over the node set.
            TIntIterator destinationIterator = nodeSet.iterator();

            // Start timing the calculation for the source node.
            start = System.currentTimeMillis();

            // This will be the sum of the shortest path distances to 
            // all the other nodes.
            double farness = 0.0;

            // Begin going through all the other nodes.
            while (destinationIterator.hasNext()) {

                // Recover the destination node.
                int destination = destinationIterator.next();

                // Skip reflexiveness. No need to calculate the distance
                // of a node to itself.
                if (source == destination) {
                    continue;
                }

                // Obtain (one of) the shortest path(s) from the source node
                // to the destination node.
                Path path = algorithm.clear().calcPath(source, destination);
//                System.out.println("Source " + source
//                        + ", Destination " + destination
//                        + ", Path " + path.toDetailsString());

                // Get the length of the path.
                Double length = path.distance();

                // If the algorithm finds no path from the source node
                // to the destination node, it returns a length of 0.0. 
                // But no path existing corresponds to the destination node
                // being "infinitely" far away from the source node.
                if (length == 0.0) {
                    length = Double.POSITIVE_INFINITY;
                }

                // Add this shortest path to the sum of all shortest paths.
                farness += length;

                // Once we find one destination node which is infinitely
                // far away, we know that the closeness centrality of
                // the source node will be zero.
                if (Double.isInfinite(farness)) {
                    break; // no need to continue,
                }
            }

            // Finish timing the calculation for the source node.
            stop = System.currentTimeMillis();

            // The value of closeness centrality for the source node.
            double closeness = (nodeSet.size() - 1) / farness;

            // Print out the calculation time for this source node.
            System.out.
                    println(
                    "Closeness for " + source + ": "
                    + (stop - start) + " ms. "
                    + "Closeness: " + closeness);

            // Store the closeness centrality for this source node.
            result.put(source, closeness);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Closeness centrality computation took "
                + time + " ms.");
        // Return the Map of closeness centrality results.
        return result;
    }
//    /**
//     * Print the distances of all nodes from the given node using the given
//     * distance hash map.
//     *
//     * @param distances The distance hash map.
//     * @param node      The given node.
//     */
//    protected void printDistancesFromNode(TIntIntHashMap distances, int node) {
//        TIntIntIterator distanceIter = distances.iterator();
//        while (distanceIter.hasNext()) {
//            distanceIter.advance();
//            System.out.print("Distance from " + node
//                    + " to " + distanceIter.key()
//                    + ": ");
//            if (distanceIter.value() == Integer.MAX_VALUE) {
//                System.out.println("---");
//            } else {
//                System.out.println(distanceIter.value());
//            }
//        }
//    }
}