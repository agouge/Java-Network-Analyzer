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
import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.MyIntDeque;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.stack.array.TIntArrayStack;

/**
 * Calculates various centrality measures on an unweighted graph (i.e., a graph
 * where all edges weights are considered to be 1).
 *
 * @author Adam Gouge
 */
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
    // TODO: For the moment, we assume the graph has only one connected
    // component.
    // TODO: We might eventually delete this method because in GDMS-Topology,
    // ST_ClosenessCentrality will most likely be replaced by
    // ST_NetworkAnalyzer. At the same time, we need to compare the
    // cost of computing just closeness vs. computing closeness along
    // with betweenness.
    @Override
    public TIntDoubleHashMap computeCloseness() {

        long time = System.currentTimeMillis();

        // Closeness centrality
        TIntDoubleHashMap closenessCentrality = new TIntDoubleHashMap();

        // Recover the node set and an iterator on it.
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
    protected PathLengthData computeShortestPathsData(int startNode) {

//        System.out.println("BFS started for node " + aNode + ".");

        // Get an iterator on the node set.
        TIntIterator iterator = nodeSet.iterator();

        // Create the "queue" and add aNode to it.
        MyIntDeque queue = new MyIntDeque();
        queue.push(startNode);

        // Create the set of marked nodes and add startNode to it.
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
     *
     * Calculates the number of shortest paths from startNode to every other
     * node and the lengths of these paths using a Breadth-First Search (BFS),
     * storing them in the appropriate {@link NodeBetweennessInfo} of
     * {@link #nodeBetweenness}; also updates the predecessor sets.
     *
     * @param startNode          The start node.
     * @param stack              The stack which will return nodes ordered by
     *                           non-increasing distance from startNode.
     * @param pathsFromStartNode Holds information about shortest path lengths
     *                           from startNode to the other nodes in the network
     */
    @Override
    protected void calculateShortestPathsFromNode(
            int startNode,
            PathLengthData pathsFromStartNode,
            TIntArrayStack stack) {

        // Create the BFS traversal queue and enqueue startNode.
        MyIntDeque queue = new MyIntDeque();
        queue.push(startNode);

        // While the queue is not empty ...
        while (!queue.isEmpty()) {
            // ... dequeue a node
            int current = queue.pop();
            final NodeBetweennessInfo currentNBInfo =
                    nodeBetweenness.get(current);
            // and push this node to the stack.
            stack.push(current);
//                System.out.println("     BFS for " + currentNode + " ");

            // Get the outgoing edges of the current node.
            EdgeIterator edgesOfCurrentNode = graph.getOutgoing(current);
            // For every neighbor of the current node ...
            while (edgesOfCurrentNode.next()) {

                int neighbor = edgesOfCurrentNode.node();
                final NodeBetweennessInfo neighborNBInfo =
                        nodeBetweenness.get(neighbor);

                // If this neighbor is found for the first time ...
                if (neighborNBInfo.getDistance() < 0) {
                    // then enqueue it
                    queue.push(neighbor);
                    // and update the distance.
//                        System.out.println("d(" + startNode
//                                + "," + neighbor + ") = 1 + d("
//                                + startNode + "," + currentNode
//                                + ") = "
//                                + (distancesFromStartNode.get(currentNode) + 1));
                    int updatedDistance = currentNBInfo.getDistance() + 1;
                    neighborNBInfo.setDistance(updatedDistance);
                    // Add this to the path length data. (For closeness)
                    pathsFromStartNode.addSPLength(updatedDistance);
                }

                // If this is a shortest path from startNode to currentNode
                // via neighbor ...
                if (neighborNBInfo.getDistance()
                        == currentNBInfo.getDistance() + 1) {
                    // then update the number of shortest paths,
//                    System.out.println(
//                            "sp(" + startNode + "," + neighbor + ") "
//                            + "= sp(" + startNode + "," + neighbor + ") "
//                            + "+ sp(" + startNode + "," + currentNode + ") "
//                            + "= " + shortestPathsCount.get(neighbor)
//                            + " + " + shortestPathsCount.get(currentNode)
//                            + " = "
//                            + (shortestPathsCount.get(neighbor)
//                            + shortestPathsCount.get(currentNode)));
                    neighborNBInfo.accumulateSPCount(currentNBInfo.getSPCount());
                    // and add currentNode to the set of predecessors
                    // of neighbor.
                    neighborNBInfo.addPredecessor(current);
//                    System.out.println("Added " + currentNode
//                            + " to the pred list of "
//                            + neighbor);
                }
            }
        }
//        TODO: printDistAndSPCounts(startNode, distancesFromStartNode,
//                             shortestPathsCount, predecessorsOf);
    }
//    private void printDependencyContribution(
//            int pred,
//            int startNode,
//            int node,
//            TIntDoubleHashMap dependency,
//            TIntIntHashMap shortestPathsCount,
//            double dep) {
//        System.out.println(
//                "dep(" + pred + ") "
//                + "= dep(" + pred + ") + (sp(" + startNode
//                + "," + pred
//                + ")/sp(" + startNode + "," + node
//                + "))(1 + dep(" + node
//                + ")) = " + dependency.get(pred)
//                + " + (" + shortestPathsCount.get(pred)
//                + ")/(" + shortestPathsCount.get(node)
//                + ")(1 + " + dependency.get(node)
//                + ") = " + dep);
//    }
//
//    private void printPredecessorList(
//            int node,
//            TIntArrayList predecessorList) {
//        System.out.println("Preds of " + node
//                + ": " + predecessorList.toString());
//    }
//
//    private void printBetweennessContribution(
//            int node,
//            TIntDoubleHashMap betweenness,
//            TIntDoubleHashMap dependency,
//            double updatedBetweenness) {
//        System.out.println("C(" + node
//                + ") = C(" + node
//                + ") + dep(" + node
//                + ") = " + betweenness.get(node)
//                + " + " + dependency.get(node)
//                + " = " + updatedBetweenness);
//        System.out.println("");
//    }
//
//    private void printDistAndSPCounts(
//            int startNode,
//            TIntIntHashMap distancesFromStartNode,
//            TIntIntHashMap shortestPathsCount,
//            HashMap<Integer, TIntHashSet> predecessorsOf) {
//        TIntIterator it = nodeSet.iterator();
//        while (it.hasNext()) {
//            int nd = it.next();
//            System.out.println(
//                    "(" + startNode + "," + nd + ") "
//                    + ": d = " + distancesFromStartNode.get(nd)
//                    + ", sp = " + shortestPathsCount.get(nd)
//                    + ", pred: " + predecessorsOf.get(nd).toString());
//        }
//    }
}
