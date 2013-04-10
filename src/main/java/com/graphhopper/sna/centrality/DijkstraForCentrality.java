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

import static com.graphhopper.sna.centrality.Dijkstra.TOLERANCE;
import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
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
    private final PathLengthData pathsFromStartNode;

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
            WeightedNodeBetweennessInfo startNode,
            PathLengthData pathsFromStartNode,
            Stack<WeightedNodeBetweennessInfo> stack) {
        super(graph, startNode);
        this.pathsFromStartNode = pathsFromStartNode;
        this.stack = stack;
    }

    /**
     * Before relaxing the outgoing edges of u, we push it to the stack and
     * record its shortest path length.
     *
     * @param u Vertex u.
     */
    @Override
    protected void preRelaxStep(WeightedNodeBetweennessInfo u) {
        // Push it to the stack.
        if (canPushToStack(u)) {
            stack.push(u);
        } else {
            throw new IllegalStateException(
                    "Cannot push node " + u + " to the stack.");
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
     * Returns {@code true} iff the current distance estimate on v is greater
     * than OR EQUAL TO (this corresponds to multiple shortest paths) the length
     * of the path to u plus w(u,v).
     *
     * @param u        Vertex u
     * @param v        Vertex v
     * @param uvWeight w(u,v)
     *
     * @return {@code true} iff a smaller distance estimate exists.
     */
    @Override
    protected boolean smallerEstimateExists(
            WeightedNodeBetweennessInfo u,
            WeightedNodeBetweennessInfo v,
            Double uvWeight) {
        // The TOLERANCE takes care of the "or equal to" part.
        if (v.getDistance() > u.getDistance() + uvWeight - TOLERANCE) {
            return true;
        }
        return false;
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
    protected void updateSPCount(WeightedNodeBetweennessInfo u,
                                 WeightedNodeBetweennessInfo v,
                                 double uvWeight) {
        // If the difference between the distance to v on the one hand
        // and the distance to u plus w(u,v) on the other hand is less
        // than the defined tolerance (think EQUAL), then this
        // is one of multiple shortest paths. As such, we add the number
        // of shortest paths to u.
        if (Math.abs(v.getDistance() - u.getDistance() - uvWeight)
                < TOLERANCE) {
            v.accumulateSPCount(u.getSPCount());
        } // Otherwise this is the first shortest path found to v so far,
        // so we set the number of shortest paths to that of u.
        else {
            v.setSPCount(u.getSPCount());
        }
    }
}