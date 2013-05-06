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
package org.javanetworkanalyzer.graphcreators;

import org.javanetworkanalyzer.data.IdInfo;
import static org.javanetworkanalyzer.graphcreators.GraphCreator.UNDIRECTED;
import static org.javanetworkanalyzer.graphcreators.GraphCreator.weightFieldIndex;
import org.javanetworkanalyzer.model.DirectedWeightedPseudoG;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.WeightedKeyedGraph;
import org.javanetworkanalyzer.model.WeightedPseudoG;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Creates weighted JGraphT graphs from a csv file produced by OrbisGIS.
 *
 * @author Adam Gouge
 */
public class WeightedGraphCreator<V extends IdInfo, E extends Edge>
        extends GraphCreator<V, E> {

    /**
     * Weight column name.
     */
    private final String weightField;

    /**
     * Initializes a new {@link WeightedGraphCreator}.
     *
     * @param csvFile     CSV file containing the edge information.
     * @param weightField The weight column name.
     * @param orientation The desired graph orientation.
     */
    public WeightedGraphCreator(String csvFile,
                                int orientation,
                                Class<? extends V> vertexClass,
                                Class<? extends E> edgeClass,
                                String weightField) {
        super(csvFile, orientation, vertexClass, edgeClass);
        this.weightField = weightField;
    }

    @Override
    public WeightedKeyedGraph<V, E> loadGraph()
            throws FileNotFoundException, NoSuchMethodException {
        return (WeightedKeyedGraph<V, E>) super.loadGraph();
    }

    /**
     * Initialize the start node, end node, and weight indices by reading the
     * first line of the csv file.
     *
     * @param scanner The scanner that will read the first line of the csv file.
     */
    @Override
    protected void initializeIndices(Scanner scanner) {
        String[] row = scanner.nextLine().split(SEPARATOR);
        // Go through the first line and recover the indices.
        for (int i = 0; i < row.length; i++) {
            // Note: We have to get rid of the quotation marks.
            if (row[i].replace(DOUBLE_QUOTES, EMPTY_STRING)
                    .equals(START_NODE)) {
                startNodeIndex = i;
            } else if (row[i].replace(DOUBLE_QUOTES, EMPTY_STRING)
                    .equals(END_NODE)) {
                endNodeIndex = i;
            } else if (row[i].replace(DOUBLE_QUOTES, EMPTY_STRING)
                    .equals(weightField)) {
                weightFieldIndex = i;
            }
        }
    }

    @Override
    protected KeyedGraph<V, E> initializeGraph() throws NoSuchMethodException {
        KeyedGraph<V, E> graph;
        if (orientation != UNDIRECTED) {
            // Weighted Directed or Reversed
            graph = new DirectedWeightedPseudoG<V, E>(vertexClass, edgeClass);
        } else {
            // Weighted Undirected
            graph = new WeightedPseudoG<V, E>(vertexClass, edgeClass);
        }
        return graph;
    }

    /**
     * Loads a weighted edge into the graph.
     *
     * @param row     The row from which to load the edge.
     * @param graph   The graph to which the edges will be added.
     * @param reverse {@code true} iff the edge orientation should be reversed.
     *
     * @return The newly loaded edge.
     */
    @Override
    protected E loadEdge(String[] row,
                         KeyedGraph<V, E> graph,
                         boolean reverse) {
        E edge = super.loadEdge(row, graph, reverse);
        double weight = Double.parseDouble(
                deleteDoubleQuotes(row[weightFieldIndex]));
        edge.setWeight(weight);
        return edge;
    }
}
