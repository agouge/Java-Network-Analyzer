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
package com.graphhopper.sna.model;

import java.util.Scanner;
import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Creates weighted JGraphT graphs from a csv file produced by OrbisGIS.
 *
 * @author Adam Gouge
 */
public class WeightedGraphCreator extends GraphCreator {

    /**
     * Initializes a new {@link WeightedGraphCreator}.
     *
     * @param csvFile     CSV file containing the edge information.
     * @param weightField The weight column name.
     * @param orientation The desired graph orientation.
     */
    public WeightedGraphCreator(String csvFile,
                                String weightField,
                                int orientation) {
        super(csvFile, weightField, orientation);
    }

    /**
     * Loads weighted edges.
     *
     * @param scanner The scanner that will parse the csv file.
     * @param graph   The graph to which the edges will be added.
     * @param reverse {@code true} iff the edge orientation should be reversed.
     */
    private void loadWeightedEdges(
            Scanner scanner,
            WeightedGraph<Integer, Edge> graph,
            boolean reverse) {
        // Go through the file and add each edge.
        while (scanner.hasNextLine()) {
            // Split the line.
            String[] parts = scanner.nextLine().split(SEPARATOR);
            // Note: We have to get rid of the quotation marks.
            int startNode = Integer.parseInt(
                    deleteDoubleQuotes(parts[startNodeIndex]));
            int endNode = Integer.parseInt(
                    deleteDoubleQuotes(parts[endNodeIndex]));
            double weight = Double.parseDouble(
                    deleteDoubleQuotes(parts[weightFieldIndex]));
            // Add the nodes to the graph.
            graph.addVertex(startNode);
            graph.addVertex(endNode);
            // Add the edge to the graph.
            Edge edge;
            if (reverse) {
                edge = graph.addEdge(endNode, startNode);
            } else {
                edge = graph.addEdge(startNode, endNode);
            }
            // Set the edge weight.
//             Note: graph.setEdgeWeight(edge, weight) does not work.
            edge.setWeight(weight);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadDirectedEdges(
            Scanner scanner,
            DirectedGraph<Integer, Edge> graph) {
        loadWeightedEdges(scanner, (WeightedGraph) graph, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadReversedEdges(
            Scanner scanner,
            DirectedGraph<Integer, Edge> graph) {
        loadWeightedEdges(scanner, (WeightedGraph) graph, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadUndirectedEdges(
            Scanner scanner,
            UndirectedGraph<Integer, Edge> graph) {
        loadWeightedEdges(scanner, (WeightedGraph) graph, false);
    }
}