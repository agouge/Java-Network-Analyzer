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

import com.graphhopper.sna.data.UnweightedNodeBetweennessInfo;
import com.graphhopper.sna.data.UnweightedPathLengthData;
import com.graphhopper.sna.progress.NullProgressMonitor;
import com.graphhopper.sna.progress.ProgressMonitor;
import com.graphhopper.storage.Graph;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.stack.array.TIntArrayStack;
import java.lang.reflect.InvocationTargetException;

/**
 * Calculates various centrality measures on unweighted graphs <b>assumed to be
 * connected</b> using a Breadth-First Search (BFS) to calculate all possible
 * shortest paths.
 *
 * @author Adam Gouge
 */
public class UnweightedGraphAnalyzer
        extends GraphAnalyzer<UnweightedNodeBetweennessInfo, UnweightedPathLengthData> {

    /**
     * Initializes a new instance of an unweighted graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph The graph to be analyzed.
     * @param pm    The {@link ProgressMonitor} to be used.
     */
    public UnweightedGraphAnalyzer(Graph graph, ProgressMonitor pm) throws
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        super(graph, pm, UnweightedNodeBetweennessInfo.class,
              UnweightedPathLengthData.class);
    }

    /**
     * Initializes a new instance of an unweighted graph analyzer that doesn't
     * keep track of progress.
     *
     * @param graph The graph to be analyzed.
     */
    public UnweightedGraphAnalyzer(Graph graph) throws NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        this(graph, new NullProgressMonitor());
    }

    /**
     * Calculates the number of shortest paths from startNode to every other
     * node and the lengths of these paths using a Breadth-First Search (BFS),
     * storing them in the appropriate node info of {@link #nodeBetweenness};
     * also updates the predecessor sets.
     *
     * @param startNode          The start node.
     * @param stack              The stack which will return nodes ordered by
     *                           non-increasing distance from startNode.
     * @param pathsFromStartNode Holds information about shortest path lengths
     *                           from startNode to the other nodes in the
     *                           network
     */
    @Override
    protected void calculateShortestPathsFromNode(
            int startNode,
            UnweightedPathLengthData pathsFromStartNode,
            TIntArrayStack stack) {
        BFSForCentrality algorithm =
                new BFSForCentrality(graph,
                                     startNode,
                                     nodeBetweenness,
                                     pathsFromStartNode,
                                     stack);
        algorithm.calculate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void printSPInfo(int startNode) {
        System.out.println("       d  SP pred");
        TIntIterator it = nodeSet.iterator();
        while (it.hasNext()) {
            int node = it.next();
            final UnweightedNodeBetweennessInfo nodeNBInfo =
                    nodeBetweenness.get(node);
            System.out.print("(" + startNode + "," + node + ")  ");
            System.out.format("%-8d%-3d%-12s",
                              nodeNBInfo.getDistance(),
                              nodeNBInfo.getSPCount(),
                              nodeNBInfo.getPredecessors().toString());
            System.out.println("");
        }
    }
}
