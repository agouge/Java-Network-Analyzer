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
