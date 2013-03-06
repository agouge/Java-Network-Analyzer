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
//      
public class ExampleGraphTest extends ManuallyCreatedGraphAnalyzerTest {

    private final static String EXAMPLE_GRAPH = "Example graph";
    private static final boolean PRINT_RESULTS = true;

    @Override
    protected void addVertices(Graph<Integer, Edge> graph) {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
    }

    @Override
    protected void addEdges(Graph<Integer, Edge> graph) {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 5);
        graph.addEdge(5, 2);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
    }

    @Override
    protected void addWeightedEdges(WeightedGraph<Integer, Edge> graph) {
        graph.addEdge(1, 2).setWeight(1.2);
        graph.addEdge(1, 3).setWeight(0.8);
        graph.addEdge(1, 5).setWeight(1.0);
        graph.addEdge(5, 2).setWeight(0.3);
        graph.addEdge(2, 4).setWeight(0.9);
        graph.addEdge(3, 4).setWeight(1.3);
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
        return EXAMPLE_GRAPH;
    }
}
