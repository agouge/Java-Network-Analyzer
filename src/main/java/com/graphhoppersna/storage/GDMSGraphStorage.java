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
 * GraphHopper-SNA is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * GraphHopper-SNA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.graphhoppersna.storage;

import com.graphhopper.storage.Directory;
import com.graphhopper.storage.LevelGraphStorage;
import com.graphhopper.storage.RAMDirectory;
import com.graphhopper.util.EdgeIterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * A {@link LevelGraphStorage} created from an {@code output.edges} table (csv
 * format, with a ';' delimiter) produced by the {@code ST_Graph} function of
 * the GDMS-Topology plugin in OrbisGIS.
 *
 * @author Adam Gouge
 */
public class GDMSGraphStorage extends LevelGraphStorage {

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
    private String weightField;
    /**
     * Start node index.
     */
    private int startNodeIndex = -1;
    /**
     * End node index.
     */
    private int endNodeIndex = -1;
    /**
     * Weight index.
     */
    private int weightFieldIndex = -1;

    /**
     * Constructs a {@link GDMSGraphStorage} in the given directory using the
     * specified weight field.
     *
     * @param directory   The directory in which the graph will be stored.
     * @param weightField The name of the weight column.
     */
    public GDMSGraphStorage(Directory directory, String weightField) {
        super(directory);
        this.weightField = weightField;
    }

    /**
     * Returns a new {@link GDMSGraphStorage} from a csv file produced in
     * OrbisGIS as the {@code output.edges} table given by {@code ST_Graph}.
     *
     * If the graph was already constructed, we simply load the data. The
     * boolean {@code bothDirections} is true iff every edge is to be considered
     * bidirectional.
     *
     * @param graphDirectory The directory in which the graph will be or already
     *                       was stored.
     * @param csvFile        The file from which the graph is to be constructed.
     * @param weightField    The name of the weight column.
     * @param bothDirections {@code true} iff every edge is to be considered
     *                       bidirectional.
     *
     * @return The {@link GDMSGraphStorage} that was either newly generated or
     *         loaded from storage.
     *
     * @throws FileNotFoundException
     */
    public static GDMSGraphStorage loadGDMSGraph(String graphDirectory,
            String csvFile,
            String weightField, boolean bothDirections) throws FileNotFoundException {

        // Initiate a graph object using RAMDirectory storage.
        GDMSGraphStorage graph =
                new GDMSGraphStorage(
                new RAMDirectory(
                graphDirectory,
                true), // true that we can write the graph to disk.
                weightField);
        if (graph.loadExisting()) {
            System.out.println("Loaded a previously created graph. ");
        } else {
            System.out.println("Creating a graph from CSV. ");

            // Get a scanner on the csv file.
            Scanner scanner = graph.getScannerOnCSVFile(csvFile);

            // Initialize the indices of the start_node, end_node, and length.
            graph.initializeIndices(scanner);

            // Create the LevelGraph. 
            // TODO: Is this a good way to allocate space for the graph?
            graph.createNew(graph.getNodes()); // TODO: Returns a GraphStorage!

            // Load the edges from the input file into the levelgraph.
            graph.loadEdges(scanner, bothDirections);

            // Close the scanner.
            scanner.close();
        }
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
    public Scanner getScannerOnCSVFile(String path) throws FileNotFoundException {
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
    public void initializeIndices(Scanner scanner) {
        // The csv produced by OrbisGIS uses a semicolon delimiter.
        String[] parts = scanner.nextLine().split(";");
        // Go through the first line and recover the indices.
        for (int i = 0; i < parts.length; i++) {
            // TODO: Make sure all indices are loaded correctly.
            // Note: We have to get rid of the quotation marks.
            if (parts[i].replace("\"",
                    "").equals(START_NODE)) {
                startNodeIndex = i;
            } else if (parts[i].replace("\"",
                    "").equals(END_NODE)) {
                endNodeIndex = i;
            } else if (parts[i].replace("\"",
                    "").equals(weightField)) {
                weightFieldIndex = i;
            }
        }
    }

    /**
     * Loads the edges from the csv file into the given graph.
     *
     * @param scanner        The scanner that will parse the csv file.
     * @param bothDirections {@code true} iff every edge is to be considered
     *                       bidirectional.
     */
    public void loadEdges(Scanner scanner,
            boolean bothDirections) {
        // The variables we will need to recover an edge:
        int startNode, endNode;
        double length;
        // Go through the file and add each edge.
        while (scanner.hasNextLine()) {
            // The csv produced by OrbisGIS uses a semicolon delimiter.
            String[] parts = scanner.nextLine().split(";");
            // Note: We have to get rid of the quotation marks.
            startNode = Integer.
                    parseInt(parts[startNodeIndex].replace("\"", ""));
            endNode = Integer.parseInt(parts[endNodeIndex].replace("\"", ""));
            length = Double.parseDouble(parts[weightFieldIndex].
                    replace("\"", ""));
            // Add the edge to the graph.
            this.edge(startNode, endNode, length, bothDirections);
        }
    }

    /**
     * Prints out the edges of this graph.
     *
     */
    public void printEdges() {
        // Get all the edges.
        EdgeIterator edgeIterator = this.getAllEdges();
        // Print them out.
        while (edgeIterator.next()) {
            System.out.println("EdgeID: " + edgeIterator.edge()
                    + ", " + START_NODE + " " + edgeIterator.fromNode()
                    + ", " + END_NODE + " " + edgeIterator.node()
                    + ", " + weightField + " " + edgeIterator.distance());
        }
    }
}