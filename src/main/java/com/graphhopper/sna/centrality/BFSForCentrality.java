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
import com.graphhopper.sna.data.UnweightedNodeBetweennessInfo;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.GHUtility;
import com.graphhopper.util.MyIntDeque;
import gnu.trove.stack.array.TIntArrayStack;
import java.util.Map;

/**
 * Uses BFS to do graph analysis (calculating betweenness centrality, etc.).
 *
 * @author Adam Gouge
 */
public class BFSForCentrality extends BFS<UnweightedNodeBetweennessInfo> {

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
     * Constructs a new {@link BFSForCentrality} object.
     *
     * @param graph     The graph.
     * @param startNode The start node.
     * @param nodeMap   Maps nodes to their info.
     */
    public BFSForCentrality(Graph graph,
                            int startNode,
                            Map<Integer, UnweightedNodeBetweennessInfo> nodeMap,
                            PathLengthData pathsFromStartNode,
                            TIntArrayStack stack) {
        super(graph, startNode, nodeMap);
        this.pathsFromStartNode = pathsFromStartNode;
        this.stack = stack;
    }

    /**
     * Uses BFS to do graph analysis (betweenness centrality, etc.).
     */
    @Override
    protected void calculate() {

        // Create the BFS traversal queue and enqueue startNode.
        MyIntDeque queue = new MyIntDeque();
        queue.push(startNode);

        // While the queue is not empty ...
        while (!queue.isEmpty()) {
            // ... dequeue a node
            int current = queue.pop();
            final UnweightedNodeBetweennessInfo currentNBInfo =
                    nodeMap.get(current);
            // and push this node to the stack.
            stack.push(current);

            // Get the outgoing edges of the current node.
            EdgeIterator edgesOfCurrentNode =
                    GHUtility.getCarOutgoing(graph, current);
            // For every neighbor of the current node ...
            while (edgesOfCurrentNode.next()) {

                int neighbor = edgesOfCurrentNode.node();
                final UnweightedNodeBetweennessInfo neighborNBInfo =
                        nodeMap.get(neighbor);

                // If this neighbor is found for the first time ...
                if (neighborNBInfo.getDistance() < 0) {
                    // then enqueue it
                    queue.push(neighbor);
                    // and update the distance.
                    int updatedSteps = currentNBInfo.getDistance() + 1;
                    neighborNBInfo.setDistance(updatedSteps);
                    // Add this to the path length data. (For closeness)
                    pathsFromStartNode.addSPStep(updatedSteps);
                }

                // If this is a shortest path from startNode to neighbor
                // via current ...
                if (neighborNBInfo.getDistance()
                        == currentNBInfo.getDistance() + 1) {
                    // then update the number of shortest paths,
                    neighborNBInfo.accumulateSPCount(currentNBInfo.getSPCount());
                    // and add currentNode to the set of predecessors
                    // of neighbor.
                    neighborNBInfo.addPredecessor(current);
                }
            }
        }
    }
}