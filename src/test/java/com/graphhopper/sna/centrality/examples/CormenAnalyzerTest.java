/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality.examples;

import com.graphhopper.sna.centrality.ManuallyCreatedGraphAnalyzerTest;
import com.graphhopper.sna.model.Edge;
import com.graphhopper.sna.progress.NullProgressMonitor;
import com.graphhopper.sna.progress.ProgressMonitor;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addVertices(Graph<Integer, Edge> graph) {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addEdges(Graph<Integer, Edge> graph) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addWeightedEdges(WeightedGraph<Integer, Edge> graph) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected ProgressMonitor progressMonitor() {
        return new NullProgressMonitor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean printsResults() {
        return PRINT_RESULTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getName() {
        return CORMEN_GRAPH;
    }
}
