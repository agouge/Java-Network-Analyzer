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
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.storage.GDMSGraphStorage;
import com.graphhopper.storage.Graph;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.storage.RAMDirectory;
import java.io.FileNotFoundException;

/**
 * A class of example graphs.
 *
 * @author Adam Gouge
 */
public class Graphs {

    // Weight for unweighted graphs.
    private final static double ALL_WEIGHTS_ONE = 1.0;
    // Graph names.
    public final static String CORMEN_GRAPH = "Cormen Graph";
    public final static String EXAMPLE_GRAPH_2 = "Example Graph 2";
    public final static String GRAPH2D = "2D Graph";
    // Graph types.
    public final static String WEIGHTED = "Weighted";
    public final static String UNWEIGHTED = "Unweighted";
    public final static String BIDIRECTIONAL = "Bidirectional";
    public final static String DIRECTED = "Directed";
    // Test names.
    public final static String DIJKSTRA = "Dijkstra";
    public final static String CLOSENESS_CENTRALITY = "Closeness centrality";

    /**
     * Creates the example graph in Figure 24.6 of Introduction to Algorithms
     * (Cormen), 3rd Edition.
     *
     * @return The graph.
     */
    public static Graph graphCormenWeightedDirected() {
        //                   1
        //           >2 ------------>3
        //          / |^           ->|^
        //       10/ / |      9   / / |
        //        / 2| |3    -----  | |
        //       /   | |    /      4| |6
        //      1<---------------   | |
        //       \   | |  /     7\  | |
        //       5\  | / /        \ | /
        //         \ v| /          \v|
        //          > 4 -----------> 5
        //                   2
        GraphStorage graph = new GraphStorage(new RAMDirectory());
        graph.createNew(10);

        // 1 out
        graph.edge(1, 2, 10, false);
        graph.edge(1, 4, 5, false);
        // 1 in
        graph.edge(5, 1, 7, false);

        // 2 out
        graph.edge(2, 4, 2, false);
        // 2 in
        graph.edge(4, 2, 3, false);

        // 3 out
        graph.edge(3, 5, 4, false);
        // 3 in
        graph.edge(2, 3, 1, false);
        graph.edge(4, 3, 9, false);
        graph.edge(5, 3, 6, false);

        // 4 out
        graph.edge(4, 5, 2, false);

        return graph;
    }

    /**
     * Creates the example graph in Figure 24.6 of Introduction to Algorithms
     * (Cormen), 3rd Edition, changed to be bidirectional.
     *
     * @return The graph.
     *
     * @see #graphCormenWeightedDirected()
     */
    public static Graph graphCormenWeightedBidirectional() {
        //                   1
        //           >2 ------------>3
        //          / |^           ->|^
        //       10/ / |      9   / / |
        //        / 2| |3    -----  | |
        //       /   | |    /      4| |6
        //      1<---------------   | |
        //       \   | |  /     7\  | |
        //       5\  | / /        \ | /
        //         \ v| /          \v|
        //          > 4 ------------ 5
        //                   2
        GraphStorage graph = new GraphStorage(new RAMDirectory());
        graph.createNew(10);

        // 1 out
        graph.edge(1, 2, 10, true);
        graph.edge(1, 4, 5, true);
        // 1 in
        graph.edge(5, 1, 7, true);

        // 2 out
        graph.edge(2, 4, 2, true);
        // 2 in
        graph.edge(4, 2, 3, true);

        // 3 out
        graph.edge(3, 5, 4, true);
        // 3 in
        graph.edge(2, 3, 1, true);
        graph.edge(4, 3, 9, true);
        graph.edge(5, 3, 6, true);

        // 4 out
        graph.edge(4, 5, 2, true);

        return graph;
    }

    /**
     * Creates the example graph in Figure 24.6 of Introduction to Algorithms
     * (Cormen), 3rd Edition, changed to be unweighted.
     *
     * @return The graph.
     *
     * @see #graphCormenWeightedDirected()
     */
    public static Graph graphCormenUnweightedDirected() {

        GraphStorage graph = new GraphStorage(new RAMDirectory());
        graph.createNew(10);

        // 1 out
        graph.edge(1, 2, ALL_WEIGHTS_ONE, false);
        graph.edge(1, 4, ALL_WEIGHTS_ONE, false);
        // 1 in
        graph.edge(5, 1, ALL_WEIGHTS_ONE, false);

        // 2 out
        graph.edge(2, 4, ALL_WEIGHTS_ONE, false);
        // 2 in
        graph.edge(4, 2, ALL_WEIGHTS_ONE, false);

        // 3 out
        graph.edge(3, 5, ALL_WEIGHTS_ONE, false);
        // 3 in
        graph.edge(2, 3, ALL_WEIGHTS_ONE, false);
        graph.edge(4, 3, ALL_WEIGHTS_ONE, false);
        graph.edge(5, 3, ALL_WEIGHTS_ONE, false);

        // 4 out
        graph.edge(4, 5, ALL_WEIGHTS_ONE, false);

        return graph;
    }

    /**
     * Creates the example graph in Figure 24.6 of Introduction to Algorithms
     * (Cormen), 3rd Edition, changed to be unweighted bidirectional.
     *
     * @return The graph.
     *
     * @see #graphCormenWeightedDirected()
     */
    public static Graph graphCormenUnweightedBidirectional() {

        GraphStorage graph = new GraphStorage(new RAMDirectory());
        graph.createNew(10);

        // 1 out
        graph.edge(1, 2, ALL_WEIGHTS_ONE, true);
        graph.edge(1, 4, ALL_WEIGHTS_ONE, true);
        // 1 in
        graph.edge(5, 1, ALL_WEIGHTS_ONE, true);

        // 2 out
        graph.edge(2, 4, ALL_WEIGHTS_ONE, true);
        // 2 in
        graph.edge(4, 2, ALL_WEIGHTS_ONE, true);

        // 3 out
        graph.edge(3, 5, ALL_WEIGHTS_ONE, true);
        // 3 in
        graph.edge(2, 3, ALL_WEIGHTS_ONE, true);
        graph.edge(4, 3, ALL_WEIGHTS_ONE, true);
        graph.edge(5, 3, ALL_WEIGHTS_ONE, true);

        // 4 out
        graph.edge(4, 5, ALL_WEIGHTS_ONE, true);

        return graph;
    }

    /**
     * Creates an example graph containing two shortest paths from 1 to 4.
     *
     * @return {@value GraphSetupTest#EXAMPLE_GRAPH_2}.
     */
    public static Graph weightedDirected() {
        //    0.3    0.9
        //   5--->2------->4
        //   ^    ^        ^
        //    \   |        |
        // 1.0 \  |1.2     |1.3
        //      \ |        |
        //       \|        |
        //        1------->3
        //           0.8
        GraphStorage graph = new GraphStorage(new RAMDirectory());
        graph.createNew(10);

        graph.edge(1, 2, 1.2, false);
        graph.edge(1, 3, 0.8, false);
        graph.edge(1, 5, 1.0, false);
        graph.edge(5, 2, 0.3, false);
        graph.edge(2, 4, 0.9, false);
        graph.edge(3, 4, 1.3, false);

        return graph;
    }

    /**
     * Loads an unweighted bidirectional 2D graph from storage.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    public static GDMSGraphStorage graph2DUnweightedBidirectional() throws
            FileNotFoundException {
        return GDMSGraphStorage.loadUnweightedGDMSGraph(
                "./target/Graph2D",
                "./files/graph2D.edges.csv",
                "length",
                true);
    }

    /**
     * Loads a weighted bidirectional 2D graph from storage.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    public static GDMSGraphStorage graph2DWeightedBidirectional() throws
            FileNotFoundException {
        return GDMSGraphStorage.loadGDMSGraph(
                "./target/Graph2D",
                "./files/graph2D.edges.csv",
                "length",
                true);
    }

    /**
     * Loads a directed 2D graph from storage.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    public static GDMSGraphStorage graph2DWeightedDirected() throws
            FileNotFoundException {
        return GDMSGraphStorage.loadGDMSGraph(
                "./target/Graph2D",
                "./files/graph2D.edges.csv",
                "length",
                false);
    }

    /**
     * Loads a bidirectional graph of Nantes from storage.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    public static GDMSGraphStorage graphNantesWeightedBidirectional() throws
            FileNotFoundException {
        return GDMSGraphStorage.loadGDMSGraph(
                "./target/GraphNantesBidirectional",
                "./files/nantes_1_edges.csv",
                "weight",
                true);
    }

    /**
     * Loads a bidirectional graph of the Pays de la Loire department from
     * storage.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    public static GDMSGraphStorage graphPaysDeLaLoireWeightedBidirectional()
            throws
            FileNotFoundException {
        return GDMSGraphStorage.loadGDMSGraph(
                "./target/GraphPaysDeLaLoireBidirectional",
                "./files/pays_de_la_loire.edges.csv",
                "weight",
                true);
    }
}
