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

import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.RawEdgeIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.set.hash.TIntHashSet;
import gnu.trove.stack.array.TIntArrayStack;
import java.util.HashMap;

/**
 * Calculates various centrality measures on the given graph, <b>assumed to be
 * connected</b>.
 *
 * @author Adam Gouge
 */
public abstract class GraphAnalyzer {

    /**
     * The graph to be analyzed.
     */
    protected final Graph graph;
    /**
     * The set of nodes of this graph.
     */
    protected final TIntHashSet nodeSet;
    /**
     * The number of nodes in this graph.
     */
    protected final int nodeCount;
    /**
     * Map of all nodes with their respective {@link NodeBetweennessInfo}, which
     * stores information needed for the node betweenness calculation.
     */
    protected final HashMap<Integer, NodeBetweennessInfo> nodeBetweenness;

    /**
     * Initializes a new instance of a graph analyzer.
     *
     * @param graph The graph to be analyzed.
     */
    public GraphAnalyzer(Graph graph) {
        this.graph = graph;
        this.nodeSet = nodeSet(this.graph);
        this.nodeCount = this.nodeSet.size();
        this.nodeBetweenness = new HashMap<Integer, NodeBetweennessInfo>();
    }

    /**
     * Computes the closeness centrality indices of all vertices of the graph
     * (assumed to be connected) and stores them in a hash map, where the keys
     * are the vertices and the values are the closeness.
     *
     * @return The closeness centrality hash map.
     */
    public abstract TIntDoubleHashMap computeCloseness();

    /**
     * Returns a {@link TIntHashSet} of the nodes of the given graph.
     *
     * @return a {@link TIntHashSet} of the nodes of the given graph.
     */
    // TODO: Optimize this (by making use of the data structure).
    protected static TIntHashSet nodeSet(Graph graph) {
        // Initialize the Set.
        TIntHashSet set = new TIntHashSet();
        // Get all the edges.
        RawEdgeIterator iter = graph.allEdges();
        // Add each source and destination node to the set.
        while (iter.next()) {
            set.add(iter.nodeA());
            set.add(iter.nodeB());
        }
        return set;
    }

    /**
     * Performs graph analysis and stores the results in a hash map, mapping
     * each node to a data structure holding the results of the analysis.
     *
     * @return The results of the graph analysis.
     */
    public HashMap<Integer, NodeBetweennessInfo> computeAll() {

        // ***** GLOBAL INITIALIZATION *************************
        init();

        // ***** CENTRALITY CONTRIBUTION FROM EACH NODE ********
        TIntIterator nodeSetIterator = nodeSet.iterator();
        while (nodeSetIterator.hasNext()) {
            // Calculate betweenness and closeness for each node.
//            long start = System.currentTimeMillis();
            int source = nodeSetIterator.next();
            calculateCentralityContributionFromNode(source);
//            long stop = System.currentTimeMillis();
//            System.out.println(node + ": "
//                    + (stop - start) + " ms.");
            // Reset the node betweenness (except the betweenness and
            // closeness) for every node.
            TIntIterator it = nodeSet.iterator();
            while (it.hasNext()) {
                nodeBetweenness.get(it.next()).reset();
            }
        }
        // ***** END CENTRALITY CONTRIBUTION FROM EACH NODE *****

        // ***** NORMALIZATION **********************************
        normalizeBetweenness();

        return nodeBetweenness;
    }

    /**
     * Clears and initializes the data structure that will hold all the results
     * of the network analysis.
     */
    protected abstract void init();

    /**
     * Calculates the contribution of the given node to the betweenness and
     * closeness values of all the other nodes.
     *
     * @param startNode The given node.
     */
    // TODO: For now, we assume the graph is connected.
    private void calculateCentralityContributionFromNode(int startNode) {

        // ***** LOCAL INITIALIZATION *************************
        // A data structure to hold information about startNode
        // relative to all the other nodes in the network during
        // this calculation.
        final NodeBetweennessInfo startNBInfo =
                nodeBetweenness.get(startNode);
        // Set this as the source node.
        startNBInfo.setSource();
        // When accumulating dependencies, this stack will return vertices
        // in order of non-increasing distance from startNode.
        TIntArrayStack stack = new TIntArrayStack();
        // This will be used for the closeness centrality calculation.
        PathLengthData pathsFromStartNode = new PathLengthData();
        // ***** END LOCAL INITIALIZATION *********************

        // ***** CENTRALITY CONTRIBUTION CALCULATION **********
        // Calculate all the shortest paths from startNode.
        calculateShortestPathsFromNode(startNode, pathsFromStartNode, stack);
        // At this point, we have all information required to calculate
        // closeness for startNode.
        calculateClosenessForNode(startNode, pathsFromStartNode);
        // Use the recursion formula to calculate update the dependency
        // values and their contributions to betweenness values.
        accumulateDependencies(startNode, stack);
        // ***** END CENTRALITY CONTRIBUTION CALCULATION ******
    }

    /**
     * Stores number of shortest paths and the length of these paths from
     * startNode to every other node in the {@link NodeBetweennessInfo} of every
     * other node; also updates the predecessor sets.
     *
     * @param startNode          The start node.
     * @param stack              The stack which will return nodes ordered by
     *                           non-increasing distance from startNode.
     * @param pathsFromStartNode Holds information about shortest path lengths
     *                           from startNode to all the other nodes in the
     *                           network
     */
    protected abstract void calculateShortestPathsFromNode(
            int startNode,
            PathLengthData pathsFromStartNode,
            TIntArrayStack stack);

    /**
     * Given a node and its {@link PathLengthData} calculated in
     * {@link #calculateCentralityMeasures(int)}, this method calculates
     * closeness centrality for the given node.
     *
     * @param startNode          The given node.
     * @param pathsFromStartNode Its path length data.
     */
    protected void calculateClosenessForNode(
            int startNode,
            PathLengthData pathsFromStartNode) {
        // Get the average path length for the startNode.
        final double avgPathLength = getAveragePathLength(pathsFromStartNode);
        // Once we have the average path length for this node,
        // we have the closeness centrality for this node.
        final double startNodeCloseness = (avgPathLength > 0.0)
                ? 1 / avgPathLength
                : 0.0;
        // Store it.
        final NodeBetweennessInfo startNodeInfo =
                nodeBetweenness.get(startNode);
        startNodeInfo.setCloseness(startNodeCloseness);
    }

    /**
     * Returns the average path length from the given {@link PathLengthData}.
     *
     * @param pathsFromStartNode The {@link PathLengthData}.
     *
     * @return The average path length.
     */
    protected abstract double getAveragePathLength(
            PathLengthData pathsFromStartNode);

    /**
     * Uses the recursion formula to calculate update the dependency values of
     * startNode on every node and their contributions to the betweenness values
     * of every node except startNode. Node that all these values are contained
     * in the appropriate {@link NodeBetweennessInfo} of
     * {@link #nodeBetweenness}.
     *
     * @param startNode The start node.
     * @param stack     The stack that returns nodes ordered by non-increasing
     *                  distance from startNode.
     */
    private void accumulateDependencies(int startNode, TIntArrayStack stack) {

        // *** Here we update
        // *** (A) the dependency of startNode on the other nodes.
        // *** (B) the corresponding contributions to the betweenness
        // ***     centrality scores of the other nodes.

        // For each node w returned in NON-INCREASING distance from
        // startNode, do:
        while (stack.size() != 0) {
            int w = stack.pop();
            final NodeBetweennessInfo wNBInfo = nodeBetweenness.get(w);

            // For every predecessor v of w on shortest paths from
            // startNode, do:
            TIntHashSet predecessorSet = wNBInfo.getPredecessors();
//                printPredecessorList(w, predecessorList);
            TIntIterator it = predecessorSet.iterator();
            while (it.hasNext()) {
                int predecessor = it.next();
                final NodeBetweennessInfo predecessorNBInfo = nodeBetweenness.
                        get(predecessor);

                // (A) Add the contribution of the dependency of startNode
                // on w to the dependency of startNode on v.
                double depContribution =
                        ((double) predecessorNBInfo.getSPCount()
                        / wNBInfo.getSPCount())
                        * (1 + wNBInfo.getDependency());
//                    printDependencyContribution(
//                            predecessor, startNode, w, dependency,
//                            shortestPathsCount, dep);
                predecessorNBInfo.accumulateDependency(depContribution);
            }
            // (The betweenness of w cannot receive contributions from
            // the dependency of w on w, by the definition of dependency.)
            if (w != startNode) {

                // (B) At this point, the dependency of startNode on w
                // has finished calculating, so we can add it to
                // the betweenness centrality of w.
                wNBInfo.accumulateBetweenness(
                        wNBInfo.getDependency());
//                    printBetweennessContribution(w, betweenness, dependency,
//                                                 updatedBetweenness);
            }
        } // ***** END STAGE 3, Stack iteration  **************

//        long stop = System.currentTimeMillis();
//        System.out.println(startNode + " analysis took "
//                + (stop - start) + " ms.");
    }

    /**
     * Normalizes betweenness to make all values lie between 0 and 1.
     */
    private void normalizeBetweenness() {
        long start = System.currentTimeMillis();
        TIntIterator nodeSetIterator = nodeSet.iterator();
        while (nodeSetIterator.hasNext()) {
            int node = nodeSetIterator.next();
            final NodeBetweennessInfo nodeNBInfo = nodeBetweenness.get(node);
            // If there are 2 or less nodes, the normalization factor is 1.
            double normalizationFactor =
                    (nodeCount > 2)
                    ? (1.0 / ((nodeCount - 1) * (nodeCount - 2)))
                    : 1.0;
            double normalizedBetweenness =
                    nodeNBInfo.getBetweenness() * normalizationFactor;
            nodeNBInfo.setBetweenness(normalizedBetweenness);
        }
        long stop = System.currentTimeMillis();
        System.out.println("Betweenness normalization took "
                + (stop - start) + " ms.");
    }
//    /**
//     * Prints the given {@link TIntDoubleHashMap}.
//     *
//     * @param hashmap The given {@link TIntDoubleHashMap}.
//     */
//    public static void printHashMap(TIntDoubleHashMap hashmap) {
//        TIntDoubleIterator it = hashmap.iterator();
//        while (it.hasNext()) {
//            it.advance();
//            System.out.println("(" + it.key()
//                    + "," + it.value() + ")");
//        }
//    }
}
