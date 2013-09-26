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
package org.javanetworkanalyzer.analyzers;

import org.javanetworkanalyzer.alg.CentralityAlg;
import org.javanetworkanalyzer.alg.GraphSearchAlgorithm;
import org.javanetworkanalyzer.data.PathLengthData;
import org.javanetworkanalyzer.data.VCent;
import org.javanetworkanalyzer.data.VDist;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.EdgeCent;
import org.javanetworkanalyzer.model.TraversalGraph;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import org.jgrapht.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.Stack;

/**
 * Calculates various centrality measures on the given graph, <b>assumed to be
 * connected</b>.
 *
 * @author Adam Gouge
 */
public abstract class GraphAnalyzer<V extends VCent, E extends EdgeCent, S extends PathLengthData>
        extends GeneralizedGraphAnalyzer<V, E> {

    private double maxBetweenness;
    private double minBetweenness;
    private double maxEdgeBetweenness;
    private double minEdgeBetweenness;
    /**
     * Progress monitor.
     */
    protected ProgressMonitor pm;
    /**
     * A logger.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(GraphAnalyzer.class);
    // When accumulating dependencies, this stack will return vertices
    // in order of non-increasing distance from startNode.
    protected final Stack<V> stack;

    /**
     * Initializes a new instance of a graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph The graph to be analyzed.
     * @param pm    The {@link ProgressMonitor} to be used.
     */
    // TODO: Do I need the wildcard on S?
    public GraphAnalyzer(Graph<V, E> graph,
                         ProgressMonitor pm)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        super(graph);
        this.pm = pm;
        this.stack = new Stack<V>();
        this.maxBetweenness = Double.NEGATIVE_INFINITY;
        this.minBetweenness = Double.POSITIVE_INFINITY;
        this.maxEdgeBetweenness = Double.NEGATIVE_INFINITY;
        this.minEdgeBetweenness = Double.POSITIVE_INFINITY;
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
        this(graph, new NullProgressMonitor());
    }

    /**
     * Performs graph analysis and stores the results in a hash map, mapping
     * each node to a data structure holding the results of the analysis.
     */
    public void computeAll() throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        long startTime = System.currentTimeMillis();

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

        // ***** CENTRALITY CONTRIBUTION CALCULATION **********
        // Calculate all the shortest paths from startNode.
        CentralityAlg<V, E, S> alg = calculateShortestPathsFromNode(startNode);
        // At this point, we have all information required to calculate
        // closeness for startNode.
        calculateClosenessForNode(startNode, alg.getPaths());
        // Use the recursion formula to update the dependency
        // values and their contributions to betweenness values.
        TraversalGraph<V, E> sPT = alg.reconstructTraversalGraph();
        accumulateDependencies(startNode, sPT);
        // ***** END CENTRALITY CONTRIBUTION CALCULATION ******
    }

    /**
     * Stores number of shortest paths and the length of these paths from
     * startNode to every other node in the {@link V} of every other node; also
     * updates the predecessor sets.
     *
     * @param startNode Start node
     * @return The {@link GraphSearchAlgorithm} used to find the shortest paths.
     */
    protected abstract CentralityAlg<V, E, S> calculateShortestPathsFromNode(V startNode);

    /**
     * Given a node and its path length data calculated in
     * {@link #calculateCentralityMeasures(int)}, this method calculates its
     * closeness centrality (or "out" closeness centrality for digraphs).
     *
     * @param node  The given node.
     * @param paths Its path length data.
     */
    protected void calculateClosenessForNode(V node, S paths) {
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
     */
    private void accumulateDependencies(
            V startNode,
            TraversalGraph<V, E> shortestPathTree) {

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

            for (V predecessor : (Set<V>) w.getPredecessors()) {

                final double oldDep = predecessor.getDependency();

                // (A) Add the contribution of the dependency of startNode
                // on w to the dependency of startNode on v.
                final double sigmaFactor = ((double) predecessor.getSPCount()
                        / w.getSPCount());
                final double depContribution = sigmaFactor * (1 + w.getDependency());
                predecessor.accumulateDependency(depContribution);

                // EDGE BETWEENNESS
                accumEdgeBetw(predecessor, w, sigmaFactor, shortestPathTree);
            }

            // (The betweenness of w cannot receive contributions from
            // the dependency of w on w, by the definition of dependency.)
            if (w != startNode) {
                // (B) At this point, the dependency of startNode on w
                // has finished calculating, so we can add it to
                // the betweenness centrality of w.
                final double oldBetw = w.getBetweenness();
                w.accumulateBetweenness(w.getDependency());
            }
        } // ***** END STAGE 3, Stack iteration  **************
    }

    /**
     * Accumulate edge dependencies and betweenness.
     *
     * @param predecessor      Predecessor of vertex w
     * @param w                Vertex w
     * @param sigmaFactor      Sigma factor for weighting
     * @param shortestPathTree SP"T"
     */
    private void accumEdgeBetw(
            V predecessor,
            V w,
            double sigmaFactor,
            TraversalGraph<V, E> shortestPathTree) {
        for (E sPTEdge : shortestPathTree.getAllEdges(predecessor, w)) {
            double depSumFromOutgoing = 0.0;
            for (E outEdge : shortestPathTree.outgoingEdgesOf(w)) {
                depSumFromOutgoing += outEdge.getDependency();
            }
            sPTEdge.accumulateDependency(sigmaFactor * (1 + depSumFromOutgoing));
            // It's okay to accumulate dependencies in the SPT, but the
            // betweenness values have to be accumulated to the base graph!
            sPTEdge.getBaseGraphEdge().accumulateBetweenness(sPTEdge.getDependency());
        }
    }

    /**
     * Normalizes betweenness to make all values lie in the range [0,1] with the
     * minimum betweenness value set to 0.0 and the maximum betweenness value
     * set to 1.0.
     */
    private void normalizeBetweenness() {
        long start = System.currentTimeMillis();
        findExtremeBetweennessValues();
        final double vertexBetwRange = maxBetweenness - minBetweenness;
        if (vertexBetwRange == 0.0) {
            LOGGER.warn("All vertex betweenness values are zero.");
        } else {
            for (V node : nodeSet) {
                final double normalizedBetweenness =
                        (node.getBetweenness() - minBetweenness) / vertexBetwRange;
                node.setBetweenness(normalizedBetweenness);
            }
        }
        final double edgeBetwRange = maxEdgeBetweenness - minEdgeBetweenness;
        if (edgeBetwRange == 0.0) {
            LOGGER.warn("All edge betweenness values are zero.");
        } else {
            for (E edge : graph.edgeSet()) {
                final double normalizedBetweenness =
                        (edge.getBetweenness() - minEdgeBetweenness) / edgeBetwRange;
                edge.setBetweenness(normalizedBetweenness);
            }
        }
        long stop = System.currentTimeMillis();
        LOGGER.info("({} ms) Betweenness normalization.", (stop - start));
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
        for (E edge : graph.edgeSet()) {
            final double betweenness = edge.getBetweenness();
            if (betweenness > maxEdgeBetweenness) {
                maxEdgeBetweenness = betweenness;
            }
            if (betweenness < minEdgeBetweenness) {
                minEdgeBetweenness = betweenness;
            }
        }
        long stop = System.currentTimeMillis();
        LOGGER.info("({} ms) Extreme betweenness values v({}, {}), e({}, {}).",
                (stop - start), minBetweenness, maxBetweenness,
                minEdgeBetweenness, maxEdgeBetweenness);
    }
}
