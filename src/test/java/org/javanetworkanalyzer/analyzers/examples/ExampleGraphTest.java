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
import org.javanetworkanalyzer.model.*;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Tests graph analyzers on a simple example graph.
 *
 * @author Adam Gouge
 */
//    0.3    0.9
//   5--->2------->4
//   ^    ^        ^
//    \   |        |
// 1.0 \  |1.2     |1.3
//      \ |        |
//       \|        |
//        1------->3
//           0.8
public class ExampleGraphTest extends ManuallyCreatedGraphAnalyzerTest {

    private final static String EXAMPLE_GRAPH = "Example graph";
    private static final boolean PRINT_RESULTS = false;
    private static final boolean CHECK_RESULTS = true;
    private static final int NUMBER_OF_NODES = 5;
    private static final double N_MINUS_ONE = NUMBER_OF_NODES - 1;

    @Override
    protected void addEdges(
            KeyedGraph<? extends VCent, EdgeCent> graph) {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 5);
        graph.addEdge(5, 2);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
    }

    @Override
    protected void addWeightedEdges(
            WeightedKeyedGraph<? extends VCent, EdgeCent> graph) {
        graph.addEdge(1, 2).setWeight(1.2);
        graph.addEdge(1, 3).setWeight(0.8);
        graph.addEdge(1, 5).setWeight(1.0);
        graph.addEdge(5, 2).setWeight(0.3);
        graph.addEdge(2, 4).setWeight(0.9);
        graph.addEdge(3, 4).setWeight(1.3);
    }

    @Test
    public void unweightedDirectedTest()
            throws FileNotFoundException, NoSuchMethodException {
        DirectedG<VUCent, EdgeCent> graph =
                super.unweightedDirectedAnalysis();
        if (CHECK_RESULTS) {
            // Max = 1.5, min = 0
            checkBetweenness(new double[]{
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0.5 + 0 + 0 + 1) / 1.5,
                (double) (0.5 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5}, graph);
            checkCloseness(new double[]{
                (double) (NUMBER_OF_NODES - 1) / (1 + 1 + 2 + 1),
                0.0, 0.0, 0.0, 0.0
            }, graph);
        }
    }

    @Test
    public void unweightedReversedTest()
            throws FileNotFoundException, NoSuchMethodException {
        DirectedG<VUCent, EdgeCent> graph =
                super.unweightedReversedAnalysis();
        if (CHECK_RESULTS) {
            // Max = 1.5, min = 0
            checkBetweenness(new double[]{
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 1.5 + 0) / 1.5,
                (double) (0.5 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5}, graph);
            checkCloseness(
                    new double[]{
                0.0, 0.0, 0.0,
                (double) (NUMBER_OF_NODES - 1) / (2 + 1 + 1 + 2),
                0.0
            }, graph);
        }
    }

    @Test
    public void unweightedUndirectedTest() {
        UndirectedG<VUCent, EdgeCent> graph =
                super.unweightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            // Max = 3, min = 0
            checkBetweenness(new double[]{
                (double) (0.5 + 1.5 + 0 + 1) / 3,
                (double) (0.5 + 0 + 1.5 + 1) / 3,
                (double) (0.5 + 0 + 0.5 + 0) / 3,
                (double) (0 + 0.5 + 0.5 + 0) / 3,
                (double) (0 + 0 + 0 + 0) / 3}, graph);
            checkCloseness(new double[]{
                (double) (NUMBER_OF_NODES - 1) / (1 + 1 + 2 + 1),
                (double) (NUMBER_OF_NODES - 1) / (1 + 2 + 1 + 1),
                (double) (NUMBER_OF_NODES - 1) / (1 + 2 + 1 + 2),
                (double) (NUMBER_OF_NODES - 1) / (2 + 1 + 1 + 2),
                (double) (NUMBER_OF_NODES - 1) / (1 + 1 + 2 + 2)
            }, graph);
        }
    }

    @Test
    public void weightedDirectedTest() {
        DirectedG<VWCent, EdgeCent> graph =
                super.weightedDirectedAnalysis();
        if (CHECK_RESULTS) {
            // Max = 1.5, min = 0
            checkBetweenness(new double[]{
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0.5 + 0 + 0 + 1) / 1.5,
                (double) (0.5 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5}, graph);
            checkCloseness(new double[]{
                (double) (NUMBER_OF_NODES - 1) / (1.2 + 0.8 + 2.1 + 1),
                0.0, 0.0, 0.0, 0.0
            }, graph);
        }
    }

    @Test
    public void weightedReversedTest() {
        DirectedG<VWCent, EdgeCent> graph =
                super.weightedReversedAnalysis();
        if (CHECK_RESULTS) {
            // Max = 1.5, min = 0
            checkBetweenness(new double[]{
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 1.5 + 0) / 1.5,
                (double) (0 + 0 + 0.5 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5,
                (double) (0 + 0 + 0 + 0) / 1.5}, graph);
            checkCloseness(new double[]{
                0.0, 0.0, 0.0,
                (double) (NUMBER_OF_NODES - 1) / (2.1 + 0.9 + 1.3 + 1.2),
                0.0
            }, graph);
        }
    }

    @Test
    public void weightedUndirectedTest() {
        UndirectedG<VWCent, EdgeCent> graph =
                super.weightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            // Max = 4, min = 0
            assertEquals((1.0 + 2 + 0 + 1) / N_MINUS_ONE, graph.getVertex(1).getBetweenness(), TOLERANCE);
            assertEquals((0.5 + 0 + 1.5 + 1) / N_MINUS_ONE, graph.getVertex(2).getBetweenness(), TOLERANCE);
            assertEquals((0.5 + 0 + 0.5 + 0) / N_MINUS_ONE, graph.getVertex(3).getBetweenness(), TOLERANCE);
            assertEquals((0.0 + 0 + 0 + 0) / N_MINUS_ONE, graph.getVertex(4).getBetweenness(), TOLERANCE);
            assertEquals((0.0 + 0 + 0 + 0) / N_MINUS_ONE, graph.getVertex(5).getBetweenness(), TOLERANCE);

            assertEquals(N_MINUS_ONE / (1.2 + 0.8 + 2.1 + 1.0), graph.getVertex(1).getCloseness(), TOLERANCE);
            assertEquals(N_MINUS_ONE / (1.2 + 2.0 + 0.9 + 0.3), graph.getVertex(2).getCloseness(), TOLERANCE);
            assertEquals(N_MINUS_ONE / (0.8 + 2.0 + 1.3 + 1.8), graph.getVertex(3).getCloseness(), TOLERANCE);
            assertEquals(N_MINUS_ONE / (2.1 + 0.9 + 1.3 + 1.2), graph.getVertex(4).getCloseness(), TOLERANCE);
            assertEquals(N_MINUS_ONE / (1.0 + 0.3 + 1.8 + 1.2), graph.getVertex(5).getCloseness(), TOLERANCE);
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
    public String getName() {
        return EXAMPLE_GRAPH;
    }

    @Override
    protected int getNumberOfNodes() {
        return NUMBER_OF_NODES;
    }
}
