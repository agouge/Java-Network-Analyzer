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

import org.javanetworkanalyzer.data.VUBetw;
import org.javanetworkanalyzer.data.UnweightedPathLengthData;
import java.util.LinkedList;
import java.util.Stack;
import org.jgrapht.Graph;

/**
 * Uses BFS to do graph analysis (calculating betweenness centrality, etc.).
 *
 * @author Adam Gouge
 */
public class BFSForCentrality<E> extends BFS<VUBetw, E> {

    /**
     * Stack that will return the nodes ordered by non-increasing distance from
     * the source node.
     */
    private final Stack<VUBetw> stack;
    /**
     * Data structure used to hold information used to calculate closeness.
     */
    private final UnweightedPathLengthData pathsFromStartNode;

    /**
     * Constructs a new {@link BFSForCentrality} object.
     *
     * @param graph     The graph.
     * @param startNode The start node.
     * @param nodeMap   Maps nodes to their info.
     */
    public BFSForCentrality(Graph<VUBetw, E> graph,
                            Stack<VUBetw> stack) {
        super(graph);
        this.stack = stack;
        this.pathsFromStartNode = new UnweightedPathLengthData();
    }

    @Override
    protected void init(VUBetw startNode) {
        super.init(startNode);
        stack.clear();
        pathsFromStartNode.clear();
    }

    /**
     * Dequeues a node from the given queue and pushes it to the stack.
     *
     * @param queue The queue.
     *
     * @return The newly dequeued node.
     */
    @Override
    protected VUBetw dequeueStep(
            LinkedList<VUBetw> queue) {
        // Dequeue a node.
        VUBetw current = queue.poll();
        // Push it to the stack.
        stack.push(current);
        // Return it.
        return current;
    }

    @Override
    protected void firstTimeFoundStep(
            final VUBetw current,
            final VUBetw neighbor) {
        // Add this to the path length data. (For closeness)
        pathsFromStartNode.addSPLength(neighbor.getDistance());
    }

    @Override
    protected void shortestPathStep(VUBetw current,
                                    VUBetw neighbor) {
        // Update the number of shortest paths.
        neighbor.accumulateSPCount(current.getSPCount());
        // Add currentNode to the set of predecessors of neighbor.
        neighbor.addPredecessor(current);
    }

    public UnweightedPathLengthData getPaths() {
        return pathsFromStartNode;
    }
}