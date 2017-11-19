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

import org.javanetworkanalyzer.data.VDFS;
import org.javanetworkanalyzer.model.EdgeSPT;
import org.jgrapht.Graph;

/**
 * Root Depth First Search (DFS) class.
 *
 * @param <V> The data structure to hold node information during the execution
 *            of DFS.
 * @author Adam Gouge
 * @param <E> edge
 */
public class DFS<V extends VDFS, E extends EdgeSPT> extends GraphSearchAlgorithm<V, E> {

    /**
     * For discovery and finishing times.
     */
    private int time = 0;

    /**
     * Constructor.
     *
     * @param graph The graph.
     */
    public DFS(Graph<V, E> graph) {
        super(graph);
    }

    /**
     * Does the depth first search for all nodes in the graph.
     */
    public void calculate() {
        for (V node : graph.vertexSet()) {
            if (node.getDiscoveryTime() < 0) {
                calculate(node);
            }
        }
    }

    /**
     * Does a depth first search from the given start node.
     *
     * @param startNode Start node
     */
    @Override
    public void calculate(V startNode) {
        init(startNode);
        visit(startNode);
    }

    /**
     * Visit the given node, updating its predecessor and discovery and
     * finishing times.
     *
     * @param node The node.
     */
    protected void visit(V node) {

        time++;

        node.setDiscoveryTime(time);

        for (V neighbor : successorListOf(node)) {
            if (neighbor.getDiscoveryTime() < 0) {
                neighbor.addPredecessor(node);
                neighbor.addPredecessorEdge(graph.getEdge(node, neighbor));
                visit(neighbor);
            }
        }

        time++;

        node.setFinishingTime(time);
    }
}
