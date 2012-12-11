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
package com.graphhoppersna.centrality;

import com.graphhopper.routing.DijkstraSimple;
import com.graphhopper.routing.Path;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of Freeman's original closeness centrality.
 *
 * <p> Freeman, Linton, A set of measures of centrality based upon betweenness,
 * Sociometry 40: 35–41, 1977. </p>
 *
 * @author Adam Gouge
 */
public class ClosenessCentrality {

    /**
     * The graph on which we will calculate the closeness centrality.
     */
    private Graph graph;

    /**
     * Constructs a {@link ClosenessCentrality} object.
     *
     * @param graph The graph on which we will calculate the closeness
     *              centrality.
     */
    public ClosenessCentrality(Graph graph) {
        this.graph = graph;
    }

    /**
     * Returns a {@link Set} of nodes of this graph.
     *
     * Note: We have to cast each {@code int} node as an {@link Integer}.
     *
     * @return a {@link Set} of nodes of this graph.
     */
    // TODO: Optimize this (by making use of the data structure). 
    // TODO: Is there a better structure to use than a Set?
    // TODO: Is there a way to avoid using Integers?
    public Set<Integer> nodeSet() {
        // Initialize the Set.
        Set<Integer> nodeSet = new HashSet<Integer>();
        // Get all the edges.
        EdgeIterator iter = graph.getAllEdges();
        // Add each source and destination node to the set.
        while (iter.next()) {
            nodeSet.add(new Integer(iter.fromNode()));
            nodeSet.add(new Integer(iter.node()));
        }
        return nodeSet;
    }

    /**
     * Calculates closeness centrality naively by calculating, for each node,
     * the shortest paths to every other node, using {@link DijkstraSimple}.
     *
     * @return A map with the vertex as the key and the closeness centrality as
     *         the value.
     */
    public Map<Integer, Double> calculateUsingDijkstraSimple() {

        // Initiate the result Map.
        Map<Integer, Double> result = new HashMap<Integer, Double>();

        // Recover the set of nodes.
        Set<Integer> nodeSet = nodeSet();

        // For tracking progress.
        long start, stop;
        int size = nodeSet.size();
        System.out.println("There are " + size + " nodes.");

        // Used for calculating the shortest paths.
        DijkstraSimple ds = new DijkstraSimple(
                graph);

        // Fix a node.
        for (Integer source : nodeSet) {

            // Start timing the calculation for the source node.
            start = System.currentTimeMillis();

            // This will be the sum of the shortest path distances to 
            // all the other nodes.
            double farness = 0.0;

            // Begin going through all the other nodes.
            for (Integer destination : nodeSet) {

                // Skip reflexiveness. No need to calculate the distance
                // of a node to itself.
                if (source == destination) {
                    continue;
                }

                // Obtain (one of) the shortest path(s) from the source node
                // to the destination node.
                Path path = ds.clear().calcPath(source.intValue(), destination.
                        intValue());

                // Get the length of the path.
                Double length = path.distance();

                // If the DijkstraSimple finds no path from the source node
                // to the destination node, it returns a length of 0.0. 
                // But no path existing corresponds to the destination node
                // being "infinitely" far away from the source node.
                if (length == 0.0) {
                    length = Double.POSITIVE_INFINITY;
                }

                // Add this shortest path to the sum of all shortest paths.
                farness += length;

                // Once we find one destination node which is infinitely
                // far away, we know that the closeness centrality of
                // the source node will be zero.
                if (Double.isInfinite(farness)) {
                    break; // no need to continue,
                }
            }

            // Finish timing the calculation for the source node.
            stop = System.currentTimeMillis();
            
            // Print out the calculation time for this source node.
            System.out.
                    println(
                    "Calculation for " + source + ": " 
                    + (stop - start) + " ms (" 
                    + (source / size) + "%).");

            // Store the closeness centrality for this source node.
            result.put(source, (nodeSet.size() - 1) / farness);
        }
        
        // Return the Map of closeness centrality results.
        return result;
    }
}