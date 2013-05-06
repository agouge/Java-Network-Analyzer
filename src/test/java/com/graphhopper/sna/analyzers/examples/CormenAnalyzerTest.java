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

import com.graphhopper.sna.analyzers.ManuallyCreatedGraphAnalyzerTest;
import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.data.UnweightedNodeBetweennessInfo;
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.sna.model.DirectedG;
import com.graphhopper.sna.model.Edge;
import com.graphhopper.sna.model.KeyedGraph;
import com.graphhopper.sna.model.UndirectedG;
import com.graphhopper.sna.model.WeightedKeyedGraph;
import com.graphhopper.sna.progress.NullProgressMonitor;
import com.graphhopper.sna.progress.ProgressMonitor;
import org.junit.Test;

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
//         \ v| /          \v|
//          > 4 -----------> 5
//
public class CormenAnalyzerTest extends ManuallyCreatedGraphAnalyzerTest {

    private final static String CORMEN_GRAPH = "Cormen graph";
    private static final boolean PRINT_RESULTS = true;
    private static final int numberOfNodes = 5;
    private static final boolean CHECK_RESULTS = true;
    private static final int NUMBER_OF_NODES = 5;

    @Override
    protected void addEdges(
            KeyedGraph<? extends NodeBetweennessInfo, Edge> graph) {
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(5, 1);
        graph.addEdge(2, 4);
        graph.addEdge(4, 2);
        graph.addEdge(3, 5);
        graph.addEdge(2, 3);
        graph.addEdge(4, 3);
        graph.addEdge(5, 3);
        graph.addEdge(4, 5);
    }

    @Override
    protected void addWeightedEdges(
            WeightedKeyedGraph<? extends NodeBetweennessInfo, Edge> graph) {
        graph.addEdge(1, 2).setWeight(10);
        graph.addEdge(1, 4).setWeight(5);
        graph.addEdge(5, 1).setWeight(7);
        graph.addEdge(2, 4).setWeight(2);
        graph.addEdge(4, 2).setWeight(3);
        graph.addEdge(3, 5).setWeight(4);
        graph.addEdge(2, 3).setWeight(1);
        graph.addEdge(4, 3).setWeight(9);
        graph.addEdge(5, 3).setWeight(6);
        graph.addEdge(4, 5).setWeight(2);
    }

    @Test
    public void unweightedDirectedTest() {
        DirectedG<UnweightedNodeBetweennessInfo, Edge> graph =
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
    }

    @Test
    public void unweightedReversedTest() {
        DirectedG<UnweightedNodeBetweennessInfo, Edge> graph =
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
    }

    @Test
    public void unweightedUndirectedTest() {
        UndirectedG<UnweightedNodeBetweennessInfo, Edge> graph =
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
    }

    @Test
    public void weightedDirectedTest() {
        DirectedG<WeightedNodeBetweennessInfo, Edge> graph =
                super.weightedDirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.57142857143,
                0.00000000000,
                0.00000000000,
                1.00000000000,
                0.85714285714
            }, graph);
            checkCloseness(new double[]{
                0.13793103448,
                0.22222222222,
                0.08000000000,
                0.22222222222,
                0.10000000000
            }, graph);
        }
    }

    @Test
    public void weightedReversedTest() {
        DirectedG<WeightedNodeBetweennessInfo, Edge> graph =
                super.weightedReversedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.57142857143,
                0.42857142857,
                0.00000000000,
                1.00000000000,
                0.71428571429
            }, graph);
            checkCloseness(new double[]{
                0.10526315789,
                0.08888888889,
                0.20000000000,
                0.11428571429,
                0.23529411765
            }, graph);
        }
    }

    @Test
    public void weightedUndirectedTest() {
        UndirectedG<WeightedNodeBetweennessInfo, Edge> graph =
                super.weightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.00000000000,
                0.62500000000,
                0.12500000000,
                1.00000000000,
                0.00000000000
            }, graph);
            checkCloseness(new double[]{
                0.14814814815,
                0.28571428571,
                0.25000000000,
                0.33333333333,
                0.23529411765
            }, graph);
        }
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
    protected String getName() {
        return CORMEN_GRAPH;
    }

    @Override
    protected int getNumberOfNodes() {
        return numberOfNodes;
    }
}
