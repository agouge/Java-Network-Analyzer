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
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.WeightedNodeBetweennessInfo;
import org.javanetworkanalyzer.data.WeightedPathLengthData;
import java.util.PriorityQueue;
import java.util.Stack;
import org.jgrapht.Graph;

/**
 * An implementation of Dijkstra's algorithm which can be used to calculate
 * betweenness and closeness in a {@link GraphAnalyzer}.
 *
 * @author Adam Gouge
 */
public class DijkstraForCentrality<E>
        extends Dijkstra<WeightedNodeBetweennessInfo, E> {

    /**
     * Stack that will return the nodes ordered by non-increasing distance from
     * the source node.
     */
    private final Stack<WeightedNodeBetweennessInfo> stack;
    /**
     * Data structure used to hold information used to calculate closeness.
     */
    private final WeightedPathLengthData pathsFromStartNode;

    /**
     * Constructs a new {@link DijkstraForCentrality} object.
     *
     * @param graph              The graph.
     * @param startNode          The start node.
     * @param pathsFromStartNode Information for calculating closeness.
     * @param stack              The stack which will return nodes ordered by
     *                           non-increasing distance from startNode.
     */
    public DijkstraForCentrality(
            Graph<WeightedNodeBetweennessInfo, E> graph,
            Stack<WeightedNodeBetweennessInfo> stack) {
        super(graph);
        this.stack = stack;
        this.pathsFromStartNode = new WeightedPathLengthData();
    }

    @Override
    protected void init(WeightedNodeBetweennessInfo startNode) {
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
    protected void preRelaxStep(WeightedNodeBetweennessInfo startNode,
                                WeightedNodeBetweennessInfo u) {
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
    }

    /**
     * Checks if the given node can be pushed to the stack by making sure its
     * distance is greater than the node previously pushed to the stack.
     *
     * @param node The node.
     *
     * @return {@code true} if and only if the node can be pushed to the stack.
     */
    private boolean canPushToStack(WeightedNodeBetweennessInfo node) {
        if (!(stack.size() == 0)) {
            if (node.getDistance() < stack.peek().getDistance()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the predecessor of v to be u and updates the distance estimate on v
     * to equal the distance to u plus w(u,v).
     *
     * @param u        Vertex u
     * @param v        Vertex v
     * @param uvWeight w(u,v)
     * @param queue    Queue
     */
    @Override
    protected void updateNeighbor(
            WeightedNodeBetweennessInfo u,
            WeightedNodeBetweennessInfo v,
            Double uvWeight,
            PriorityQueue<WeightedNodeBetweennessInfo> queue) {
        updateSPCount(u, v, uvWeight);
        super.updateNeighbor(u, v, uvWeight, queue);
    }

    /**
     * Updates the number of shortest paths leading to v when relaxing the edge
     * (u,v).
     *
     * @param u        Node u.
     * @param v        Node v.
     * @param uvWeight w(u,v).
     */
    // TODO: Maybe we test if v.getSPCount == 0 instead?
    // (Potentially more efficient.)
    protected void updateSPCount(WeightedNodeBetweennessInfo u,
                                 WeightedNodeBetweennessInfo v,
                                 double uvWeight) {
        // If the distance to v is just below the distance to u plus w(u,v)
        // (this situation is allowed by the tolerance), then this
        // is one of multiple shortest paths. As such, we add the number
        // of shortest paths to u.
        if (v.getDistance() <= u.getDistance() + uvWeight) {
            v.accumulateSPCount(u.getSPCount());
        } // Otherwise this is the first shortest path found to v so far,
        // so we set the number of shortest paths to that of u.
        else {
            v.setSPCount(u.getSPCount());
        }
    }

    /**
     * Returns the path length data.
     *
     * @return The path length data
     */
    public WeightedPathLengthData getPaths() {
        return pathsFromStartNode;
    }
}