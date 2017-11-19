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
package org.javanetworkanalyzer.graphcreators;

import org.javanetworkanalyzer.data.VId;
import static org.javanetworkanalyzer.graphcreators.GraphCreator.UNDIRECTED;

import org.javanetworkanalyzer.model.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Creates weighted JGraphT graphs from a csv file produced by OrbisGIS.
 *
 * @author Adam Gouge
 */
public class WeightedGraphCreator<V extends VId, E extends Edge>
        extends GraphCreator<V, E> {

    /**
     * Weight column name.
     */
    private final String weightField;
    /**
     * Weight index.
     */
    protected static int weightFieldIndex = -1;

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
