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
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import gnu.trove.set.hash.TIntHashSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Home-brewed implementation of Dijkstra's algorithm.
 *
 * @author Adam Gouge
 */
public class Dijkstra {

    /**
     * The graph on which to calculate shortest paths.
     */
    protected final Graph graph;
    /**
     * The set of nodes of this graph.
     */
    protected final TIntHashSet nodeSet;
    /**
     * Map of all nodes with their respective {@link NodeBetweennessInfo}, which
     * stores information calculated during the execution of Dijkstra's
     * algorithm in {@link Dijkstra#calculate()}.
     */
    protected final HashMap<Integer, NodeBetweennessInfo> nodeBetweenness;
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
    public Dijkstra(Graph graph,
                    final HashMap<Integer, NodeBetweennessInfo> nodeBetweenness,
                    int startNode) {
        this.graph = graph;
        this.nodeSet = GraphAnalyzer.nodeSet(graph);
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

        PriorityQueue<Integer> queue = createPriorityQueue();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            // Extract the minimum element.
//            System.out.println(queue.toString());
            int u = queue.poll();
//            printExtractedNode(u);
            // Relax every neighbor of u.
            EdgeIterator outgoingEdges = graph.getOutgoing(u);
            while (outgoingEdges.next()) {
                int v = outgoingEdges.node();
                double uvWeight = outgoingEdges.distance();
                relax(u, v, uvWeight, queue);
            }
//            System.out.println("");
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
    protected void relax(int u,
                       int v,
                       double uvWeight,
                       PriorityQueue<Integer> queue) {
        final NodeBetweennessInfo uNBInfo = nodeBetweenness.get(u);
        final NodeBetweennessInfo vNBInfo = nodeBetweenness.get(v);
//        System.out.println("-----(dist to " + v
//                + ") - (dist to " + u
//                + ") - w(" + u + "," + v + ") = "
//                + vNBInfo.getDistanceDouble()
//                + " - " + uNBInfo.getDistanceDouble()
//                + " - " + uvWeight
//                + " = " + (vNBInfo.getDistanceDouble()
//                - uNBInfo.getDistanceDouble()
//                - uvWeight));
        if (vNBInfo.getDistance()
                - uNBInfo.getDistance()
                - uvWeight > 0.0) {

            // TODO: Is this the right thing to do?


            // This is one of several shortest paths to v.
            if (Math.abs(vNBInfo.getDistance()
                    - uNBInfo.getDistance()
                    - uvWeight) < TOLERANCE) {
//                System.out.println(
//                        "     !!! multiple shortest paths to "
//                        + v + " !!!");
                vNBInfo.accumulateSPCount(uNBInfo.getSPCount());
                vNBInfo.addPredecessor(u);
            } // This is the unique shortest path to v we've found.
            else {
//                System.out.println("--- one shortest path to " + v + " ---");
                // This is the first shortest path to v we've found.
//                if (!(vNBInfo.getSPCount() == 0)) {
//                    System.out.println("_______ OH NO _______");
//                }
                vNBInfo.setSPCount(uNBInfo.getSPCount());
                vNBInfo.getPredecessors().clear();
                vNBInfo.addPredecessor(u);
            }

//            System.out.println("dist to "
//                    + v + ": "
//                    + vNBInfo.getDistanceDouble()
//                    + " --> ("
//                    + uNBInfo.getDistanceDouble() + " + " + uvWeight
//                    + ") = " + (uNBInfo.getDistanceDouble() + uvWeight));
            vNBInfo.setDistance(
                    uNBInfo.getDistance()
                    + uvWeight);
            queue.remove(v);
            queue.add(v);

//            System.out.println("*****(dist to " + v
//                    + ") - (dist to " + u
//                    + ") - w(" + u + "," + v + ") = "
//                    + vNBInfo.getDistanceDouble()
//                    + " - " + uNBInfo.getDistanceDouble()
//                    + " - " + uvWeight
//                    + " = " + (vNBInfo.getDistanceDouble()
//                    - uNBInfo.getDistanceDouble()
//                    - uvWeight));

//            System.out.println("  " + v
//                    + " pred: " + vNBInfo.getPredecessors().toString()
//                    + ", sp-count: " + vNBInfo.getSPCount());
        }
    }

//    /**
//     * Prints the given node as it is settled in {@link Dijkstra#calculate()}.
//     *
//     * @param node The node.
//     */
//    private void printSettledNode(int node) {
//        System.out.println("Settled " + node + ", final dist = "
//                + nodeBetweenness.get(node).getDistanceDouble()
//                + ", pred: "
//                + nodeBetweenness.get(node).getPredecessors().toString()
//                + ", sp-count: " + nodeBetweenness.get(node).getSPCount());
//    }

    protected PriorityQueue<Integer> createPriorityQueue() {
        return new PriorityQueue<Integer>(
                nodeSet.size(),
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer v1, Integer v2) {
                        return Double.compare(
                                nodeBetweenness.get(v1).getDistance(),
                                nodeBetweenness.get(v2).getDistance());
                    }
                });
    }
}
