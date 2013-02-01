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

import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.storage.GDMSGraphStorage;
import gnu.trove.map.hash.TIntDoubleHashMap;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests graph analysis on weighted graphs.
 *
 * @author Adam Gouge
 */
// TODO: Make up a better example where contraction hierarchies are actually used.
public class WeightedGraphAnalyzerTest extends GraphSetupTest {

    /**
     * Tests graph analysis on a 2D weighted bidirectional graph.
     *
     * <p> For now, we just compute closeness centrality, but full graph
     * analysis will be available soon.
     *
     * @throws FileNotFoundException
     */
    // TODO: Test betweenness once implemented.
    @Test
    public void testWeightedGraph2DBidirectional() throws
            FileNotFoundException {

        // Prepare the graph.
        GDMSGraphStorage graph = prepareGraph2DBidirectional();

        // Prepare the unweighted graph analyzer.
        WeightedGraphAnalyzer analyzer =
                new WeightedGraphAnalyzer(graph);

        // Calculate closeness.
        TIntDoubleHashMap result = analyzer.computeCloseness();

        printResults(result);

        // Check values.
        assertEquals(result.get(6), 0.005575422644913597, TOLERANCE);
        assertEquals(result.get(5), 0.0034950150600198938, TOLERANCE);
        assertEquals(result.get(4), 0.0032353894664778064, TOLERANCE);
        assertEquals(result.get(3), 0.005575422644913597, TOLERANCE);
        assertEquals(result.get(2), 0.00353278744701702, TOLERANCE);
        assertEquals(result.get(1), 0.0037875086449884817, TOLERANCE);
    }

    /**
     * Tests graph analysis on a 2D weighted directed graph.
     *
     * <p> For now, we just compute closeness centrality, but full graph
     * analysis will be available soon.
     *
     * @throws FileNotFoundException
     */
    // TODO: Test betweenness once implemented.
    // TODO: Check closeness definition for directed graphs.
    @Test
    public void testWeightedGraph2DDirected() throws
            FileNotFoundException {

        // Prepare the graph.
        GDMSGraphStorage graph = prepareGraph2DDirected();

        // Prepare the unweighted graph analyzer.
        WeightedGraphAnalyzer analyzer =
                new WeightedGraphAnalyzer(graph);

        // Calculate closeness.
        TIntDoubleHashMap result = analyzer.computeCloseness();

        // Check values.
        assertEquals(result.get(6), 0.0, TOLERANCE);
        assertEquals(result.get(5), 0.0, TOLERANCE);
        assertEquals(result.get(4), 0.0, TOLERANCE);
        assertEquals(result.get(3), 0.0, TOLERANCE);
        assertEquals(result.get(2), 0.00353278744701702, TOLERANCE);
        assertEquals(result.get(1), 0.0, TOLERANCE);
    }
//    /**
//     * Tests graph analysis on a weighted bidirectional graph of Nantes.
//     *
//     * <p> For now, we just compute closeness centrality, but full graph
//     * analysis will be available soon.
//     *
//     * @throws FileNotFoundException
//     */
//    // TODO: Test betweenness once implemented.
//    @Test
//    public void testWeightedGraphNantesBidirectional() throws
//            FileNotFoundException {
//
//        // Prepare the graph.
//        GDMSGraphStorage graph = prepareGraphNantesBidirectional();
//
//        // Prepare the unweighted graph analyzer.
//        WeightedGraphAnalyzer analyzer =
//                new WeightedGraphAnalyzer(graph);
//
//        // Calculate closeness.
//        TIntDoubleHashMap result = analyzer.computeCloseness();
//    }
}
