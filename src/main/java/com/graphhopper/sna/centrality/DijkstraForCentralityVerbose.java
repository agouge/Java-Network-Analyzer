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
import gnu.trove.stack.array.TIntArrayStack;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * An implementation of Dijkstra's algorithm with can be used to calculate
 * betweenness and closeness in a {@link GraphAnalyzer}; shows verbose output
 * for debugging.
 *
 * Only the null {@link DijkstraForCentrality
 * #relax(int, int, double, java.util.PriorityQueue) relax} and null {@link
 * DijkstraForCentrality#
 * updateSPCount(int, int, com.graphhopper.sna.data.NodeBetweennessInfo,
 * com.graphhopper.sna.data.NodeBetweennessInfo, double) updateSPCount} methods
 * are overridden.
 *
 * @author Adam Gouge
 */
public class DijkstraForCentralityVerbose extends DijkstraForCentrality {

    /**
     * Constructs a new {@link DijkstraForCentralityVerbose} object.
     *
     * @param graph              The graph.
     * @param nodeBetweenness    The hash map.
     * @param startNode          The start node.
     * @param pathsFromStartNode Information for calculating closeness.
     * @param stack              The stack which will return nodes ordered by
     *                           non-increasing distance from startNode.
     */
    public DijkstraForCentralityVerbose(
            Graph graph,
            final Map<Integer, WeightedNodeBetweennessInfo> nodeBetweenness,
            int startNode,
            PathLengthData pathsFromStartNode,
            TIntArrayStack stack) {
        super(graph, nodeBetweenness, startNode, pathsFromStartNode, stack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void relax(int u,
                         int v,
                         double uvWeight,
                         PriorityQueue<Integer> queue) {
        System.out.println("Relaxing (" + u + "," + v + ")"
                + ": " + nodeBetweenness.get(v).getDistance() + " ? "
                + (nodeBetweenness.get(u).getDistance() + uvWeight) + ".");
        super.relax(u, v, uvWeight, queue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateSPCount(
            int u,
            int v,
            final WeightedNodeBetweennessInfo uNBInfo,
            final WeightedNodeBetweennessInfo vNBInfo,
            double uvWeight) {

        System.out.println(" -- Update: "
                + (vNBInfo.getDistance() - uNBInfo.getDistance() - uvWeight));
        // If the difference between the distance to v on the one hand
        // and the distance to u plus w(u,v) on the other hand is less
        // than the defined tolerance (so practically equal), then this 
        // is one of multiple shortest paths. As such, we add the number
        // of shortest paths to u.
        if (Math.abs(vNBInfo.getDistance()
                - uNBInfo.getDistance()
                - uvWeight) < TOLERANCE) {
            System.out.print("   " + v + " MULTIPLE. count "
                    + vNBInfo.getSPCount() + ":");
            vNBInfo.accumulateSPCount(uNBInfo.getSPCount());
            System.out.println(vNBInfo.getSPCount()
                    + " (accumulated " + u
                    + ".count = " + uNBInfo.getSPCount()
                    + "). Accum pred " + u
                    + ". Now " + vNBInfo.getPredecessors().toString());
        } // Otherwise this is the first shortest path found to v so far.
        else {
            System.out.print("   " + v + " FIRST. count "
                    + vNBInfo.getSPCount() + ":");
            vNBInfo.setSPCount(uNBInfo.getSPCount());
            System.out.println(vNBInfo.getSPCount()
                    + " (reset to " + u
                    + ".count = " + uNBInfo.getSPCount()
                    + "). Reset preds to {" + u + "}.");
        }
    }
}