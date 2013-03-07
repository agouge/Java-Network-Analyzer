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
import com.graphhopper.sna.model.Edge;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import org.jgrapht.Graph;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

/**
 * Home-brewed implementation of Dijkstra's algorithm.
 *
 * @author Adam Gouge
 */
public class Dijkstra {

    /**
     * The graph on which to calculate shortest paths.
     */
    protected final Graph<Integer, Edge> graph;
    /**
     * Map of all nodes with their respective {@link NodeBetweennessInfo}, which
     * stores information calculated during the execution of Dijkstra's
     * algorithm in {@link Dijkstra#calculate()}.
     */
    protected final Map<Integer, NodeBetweennessInfo> nodeBetweenness;
    /**
     * Start node.
     */
    protected final int startNode;
    /**
     * Tolerance to be used when determining if two potential shortest paths
     * have the same length.
     */
    protected static final double TOLERANCE = 0.000000001;

    /**
     * Constructs a new {@link Dijkstra} object.
     *
     * @param graph           The graph.
     * @param nodeBetweenness The hash map.
     * @param startNode       The start node.
     */
    public Dijkstra(Graph<Integer, Edge> graph,
                    final Map<Integer, NodeBetweennessInfo> nodeBetweenness,
                    int startNode) {
        this.graph = graph;
        this.nodeBetweenness = nodeBetweenness;
        this.startNode = startNode;
    }

    /**
     * Calculates the distance and number of shortest paths from startNode to
     * all the other nodes, as well as the predecessor of each node on shortest
     * paths from startNode.
     */
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
     * Sets the distance from startNode to itself to 0.0 and the number of
     * shortest paths to 1; all other initializations of distance, shortest path
     * counts, and predecessor lists are already taken care of by the
     * constructor of {@link NodeBetweennessInfo}.
     *
     * @param graph     The graph.
     * @param startNode The start node.
     */
    protected void initializeSingleSource(
            Graph graph,
            int startNode) {
        nodeBetweenness.get(startNode).setSource();
    }

    /**
     * Relaxes the edge (u,v) and updates the queue appropriately.
     *
     * @param u        Node u.
     * @param v        Node v.
     * @param uvWeight The weight of the edge (u,v).
     * @param queue    The queue.
     */
    protected void relax(FibonacciHeapNode<Integer> u,
                         FibonacciHeapNode<Integer> v,
                         double uvWeight,
                         FibonacciHeap<Integer> queue) {
        // Get the node betweenness info objects.
        final NodeBetweennessInfo uNBInfo =
                nodeBetweenness.get(u.getData());
        final NodeBetweennessInfo vNBInfo =
                nodeBetweenness.get(v.getData());
        // If the length of this path to v through u is less than
        // OR EQUAL TO (this corresponds to multiple shortest paths)
        // the current distance estimate on v, then update the
        // shortest path information of v. The TOLERANCE takes care
        // of the "or equal to" part.
        if (vNBInfo.getDistance() - uNBInfo.getDistance() - uvWeight
                >= 0.0) {
//            System.out.println(
//                    vNBInfo.getDistance() > uNBInfo.getDistance() - uvWeight);
//            System.out.println("V dist = " + vNBInfo.getDistance());
//            System.out.println("U dist (" + uNBInfo.getDistance()
//                    + ") + uvWeight (" + uvWeight
//                    + ") = " + (uNBInfo.getDistance() + uvWeight) + ".");
//            System.out.println("Diff: " + (vNBInfo.getDistance() - uNBInfo.
//                    getDistance() - uvWeight));
            updateSPCount(uNBInfo, vNBInfo, uvWeight);
            vNBInfo.addPredecessor(u.getData());
            vNBInfo.setDistance(uNBInfo.getDistance() + uvWeight);
            queue.decreaseKey(v, uvWeight);
        }
    }

    /**
     * Updates the number of shortest paths leading to v when relaxing the edge
     * (u,v).
     *
     * @param u        Node u.
     * @param v        Node v.
     * @param uNBInfo  {@link NodeBetweennessInfo} for u.
     * @param vNBInfo  {@link NodeBetweennessInfo} for v.
     * @param uvWeight w(u,v).
     */
    protected void updateSPCount(
            final NodeBetweennessInfo uNBInfo,
            final NodeBetweennessInfo vNBInfo,
            double uvWeight) {
        // If the difference between the distance to v on the one hand
        // and the distance to u plus w(u,v) on the other hand is less
        // than the defined tolerance (think EQUAL), then this
        // is one of multiple shortest paths. As such, we add the number
        // of shortest paths to u.
        if (Math.abs(vNBInfo.getDistance()
                - uNBInfo.getDistance()
                - uvWeight) < TOLERANCE) {
            vNBInfo.accumulateSPCount(uNBInfo.getSPCount());
        } // Otherwise this is the first shortest path found to v so far,
        // so we set the number of shortest paths to that of u.
        else {
            vNBInfo.setSPCount(uNBInfo.getSPCount());
        }
    }
}
