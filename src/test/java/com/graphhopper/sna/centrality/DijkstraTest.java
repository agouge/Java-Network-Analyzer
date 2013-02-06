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
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.storage.Graph;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests {@link Dijkstra}.
 *
 * @author Adam Gouge
 */
public class DijkstraTest extends CentralityTest {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName() {
        return Graphs.DIJKSTRA;
    }

    /**
     * Calculates the distance and number of shortest paths from the given
     * source node to all the other nodes.
     *
     * @param graph           The graph.
     * @param nodeBetweenness Hash map holding distance and shortest path info.
     * @param source          Source node.
     *
     * @return The hash map.
     */
    protected HashMap<Integer, NodeBetweennessInfo> dijkstra(
            Graph graph,
            HashMap<Integer, NodeBetweennessInfo> nodeBetweenness,
            int source) {

        long start = System.currentTimeMillis();
        Dijkstra algorithm = new Dijkstra(graph, nodeBetweenness, source);
        algorithm.calculate();
        long stop = System.currentTimeMillis();
//        System.out.println("Dijkstra for " + startNode
//                + " in " + (stop - start) + " ms.");
        return nodeBetweenness;
    }

    /**
     * Initializes the hash map that will be used by the
     * {@link DijkstraTest#dijkstra(com.graphhopper.storage.Graph, java.util.HashMap, int) dijkstra}
     * method.
     *
     * @param graph   The graph.
     * @param nodeSet The node set of this graph (passed so that we don't have
     *                to regenerate it).
     *
     * @return The newly initialized hash map.
     */
    protected HashMap<Integer, NodeBetweennessInfo> initDijkstra(
            Graph graph,
            TIntHashSet nodeSet) {
        long start = System.currentTimeMillis();
        HashMap<Integer, NodeBetweennessInfo> nodeBetweenness =
                new HashMap<Integer, NodeBetweennessInfo>();
        nodeBetweenness.clear();
        TIntIterator it = nodeSet.iterator();
//        long start2 = System.currentTimeMillis();
//        long stop2;
//        int count = 0;
        while (it.hasNext()) {
//            count++;
            nodeBetweenness.put(it.next(), new WeightedNodeBetweennessInfo());
//            if ((count % 1000) == 0) {
//                stop2 = System.currentTimeMillis();
//                System.out.println((stop2 - start2)
//                        + " ms to initialize 1000 nodes");
//                start2 = stop2;
//            }
        }
        long stop = System.currentTimeMillis();
        System.out.println(TIME + (stop - start) + " ms - Initialization.");
        return nodeBetweenness;
    }

    /**
     * Resets the hash map used by the
     * {@link DijkstraTest#dijkstra(com.graphhopper.storage.Graph, java.util.HashMap, int) dijkstra}
     * method for use in the next iteration.
     *
     * @param nodeBetweenness The hash map.
     * @param nodeSet         The node set of this graph (passed so that we
     *                        don't have to regenerate it).
     */
    protected void resetHashMap(
            HashMap<Integer, NodeBetweennessInfo> nodeBetweenness,
            TIntHashSet nodeSet) {
        TIntIterator it = nodeSet.iterator();
        while (it.hasNext()) {
            nodeBetweenness.get(it.next()).reset();
        }
    }

    /**
     * Tests Dijkstra on the given graph from the given source.
     *
     * @param testName The name of this test.
     * @param graph    The graph.
     * @param source   The source node.
     */
    private HashMap<Integer, NodeBetweennessInfo> testDijkstra(
            String testName,
            Graph graph,
            int source) {
        System.out.println("***** DIJKSTRA: "
                + testName + ". Source: "
                + source + " *****");
        long start = System.currentTimeMillis();
        TIntHashSet nodeSet = GraphAnalyzer.nodeSet(graph);
        HashMap<Integer, NodeBetweennessInfo> nodeBetweenness =
                initDijkstra(graph, nodeSet);
        dijkstra(graph, nodeBetweenness, source);
        long stop = System.currentTimeMillis();
        System.out.println(TIME
                + (stop - start) + " ms - Dijkstra: "
                + testName + ". Source: " + source);
        System.out.println("");
        return nodeBetweenness;
    }

    /**
     * Tests Dijkstra on the graph prepared in
     * {@link GraphSetupTest#exampleGraph1()} from source node 1.
     */
    @Test
    public void testDijkstra1() {
        HashMap<Integer, NodeBetweennessInfo> nodeBetweenness =
                testDijkstra(Graphs.CORMEN_GRAPH,
                             Graphs.graphCormenWeightedDirected(),
                             1);
        // Check distances.
        assertEquals(nodeBetweenness.get(1).getDistance(), 0.0, TOLERANCE);
        assertEquals(nodeBetweenness.get(4).getDistance(), 5.0, TOLERANCE);
        assertEquals(nodeBetweenness.get(5).getDistance(), 7.0, TOLERANCE);
        assertEquals(nodeBetweenness.get(2).getDistance(), 8.0, TOLERANCE);
        assertEquals(nodeBetweenness.get(3).getDistance(), 9.0, TOLERANCE);
        // Check shortest path counts.
        assertEquals(nodeBetweenness.get(1).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(4).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(5).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(2).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(3).getSPCount(), 1);
        // Check predecessors.
        assertTrue(nodeBetweenness.get(1).getPredecessors().isEmpty());
        assertEquals(nodeBetweenness.get(4).getPredecessors(),
                     new TIntHashSet(new int[]{1}));
        assertEquals(nodeBetweenness.get(5).getPredecessors(),
                     new TIntHashSet(new int[]{4}));
        assertEquals(nodeBetweenness.get(2).getPredecessors(),
                     new TIntHashSet(new int[]{4, 1}));
        assertEquals(nodeBetweenness.get(3).getPredecessors(),
                     new TIntHashSet(new int[]{2, 4, 5}));
    }

    /**
     * Tests Dijkstra on the graph prepared in
     * {@link GraphSetupTest#exampleGraph2()} from source node 1.
     */
    @Test
    public void testDijkstra2() {
        HashMap<Integer, NodeBetweennessInfo> nodeBetweenness =
                testDijkstra(Graphs.EXAMPLE_GRAPH_2,
                             Graphs.weightedDirected(),
                             1);
        // Check distances.
        assertEquals(nodeBetweenness.get(1).getDistance(), 0.0, TOLERANCE);
        assertEquals(nodeBetweenness.get(3).getDistance(), 0.8, TOLERANCE);
        assertEquals(nodeBetweenness.get(5).getDistance(), 1.0, TOLERANCE);
        assertEquals(nodeBetweenness.get(2).getDistance(), 1.2, TOLERANCE);
        assertEquals(nodeBetweenness.get(4).getDistance(), 2.1, TOLERANCE);
        // Check shortest path counts.
        assertEquals(nodeBetweenness.get(1).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(3).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(5).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(2).getSPCount(), 1);
        assertEquals(nodeBetweenness.get(4).getSPCount(), 2);
        // Check predecessors.
        assertTrue(nodeBetweenness.get(1).getPredecessors().isEmpty());
        assertEquals(nodeBetweenness.get(3).getPredecessors(),
                     new TIntHashSet(new int[]{1}));
        assertEquals(nodeBetweenness.get(5).getPredecessors(),
                     new TIntHashSet(new int[]{1}));
        assertEquals(nodeBetweenness.get(2).getPredecessors(),
                     new TIntHashSet(new int[]{1}));
        assertEquals(nodeBetweenness.get(4).getPredecessors(),
                     new TIntHashSet(new int[]{2, 3}));
    }
}
