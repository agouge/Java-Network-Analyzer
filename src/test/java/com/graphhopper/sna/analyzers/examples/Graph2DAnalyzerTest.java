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
package com.graphhopper.sna.analyzers.examples;

import com.graphhopper.sna.analyzers.GraphAnalyzerTest;
import com.graphhopper.sna.progress.NullProgressMonitor;
import com.graphhopper.sna.progress.ProgressMonitor;
import java.io.FileNotFoundException;
import org.junit.Test;

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

//    @Test
//    @Override
//    public void unweightedDirected() throws FileNotFoundException {
////        super.unweightedDirected();
//    }
//
//    @Test
//    @Override
//    public void unweightedReversed() throws FileNotFoundException {
////        super.unweightedReversed();
//    }
//
//    @Test
//    @Override
//    public void unweightedUndirected() throws FileNotFoundException {
////        super.unweightedUndirected();
//    }
//
//    @Test
//    @Override
//    public void weightedDirected() throws FileNotFoundException {
////        super.weightedDirected();
//    }
//
//    @Test
//    @Override
//    public void weightedReversed() throws FileNotFoundException {
////        super.weightedReversed();
//    }
//
//    @Test
//    @Override
//    public void weightedUndirected() throws FileNotFoundException {
////        super.weightedUndirected();
//    }

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
