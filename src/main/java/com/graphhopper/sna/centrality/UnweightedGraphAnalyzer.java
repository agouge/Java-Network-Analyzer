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
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.MyIntDeque;
import com.graphhopper.sna.data.PathLengthData;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.hash.TIntHashSet;

/**
 * Calculates various centrality measures on an unweighted graph (i.e., a graph
 * where all edges weights are considered to be 1).
 *
 * @author Adam Gouge
 */
// TODO: Implement betweenness centrality.
public class UnweightedGraphAnalyzer extends GraphAnalyzer {

    /**
     * Initializes a new instance of an unweighted graph analyzer.
     *
     * @param graph The graph to be analyzed.
     */
    public UnweightedGraphAnalyzer(Graph graph) {
        super(graph);
    }

    /**
     * {@inheritDoc}
     */
    // TODO: For the moment, we assume the graph has only one
    // connected component.
    @Override
    public TIntDoubleHashMap computeCloseness() {

        long time = System.currentTimeMillis();

        // Closeness centrality
        TIntDoubleHashMap closenessCentrality = new TIntDoubleHashMap();

        // Recover the node set and an iterator on it.
        TIntHashSet nodeSet = nodeSet();
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
                    computeShortestPathsData(node);
//            System.out.println("Number of shortest path lengths accumulated: " 
//                    + pathLengths.getCount());

            // Recover the eccentricity for this node.
            final double eccentricity = pathLengthsData.getMaxLength();

            // Get the average path length for this node and store it.
            final double apl =
                    (pathLengthsData.getCount() > 0)
                    ? pathLengthsData.getAverageLength() : 0.0;

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
        System.out.println("Network analysis took "
                + time
                + " ms.");
        return closenessCentrality;
    }

    /**
     * Computes the shortest path lengths from the given node to all other nodes
     * in the graph (assuming all edges have weight 1) and stores them in a
     * {@link PathLengthData} object.
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
    protected PathLengthData computeShortestPathsData(int startNode) {

//        System.out.println("BFS started for node " + aNode + ".");

        // Get an iterator on the node set.
        TIntHashSet nodeSet = nodeSet();
        TIntIterator iterator = nodeSet.iterator();

        // Create the "queue" and add aNode to it.
        MyIntDeque queue = new MyIntDeque();
        queue.push(startNode);

        // Create the set of marked nodes and add aNode to it.
        MyBitSet marked = new MyBitSetImpl(nodeCount);
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
//                + aNode + " to be zero.");
        distances.put(startNode, 0);
//        printDistances(distances.iterator(), aNode);

        // Initialize the result to be returned.
        PathLengthData result = new PathLengthData();

        // The current child node to be considered.
        int currentNode;
        // The current distance from aNode.
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
                    result.addSPLength(currentDistance);
                }
            }
        }

        // TODO: If a node is unreachable, then make the average path length
        // infinite in order to make closeness centrality zero.
//        if (dist.containsValue(Integer.MAX_VALUE)) {
//            result.addSPLength(Integer.MAX_VALUE);
//        }
        return result;
    }

    /**
     * Print the distances of all nodes from the given node using the given
     * distance hash map.
     *
     * @param distances The distance hash map.
     * @param node      The given node.
     */
    private void printDistances(TIntIntHashMap distances, int node) {
        TIntIntIterator distanceIter = distances.iterator();
        while (distanceIter.hasNext()) {
            distanceIter.advance();
            System.out.print("Distance from " + node
                    + " to " + distanceIter.key()
                    + ": ");
            if (distanceIter.value() == Integer.MAX_VALUE) {
                System.out.println("---");
            } else {
                System.out.println(distanceIter.value());
            }
        }
    }
}
