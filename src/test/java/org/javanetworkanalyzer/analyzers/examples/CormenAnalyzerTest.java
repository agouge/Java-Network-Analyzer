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
import org.javanetworkanalyzer.data.VCent;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.model.DirectedG;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.UndirectedG;
import org.javanetworkanalyzer.model.WeightedKeyedGraph;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
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
    private static final boolean PRINT_RESULTS = false;
    private static final int numberOfNodes = 5;
    private static final boolean CHECK_RESULTS = true;
    private static final int NUMBER_OF_NODES = 5;

    @Override
    protected void addEdges(
            KeyedGraph<? extends VCent, Edge> graph) {
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
            WeightedKeyedGraph<? extends VCent, Edge> graph) {
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
        DirectedG<VUCent, Edge> graph =
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
        DirectedG<VUCent, Edge> graph =
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
        UndirectedG<VUCent, Edge> graph =
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
        DirectedG<VWCent, Edge> graph =
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
        DirectedG<VWCent, Edge> graph =
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
        UndirectedG<VWCent, Edge> graph =
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
