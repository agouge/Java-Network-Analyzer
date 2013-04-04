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
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Home-brewed implementation of Dijkstra's algorithm.
 *
 * @author Adam Gouge
 */
public class Dijkstra extends GraphSearchAlgorithm {

    /**
     * Tolerance to be used when determining if two potential shortest paths
     * have the same length.
     */
    protected static final double TOLERANCE = 0.000000001;
    /**
     * Stores information calculated during the execution of Dijkstra's
     * algorithm in {@link Dijkstra#calculate()}.
     */
    protected Map<Integer, WeightedNodeBetweennessInfo> nodeBetweenness;

    /**
     * Constructs a new {@link Dijkstra} object.
     *
     * @param graph           The graph.
     * @param nodeBetweenness The hash map.
     * @param startNode       The start node.
     */
    public Dijkstra(Graph graph,
                    int startNode,
                    final Map<Integer, WeightedNodeBetweennessInfo> nodeBetweenness) {
        super(graph, startNode);
        this.nodeBetweenness = nodeBetweenness;
    }

    /**
     * Calculates the distance and number of shortest paths from startNode to
     * all the other nodes, as well as the predecessor of each node on shortest
     * paths from startNode.
     */
    public void calculate() {

        nodeBetweenness.get(startNode).setSource();

        PriorityQueue<Integer> queue = createPriorityQueue();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            // Extract the minimum element.
            int u = queue.poll();
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
        // Get the node betweenness info objects.
        final WeightedNodeBetweennessInfo uNBInfo = nodeBetweenness.get(u);
        final WeightedNodeBetweennessInfo vNBInfo = nodeBetweenness.get(v);
        // If the length of this path to v through u is less than
        // OR EQUAL TO (this corresponds to multiple shortest paths)
        // the current distance estimate on v, then update the
        // shortest path information of v. The TOLERANCE takes care
        // of the "or equal to" part.
        if (vNBInfo.getDistance() - uNBInfo.getDistance() - uvWeight
                > -TOLERANCE) {
            updateSPCount(u, v, uNBInfo, vNBInfo, uvWeight);
            vNBInfo.addPredecessor(u);
            vNBInfo.setDistance(uNBInfo.getDistance() + uvWeight);
            queue.remove(v);
            queue.add(v);
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
            int u,
            int v,
            final WeightedNodeBetweennessInfo uNBInfo,
            final WeightedNodeBetweennessInfo vNBInfo,
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

    /**
     * Creates the {@link PriorityQueue} used in {@link Dijkstra}'s algorithm.
     *
     * @return The {@link PriorityQueue} used in {@link Dijkstra}'s algorithm.
     */
    protected PriorityQueue<Integer> createPriorityQueue() {
        return new PriorityQueue<Integer>(
                graph.nodes(),
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
