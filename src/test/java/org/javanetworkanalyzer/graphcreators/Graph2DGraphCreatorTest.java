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
package org.javanetworkanalyzer.graphcreators;

import org.javanetworkanalyzer.data.VBetw;
import org.javanetworkanalyzer.data.VUBetw;
import org.javanetworkanalyzer.data.VWBetw;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.UndirectedG;
import java.io.FileNotFoundException;
import org.junit.Test;

/**
 * Tests loading the 2D graph under all possible configurations (weighted or
 * unweighted; directed, reversed or undirected).
 *
 * @author Adam Gouge
 */
public class Graph2DGraphCreatorTest {

    private static final String FILENAME = "./files/graph2D.edges.csv";
    private static final String WEIGHT = "length";

    @Test
    public void unweightedDirected() throws FileNotFoundException,
            NoSuchMethodException {
        System.out.println("\n***** 2D Unweighted Directed *****");
        KeyedGraph<? extends VBetw, Edge> graph =
                load2DGraph(false, GraphCreator.DIRECTED);
//        printEdges(graph);
    }

    @Test
    public void unweightedReversed() throws FileNotFoundException,
            NoSuchMethodException {
        System.out.println("***** 2D Unweighted Reversed *****");
        KeyedGraph<? extends VBetw, Edge> graph =
                load2DGraph(false, GraphCreator.REVERSED);
//        printEdges(graph);
    }

    @Test
    public void unweightedUndirected() throws FileNotFoundException,
            NoSuchMethodException {
        System.out.println("***** 2D Unweighted Undirected *****");
        KeyedGraph<? extends VBetw, Edge> graph =
                load2DGraph(false, GraphCreator.UNDIRECTED);
//        printEdges(graph);
    }

    @Test
    public void weightedDirected() throws FileNotFoundException,
            NoSuchMethodException {
        System.out.println("***** 2D Weighted Directed *****");
        KeyedGraph<? extends VBetw, Edge> graph =
                load2DGraph(true, GraphCreator.DIRECTED);
//        printEdges(graph);
    }

    @Test
    public void weightedReversed() throws FileNotFoundException,
            NoSuchMethodException {
        System.out.println("***** 2D Weighted Reversed *****");
        KeyedGraph<? extends VBetw, Edge> graph =
                load2DGraph(true, GraphCreator.REVERSED);
//        printEdges(graph);
    }

    @Test
    public void weightedUndirected() throws FileNotFoundException,
            NoSuchMethodException {
        System.out.println("***** 2D Weighted Undirected *****");
        KeyedGraph<? extends VBetw, Edge> graph =
                load2DGraph(true, GraphCreator.UNDIRECTED);
//        printEdges(graph);
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
    private KeyedGraph<? extends VBetw, Edge> load2DGraph(
            boolean weighted,
            int orientation) throws FileNotFoundException,
            NoSuchMethodException {
        if (weighted) {
            return new WeightedGraphCreator<VWBetw, Edge>(
                    FILENAME,
                    orientation,
                    VWBetw.class,
                    Edge.class,
                    WEIGHT).loadGraph();
        } else {
            return new GraphCreator<VUBetw, Edge>(
                    FILENAME,
                    orientation,
                    VUBetw.class,
                    Edge.class).loadGraph();
        }
    }

    /**
     * Prints all edges of the graph.
     *
     * @param graph The graph.
     */
    private void printEdges(
            KeyedGraph<? extends VBetw, Edge> graph) {
        for (Edge edge : graph.edgeSet()) {
            String edgeString = graph.getEdgeSource(edge).getID() + " ";
            if (graph instanceof UndirectedG) {
                edgeString += "<";
            }
            edgeString += "--> " + graph.getEdgeTarget(edge).getID()
                    + " (" + graph.getEdgeWeight(edge) + ")";
            System.out.println(edgeString);
        }
        System.out.println("");
    }
}