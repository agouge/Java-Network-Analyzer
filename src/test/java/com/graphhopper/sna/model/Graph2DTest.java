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
package com.graphhopper.sna.model;

import java.io.FileNotFoundException;
import org.jgrapht.Graph;
import org.junit.Test;

/**
 * Tests loading the 2D graph under all possible configurations (weighted or
 * unweighted; directed, reversed or undirected).
 *
 * @author Adam Gouge
 */
public class Graph2DTest {

    private static final String FILENAME = "./files/graph2D.edges.csv";
    private static final String WEIGHT = "length";

    @Test
    public void unweightedDirected() throws FileNotFoundException {
        System.out.println("\n***** 2D Unweighted Directed *****");
        Graph graph = load2DGraph(false, GraphCreator.DIRECTED);
    }

    @Test
    public void unweightedReversed() throws FileNotFoundException {
        System.out.println("***** 2D Unweighted Reversed *****");
        Graph graph = load2DGraph(false, GraphCreator.REVERSED);
    }

    @Test
    public void unweightedUndirected() throws FileNotFoundException {
        System.out.println("***** 2D Unweighted Undirected *****");
        Graph graph = load2DGraph(false, GraphCreator.UNDIRECTED);
    }

    @Test
    public void weightedDirected() throws FileNotFoundException {
        System.out.println("***** 2D Weighted Directed *****");
        Graph graph = load2DGraph(true, GraphCreator.DIRECTED);
    }

    @Test
    public void weightedReversed() throws FileNotFoundException {
        System.out.println("***** 2D Weighted Reversed *****");
        Graph graph = load2DGraph(true, GraphCreator.REVERSED);
    }

    @Test
    public void weightedUndirected() throws FileNotFoundException {
        System.out.println("***** 2D Weighted Undirected *****");
        Graph graph = load2DGraph(true, GraphCreator.UNDIRECTED);
    }

    /**
     * Loads the 2D graph according to whether it is to be considered weighted
     * and according to the given orientation.
     *
     * @param weighted    {@code true} iff the graph is to be considered
     *                    weighted.
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    private Graph load2DGraph(boolean weighted,
                              int orientation) throws FileNotFoundException {
        if (weighted) {
            return new WeightedGraphCreator(
                    FILENAME,
                    WEIGHT,
                    orientation,
                    Edge.class).loadGraph();
        } else {
            return new UnweightedGraphCreator(
                    FILENAME,
                    orientation,
                    Edge.class).loadGraph();
        }
    }
//    /**
//     * Prints all edges of the graph.
//     *
//     * @param graph The graph.
//     */
//    private void printEdges(Graph<Integer, Edge> graph) {
//        Set<Edge> edgeSet = graph.edgeSet();
//        Iterator<Edge> iterator = edgeSet.iterator();
//        while (iterator.hasNext()) {
//            Edge edge = iterator.next();
//            String edgeString = graph.getEdgeSource(edge).toString() + " ";
//            if (graph instanceof UndirectedGraph) {
//                edgeString += "<";
//            }
//            edgeString += "--> " + graph.getEdgeTarget(edge)
//                    + " (" + graph.getEdgeWeight(edge) + ")";
//            System.out.println(edgeString);
//        }
//        System.out.println("");
//    }
}