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
package com.graphhopper.sna.graphcreators;

import com.graphhopper.sna.data.IdInfo;
import com.graphhopper.sna.model.DirectedG;
import com.graphhopper.sna.model.DirectedPseudoG;
import com.graphhopper.sna.model.DirectedWeightedPseudoG;
import com.graphhopper.sna.model.Edge;
import com.graphhopper.sna.model.KeyedGraph;
import com.graphhopper.sna.model.PseudoG;
import com.graphhopper.sna.model.UndirectedG;
import com.graphhopper.sna.model.WeightedPseudoG;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Creates JGraphT graphs from a csv file produced by OrbisGIS.
 *
 * @author Adam Gouge
 */
public abstract class GraphCreator<V extends IdInfo, E extends Edge> {

    /**
     * Start node column name.
     */
    private static final String START_NODE = "start_node";
    /**
     * End node column name.
     */
    private static final String END_NODE = "end_node";
    /**
     * Weight column name.
     */
    private final String weightField;
    /**
     * Orientation.
     */
    private final int orientation;
    /**
     * Vertex class used for initializing the graph.
     */
    private final Class<? extends V> vertexClass;
    /**
     * Edge class used for initializing the graph.
     */
    private final Class<? extends E> edgeClass;
    /**
     * CSV file from which to load the edges.
     */
    private final String csvFile;
    /**
     * Start node index.
     */
    protected static int startNodeIndex = -1;
    /**
     * End node index.
     */
    protected static int endNodeIndex = -1;
    /**
     * Weight index.
     */
    protected static int weightFieldIndex = -1;
    /**
     * Specifies a directed graph.
     */
    public static final int DIRECTED = 1;
    /**
     * Specifies a directed graph with reversed edge orientation.
     */
    public static final int REVERSED = 2;
    /**
     * Specifies an undirected graph.
     */
    public static final int UNDIRECTED = 3;
    /**
     * The csv produced by OrbisGIS uses a semicolon delimiter.
     */
    protected static final String SEPARATOR = ";";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String EMPTY_STRING = "";

    /**
     * Initializes a new {@link GraphCreator}.
     *
     * @param csvFile     CSV file containing the edge information.
     * @param weightField The weight column name.
     * @param orientation The desired graph orientation.
     */
    // TODO: Make sure the orientation is valid!
    public GraphCreator(String csvFile,
                        String weightField,
                        int orientation,
                        Class<? extends V> vertexClass,
                        Class<? extends E> edgeClass) {
        this.csvFile = csvFile;
        this.weightField = weightField;
        this.orientation = orientation;
        this.vertexClass = vertexClass;
        this.edgeClass = edgeClass;
    }

    /**
     * Returns a new graph from a csv file produced in OrbisGIS as the
     * {@code output.edges} table given by {@code ST_Graph}.
     *
     * @param csvFile     The file from which the graph is to be constructed.
     * @param weightField The name of the weight column.
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    public KeyedGraph<V, E> loadGraph()
            throws FileNotFoundException, NoSuchMethodException {

        long start = System.currentTimeMillis();
        System.out.println("Creating a graph from CSV. ");

        // Get a scanner on the csv file.
        Scanner scanner = getScannerOnCSVFile(csvFile);

        // Initialize the indices of the start_node, end_node, and length.
        initializeIndices(scanner);
        // Load the edges from the input file.
        KeyedGraph<V, E> graph = loadEdges(scanner);

        long stop = System.currentTimeMillis();
        System.out.println("Created graph in " + (stop - start) + " ms.");

        // Close the scanner.
        scanner.close();

        // Return the grpah.
        return graph;
    }

    /**
     * Gets a {@link Scanner} on the given csv file that will be used to parse
     * the file.
     *
     * @param path The path of the csv file.
     *
     * @return The {@link Scanner}.
     *
     * @throws FileNotFoundException
     */
    private Scanner getScannerOnCSVFile(String path) throws
            FileNotFoundException {
        // Open the edges file.
        File edgesFile = new File(path);
        // We use a BufferedReader for efficiency.
        BufferedReader bufferedReader =
                new BufferedReader(
                new FileReader(
                edgesFile));
        // Get a scanner on the edges file.
        Scanner scanner = new Scanner(bufferedReader);
        return scanner;
    }

    /**
     * Initialize the start node, end node, and weight indices by reading the
     * first line of the csv file.
     *
     * @param scanner The scanner that will read the first line of the csv file.
     */
    private void initializeIndices(Scanner scanner) {
        String[] parts = scanner.nextLine().split(SEPARATOR);
        // Go through the first line and recover the indices.
        for (int i = 0; i < parts.length; i++) {
            // Note: We have to get rid of the quotation marks.
            if (parts[i].replace(DOUBLE_QUOTES, EMPTY_STRING)
                    .equals(START_NODE)) {
                startNodeIndex = i;
            } else if (parts[i].replace(DOUBLE_QUOTES, EMPTY_STRING)
                    .equals(END_NODE)) {
                endNodeIndex = i;
            } else if (parts[i].replace(DOUBLE_QUOTES, EMPTY_STRING)
                    .equals(weightField)) {
                weightFieldIndex = i;
            }
        }
    }

    /**
     * Replaces " by the empty string.
     *
     * @param s The {@link String} to be parsed.
     *
     * @return The given {@link String} with no quotes.
     */
    protected String deleteDoubleQuotes(String s) {
        return s.replace(DOUBLE_QUOTES, EMPTY_STRING);
    }

    /**
     * Loads the edges from the csv file into a graph.
     *
     * @param scanner The scanner that will parse the csv file.
     */
    // TODO: A Multigraph does not allow loops!
    private KeyedGraph<V, E> loadEdges(Scanner scanner) throws NoSuchMethodException {
        // Unweighted
        if (weightField == null) {
            if (orientation != UNDIRECTED) {
                DirectedPseudoG<V, E> graph =
                        new DirectedPseudoG<V, E>(vertexClass, edgeClass);
                loadDirectedOrReversedEdges(scanner, graph);
                return graph;
            } else {
                PseudoG<V, E> graph =
                        new PseudoG<V, E>(vertexClass, edgeClass);
                loadUndirectedEdges(scanner, graph);
                return graph;
            }
        } // Weighted
        else {
            if (orientation != UNDIRECTED) {
                DirectedWeightedPseudoG<V, E> graph =
                        new DirectedWeightedPseudoG<V, E>(vertexClass, edgeClass);
                loadDirectedOrReversedEdges(scanner, graph);
                return graph;
            } else {
                WeightedPseudoG<V, E> graph =
                        new WeightedPseudoG<V, E>(vertexClass, edgeClass);
                loadUndirectedEdges(scanner, graph);
                return graph;
            }
        }
    }

    /**
     * Loads directed edges; the weight is decided by the implementation of this
     * method.
     *
     * @param scanner The scanner that will parse the csv file.
     * @param graph   The graph to which the edges will be added.
     */
    protected abstract void loadDirectedEdges(
            Scanner scanner,
            DirectedG<V, E> graph);

    /**
     * Loads directed edges with orientations reversed; the weight is decided by
     * the implementation of this method.
     *
     * @param scanner The scanner that will parse the csv file.
     * @param graph   The graph to which the edges will be added.
     */
    protected abstract void loadReversedEdges(
            Scanner scanner,
            DirectedG<V, E> graph);

    /**
     * Loads undirected edges; the weight is decided by the implementation of
     * this method.
     *
     * @param scanner The scanner that will parse the csv file.
     * @param graph   The graph to which the edges will be added.
     */
    protected abstract void loadUndirectedEdges(
            Scanner scanner,
            UndirectedG<V, E> graph);

    /**
     * Loads directed or reversed edges depending on the orientation.
     *
     * @param scanner The scanner that will parse the csv file.
     * @param graph   The graph to which the edges will be added.
     */
    private void loadDirectedOrReversedEdges(Scanner scanner,
                                             DirectedG<V, E> graph) {
        if (orientation == DIRECTED) {
            loadDirectedEdges(scanner, graph);
        } else {
            loadReversedEdges(scanner, graph);
        }
    }
}
