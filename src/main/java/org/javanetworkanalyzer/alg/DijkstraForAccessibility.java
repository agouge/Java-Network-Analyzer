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

import java.util.PriorityQueue;
import org.javanetworkanalyzer.data.VAccess;
import org.javanetworkanalyzer.model.EdgeSPT;
import org.jgrapht.Graph;

/**
 * An implementation of Dijkstra's algorithm which can be used to calculate, for
 * each vertex, the (distance to the) closest destination among several possible
 * destinations in an {@link AccessibilityAnalyzer}.
 *
 * @author Adam Gouge
 * @param <E> edge
 */
// TODO: Enable **multiple** "closest" destinations within a given tolerance.
public class DijkstraForAccessibility<E extends EdgeSPT> extends Dijkstra<VAccess, E> {

    /**
     * Constructor: sets the graph.
     *
     * @param graph Graph
     */
    public DijkstraForAccessibility(Graph<VAccess, E> graph) {
        super(graph);
    }

    /**
     * Updates the distance to the neighbor and updates the distance to the
     * closest destination if necessary.
     *
     * @param startNode the start vertex
     * @param u        Vertex u
     * @param v        Vertex v
     * @param uvWeight w(u,v)
     * @param e        Edge e
     * @param queue    Queue
     */
    @Override
    protected void shortestPathSoFarUpdate(VAccess startNode, VAccess u, VAccess v,
                                           Double uvWeight, E e, PriorityQueue<VAccess> queue) {
        // If the distance from the start node to v (so the distance *from* v
        // *to* the destination represented by the start node in a reversed
        // graph) is less than the distance to any previously found closest
        // destination, then update v.
        final double distance = u.getDistance() + uvWeight;
        if (v.getDistanceToClosestDestination() > distance) {
            v.setDistanceToClosestDestination(distance);
            v.setClosestDestinationId(startNode.getID());
        }
        super.shortestPathSoFarUpdate(startNode, u, v, uvWeight, e, queue);
    }
}
