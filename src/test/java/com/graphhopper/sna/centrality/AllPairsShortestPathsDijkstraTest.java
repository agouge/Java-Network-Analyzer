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

import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.storage.Graph;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import java.util.HashMap;
import org.junit.Test;

/**
 * Tests using {@link Dijkstra} to solve the all pairs shortest paths problem.
 *
 * @author Adam Gouge
 */
public class AllPairsShortestPathsDijkstraTest extends DijkstraTest {

    /**
     * Calculates all pairs shortest paths on the given graph.
     *
     * @param testName The test name.
     * @param graph    The graph.
     */
    private void testAllPairsShortestPaths(
            String testName,
            Graph graph) {

        System.out.println("***** ALL PAIRS SHORTEST PATHS: "
                + testName + " *****");

        long start = System.currentTimeMillis();
        TIntHashSet nodeSet = GraphAnalyzer.nodeSet(graph);
        HashMap<Integer, WeightedNodeBetweennessInfo> nodeBetweenness =
                initDijkstra(graph, nodeSet);
        for (TIntIterator it = nodeSet.iterator();
                it.hasNext();) {
            int source = it.next();
//            System.out.println(
//                    "********************* "
//                    + source
//                    + " *********************");
            dijkstra(graph, source, nodeBetweenness);
            resetHashMap(nodeBetweenness, nodeSet);
        }
        long stop = System.currentTimeMillis();

        System.out.println(TIME
                + (stop - start) + " ms - All pairs shortest paths: "
                + testName);
        System.out.println("");
    }

    /**
     * Tests all pairs shortest paths on the graph prepared in
     * {@link GraphSetupTest#exampleGraph1()}.
     */
    @Test
    public void testAllPairsSP1() {
        testAllPairsShortestPaths(Graphs.CORMEN_GRAPH,
                                  Graphs.graphCormenWeightedDirected());
    }

    /**
     * Tests all pairs shortest paths on the graph prepared in
     * {@link GraphSetupTest#exampleGraph2()}.
     */
    @Test
    public void testAllPairsSP2() {
        testAllPairsShortestPaths(Graphs.EXAMPLE_GRAPH_2,
                                  Graphs.weightedDirected());
    }
}
