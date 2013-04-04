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

import com.graphhopper.sna.data.UnweightedNodeBetweennessInfo;
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.storage.Graph;
import gnu.trove.iterator.TIntIterator;
import java.io.FileNotFoundException;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Compares the results of graph analysis on unweighted graphs using an
 * {@link UnweightedGraphAnalyzer} on the one hand and a
 * {@link WeightedGraphAnalyzer} on the other hand (with the graph considered to
 * be weighted with all weights equal to 1.0).
 *
 * @author Adam Gouge
 */
public class WeightedVsUnweightedComparisonTest extends GraphAnalyzerTest {

    private static final String name =
            Graphs.WEIGHTED + " vs " + Graphs.UNWEIGHTED + " comparison test";

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName() {
        return name;
    }

    /**
     * Tests the 2D graph.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test2DGraph() throws FileNotFoundException {
        printTitle(Graphs.GRAPH2D);
        Graph graph = Graphs.graph2DUnweightedBidirectional();
        doAnalysis(graph);
    }

    /**
     * Tests the Cormen graph.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void testCormen() throws FileNotFoundException {
        printTitle(Graphs.CORMEN_GRAPH);
        Graph graph = Graphs.graphCormenUnweightedBidirectional();
        doAnalysis(graph);
    }

//    /**
//     * Tests the Nantes graph.
//     *
//     * @throws FileNotFoundException
//     */
//    @Test
//    public void testNantes() throws FileNotFoundException {
//        printTitle("Nantes");
//        doAnalysis(Graphs.graphNantesUnweightedBidirectional());
//    }
    /**
     * Make sure the graph analysis results from a weighted analysis (with all
     * weights one) and an unweighted analysis are equal.
     *
     * @param graph             The graph.
     * @param weightedResults   The weighted results.
     * @param unweightedResults The unweighted results.
     */
    private void checkResults(Graph graph,
                              Map<Integer, WeightedNodeBetweennessInfo> weightedResults,
                              Map<Integer, UnweightedNodeBetweennessInfo> unweightedResults) {
        for (TIntIterator it = GraphAnalyzer.nodeSet(graph).iterator();
                it.hasNext();) {
            int next = it.next();
            assertEquals(weightedResults.get(next).getBetweenness(),
                         unweightedResults.get(next).getBetweenness(), TOLERANCE);
            assertEquals(weightedResults.get(next).getCloseness(),
                         unweightedResults.get(next).getCloseness(), TOLERANCE);
        }
    }

    /**
     * Do weighted analysis (with all weights one) and unweighted analysis on
     * the given graph.
     *
     * @param graph        The graph.
     * @param verbose      Print verbose output?
     * @param printResults Print results?
     */
    @Override
    protected Map<Integer, WeightedNodeBetweennessInfo> doAnalysis(
            Graph graph,
            boolean printResults) {

        // Do Unweighted analysis.
        UnweightedBidirectionalGraphAnalyzerTest unweightedTest =
                new UnweightedBidirectionalGraphAnalyzerTest();
        Map<Integer, UnweightedNodeBetweennessInfo> unweightedResults =
                printResults
                ? unweightedTest.doAnalysisPrintResults(graph)
                : unweightedTest.doAnalysis(graph);

        // Do weighted analysis.
        WeightedBidirectionalGraphAnalyzerTest weightedTest =
                new WeightedBidirectionalGraphAnalyzerTest();
        Map<Integer, WeightedNodeBetweennessInfo> weightedResults =
                printResults
                ? weightedTest.doAnalysisPrintResults(graph)
                : weightedTest.doAnalysis(graph);

        // Check the results.
        checkResults(graph, weightedResults, unweightedResults);

        // If the above test failed, then the weighted and unweighted
        // results are exactly the same, so chose one set of results
        // and return it.
        return weightedResults;
    }
}
