/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.storage.GDMSGraphStorage;
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
//    public static GDMSGraphStorage prepareGraphNantes() throws
//            FileNotFoundException {
//        return GDMSGraphStorage.loadGDMSGraph(
//                "./target/GraphNantes",
//                "./files/nantes_1_edges.csv",
//                "weight",
//                true);
//    }
}
