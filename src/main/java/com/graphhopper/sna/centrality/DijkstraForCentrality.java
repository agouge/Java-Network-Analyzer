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
import com.graphhopper.sna.model.Edge;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import gnu.trove.stack.array.TIntArrayStack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

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
            Graph<Integer, Edge> graph,
            final Map<Integer, NodeBetweennessInfo> nodeBetweenness,
            int startNode,
            PathLengthData pathsFromStartNode,
            TIntArrayStack stack) {
        super(graph, nodeBetweenness, startNode);
        this.pathsFromStartNode = pathsFromStartNode;
        this.stack = stack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculate() {

        initializeSingleSource(graph, startNode);

        FibonacciHeap<Integer> queue = new FibonacciHeap<Integer>();
        Map<Integer, FibonacciHeapNode<Integer>> map =
                new HashMap<Integer, FibonacciHeapNode<Integer>>();
        TIntHashSet nodeSet = new TIntHashSet(graph.vertexSet());
        nodeSet.remove(startNode);
        TIntIterator it = nodeSet.iterator();
        while (it.hasNext()) {
            int node = it.next();
            FibonacciHeapNode<Integer> heapNode =
                    new FibonacciHeapNode<Integer>(node);
            queue.insert(heapNode,
                         Double.POSITIVE_INFINITY);
            map.put(node, heapNode);
        }
        FibonacciHeapNode<Integer> startHeapNode =
                new FibonacciHeapNode<Integer>(startNode);
        queue.insert(startHeapNode, 0.0);
        map.put(startNode, startHeapNode);

        while (!queue.isEmpty()) {
            // Extract the minimum element.
            FibonacciHeapNode<Integer> u = queue.removeMin();
            // Push it to the stack.
            if (canPushToStack(u.getData())) {
                System.out.println("Pushed " + u.getData()
                        + ", dist = " + nodeBetweenness.get(u.getData()).
                        getDistance());
                stack.push(u.getData());
            } else {
                throw new IllegalStateException(
                        "Cannot push node " + u.getData() + " to the stack.");
            }
            // Record this shortest path length (for closeness).
            if (u.getData() != startNode) {
                pathsFromStartNode.addSPLength(
                        nodeBetweenness.get(u.getData()).getDistance());
            }
            // Relax every neighbor of u.
            // Get the outgoing edges of the current node.
            // TODO: Need to make sure this gives OUTGOING edges!
            Set<Edge> outgoingEdges =
                    GraphAnalyzer.getOutgoingEdges(graph, u.getData());
            Iterator<Edge> edgesOfCurrentNode = outgoingEdges.iterator();
            // For every neighbor of the current node ...
            while (edgesOfCurrentNode.hasNext()) {
                // Get the next edge.
                Edge edge = edgesOfCurrentNode.next();
                // Get the neighbor using this edge.
                FibonacciHeapNode<Integer> v =
                        map.get(
                        GraphAnalyzer.getAdjacentNode(graph, edge, u.getData()));
                double uvWeight = graph.getEdgeWeight(edge);
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
                System.out.println("Node " + stack.peek()
                        + " had distance "
                        + nodeBetweenness.get(stack.peek()).getDistance()
                        + " > " + nodeBetweenness.get(node).getDistance()
                        + " = dist(" + node + ").");
                return false;
            }
        }
        return true;
    }
}