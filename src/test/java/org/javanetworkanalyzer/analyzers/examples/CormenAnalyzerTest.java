/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is distributed under the GPL 3 license. It is produced
 * by the "Atelier SIG" team of the <a href="http://www.irstv.fr">IRSTV
 * Institute</a>, CNRS FR 2488.
 *
 * Copyright 2013 IRSTV (CNRS FR 2488).
 *
 * Java Network Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Java Network Analyzer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Java Network Analyzer. If not, see <http://www.gnu.org/licenses/>.
 */
package org.javanetworkanalyzer.analyzers.examples;

import org.javanetworkanalyzer.analyzers.ManuallyCreatedGraphAnalyzerTest;
import org.javanetworkanalyzer.analyzers.WeightedGraphAnalyzer;
import org.javanetworkanalyzer.data.VCent;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.graphcreators.GraphCreator;
import org.javanetworkanalyzer.graphcreators.WeightedGraphCreator;
import org.javanetworkanalyzer.model.*;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests graph analyzers on the example graph in Figure 24.6 of Introduction to
 * Algorithms (Cormen), 3rd Edition.
 *
 * @author Adam Gouge
 */
//                   1
//           >2 ------------>3
//          / |^           ->|^
//       10/ / |      9   / / |
//        / 2| |3    -----  | |
//       /   | |    /      4| |6
//      1<---------------   | |
//       \   | |  /     7\  | |
//       5\  | / /        \ | /
//         \ v| /    2     \v|
//          > 4 -----------> 5
//
public class CormenAnalyzerTest extends ManuallyCreatedGraphAnalyzerTest {

    private final static String CORMEN_GRAPH = "Cormen graph";
    private static final boolean PRINT_RESULTS = false;
    private static final boolean CHECK_RESULTS = true;
    private static final int NUMBER_OF_NODES = 5;

    private EdgeCent e1;
    private EdgeCent e2;
    private EdgeCent e3;
    private EdgeCent e4;
    private EdgeCent e5;
    private EdgeCent e6;
    private EdgeCent e7;
    private EdgeCent e8;
    private EdgeCent e9;
    private EdgeCent e10;

    @Override
    protected void addEdges(
            KeyedGraph<? extends VCent, EdgeCent> graph) {
        e1 = graph.addEdge(1, 2);
        e2 = graph.addEdge(1, 4);
        e3 = graph.addEdge(5, 1);
        e4 = graph.addEdge(2, 4);
        e5 = graph.addEdge(4, 2);
        e6 = graph.addEdge(2, 3);
        e7 = graph.addEdge(4, 3);
        e8 = graph.addEdge(3, 5);
        e9 = graph.addEdge(5, 3);
        e10 = graph.addEdge(4, 5);
    }

    @Override
    protected void addWeightedEdges(
            WeightedKeyedGraph<? extends VCent, EdgeCent> graph) {
        e1 = graph.addEdge(1, 2).setWeight(10);
        e2 = graph.addEdge(1, 4).setWeight(5);
        e3 = graph.addEdge(5, 1).setWeight(7);
        e4 = graph.addEdge(2, 4).setWeight(2);
        e5 = graph.addEdge(4, 2).setWeight(3);
        e6 = graph.addEdge(2, 3).setWeight(1);
        e7 = graph.addEdge(4, 3).setWeight(9);
        e8 = graph.addEdge(3, 5).setWeight(4);
        e9 = graph.addEdge(5, 3).setWeight(6);
        e10 = graph.addEdge(4, 5).setWeight(2);
    }

    @Test
    public void unweightedDirectedTest() {
        DirectedG<VUCent, EdgeCent> graph =
                super.unweightedDirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.77777777778,
                0.00000000000,
                0.11111111111,
                0.44444444444,
                1.00000000000}, graph);
            checkCloseness(new double[]{
                0.66666666667,
                0.57142857143,
                0.44444444444,
                0.80000000000,
                0.66666666667
            }, graph);
        }
        assertEquals(4, e1.getBetweenness(), TOLERANCE);
        assertEquals(5, e2.getBetweenness(), TOLERANCE);
        assertEquals(8, e3.getBetweenness(), TOLERANCE);
        assertEquals(2, e4.getBetweenness(), TOLERANCE);
        assertEquals(1, e5.getBetweenness(), TOLERANCE);
        assertEquals(3, e6.getBetweenness(), TOLERANCE);
        assertEquals(2, e7.getBetweenness(), TOLERANCE);
        assertEquals(5, e8.getBetweenness(), TOLERANCE);
        assertEquals(1, e9.getBetweenness(), TOLERANCE);
        assertEquals(4, e10.getBetweenness(), TOLERANCE);
    }

    @Test
    public void unweightedReversedTest() {
        DirectedG<VUCent, EdgeCent> graph =
                super.unweightedReversedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.77777777778,
                0.00000000000,
                0.11111111111,
                0.44444444444,
                1.00000000000
            }, graph);
            checkCloseness(new double[]{
                0.50000000000,
                0.57142857143,
                0.80000000000,
                0.57142857143,
                0.66666666667
            }, graph);
        }
        assertEquals(4, e1.getBetweenness(), TOLERANCE);
        assertEquals(5, e2.getBetweenness(), TOLERANCE);
        assertEquals(9, e3.getBetweenness(), TOLERANCE);
        assertEquals(3, e4.getBetweenness(), TOLERANCE);
        assertEquals(1, e5.getBetweenness(), TOLERANCE);
        assertEquals(4, e6.getBetweenness(), TOLERANCE);
        assertEquals(2, e7.getBetweenness(), TOLERANCE);
        assertEquals(6, e8.getBetweenness(), TOLERANCE);
        assertEquals(1, e9.getBetweenness(), TOLERANCE);
        assertEquals(5, e10.getBetweenness(), TOLERANCE);
    }

    @Test
    public void unweightedUndirectedTest() {
        UndirectedG<VUCent, EdgeCent> graph =
                super.unweightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.00000000000,
                0.14285714286,
                0.28571428571,
                1.00000000000,
                0.50000000000
            }, graph);
            checkCloseness(new double[]{
                0.80000000000,
                0.80000000000,
                0.80000000000,
                1.00000000000,
                0.80000000000
            }, graph);
        }
        assertEquals(6, e1.getBetweenness(), TOLERANCE);
        assertEquals(4, e2.getBetweenness(), TOLERANCE);
        assertEquals(6, e3.getBetweenness(), TOLERANCE);
        assertEquals(3, e4.getBetweenness(), TOLERANCE);
        assertEquals(0, e5.getBetweenness(), TOLERANCE);
        assertEquals(6, e6.getBetweenness(), TOLERANCE);
        assertEquals(4, e7.getBetweenness(), TOLERANCE);
        assertEquals(4, e8.getBetweenness(), TOLERANCE);
        assertEquals(0, e9.getBetweenness(), TOLERANCE);
        assertEquals(4, e10.getBetweenness(), TOLERANCE);
    }

    @Test
    public void weightedDirectedTest() throws Exception {
        DirectedG<VWCent, EdgeCent> graph =
                super.weightedDirectedAnalysis();

        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                4.0 / 7,
                2.0 / 7,
                0.0,
                1.0,
                5.0 / 7
            }, graph);
            checkCloseness(new double[]{
                0.13793103448,
                0.22222222222,
                0.08000000000,
                0.22222222222,
                0.10000000000
            }, graph);
        }
        assertEquals(0, e1.getBetweenness(), TOLERANCE);
        assertEquals(8, e2.getBetweenness(), TOLERANCE);
        assertEquals(8, e3.getBetweenness(), TOLERANCE);
        assertEquals(3, e4.getBetweenness(), TOLERANCE);
        assertEquals(6, e5.getBetweenness(), TOLERANCE);
        assertEquals(3, e6.getBetweenness(), TOLERANCE);
        assertEquals(0, e7.getBetweenness(), TOLERANCE);
        assertEquals(4, e8.getBetweenness(), TOLERANCE);
        assertEquals(1, e9.getBetweenness(), TOLERANCE);
        assertEquals(5, e10.getBetweenness(), TOLERANCE);
    }

    @Test
    public void weightedReversedTest() {
        DirectedG<VWCent, EdgeCent> graph =
                super.weightedReversedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                4.0 / 7,
                2.0 / 7,
                0.0,
                1.0,
                5.0 / 7
            }, graph);
            checkCloseness(new double[]{
                0.10526315789,
                0.08888888889,
                0.20000000000,
                0.11428571429,
                0.23529411765
            }, graph);
        }
        // Note: These happen to be the same results as in weightedDirectedTest
        // but this is not always true.
        assertEquals(0, e1.getBetweenness(), TOLERANCE);
        assertEquals(8, e2.getBetweenness(), TOLERANCE);
        assertEquals(8, e3.getBetweenness(), TOLERANCE);
        assertEquals(3, e4.getBetweenness(), TOLERANCE);
        assertEquals(6, e5.getBetweenness(), TOLERANCE);
        assertEquals(3, e6.getBetweenness(), TOLERANCE);
        assertEquals(0, e7.getBetweenness(), TOLERANCE);
        assertEquals(4, e8.getBetweenness(), TOLERANCE);
        assertEquals(1, e9.getBetweenness(), TOLERANCE);
        assertEquals(5, e10.getBetweenness(), TOLERANCE);
    }

    @Test
    public void weightedUndirectedTest() {
        UndirectedG<VWCent, EdgeCent> graph =
                super.weightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.0,
                4.0 / 7,
                0.0,
                1.0,
                0.0
            }, graph);
            checkCloseness(new double[]{
                0.14814814815,
                0.28571428571,
                0.25000000000,
                0.33333333333,
                0.23529411765
            }, graph);
        }
        assertEquals(0, e1.getBetweenness(), TOLERANCE);
        assertEquals(8, e2.getBetweenness(), TOLERANCE);
        assertEquals(2, e3.getBetweenness(), TOLERANCE);
        assertEquals(10, e4.getBetweenness(), TOLERANCE);
        assertEquals(0, e5.getBetweenness(), TOLERANCE);
        assertEquals(6, e6.getBetweenness(), TOLERANCE);
        assertEquals(0, e7.getBetweenness(), TOLERANCE);
        assertEquals(2, e8.getBetweenness(), TOLERANCE);
        assertEquals(0, e9.getBetweenness(), TOLERANCE);
        assertEquals(6, e10.getBetweenness(), TOLERANCE);
    }

    @Override
    protected ProgressMonitor progressMonitor() {
        return new NullProgressMonitor();
    }

    @Override
    protected boolean printsResults() {
        return PRINT_RESULTS;
    }

    @Override
    public String getName() {
        return CORMEN_GRAPH;
    }

    @Override
    protected int getNumberOfNodes() {
        return NUMBER_OF_NODES;
    }
}
