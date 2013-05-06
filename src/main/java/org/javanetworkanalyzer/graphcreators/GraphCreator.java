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
import org.javanetworkanalyzer.model.DirectedPseudoG;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.PseudoG;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Creates JGraphT graphs from a csv file produced by OrbisGIS.
 *
 * @param <V> Vertex
 * @param <E> Edge
 *
 * @author Adam Gouge
 */
public class GraphCreator<V extends IdInfo, E extends Edge> {

    /**
     * Start node column name.
     */
    protected static final String START_NODE = "start_node";
    /**
     * End node column name.
     */
    protected static final String END_NODE = "end_node";
    /**
     * Orientation.
     */
    protected final int orientation;
    /**
     * Vertex class used for initializing the graph.
     */
    protected final Class<? extends V> vertexClass;
    /**
     * Edge class used for initializing the graph.
     */
    protected final Class<? extends E> edgeClass;
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
    protected static final String DOUBLE_QUOTES = "\"";
    protected static final String EMPTY_STRING = "";

    /**
     * Initializes a new {@link GraphCreator}.
     *
     * @param csvFile     CSV file containing the edge information.
     * @param weightField The weight column name.
     * @param orientation The desired graph orientation.
     * @param vertexClass The vertex class
     * @param edgeClass   The edge class
     */
    // TODO: Make sure the orientation is valid!
    public GraphCreator(String csvFile,
                        int orientation,
                        Class<? extends V> vertexClass,
                        Class<? extends E> edgeClass) {
        this.csvFile = csvFile;
        this.orientation = orientation;
        this.vertexClass = vertexClass;
        this.edgeClass = edgeClass;
    }

    /**
     * Returns a new graph from a csv file produced in OrbisGIS as the
     * {@code output.edges} table given by {@code ST_Graph}.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    public KeyedGraph<V, E> loadGraph()
            throws FileNotFoundException, NoSuchMethodException {

        long start = System.currentTimeMillis();
        System.out.println("Creating a graph from CSV. ");

        // Get a scanner on the csv file.
        Scanner scanner = getScannerOnCSVFile(csvFile);

        // Initialize the indices of the start_node, end_node, and weight.
        initializeIndices(scanner);

        // Initialize a graph.
        KeyedGraph<V, E> graph = initializeGraph();

        // Load the edges from the input file.
        loadEdges(scanner, graph);

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
     * Initialize the start node and end node indices by reading the first line
     * of the csv file.
     *
     * @param scanner The scanner that will read the first line of the csv file.
     */
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
     * Initializes a graph.
     *
     * @return The newly initialized graph
     *
     * @throws NoSuchMethodException If the vertex class does not have a
     *                               constructor with just an Integer parameter.
     */
    protected KeyedGraph<V, E> initializeGraph() throws NoSuchMethodException {
        KeyedGraph<V, E> graph;
        if (orientation != UNDIRECTED) {
            // Unweighted Directed or Reversed
            graph = new DirectedPseudoG<V, E>(vertexClass, edgeClass);
        } else {
            // Unweighted Undirected
            graph = new PseudoG<V, E>(vertexClass, edgeClass);
        }
        return graph;
    }

    /**
     * Loads all edges into the graph.
     *
     * @param scanner The scanner that will parse the csv file.
     * @param graph   The graph.
     *
     * @return The graph.
     */
    private KeyedGraph<V, E> loadEdges(Scanner scanner,
                                       KeyedGraph<V, E> graph) {
        // Should we reverse the edge orientation?
        boolean reverse = (orientation == REVERSED) ? true : false;
        // Go through the file and add each edge.
        while (scanner.hasNextLine()) {
            // Split the line.
            String[] row = scanner.nextLine().split(SEPARATOR);
            loadEdge(row, graph, reverse);
        }
        return graph;
    }

    /**
     * Loads an edge into the graph.
     *
     * @param row     The row from which to load the edge.
     * @param graph   The graph to which the edges will be added.
     * @param reverse {@code true} iff the edge orientation should be reversed.
     *
     * @return The newly loaded edge.
     */
    protected E loadEdge(String[] row,
                         KeyedGraph<V, E> graph,
                         boolean reverse) {
        // Note: We have to get rid of the quotation marks.
        int startNode = Integer.parseInt(
                deleteDoubleQuotes(row[startNodeIndex]));
        int endNode = Integer.parseInt(
                deleteDoubleQuotes(row[endNodeIndex]));
        // Add the edge to the graph.
        E edge;
        if (reverse) {
            edge = graph.addEdge(endNode, startNode);
        } else {
            edge = graph.addEdge(startNode, endNode);
        }
        // And return it.
        return edge;
    }
}
