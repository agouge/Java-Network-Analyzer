/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is distributed under the GPL 3 license. It is produced
 * by the "Atelier SIG" team of the <a href="http://www.irstv.fr">IRSTV
 * Institute</a>, CNRS FR 2488.
 *
 * Copyright 2013 IRSTV (CNRS FR 2488).
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
 */
package org.javanetworkanalyzer.analyzers;

import java.util.Set;
import org.javanetworkanalyzer.alg.DijkstraForAccessibility;
import org.javanetworkanalyzer.data.VAccess;
import org.javanetworkanalyzer.model.EdgeSPT;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.EdgeReversedGraph;

/**
 * Calculates, for each vertex, the (distance to the) closest destination among
 * several possible destinations.
 *
 * @author Adam Gouge
 */
public class AccessibilityAnalyzer<E extends EdgeSPT> extends GeneralizedGraphAnalyzer<VAccess, E> {

    /**
     * The set of destinations.
     */
    private Set<VAccess> destinations;

    /**
     * Constructor: sets the graph.
     *
     * @param graph Graph
     */
    public AccessibilityAnalyzer(Graph<VAccess, E> graph,
                                 Set<VAccess> destinations) {
        super(graph);
        this.destinations = destinations;
        verifyDestinations();
    }

    /**
     * Performs accessibility analysis.
     */
    public void compute() {
        // If the graph is directed, then reverse it.
        Graph<VAccess, E> g;
        if (graph instanceof DirectedGraph) {
            g = new EdgeReversedGraph<VAccess, E>((DirectedGraph) graph);
        } else {
            g = graph;
        }
        // Obtain a Dijkstra algorithm on the reversed graph.
        DijkstraForAccessibility<E> dijkstra =
                new DijkstraForAccessibility<E>(g);
        // Now shortest paths from each destination the reversed graph
        // correspond to shortest paths to each destination in the original
        // graph.
        for (VAccess dest : destinations) {
            // Update the distance of each destination to itself.
            dest.setClosestDestinationId(dest.getID());
            dest.setDistanceToClosestDestination(0.0);
            // Calculate all shortest paths from this destination and update
            // the closest destination accordingly.
            dijkstra.calculate(dest);
        }
    }

    /**
     * Makes sure that every requested destination is contained in the graph.
     */
    private void verifyDestinations() {
        for (VAccess dest : destinations) {
            if (!graph.containsVertex(dest)) {
                throw new IllegalArgumentException(
                        "Destination " + dest.getID() + " is not contained "
                        + "in the graph.");
            }
        }
    }
}
