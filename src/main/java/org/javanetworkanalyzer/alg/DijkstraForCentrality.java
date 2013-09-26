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
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.data.WeightedPathLengthData;
import java.util.PriorityQueue;
import java.util.Stack;

import org.javanetworkanalyzer.model.EdgeSPT;
import org.jgrapht.Graph;

/**
 * An implementation of Dijkstra's algorithm which can be used to calculate
 * betweenness and closeness in a {@link GraphAnalyzer}.
 *
 * @author Adam Gouge
 */
public class DijkstraForCentrality<E extends EdgeSPT> extends Dijkstra<VWCent, E>
        implements CentralityAlg<VWCent, E, WeightedPathLengthData> {

    /**
     * Stack that will return the nodes ordered by non-increasing distance from
     * the source node.
     */
    private final Stack<VWCent> stack;
    /**
     * Data structure used to hold information used to calculate closeness.
     */
    private final WeightedPathLengthData pathsFromStartNode;

    /**
     * Constructs a new {@link DijkstraForCentrality} object.
     *
     * @param graph The graph.
     * @param stack The stack which will return nodes ordered by non-increasing
     *              distance from startNode.
     */
    public DijkstraForCentrality(
            Graph<VWCent, E> graph,
            Stack<VWCent> stack) {
        super(graph);
        this.stack = stack;
        this.pathsFromStartNode = new WeightedPathLengthData();
    }

    @Override
    protected void init(VWCent startNode) {
        super.init(startNode);
        stack.clear();
        pathsFromStartNode.clear();
    }

    /**
     * Before relaxing the outgoing edges of u, we push it to the stack and
     * record its shortest path length.
     *
     * @param u Vertex u.
     */
    @Override
    protected boolean preRelaxStep(VWCent startNode, VWCent u) {
        // Push it to the stack.
        if (canPushToStack(u)) {
            stack.push(u);
        } else {
            throw new IllegalStateException(
                    "Cannot push node " + u.getID() + " to the stack.");
        }
        // Record this shortest path length (for closeness).
        if (!u.equals(startNode)) {
            pathsFromStartNode.addSPLength(u.getDistance());
        }
        // Don't stop the search.
        return false;
    }

    /**
     * Checks if the given node can be pushed to the stack by making sure its
     * distance is greater than the node previously pushed to the stack.
     *
     * @param node The node.
     *
     * @return {@code true} if and only if the node can be pushed to the stack.
     */
    private boolean canPushToStack(VWCent node) {
        if (!(stack.size() == 0)) {
            if (node.getDistance() < stack.peek().getDistance()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void shortestPathSoFarUpdate(VWCent startNode, VWCent u, VWCent v,
                                           Double uvWeight,
                                           E e, PriorityQueue<VWCent> queue) {
        // Reset the number of shortest paths
        v.setSPCount(u.getSPCount());
        super.shortestPathSoFarUpdate(startNode, u, v, uvWeight, e, queue);
    }

    @Override
    protected void multipleShortestPathUpdate(VWCent u, VWCent v, E e) {
        // Accumulate the number of shortest paths
        v.accumulateSPCount(u.getSPCount());
        super.multipleShortestPathUpdate(u, v, e);
    }

    @Override
    public WeightedPathLengthData getPaths() {
        return pathsFromStartNode;
    }
}