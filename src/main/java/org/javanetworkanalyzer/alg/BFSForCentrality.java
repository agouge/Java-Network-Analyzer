/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 * It is part of the OrbisGIS tool ecosystem.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * Java Network Analyzer is distributed under GPL 3 license.
 *
 * Copyright (C) 2012-2014 CNRS (IRSTV CNRS FR 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC CNRS UMR 6285)
 *
 * Java Network Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Java Network Analyzer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Java Network Analyzer. If not, see <http://www.gnu.org/licenses/>.
 * 
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.UnweightedPathLengthData;
import java.util.LinkedList;
import java.util.Stack;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.model.EdgeSPT;
import org.jgrapht.Graph;

/**
 * Uses BFS to do graph analysis (calculating betweenness centrality, etc.).
 *
 * @author Adam Gouge
 * @param <E> edge
 */
public class BFSForCentrality<E extends EdgeSPT> extends BFS<VUCent, E>
        implements CentralityAlg<VUCent, E, UnweightedPathLengthData> {

    /**
     * Stack that will return the nodes ordered by non-increasing distance from
     * the source node.
     */
    private final Stack<VUCent> stack;
    /**
     * Data structure used to hold information used to calculate closeness.
     */
    private final UnweightedPathLengthData pathsFromStartNode;

    /**
     * Constructs a new {@link BFSForCentrality} object.
     *
     * @param graph The graph.
     * @param stack Stack which returns nodes in order of non-increasing
     *              distance from the start node
     */
    public BFSForCentrality(Graph<VUCent, E> graph,
                            Stack<VUCent> stack) {
        super(graph);
        this.stack = stack;
        this.pathsFromStartNode = new UnweightedPathLengthData();
    }

    @Override
    protected void init(VUCent startNode) {
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
    protected VUCent dequeueStep(LinkedList<VUCent> queue) {
        // Dequeue a node.
        VUCent current = queue.poll();
        // Push it to the stack.
        stack.push(current);
        // Return it.
        return current;
    }

    @Override
    protected void firstTimeFoundStep(
            final VUCent current,
            final VUCent neighbor) {
        // Add this to the path length data. (For closeness)
        pathsFromStartNode.addSPLength(neighbor.getDistance());
    }

    @Override
    protected void shortestPathStep(VUCent current,
                                    VUCent neighbor, E e) {
        // Add currentNode to the set of predecessors of neighbor.
        super.shortestPathStep(current, neighbor, e);
        // Update the number of shortest paths.
        neighbor.accumulateSPCount(current.getSPCount());
    }

    @Override
    public UnweightedPathLengthData getPaths() {
        return pathsFromStartNode;
    }
}
