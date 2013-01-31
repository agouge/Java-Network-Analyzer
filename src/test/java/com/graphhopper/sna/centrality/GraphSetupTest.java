/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.storage.GDMSGraphStorage;
import com.graphhopper.storage.Graph;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.storage.RAMDirectory;
import java.io.FileNotFoundException;

/**
 * Loads {@link GDMSGraphStorage} graphs from csv files.
 *
 * @author agouge
 */
public class GraphSetupTest {

    /**
     * Used for checking results of centrality index computations.
     */
    protected final static double TOLERANCE = 0.000000000001;
    /**
     * Timing message.
     */
    protected final static String TIME = " [time] ";
    /**
     * Example graph 1 name.
     */
    protected final static String EXAMPLE_GRAPH_1 = "Example Graph 1";
    /**
     * Example graph 2 name.
     */
    protected final static String EXAMPLE_GRAPH_2 = "Example Graph 2";

    /**
     * Creates the example graph in Figure 24.6 of Introduction to Algorithms
     * (Cormen), 3rd Edition.
     *
     * @return {@value GraphSetupTest#EXAMPLE_GRAPH_2}.
     */
    public Graph exampleGraph1() {
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
     * Creates an example graph containing two shortest paths from 1 to 4.
     *
     * @return {@value GraphSetupTest#EXAMPLE_GRAPH_2}.
     */
    public Graph exampleGraph2() {
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
     * Loads a bidirectional 2D graph from storage.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    public static GDMSGraphStorage prepareGraph2DBidirectional() throws
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
    public static GDMSGraphStorage prepareGraph2DDirected() throws
            FileNotFoundException {
        return GDMSGraphStorage.loadGDMSGraph(
                "./target/Graph2D",
                "./files/graph2D.edges.csv",
                "length",
                false);
    }
//    /**
//     * Prints the closeness centrality result.
//     *
//     * @param resultIterator The iterator over the r
//     */
//    public void printResult(TIntDoubleHashMap result) {
//        TIntDoubleIterator resultIterator = result.iterator();
//        while (resultIterator.hasNext()) {
//            resultIterator.advance();
//            System.out.println(resultIterator.key() + ", " + resultIterator.
//                    value());
//        }
//    }
//    /**
//     * Loads a bidirectional graph of Nantes from storage.
//     *
//     * @return The graph.
//     *
//     * @throws FileNotFoundException
//     */
//    public static GDMSGraphStorage prepareGraphNantesBidirectional() throws
//            FileNotFoundException {
//        return GDMSGraphStorage.loadGDMSGraph(
//                "./target/GraphNantesBidirectional",
//                "./files/nantes_1_edges.csv",
//                "weight",
//                true);
//    }
//    /**
//     * Loads a bidirectional graph of the Pays de la Loire department from
//     * storage.
//     *
//     * @return The graph.
//     *
//     * @throws FileNotFoundException
//     */
//    public static GDMSGraphStorage prepareGraphPaysDeLaLoireBidirectional()
//            throws
//            FileNotFoundException {
//        return GDMSGraphStorage.loadGDMSGraph(
//                "./target/GraphPaysDeLaLoireBidirectional",
//                "./files/pays_de_la_loire.edges.csv",
//                "weight",
//                true);
//    }
}
