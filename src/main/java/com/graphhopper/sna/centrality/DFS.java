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

import com.graphhopper.sna.data.DFSInfo;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.GHUtility;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import java.util.Map;

/**
 * Root Depth First Search (DFS) class.
 *
 * @param <T> The data structure to hold node information during the execution
 *            of DFS.
 *
 * @author Adam Gouge
 */
public class DFS<T extends DFSInfo> {

    /**
     * The graph on which to calculate shortest paths.
     */
    protected final Graph graph;
    /**
     * Stores information calculated during the execution of BFS in
     * {@link BFS#calculate()}.
     */
    protected final Map<Integer, T> nodeMap;
    /**
     * For discovery and finishing times.
     */
    private int time = 0;

    /**
     * Constructs a new {@link DFS} object.
     *
     * @param graph   The graph.
     * @param nodeMap Maps nodes to their info.
     */
    public DFS(Graph graph,
               final Map<Integer, T> nodeMap) {
        this.graph = graph;
        this.nodeMap = nodeMap;
    }

    /**
     * Do the depth first search.
     */
    protected void calculate() {

        TIntHashSet nodeSet = GeneralizedGraphAnalyzer.nodeSet(graph);
        TIntIterator it = nodeSet.iterator();
        while (it.hasNext()) {
            int current = it.next();
            if (nodeMap.get(current).getDiscoveryTime() < 0) {
                visit(current);
            }
        }
    }

    /**
     * Visit the current node, updating its predecessor and discovery and
     * finishing times.
     *
     * @param current
     */
    private void visit(int current) {

        time++;

        T currentInfo = nodeMap.get(current);
        currentInfo.setDiscoveryTime(time);

        EdgeIterator outgoingEdges =
                GHUtility.getCarOutgoing(graph, current);
        while (outgoingEdges.next()) {

            int neighbor = outgoingEdges.node();
            T neighborInfo = nodeMap.get(neighbor);

            if (neighborInfo.getDiscoveryTime() < 0) {
                neighborInfo.addPredecessor(current);
                visit(neighbor);
            }
        }

        time++;

        currentInfo.setFinishingTime(time);
    }
}
