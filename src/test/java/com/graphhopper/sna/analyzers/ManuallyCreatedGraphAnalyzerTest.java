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
package com.graphhopper.sna.analyzers;

import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.data.UnweightedNodeBetweennessInfo;
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.sna.model.Edge;
import com.graphhopper.sna.graphcreators.GraphCreator;
import static com.graphhopper.sna.graphcreators.GraphCreator.UNDIRECTED;
import com.graphhopper.sna.model.DirectedG;
import com.graphhopper.sna.model.DirectedPseudoG;
import com.graphhopper.sna.model.DirectedWeightedPseudoG;
import com.graphhopper.sna.model.KeyedGraph;
import com.graphhopper.sna.model.PseudoG;
import com.graphhopper.sna.model.UndirectedG;
import com.graphhopper.sna.model.WeightedKeyedGraph;
import com.graphhopper.sna.model.WeightedPseudoG;

/**
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
    protected abstract void addVertices(
            KeyedGraph<? extends NodeBetweennessInfo, Edge> graph);

    /**
     * Manually adds edges to the graph.
     *
     * @param graph The graph.
     */
    protected abstract void addEdges(
            KeyedGraph<? extends NodeBetweennessInfo, Edge> graph);

    /**
     * Manually adds weighted edges to the graph.
     *
     * @param graph The graph.
     */
    protected abstract void addWeightedEdges(
            WeightedKeyedGraph<? extends NodeBetweennessInfo, Edge> graph);

    /**
     * Loads an unweighted graph with the given orientation.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    @Override
    protected KeyedGraph<UnweightedNodeBetweennessInfo, Edge> unweightedGraph(
            int orientation) {
        if (orientation == GraphCreator.DIRECTED) {
            return unweightedDirectedGraph();
        } else if (orientation == GraphCreator.REVERSED) {
            return unweightedReversedGraph();
        } else if (orientation == GraphCreator.UNDIRECTED) {
            return unweightedUndirectedGraph();
        } else {
            throw new IllegalArgumentException(ORIENTATION_ERROR);
        }
    }

    /**
     * Loads a weighted graph with the given orientation.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     */
    @Override
    protected KeyedGraph<WeightedNodeBetweennessInfo, Edge> weightedGraph(
            int orientation) {
        if (orientation == GraphCreator.DIRECTED) {
            return weightedDirectedGraph();
        } else if (orientation == GraphCreator.REVERSED) {
            return weightedReversedGraph();
        } else if (orientation == GraphCreator.UNDIRECTED) {
            return weightedUndirectedGraph();
        } else {
            throw new IllegalArgumentException(ORIENTATION_ERROR);
        }
    }

    /**
     * Creates an unweighted directed graph.
     */
    private DirectedG unweightedDirectedGraph() {
        KeyedGraph<? extends NodeBetweennessInfo, Edge> graph = null;
        try {
            graph = initializeGraph(null,
                                    GraphCreator.DIRECTED);
            addVertices(graph);
            addEdges(graph);
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates an unweighted edge reversed graph.
     */
    private DirectedG unweightedReversedGraph() {
        KeyedGraph<? extends NodeBetweennessInfo, Edge> graph = null;
        try {
            graph = initializeGraph(null,
                                    GraphCreator.REVERSED);
            addVertices(graph);
            addEdges(graph);
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates an unweighted undirected graph.
     */
    private UndirectedG unweightedUndirectedGraph() {
        KeyedGraph<? extends NodeBetweennessInfo, Edge> graph = null;
        try {
            graph = initializeGraph(null,
                                    GraphCreator.UNDIRECTED);
            addVertices(graph);
            addEdges(graph);
        } catch (NoSuchMethodException ex) {
        }
        return (UndirectedG) graph;
    }

    /**
     * Creates a weighted directed graph.
     */
    private DirectedG weightedDirectedGraph() {
        KeyedGraph<? extends NodeBetweennessInfo, Edge> graph = null;
        try {
            graph = initializeGraph(getWeightColumnName(),
                                    GraphCreator.DIRECTED);
            addVertices(graph);
            addWeightedEdges((WeightedKeyedGraph) graph);
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates a weighted edge reversed graph.
     */
    private DirectedG weightedReversedGraph() {
        KeyedGraph<? extends NodeBetweennessInfo, Edge> graph = null;
        try {
            graph = initializeGraph(getWeightColumnName(),
                                    GraphCreator.REVERSED);
            addVertices(graph);
            addWeightedEdges((WeightedKeyedGraph) graph);
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates a weighted undirected graph.
     */
    private UndirectedG weightedUndirectedGraph() {
        KeyedGraph<? extends NodeBetweennessInfo, Edge> graph = null;
        try {
            graph = initializeGraph(getWeightColumnName(),
                                    GraphCreator.UNDIRECTED);
            addVertices(graph);
            addWeightedEdges((WeightedKeyedGraph) graph);
        } catch (NoSuchMethodException ex) {
        }
        return (UndirectedG) graph;
    }

    /**
     * Initializes a JGraphT graph according to the given weight column name and
     * orientation.
     *
     * @param weightColumnName The weight column name.
     * @param orientation      The orientation.
     *
     * @return The newly initialized graph.
     */
    private KeyedGraph<? extends NodeBetweennessInfo, Edge> initializeGraph(
            String weightColumnName,
            int orientation) throws NoSuchMethodException {
        // Unweighted
        if (weightColumnName == null) {
            if (orientation != UNDIRECTED) {
                return new DirectedPseudoG<UnweightedNodeBetweennessInfo, Edge>(
                        UnweightedNodeBetweennessInfo.class, Edge.class);
            } else {
                return new PseudoG<UnweightedNodeBetweennessInfo, Edge>(
                        UnweightedNodeBetweennessInfo.class, Edge.class);
            }
        } // Weighted
        else {
            if (orientation != UNDIRECTED) {
                return new DirectedWeightedPseudoG<WeightedNodeBetweennessInfo, Edge>(
                        WeightedNodeBetweennessInfo.class, Edge.class);
            } else {
                return new WeightedPseudoG<WeightedNodeBetweennessInfo, Edge>(
                        WeightedNodeBetweennessInfo.class, Edge.class);
            }
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
