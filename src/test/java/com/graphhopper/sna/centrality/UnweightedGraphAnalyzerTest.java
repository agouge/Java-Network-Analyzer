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

import com.graphhopper.sna.data.NetworkAnalysisResult;
import com.graphhopper.sna.storage.GDMSGraphStorage;
import gnu.trove.map.hash.TIntDoubleHashMap;
import java.io.FileNotFoundException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests graph analysis on unweighted graphs.
 *
 * @author Adam Gouge
 */
public class UnweightedGraphAnalyzerTest extends GraphSetupTest {

    /**
     * Tests graph analysis on a 2D unweighted directed graph.
     *
     * <p> For now, we just compute closeness centrality, but full graph
     * analysis will be available soon.
     *
     * @throws FileNotFoundException
     */
    // TODO: Test betweenness once implemented.
    // TODO: Check closeness definition for directed graphs.
    @Test
    public void testUnweightedGraph2DDirected() throws
            FileNotFoundException {

        System.out.println("\n***** CLOSENESS 2D DIRECTED *****");

        // Prepare the graph.
        GDMSGraphStorage graph = prepareGraph2DDirected();

        // Prepare the unweighted graph analyzer.
        UnweightedGraphAnalyzer analyzer =
                new UnweightedGraphAnalyzer(graph);

        // Calculate closeness.
        TIntDoubleHashMap result = analyzer.computeCloseness();

        // Check values.
        assertEquals(result.get(6), 0.6666666666666666, TOLERANCE);
        assertEquals(result.get(5), 0.0, TOLERANCE);
        assertEquals(result.get(4), 0.0, TOLERANCE);
        assertEquals(result.get(3), 0.5714285714285714, TOLERANCE);
        assertEquals(result.get(2), 0.4166666666666667, TOLERANCE);
        assertEquals(result.get(1), 1.0, TOLERANCE);
    }

    /**
     * Tests graph analysis on a 2D unweighted bidirectional graph.
     *
     * @throws FileNotFoundException
     */
    // TODO: Write a similar test for directed graphs.
    @Test
    public void testUnweightedGraphAnalysis2DBidirectional() throws
            FileNotFoundException {

        System.out.println("\n***** UNWEIGHTED 2D BIDIRECTIONAL *****");

        // Prepare the graph.
        GDMSGraphStorage graph = prepareGraph2DBidirectional();

        // Prepare the unweighted graph analyzer.
        UnweightedGraphAnalyzer analyzer =
                new UnweightedGraphAnalyzer(graph);

        // Calculate betweenness.
        long start = System.currentTimeMillis();
        NetworkAnalysisResult result = analyzer.computeAll();
        long stop = System.currentTimeMillis();
        System.out.println("Network analysis took " + (stop - start)
                + " ms to compute.");
        
        TIntDoubleHashMap closeness = result.getCloseness();
        TIntDoubleHashMap betweenness = result.getBetweenness();
        
        System.out.println("CLOSENESS");
        GraphAnalyzer.printHashMap(closeness);
        
        // Check values.
        assertEquals(closeness.get(6), 0.625, TOLERANCE);
        assertEquals(closeness.get(5), 0.4166666666666667, TOLERANCE);
        assertEquals(closeness.get(4), 0.35714285714285715, TOLERANCE);
        assertEquals(closeness.get(3), 0.625, TOLERANCE);
        assertEquals(closeness.get(2), 0.4166666666666667, TOLERANCE);
        assertEquals(closeness.get(1), 0.5, TOLERANCE);
        
        System.out.println("BETWEENNESS");
        GraphAnalyzer.printHashMap(betweenness);
        
        assertEquals(betweenness.get(6), 0.45, TOLERANCE);
        assertEquals(betweenness.get(5), 0.0, TOLERANCE);
        assertEquals(betweenness.get(4), 0.0, TOLERANCE);
        assertEquals(betweenness.get(3), 0.6, TOLERANCE);
        assertEquals(betweenness.get(2), 0.0, TOLERANCE);
        assertEquals(betweenness.get(1), 0.3, TOLERANCE);
    }
}
