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
import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.MyIntDeque;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.hash.TIntHashSet;
import gnu.trove.stack.array.TIntArrayStack;
import java.util.HashMap;

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

        // Create the set of marked nodes and add startNode to it.
        MyBitSet marked = new MyBitSetImpl(nodeCount);
        marked.add(startNode);

        // Initialize the distances.
        TIntIntHashMap distances = new TIntIntHashMap();
        while (iterator.hasNext()) { // TODO: Shouldn't I use a different iterator here?
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

    /*
     * {@inheritDoc}
     */
    @Override
    public NetworkAnalysisResult computeAll() {

        // ***** STAGE 0: GLOBAL INITIALIZATION ****
        // A data structure to hold all the results of the network analysis.
        NetworkAnalysisResult finalResult = new NetworkAnalysisResult(this);
        // Betweenness centrality will be stored in a hashmap with all values
        // initialized to 0.0.
        TIntDoubleHashMap betweenness = finalResult.getBetweenness();
        // Closeness centrality hash map.
        TIntDoubleHashMap closenessCentrality = finalResult.getCloseness();
        // ***** END STAGE 0 ***********************

        // Go through all the nodes:
        TIntHashSet nodeSet = nodeSet();
        TIntIterator nodeSetIterator = nodeSet.iterator();
        while (nodeSetIterator.hasNext()) {
            // Get a node.
            int startNode = nodeSetIterator.next();
//            // Start timing for this node.
//            long start = System.currentTimeMillis();
//            System.out.println("***** STARTED BFS FOR " + startNode
//                    + " *****");

            // ***** STAGE 1: LOCAL INITIALIZATION *****

            // *** Here we initialize data structures to contain the distance
            // *** from startNode to every other vertex, the number of shortest
            // *** paths from startNode to every other vertex, and the 
            // *** predecessor list of every other vertex on shortest paths 
            // *** from startNode. 

            // To keep track of the distances from startNode.
            TIntIntHashMap distancesFromStartNode =
                    initDistancesFrom(startNode);
//            printDistancesFromNode(distances, startNode);
            // To keep track of the number of shortest paths from startNode.
            TIntIntHashMap shortestPathsCount =
                    initNumberShortestPathsFrom(startNode);
            // Initialize a hash map of predecessor lists indexed by nodes.
            // TODO: Use a TIntHashSet instead.
            HashMap<Integer, TIntHashSet> predecessorsOf =
                    new HashMap<Integer, TIntHashSet>();
            // This will be used for the closeness centrality calculation.
            PathLengthData pathsFromStartNode = new PathLengthData();

            // *** We also initialize the necessary data structures for
            // *** BFS traversal and for returning nodes ordered by
            // *** NON-INCREASING distance from startNode.

            // Create the BFS traversal queue and enqueue startNode.
            MyIntDeque queue = new MyIntDeque();
            queue.push(startNode);
            // In Stage 3, stack will return vertices in order of
            // NON-INCREASING distance from startNode.
            TIntArrayStack stack = new TIntArrayStack();
            // ***** END STAGE 1 ***********************

            // ***** STAGE 2: BFS TRAVERSAL ************
            // *** At the end of Stage 2, we will have obtained the distance
            // *** from startNode to every other vertex, the number of shortest
            // *** paths from startNode to every other vertex, and the 
            // *** predecessor list of every other vertex on shortest paths 
            // *** from startNode. This information will be used in Stage 3
            // *** to update dependency values and their corresponding 
            // *** contributions to the betweenness centrality score of
            // *** startNode.

            // While the queue is not empty ...
            while (!queue.isEmpty()) {
                // ... dequeue a node
                int currentNode = queue.pop();
                // and push this node to the stack.
                stack.push(currentNode);
//                System.out.println("     BFS for " + currentNode + " ");

                // Get the outgoing edges of the current node.
                EdgeIterator edgesOfCurrentNode = graph.getOutgoing(currentNode);
                while (edgesOfCurrentNode.next()) {
                    // For every neighbor of the current node ...
                    int neighbor = edgesOfCurrentNode.node();
                    // Initialize the list of predecessors of neighbor.
                    if (!predecessorsOf.containsKey(neighbor)) {
                        predecessorsOf.put(neighbor, new TIntHashSet());
//                        System.out.println("-- (Init pred list for "
//                                + neighbor + ") --");
                    }

                    // If this neighbor is found for the first time ...
                    if (distancesFromStartNode.get(neighbor) < 0) {
                        // then enqueue it
                        queue.push(neighbor);
                        // and update the distance.
//                        System.out.println("d(" + startNode
//                                + "," + neighbor + ") = 1 + d("
//                                + startNode + "," + currentNode
//                                + ") = "
//                                + (distancesFromStartNode.get(currentNode) + 1));
                        int updatedDistance = distancesFromStartNode.get(
                                currentNode) + 1;
                        distancesFromStartNode.put(
                                neighbor,
                                updatedDistance);
                        // Add this to the path length data. (For closeness)
                        pathsFromStartNode.addSPLength(updatedDistance);
                    }

                    // If this is a shortest path from startNode to currentNode
                    // via neighbor ...
                    if (distancesFromStartNode.get(neighbor)
                            == distancesFromStartNode.get(currentNode) + 1) {
                        // then update the number of shortest paths,
//                        System.out.println(
//                                "sp(" + startNode + "," + neighbor + ") "
//                                + "= sp(" + startNode + "," + neighbor + ") "
//                                + "+ sp(" + startNode + "," + currentNode + ") "
//                                + "= " + shortestPathsCount.get(neighbor)
//                                + " + " + shortestPathsCount.get(currentNode)
//                                + " = "
//                                + (shortestPathsCount.get(neighbor)
//                                + shortestPathsCount.get(currentNode)));
                        shortestPathsCount.put(
                                neighbor,
                                shortestPathsCount.get(neighbor)
                                + shortestPathsCount.get(currentNode));
                        // and append currentNode to the list of predecessors
                        // of neighbor.
                        TIntHashSet predsOfNeighbor =
                                predecessorsOf.get(neighbor);
                        // TODO: Do we need to check if the pred list already
                        // contains currentNode?
//                        if (!predsOfNeighbor.contains(currentNode)) {
                        predsOfNeighbor.add(currentNode);
//                        System.out.println("Added " + currentNode
//                                + " to the pred list of "
//                                + neighbor);
//                        }
                    }
                }
            } // ***** END STAGE 2, Queue iteration *************
//            printDistAndSPCounts(startNode, distancesFromStartNode,
//                                 shortestPathsCount, predecessorsOf);
            calculateClosenessForStartNode(closenessCentrality, startNode,
                                           pathsFromStartNode);

            // ***** STAGE 3: DEPENDENCY ACCUMULATION **

            // *** Here we update 
            // *** (A) the dependency of startNode on the other nodes.
            // *** (B) the corresponding contributions to the betweenness 
            // ***     centrality scores of the other nodes.

            // The dependency of startNode on the given node.
            TIntDoubleHashMap dependency =
                    initDependenciesFrom(startNode);

            // For each node w returned in NON-INCREASING distance from 
            // startNode, do:
            while (stack.size() != 0) {
                int w = stack.pop();

                // For every predecessor v of w on shortest paths from
                // startNode, do:
                TIntHashSet predecessorList =
                        predecessorsOf.get(w);
//                printPredecessorList(w, predecessorList);
                TIntIterator it =
                        predecessorList.iterator();
                while (it.hasNext()) {
                    int predecessor = it.next();

                    // (A) Add the contribution of the dependency of startNode
                    // on w to the dependency of startNode on v.
                    double dep = dependency.get(predecessor)
                            + ((double) shortestPathsCount.get(predecessor)
                            / shortestPathsCount.get(w))
                            * (1 + dependency.get(w));
//                    printDependencyContribution(
//                            predecessor, startNode, w, dependency,
//                            shortestPathsCount, dep);
                    dependency.put(
                            predecessor,
                            dep);
                }
                // (The betweenness of w cannot receive contributions from
                // the dependency of w on w.)
                if (w != startNode) {

                    // (B) At this point, the dependency of startNode on w
                    // has finished calculating, so we can add it to
                    // the betweenness centrality of w.
                    double updatedBetweenness = betweenness.get(w)
                            + dependency.get(w);
//                    printBetweennessContribution(w, betweenness, dependency,
//                                                 updatedBetweenness);
                    betweenness.put(
                            w,
                            updatedBetweenness);
                }
            } // ***** END STAGE 3, Stack iteration  **************
        }
        // Normalize the betweenness centrality values.
        normalize(betweenness);
        return finalResult;
    }

    private TIntIntHashMap initTIntIntHashMap(
            int defaultValue,
            int startNode,
            int startNodeValue) {
        TIntIntHashMap hashMap = new TIntIntHashMap();
        // TODO: Maybe a TIntArrayList would be better?
        TIntIterator it = nodeSet().iterator();
        while (it.hasNext()) {
            hashMap.put(
                    it.next(),
                    defaultValue);
        }
        hashMap.put(startNode, startNodeValue);
        return hashMap;
    }

    private TIntDoubleHashMap initTIntDoubleHashMap(
            double defaultValue,
            int startNode,
            double startNodeValue) {
        TIntDoubleHashMap hashMap = new TIntDoubleHashMap();
        // TODO: Maybe a TIntArrayList would be better?
        TIntIterator it = nodeSet().iterator();
        while (it.hasNext()) {
            hashMap.put(
                    it.next(),
                    defaultValue);
        }
        hashMap.put(startNode, startNodeValue);
        return hashMap;
    }

    private TIntIntHashMap initNumberShortestPathsFrom(int startNode) {
        // Initialize the number of shortest paths from the start node
        // to itself to be 1, and all other to be 0.
        return initTIntIntHashMap(0, startNode, 1);
    }

    private TIntIntHashMap initDistancesFrom(int startNode) {
        // Initialize distance from startNode to itself to 0
        // and all other distances to -1.
        return initTIntIntHashMap(-1, startNode, 0);
    }

    private TIntDoubleHashMap initDependenciesFrom(int startNode) {
        // Initialize all dependencies to 0.0.
        return initTIntDoubleHashMap(0.0, startNode, 0.0);
    }

    private TIntDoubleHashMap initBetweenness() {
        TIntDoubleHashMap betweennessCentrality = new TIntDoubleHashMap();
        TIntIterator it = nodeSet().iterator();
        while (it.hasNext()) {
            betweennessCentrality.put(
                    it.next(),
                    0.0);
        }
        return betweennessCentrality;
    }

    private void printDependencyContribution(int pred, int startNode, int node,
                                             TIntDoubleHashMap dependency,
                                             TIntIntHashMap shortestPathsCount,
                                             double dep) {
        System.out.println(
                "dep(" + pred + ") "
                + "= dep(" + pred + ") + (sp(" + startNode
                + "," + pred
                + ")/sp(" + startNode + "," + node
                + "))(1 + dep(" + node
                + ")) = " + dependency.get(pred)
                + " + (" + shortestPathsCount.get(pred)
                + ")/(" + shortestPathsCount.get(node)
                + ")(1 + " + dependency.get(node)
                + ") = " + dep);
    }

    private void printPredecessorList(int node, TIntArrayList predecessorList) {
        System.out.println("Preds of " + node
                + ": " + predecessorList.toString());
    }

    private void printBetweennessContribution(int node,
                                              TIntDoubleHashMap betweenness,
                                              TIntDoubleHashMap dependency,
                                              double updatedBetweenness) {
        System.out.println("C(" + node
                + ") = C(" + node
                + ") + dep(" + node
                + ") = " + betweenness.get(node)
                + " + " + dependency.get(node)
                + " = " + updatedBetweenness);
        System.out.println("");
    }

    private void printDistAndSPCounts(int startNode,
                                      TIntIntHashMap distancesFromStartNode,
                                      TIntIntHashMap shortestPathsCount,
                                      HashMap<Integer, TIntHashSet> predecessorsOf) {
        TIntIterator it = nodeSet().iterator();
        while (it.hasNext()) {
            int nd = it.next();
            System.out.println(
                    "(" + startNode + "," + nd + ") "
                    + ": d = " + distancesFromStartNode.get(nd)
                    + ", sp = " + shortestPathsCount.get(nd)
                    + ", pred: " + predecessorsOf.get(nd).toString());
        }
    }

    /**
     * Normalizes betweenness.
     *
     * @param nodeSet
     * @param betweenness
     */
    private void normalize(TIntDoubleHashMap betweenness) {
        // ***** STAGE 4: Normalization ************
        // *** We normalize to make all values lie between 0 and 1.
        long start = System.currentTimeMillis();
        TIntHashSet nodeSet = nodeSet();
        int N = nodeSet.size();
        TIntIterator nodeSetIterator = nodeSet.iterator();
        while (nodeSetIterator.hasNext()) {
            int node = nodeSetIterator.next();
            double normalizationFactor = (N - 1) * (N - 2);
            double normalizedBetweenness =
                    betweenness.get(node) / normalizationFactor;
            betweenness.put(node, normalizedBetweenness);
        }
        long stop = System.currentTimeMillis();
        System.out.println("Betweenness normalization took "
                + (stop - start) + " ms.");
    }

    private void calculateClosenessForStartNode(
            TIntDoubleHashMap closenessCentrality,
            int startNode,
            PathLengthData pathLengthData) {
        // ***** STAGE 4: Closeness Centrality Calculation ****
        // *** TODO: For now, we assume the graph is connected.

        // Get the average path length for the startNode.
        final double avgPathLength =
                (pathLengthData.getCount() > 0)
                ? pathLengthData.getAverageLength() : 0.0;
        // Once we have the average path length for this node, 
        // we have the closeness centrality for this node.
        final double startNodeCloseness = (avgPathLength > 0.0)
                ? 1 / avgPathLength
                : 0.0;
        // Store it.
        closenessCentrality.put(startNode, startNodeCloseness);
    }
}
