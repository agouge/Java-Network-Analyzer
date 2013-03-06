/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.progress.NullProgressMonitor;
import com.graphhopper.sna.progress.ProgressMonitor;

/**
 *
 * @author Adam Gouge
 */
public class Graph2DAnalyzerTest extends GraphAnalyzerTest {

    private final static String GRAPH2D = "2D Graph";
    private static final String FILENAME = "./files/graph2D.edges.csv";
    private static final String LENGTH = "length";
    private static final boolean PRINT_RESULTS = true;

    /**
     * {@inheritDoc}
     */
    @Override
    protected ProgressMonitor progressMonitor() {
        return new NullProgressMonitor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean printsResults() {
        return PRINT_RESULTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFilename() {
        return FILENAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWeightColumnName() {
        return LENGTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName() {
        return GRAPH2D;
    }
//    /**
//     * Checks the computations for the 2D bidirectional graph.
//     *
//     * @param result Result to check.
//     */
//    private void check2DGraphResults(
//            Map<Integer, NodeBetweennessInfo> result) {
//        assertEquals(result.get(6).getCloseness(),
//                     0.625, TOLERANCE);
//        assertEquals(result.get(5).getCloseness(),
//                     0.4166666666666667, TOLERANCE);
//        assertEquals(result.get(4).getCloseness(),
//                     0.35714285714285715, TOLERANCE);
//        assertEquals(result.get(3).getCloseness(),
//                     0.625, TOLERANCE);
//        assertEquals(result.get(2).getCloseness(),
//                     0.4166666666666667, TOLERANCE);
//        assertEquals(result.get(1).getCloseness(),
//                     0.5, TOLERANCE);
//        assertEquals(result.get(6).getBetweenness(),
//                     0.75, TOLERANCE);
//        assertEquals(result.get(5).getBetweenness(),
//                     0.0, TOLERANCE);
//        assertEquals(result.get(4).getBetweenness(),
//                     0.0, TOLERANCE);
//        assertEquals(result.get(3).getBetweenness(),
//                     1.0, TOLERANCE);
//        assertEquals(result.get(2).getBetweenness(),
//                     0.0, TOLERANCE);
//        assertEquals(result.get(1).getBetweenness(),
//                     0.5, TOLERANCE);
//    }
}
