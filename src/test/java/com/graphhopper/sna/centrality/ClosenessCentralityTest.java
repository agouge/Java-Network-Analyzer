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

import com.graphhopper.sna.progress.DefaultProgressMonitor;
import com.graphhopper.sna.storage.GDMSGraphStorage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * A class to test {@link ClosenessCentrality}.
 *
 * @author Adam Gouge
 */
public class ClosenessCentralityTest extends CentralityTest {

    /**
     * Tests closeness on a 2D weighted bidirectional graph.
     *
     * @throws FileNotFoundException
     */
    // TODO: Test betweenness once implemented.
    @Test
    public void testCCWeightedGraph2DBidirectional() throws
            FileNotFoundException {

        // Prepare the graph.
        GDMSGraphStorage graph = Graphs.graph2DWeightedBidirectional();

        // Prepare the unweighted graph analyzer.
        WeightedGraphAnalyzer analyzer =
                new WeightedGraphAnalyzer(graph, new DefaultProgressMonitor());

        // Calculate closeness.
        Map<Integer, Double> result = analyzer.computeCloseness();

        // Check values.
        assertEquals(result.get(1), 0.0037875086449884817, TOLERANCE);
        assertEquals(result.get(2), 0.00353278744701702, TOLERANCE);
        assertEquals(result.get(3), 0.005575422644913597, TOLERANCE);
        assertEquals(result.get(4), 0.0032353894664778064, TOLERANCE);
        assertEquals(result.get(5), 0.0034950150600198938, TOLERANCE);
        assertEquals(result.get(6), 0.005575422644913597, TOLERANCE);
    }

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

        // TODO: Fix title.
//        printTitle(Graphs.GRAPH2D);

        // TODO: Prepare the right graph.
        // Prepare the graph.
        GDMSGraphStorage graph = Graphs.graph2DWeightedDirected();

        // Prepare the unweighted graph analyzer.
        UnweightedGraphAnalyzer analyzer =
                new UnweightedGraphAnalyzer(graph);

        // Calculate closeness.
        Map<Integer, Double> result = analyzer.computeCloseness();

        // Check values.
        assertEquals(result.get(6), 0.6666666666666666, TOLERANCE);
        assertEquals(result.get(5), 0.0, TOLERANCE);
        assertEquals(result.get(4), 0.0, TOLERANCE);
        assertEquals(result.get(3), 0.5714285714285714, TOLERANCE);
        assertEquals(result.get(2), 0.4166666666666667, TOLERANCE);
        assertEquals(result.get(1), 1.0, TOLERANCE);
    }

    /**
     * Tests closeness on a 2D weighted directed graph.
     *
     * @throws FileNotFoundException
     */
    // TODO: Check closeness definition for directed graphs.
    @Test
    public void testCCWeightedGraph2DDirected() throws
            FileNotFoundException {

        // Prepare the graph.
        GDMSGraphStorage graph = Graphs.graph2DWeightedDirected();

        // Prepare the unweighted graph analyzer.
        WeightedGraphAnalyzer analyzer =
                new WeightedGraphAnalyzer(graph);

        // Calculate closeness.
        Map<Integer, Double> result = analyzer.computeCloseness();

        // Check values.
        assertEquals(result.get(6), 0.0, TOLERANCE);
        assertEquals(result.get(5), 0.0, TOLERANCE);
        assertEquals(result.get(4), 0.0, TOLERANCE);
        assertEquals(result.get(3), 0.0, TOLERANCE);
        assertEquals(result.get(2), 0.00353278744701702, TOLERANCE);
        assertEquals(result.get(1), 0.0, TOLERANCE);
    }

    /**
     * Tests the closeness centrality algorithm using a {@link DijkstraSimple}
     * on a 2D bidirectional graph loaded from a csv file.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testClosenessCentralityGraph2DBidirectionalUsingDijkstraSimple()
            throws FileNotFoundException {
        ClosenessCentrality cc = new ClosenessCentrality(
                Graphs.graph2DWeightedBidirectional(),
                new DefaultProgressMonitor());
        Map<Integer, Double> result = cc.calculateUsingDijkstraSimple();
    }

    /**
     * Tests the closeness centrality algorithm using a
     * {@link DijkstraBidirection} on a 2D bidirectional graph loaded from a csv
     * file.
     *
     * @throws IOException
     */
    @Test
    public void testClosenessCentralityGraph2DBidirectionalUsingDijkstraBidirection()
            throws IOException {
        ClosenessCentrality cc = new ClosenessCentrality(
                Graphs.graph2DWeightedBidirectional(),
                new DefaultProgressMonitor());
        Map<Integer, Double> result = cc.calculateUsingDijkstraBidirection();
    }

    /**
     * Tests the closeness centrality algorithm using a
     * {@link DijkstraBidirectionRef} on a 2D bidirectional graph loaded from a
     * csv file.
     *
     * @throws IOException
     */
    @Test
    public void testClosenessCentralityGraph2DBidirectionalUsingDijkstraBidirectionRef()
            throws IOException {
        ClosenessCentrality cc = new ClosenessCentrality(
                Graphs.graph2DWeightedBidirectional(),
                new DefaultProgressMonitor());
        Map<Integer, Double> result = cc.
                calculateUsingDijkstraBidirectionRef();
    }

    /**
     * Tests the closeness centrality algorithm using an {@link AStar} on a 2D
     * bidirectional graph loaded from a csv file.
     *
     * @throws IOException
     */
    @Test
    public void testClosenessCentralityGraph2DBidirectionalUsingAStar() throws
            IOException {
        ClosenessCentrality cc = new ClosenessCentrality(
                Graphs.graph2DWeightedBidirectional(),
                new DefaultProgressMonitor());
        Map<Integer, Double> result = cc.calculateUsingAStar();
    }

    /**
     * Tests the closeness centrality algorithm using an
     * {@link AStarBidirection} on a 2D bidirectional graph loaded from a csv
     * file.
     *
     * @throws IOException
     */
    @Test
    public void testClosenessCentralityGraph2DBidirectionalUsingAStarBidirection()
            throws IOException {
        ClosenessCentrality cc = new ClosenessCentrality(
                Graphs.graph2DWeightedBidirectional(),
                new DefaultProgressMonitor());
        Map<Integer, Double> result = cc.calculateUsingAStarBidirection();
    }

    /**
     * Tests the closeness centrality algorithm using contraction hierarchies on
     * a 2D bidirectional graph loaded from a csv file.
     *
     * @throws IOException
     */
    @Test
    public void testClosenessCentralityGraph2DBidirectionalUsingContractionHierarchies()
            throws IOException {
        ClosenessCentrality cc = new ClosenessCentrality(
                Graphs.graph2DWeightedBidirectional(),
                new DefaultProgressMonitor());
        Map<Integer, Double> result = cc.
                calculateUsingContractionHierarchies();
    }

//    @Test
//    public void testNantes() throws
//            FileNotFoundException {
//
//        // Prepare the graph.
//        GDMSGraphStorage graph = Graphs.graphNantesWeightedBidirectional();
//
//        // Prepare the unweighted graph analyzer.
//        WeightedGraphAnalyzer analyzer =
//                new WeightedGraphAnalyzer(graph,
//                                          new DefaultProgressMonitor());
//
//        // Calculate closeness.
//        HashMap<Integer, Double> result = analyzer.computeCloseness();
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName() {
        return Graphs.CLOSENESS_CENTRALITY;
    }
}
