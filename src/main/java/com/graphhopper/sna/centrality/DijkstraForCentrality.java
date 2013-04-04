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

import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import gnu.trove.stack.array.TIntArrayStack;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * An implementation of Dijkstra's algorithm with can be used to calculate
 * betweenness and closeness in a {@link GraphAnalyzer}.
 *
 * @author Adam Gouge
 */
public class DijkstraForCentrality extends Dijkstra {

    /**
     * Stack that will return the nodes ordered by non-increasing distance from
     * the source node.
     */
    private final TIntArrayStack stack;
    /**
     * Data structure used to hold information used to calculate closeness.
     */
    private final PathLengthData pathsFromStartNode;

    /**
     * Constructs a new {@link DijkstraForCentrality} object.
     *
     * @param graph              The graph.
     * @param nodeBetweenness    The hash map.
     * @param startNode          The start node.
     * @param pathsFromStartNode Information for calculating closeness.
     * @param stack              The stack which will return nodes ordered by
     *                           non-increasing distance from startNode.
     */
    public DijkstraForCentrality(
            Graph graph,
            final Map<Integer, WeightedNodeBetweennessInfo> nodeBetweenness,
            int startNode,
            PathLengthData pathsFromStartNode,
            TIntArrayStack stack) {
        super(graph, startNode, nodeBetweenness);
        this.pathsFromStartNode = pathsFromStartNode;
        this.stack = stack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculate() {

        nodeBetweenness.get(startNode).setSource();

        PriorityQueue<Integer> queue = createPriorityQueue();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            // Extract the minimum element.
            int u = queue.poll();
            // Push it to the stack.
            if (canPushToStack(u)) {
                stack.push(u);
            } else {
                throw new IllegalStateException(
                        "Cannot push node " + u + " to the stack.");
            }
            // Record this shortest path length (for closeness).
            if (u != startNode) {
                pathsFromStartNode.addSPLength(
                        nodeBetweenness.get(u).getDistance());
            }
            // Relax every neighbor of u.
            for (EdgeIterator outgoingEdges =
                    GeneralizedGraphAnalyzer.outgoingEdges(graph, u);
                    outgoingEdges.next();) {
                int v = outgoingEdges.adjNode();
                double uvWeight = outgoingEdges.distance();
                relax(u, v, uvWeight, queue);
            }
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
    private boolean canPushToStack(int node) {
        if (!(stack.size() == 0)) {
            if (nodeBetweenness.get(node).getDistance()
                    < nodeBetweenness.get(stack.peek()).getDistance()) {
                return false;
            }
        }
        return true;
    }
}