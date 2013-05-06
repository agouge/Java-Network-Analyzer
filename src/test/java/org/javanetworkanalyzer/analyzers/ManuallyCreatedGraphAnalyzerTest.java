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
package org.javanetworkanalyzer.analyzers;

import org.javanetworkanalyzer.data.VBetw;
import org.javanetworkanalyzer.data.VUBetw;
import org.javanetworkanalyzer.data.VWBetw;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.graphcreators.GraphCreator;
import static org.javanetworkanalyzer.graphcreators.GraphCreator.UNDIRECTED;
import org.javanetworkanalyzer.model.DirectedG;
import org.javanetworkanalyzer.model.DirectedPseudoG;
import org.javanetworkanalyzer.model.DirectedWeightedPseudoG;
import org.javanetworkanalyzer.model.EdgeReversedG;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.PseudoG;
import org.javanetworkanalyzer.model.UndirectedG;
import org.javanetworkanalyzer.model.WeightedEdgeReversedG;
import org.javanetworkanalyzer.model.WeightedKeyedGraph;
import org.javanetworkanalyzer.model.WeightedPseudoG;

/**
 * Test helper for manually-entered graphs.
 *
 * @author Adam Gouge
 */
public abstract class ManuallyCreatedGraphAnalyzerTest extends GraphAnalyzerTest {

    private static final String ORIENTATION_ERROR = "Please enter a valid "
            + "orientation: 1, 2 or 3.";

    /**
     * Manually adds vertices to the graph.
     *
     * @param graph The graph.
     */
    protected void addVertices(
            KeyedGraph<? extends VBetw, Edge> graph) {
        for (int i = 1; i <= getNumberOfNodes(); i++) {
            graph.addVertex(i);
        }
    }

    /**
     * Manually adds edges to the graph.
     *
     * @param graph The graph.
     */
    protected abstract void addEdges(
            KeyedGraph<? extends VBetw, Edge> graph);

    /**
     * Manually adds weighted edges to the graph.
     *
     * @param graph The graph.
     */
    protected abstract void addWeightedEdges(
            WeightedKeyedGraph<? extends VBetw, Edge> graph);

    /**
     * Loads an unweighted graph with the given orientation.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     */
    @Override
    protected KeyedGraph<VUBetw, Edge> unweightedGraph(
            int orientation) {
        KeyedGraph<VUBetw, Edge> graph;
        if (orientation == GraphCreator.DIRECTED) {
            graph = unweightedDirectedGraph();
        } else if (orientation == GraphCreator.REVERSED) {
            graph = unweightedReversedGraph();
        } else if (orientation == GraphCreator.UNDIRECTED) {
            graph = unweightedUndirectedGraph();
        } else {
            throw new IllegalArgumentException(ORIENTATION_ERROR);
        }
        return graph;
    }

    /**
     * Loads a weighted graph with the given orientation.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     */
    @Override
    protected WeightedKeyedGraph<VWBetw, Edge> weightedGraph(
            int orientation) {
        KeyedGraph<VWBetw, Edge> graph;
        if (orientation == GraphCreator.DIRECTED) {
            graph = weightedDirectedGraph();
        } else if (orientation == GraphCreator.REVERSED) {
            graph = weightedReversedGraph();
        } else if (orientation == GraphCreator.UNDIRECTED) {
            graph = weightedUndirectedGraph();
        } else {
            throw new IllegalArgumentException(ORIENTATION_ERROR);
        }
        return (WeightedKeyedGraph) graph;
    }

    /**
     * Creates an unweighted directed graph.
     */
    private DirectedG<VUBetw, Edge> unweightedDirectedGraph() {
        KeyedGraph<? extends VBetw, Edge> graph = null;
        try {
            graph = initializeUnweightedGraph(GraphCreator.DIRECTED);
            addVertices(graph);
            addEdges(graph);
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates an unweighted edge reversed graph.
     */
    private DirectedG<VUBetw, Edge> unweightedReversedGraph() {
        EdgeReversedG<VUBetw, Edge> graph = null;
        try {
            graph = new EdgeReversedG<VUBetw, Edge>(
                    unweightedDirectedGraph());
        } catch (NoSuchMethodException ex) {
        }
        return graph;
    }

    /**
     * Creates an unweighted undirected graph.
     */
    private UndirectedG<VUBetw, Edge> unweightedUndirectedGraph() {
        KeyedGraph<? extends VBetw, Edge> graph = null;
        try {
            graph = initializeUnweightedGraph(GraphCreator.UNDIRECTED);
            addVertices(graph);
            addEdges(graph);
        } catch (NoSuchMethodException ex) {
        }
        return (UndirectedG) graph;
    }

    /**
     * Creates a weighted directed graph.
     */
    private DirectedG<VWBetw, Edge> weightedDirectedGraph() {
        WeightedKeyedGraph<? extends VBetw, Edge> graph = null;
        try {
            graph = initializeWeightedGraph(getWeightColumnName(),
                                            GraphCreator.DIRECTED);
            addVertices(graph);
            addWeightedEdges(graph);
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates a weighted edge reversed graph.
     */
    private DirectedG<VWBetw, Edge> weightedReversedGraph() {
        WeightedKeyedGraph<VWBetw, Edge> graph = null;
        try {
            graph = new WeightedEdgeReversedG<VWBetw, Edge>(
                    weightedDirectedGraph());
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates a weighted undirected graph.
     */
    private UndirectedG<VWBetw, Edge> weightedUndirectedGraph() {
        WeightedKeyedGraph<? extends VBetw, Edge> graph = null;
        try {
            graph = initializeWeightedGraph(getWeightColumnName(),
                                            GraphCreator.UNDIRECTED);
            addVertices(graph);
            addWeightedEdges((WeightedKeyedGraph) graph);
        } catch (NoSuchMethodException ex) {
        }
        return (UndirectedG) graph;
    }

    /**
     * Initializes an unweighted JGraphT graph according to the given
     * orientation.
     *
     * @param orientation The orientation.
     *
     * @return The newly initialized graph.
     */
    private KeyedGraph<? extends VBetw, Edge> initializeUnweightedGraph(
            int orientation) throws NoSuchMethodException {
        if (orientation != UNDIRECTED) {
            return new DirectedPseudoG<VUBetw, Edge>(
                    VUBetw.class, Edge.class);
        } else {
            return new PseudoG<VUBetw, Edge>(
                    VUBetw.class, Edge.class);
        }
    }

    /**
     * Initializes a weighted JGraphT graph according to the given weight column
     * name and orientation.
     *
     * @param weightColumnName The weight column name.
     * @param orientation      The orientation.
     *
     * @return The newly initialized graph.
     */
    private WeightedKeyedGraph<? extends VBetw, Edge> initializeWeightedGraph(
            String weightColumnName,
            int orientation) throws NoSuchMethodException {
        if (orientation != UNDIRECTED) {
            return new DirectedWeightedPseudoG<VWBetw, Edge>(
                    VWBetw.class, Edge.class);
        } else {
            return new WeightedPseudoG<VWBetw, Edge>(
                    VWBetw.class, Edge.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFilename() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWeightColumnName() {
        return "";
    }
}
