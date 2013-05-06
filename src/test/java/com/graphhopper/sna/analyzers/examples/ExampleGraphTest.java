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
import java.io.FileNotFoundException;
import org.junit.Test;

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

    @Override
    protected void addEdges(
            KeyedGraph<? extends NodeBetweennessInfo, Edge> graph) {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 5);
        graph.addEdge(5, 2);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
    }

    @Override
    protected void addWeightedEdges(
            WeightedKeyedGraph<? extends NodeBetweennessInfo, Edge> graph) {
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
        DirectedG<UnweightedNodeBetweennessInfo, Edge> graph =
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
        DirectedG<UnweightedNodeBetweennessInfo, Edge> graph =
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
        UndirectedG<UnweightedNodeBetweennessInfo, Edge> graph =
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
        DirectedG<WeightedNodeBetweennessInfo, Edge> graph =
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
        DirectedG<WeightedNodeBetweennessInfo, Edge> graph =
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
        UndirectedG<WeightedNodeBetweennessInfo, Edge> graph =
                super.weightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            // Max = 4, min = 0
            checkBetweenness(new double[]{
                (double) (1 + 2 + 0 + 1) / 4,
                (double) (0.5 + 0 + 1.5 + 1) / 4,
                (double) (0.5 + 0 + 0.5 + 0) / 4,
                (double) (0 + 1 + 0 + 0) / 4,
                (double) (0 + 0 + 0 + 0) / 4}, graph);
            checkCloseness(new double[]{
                (double) (NUMBER_OF_NODES - 1) / (1.2 + 0.8 + 2.1 + 1.0),
                (double) (NUMBER_OF_NODES - 1) / (1.2 + 2.0 + 0.9 + 0.3),
                (double) (NUMBER_OF_NODES - 1) / (0.8 + 2.0 + 1.3 + 1.8),
                (double) (NUMBER_OF_NODES - 1) / (2.1 + 0.9 + 1.3 + 1.2),
                (double) (NUMBER_OF_NODES - 1) / (1.0 + 0.3 + 1.8 + 1.2)
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
        return EXAMPLE_GRAPH;
    }

    @Override
    protected int getNumberOfNodes() {
        return NUMBER_OF_NODES;
    }
}
