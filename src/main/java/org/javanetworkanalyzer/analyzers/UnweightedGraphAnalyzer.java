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

import org.javanetworkanalyzer.alg.BFSForCentrality;
import org.javanetworkanalyzer.data.VUBetw;
import org.javanetworkanalyzer.data.UnweightedPathLengthData;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;
import org.jgrapht.Graph;

/**
 * Calculates various centrality measures on unweighted graphs <b>assumed to be
 * connected</b> using a Breadth-First Search (BFS) to calculate all possible
 * shortest paths.
 *
 * @author Adam Gouge
 */
public class UnweightedGraphAnalyzer<E>
        extends GraphAnalyzer<VUBetw, E, UnweightedPathLengthData> {

    private final BFSForCentrality<E> bfs;

    /**
     * Initializes a new instance of an unweighted graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph The graph to be analyzed.
     * @param pm    The {@link ProgressMonitor} to be used.
     */
    public UnweightedGraphAnalyzer(
            Graph<VUBetw, E> graph,
            ProgressMonitor pm) throws
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        super(graph, pm);
        this.bfs = new BFSForCentrality<E>(graph, stack);
    }

    /**
     * Initializes a new instance of an unweighted graph analyzer that doesn't
     * keep track of progress.
     *
     * @param graph The graph to be analyzed.
     */
    public UnweightedGraphAnalyzer(Graph<VUBetw, E> graph)
            throws NoSuchMethodException,
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
    protected UnweightedPathLengthData calculateShortestPathsFromNode(
            VUBetw startNode) {
        bfs.calculate(startNode);
        return bfs.getPaths();
    }
}
