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
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.sna.util.ProgressMonitor;
import com.graphhopper.storage.Graph;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.stack.array.TIntArrayStack;

/**
 * Calculates various centrality measures on weighted graphs which are
 * <b>assumed to be connected</b>.
 *
 * @author Adam Gouge
 */
public class WeightedGraphAnalyzer extends GraphAnalyzer {

    /**
     * Initializes a new instance of a weighted graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph The graph to be analyzed.
     * @param pm    The {@link ProgressMonitor} to be used.
     */
    public WeightedGraphAnalyzer(Graph graph, ProgressMonitor pm) {
        super(graph, pm);
    }

    /**
     * Initializes a new instance of a weighted graph analyzer that doesn't keep
     * track of progress.
     *
     * @param graph The graph to be analyzed.
     */
    public WeightedGraphAnalyzer(Graph graph) {
        super(graph);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        nodeBetweenness.clear();
        TIntIterator nodeSetIterator = nodeSet.iterator();
        while (nodeSetIterator.hasNext()) {
            nodeBetweenness.put(nodeSetIterator.next(),
                                new WeightedNodeBetweennessInfo());
        }
    }

    /**
     * Computes the closeness centrality indices of all vertices of the graph
     * (assumed to be connected) and stores them in a hash map, where the keys
     * are the vertices and the values are the closeness.
     *
     * <p> This method first computes contraction hierarchies on the given
     * graph, which greatly reduces the shortest paths computation time.
     *
     * @return The closeness centrality hash map.
     */
    @Override
    public TIntDoubleHashMap computeCloseness() {
        ClosenessCentrality closenessCentrality =
                new ClosenessCentrality(graph);
        return closenessCentrality.calculateUsingContractionHierarchies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void calculateShortestPathsFromNode(
            int startNode,
            PathLengthData pathsFromStartNode,
            TIntArrayStack stack) {
        // Need to compute all shortest paths from s=startNode.
        //
        // Once a SP from s to a node "current"=w is found, we need to
        // (in the NodeBetweennessInfo of w):
        //     1. Increment appropriately the sPCount of w.
        //     2. Store d(s,w) in the distance of w.
        //     3. Append the predecessor v of w on the SP s->...->v->w.
        //
        // We also need to push s to the stack and push all the other nodes
        // to the stack in order of non-decreasing distance from s (so that
        // when we pop from the stack in the dependency calculation
        // {@link GraphAnalyzer.accumulateDependencies(int, TIntArrayStack)},
        // the nodes are popped in order of non-increasing distance from s.
        // This is IMPORTANT.
        DijkstraForCentrality algorithm =
                new DijkstraForCentrality(graph,
                                          nodeBetweenness,
                                          startNode,
                                          pathsFromStartNode,
                                          stack);
        algorithm.calculate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected double getAveragePathLength(
            PathLengthData pathsFromStartNode) {
        return (pathsFromStartNode.getCount() > 0)
                ? pathsFromStartNode.getAverageLength()
                : 0.0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printSPInfo(
            int startNode) {
        System.out.println("       d           SP pred");
        TIntIterator it = nodeSet.iterator();
        while (it.hasNext()) {
            int node = it.next();
            final NodeBetweennessInfo nodeNBInfo = nodeBetweenness.get(node);
            System.out.print("(" + startNode + "," + node + ")  ");
            System.out.format("%-12f%-3d%-12s",
                              nodeNBInfo.getDistance(),
                              nodeNBInfo.getSPCount(),
                              nodeNBInfo.getPredecessors().toString());
            System.out.println("");
        }
    }
}
