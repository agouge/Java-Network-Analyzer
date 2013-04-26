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
package com.graphhopper.sna.analyzers;

import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.sna.progress.NullProgressMonitor;
import com.graphhopper.sna.progress.ProgressMonitor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Stack;
import org.jgrapht.Graph;

/**
 * Calculates various centrality measures on the given graph, <b>assumed to be
 * connected</b>.
 *
 * @author Adam Gouge
 */
public abstract class GraphAnalyzer<V extends NodeBetweennessInfo, E, S extends PathLengthData>
        extends GeneralizedGraphAnalyzer<V, E> {

    /**
     * The maximum betweenness centrality value.
     */
    private double maxBetweenness;
    /**
     * The minimum betweenness centrality value.
     */
    private double minBetweenness;
    /**
     * Progress monitor.
     */
    protected ProgressMonitor pm;
    /**
     * Constructor for {@link S} objects.
     */
    private final Constructor<? extends S> sConstructor;

    /**
     * Initializes a new instance of a graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph     The graph to be analyzed.
     * @param pm        The {@link ProgressMonitor} to be used.
     * @param nodeClass The class of the {@link NodeBetweennessInfo} to use.
     */
    // TODO: Do I need the wildcard on S?
    public GraphAnalyzer(Graph<V, E> graph,
                         ProgressMonitor pm,
                         Class<? extends S> pathClass)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        super(graph);
        this.pm = pm;
        this.sConstructor = pathClass.getConstructor();
        this.maxBetweenness = Double.NEGATIVE_INFINITY;
        this.minBetweenness = Double.POSITIVE_INFINITY;
    }

    /**
     * Initializes a new instance of a graph analyzer that doesn't keep track of
     * progress.
     *
     * @param graph The graph to be analyzed.
     */
    public GraphAnalyzer(Graph<V, E> graph,
                         Class<? extends S> pathClass)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        this(graph, new NullProgressMonitor(), pathClass);
    }

    /**
     * Performs graph analysis and stores the results in a hash map, mapping
     * each node to a data structure holding the results of the analysis.
     */
    public void computeAll() throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        long startTime = System.currentTimeMillis();

        pm.startTask("Graph analysis", nodeCount);

        // ***** GLOBAL INITIALIZATION *************************
        long count = 0;
        pm.setProgress(count, startTime);
        // ***** CENTRALITY CONTRIBUTION FROM EACH NODE ********
        for (V node : nodeSet) {
            // Update the count.
            count++;

            // See if the task has been cancelled.
            if (pm.isCancelled()) {
                break;
            }
            // Calculate betweenness and closeness for each node.
            calculateCentralityContributionFromNode(node);

            // Update and print the progress.
            pm.setProgress(count, startTime);
        }
        // ***** END CENTRALITY CONTRIBUTION FROM EACH NODE *****

        // ***** NORMALIZATION **********************************
        normalizeBetweenness();

        pm.endTask();
    }

    /**
     * Resets the node (except for betweenness and closeness).
     */
    private void resetBetweenness() {
        for (V node : nodeSet) {
            node.reset();
        }
    }

    /**
     * Calculates the contribution of the given node to the betweenness and
     * closeness values of all the other nodes.
     *
     * @param startNode The given node.
     */
    // TODO: For now, we assume the graph is connected.
    private void calculateCentralityContributionFromNode(V startNode) throws
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        // ***** LOCAL INITIALIZATION *************************
        // Set this as the source node.
        startNode.setSource();
        // When accumulating dependencies, this stack will return vertices
        // in order of non-increasing distance from startNode.
        Stack<V> stack = new Stack<V>();
        // This will be used for the closeness centrality calculation.
        S pathsFromStartNode = sConstructor.newInstance();
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
//        debug(startNode);

        // ***** RESET HASH MAP VALUES IN PREPARATION FOR *****
        // *****          THE NEXT CALCULATION            *****
        resetBetweenness();
        // ***** END RESET ************************************
    }

    /**
     * Stores number of shortest paths and the length of these paths from
     * startNode to every other node in the {@link V} of every other node; also
     * updates the predecessor sets.
     *
     * @param startNode          The start node.
     * @param stack              The stack which will return nodes ordered by
     *                           non-increasing distance from startNode.
     * @param pathsFromStartNode Holds information about shortest path lengths
     *                           from startNode to all the other nodes in the
     *                           network
     */
    protected abstract void calculateShortestPathsFromNode(
            V startNode,
            S pathsFromStartNode,
            Stack<V> stack);

    /**
     * Given a node and its path length data calculated in
     * {@link #calculateCentralityMeasures(int)}, this method calculates its
     * closeness centrality (or "out" closeness centrality for digraphs).
     *
     * @param node  The given node.
     * @param paths Its path length data.
     */
    protected void calculateClosenessForNode(
            V node,
            S paths) {
        // Count the number of nodes reachable from the given node.
        int reachableNodes = paths.getCount();
        // If all other nodes are reachable, get the average path length
        // for the node.
        final double avgPathLength;
        if (reachableNodes == nodeCount - 1) {
            avgPathLength = paths.getAverageLength();
        } else {
            avgPathLength = -1;
        }
        // Once we have the average path length, we have the ("out") closeness.

        final double closeness = (avgPathLength > 0.0)
                ? 1 / avgPathLength
                : 0.0;
        // Store it.
        node.setCloseness(closeness);
    }

    /**
     * Uses the recursion formula to calculate update the dependency values of
     * startNode on every node and their contributions to the betweenness values
     * of every node except startNode. Node that all these values are contained
     * in the appropriate {@link V} of {@link #nodeBetweenness}.
     *
     * @param startNode The start node.
     * @param stack     The stack that returns nodes ordered by non-increasing
     *                  distance from startNode.
     */
    private void accumulateDependencies(V startNode, Stack<V> stack) {

        // *** Here we update
        // *** (A) the dependency of startNode on the other nodes.
        // *** (B) the corresponding contributions to the betweenness
        // ***     centrality scores of the other nodes.

        // For each node w returned in NON-INCREASING distance from
        // startNode, do:
        while (!stack.empty()) {
            final V w = stack.pop();

            // For every predecessor v of w on shortest paths from
            // startNode, do:
            for (V predecessor : (HashSet<V>) w.getPredecessors()) {

                // (A) Add the contribution of the dependency of startNode
                // on w to the dependency of startNode on v.
                double depContribution =
                        ((double) predecessor.getSPCount()
                        / w.getSPCount())
                        * (1 + w.getDependency());
                predecessor.accumulateDependency(depContribution);
            }
            // (The betweenness of w cannot receive contributions from
            // the dependency of w on w, by the definition of dependency.)
            if (w != startNode) {
                // (B) At this point, the dependency of startNode on w
                // has finished calculating, so we can add it to
                // the betweenness centrality of w.
                w.accumulateBetweenness(w.getDependency());
//                    printBetweennessContribution(w, betweenness, dependency,
//                                                 updatedBetweenness);
            }
        } // ***** END STAGE 3, Stack iteration  **************
    }

    /**
     * Normalizes betweenness to make all values lie in the range [0,1] with the
     * minimum betweenness value set to 0.0 and the maximum betweenness value
     * set to 1.0.
     */
    private void normalizeBetweenness() {
        findExtremeBetweennessValues();

        long start = System.currentTimeMillis();
        final double denominator = maxBetweenness - minBetweenness;
        for (V node : nodeSet) {
            final double normalizedBetweenness =
                    (node.getBetweenness() - minBetweenness) / denominator;
            node.setBetweenness(normalizedBetweenness);
        }
        long stop = System.currentTimeMillis();
        System.out.println("Betweenness normalization took "
                + (stop - start) + " ms.");
    }

    /**
     * Finds the maximum and minimum betweenness values.
     */
    private void findExtremeBetweennessValues() {
        long start = System.currentTimeMillis();
        for (V node : nodeSet) {
            final double betweenness = node.getBetweenness();
            if (betweenness > maxBetweenness) {
                maxBetweenness = betweenness;
            }
            if (betweenness < minBetweenness) {
                minBetweenness = betweenness;
            }
        }
        long stop = System.currentTimeMillis();
        System.out.println("Found extreme values ("
                + minBetweenness + ", " + maxBetweenness
                + ") in " + (stop - start) + " ms.");
    }

    /**
     * Prints shortest path information from the given start node to all other
     * nodes.
     *
     * @param startNode The start node.
     */
    protected void printSPInfo(V startNode) {
        System.out.println("       d           SP    pred");
        for (V node : nodeSet) {
            System.out.print("(" + startNode + "," + node + ")  ");
            String formatString = "%-12";
            if (this instanceof UnweightedGraphAnalyzer) {
                formatString += "d";
            } else {
                formatString += "f";
            }
            formatString += "%-6d%-12s";
            System.out.format(formatString,
                              node.getDistance(),
                              node.getSPCount(),
                              node.getPredecessors().toString());
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Prints out debug information about shortest paths from the given start
     * node.
     *
     * @param startNode The start node.
     */
    private void debug(V startNode) {
        int id = startNode.getID();
        for (V node : graph.vertexSet()) {
            System.out.println("d(" + id + "," + node.getID()
                    + ") = " + node.getDistance()
                    + ", spCount = " + node.getSPCount()
                    + ", dep = " + node.getDependency());
        }
        System.out.println("");
    }
}
