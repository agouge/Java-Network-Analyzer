/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 * It is part of the OrbisGIS tool ecosystem.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * Java Network Analyzer is distributed under GPL 3 license.
 *
 * Copyright (C) 2012-2014 CNRS (IRSTV CNRS FR 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC CNRS UMR 6285)
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
 * 
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.javanetworkanalyzer.analyzers;

import org.javanetworkanalyzer.data.VCent;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.model.*;
import org.javanetworkanalyzer.graphcreators.GraphCreator;
import static org.javanetworkanalyzer.graphcreators.GraphCreator.UNDIRECTED;

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
            KeyedGraph<? extends VCent, EdgeCent> graph) {
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
            KeyedGraph<? extends VCent, EdgeCent> graph);

    /**
     * Manually adds weighted edges to the graph.
     *
     * @param graph The graph.
     */
    protected abstract void addWeightedEdges(
            WeightedKeyedGraph<? extends VCent, EdgeCent> graph);

    /**
     * Loads an unweighted graph with the given orientation.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     */
    @Override
    protected KeyedGraph<VUCent, EdgeCent> unweightedGraph(
            int orientation) {
        KeyedGraph<VUCent, EdgeCent> graph;
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
    protected WeightedKeyedGraph<VWCent, EdgeCent> weightedGraph(
            int orientation) {
        KeyedGraph<VWCent, EdgeCent> graph;
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
    private DirectedG<VUCent, EdgeCent> unweightedDirectedGraph() {
        KeyedGraph<? extends VCent, EdgeCent> graph = null;
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
    private DirectedG<VUCent, EdgeCent> unweightedReversedGraph() {
        EdgeReversedG<VUCent, EdgeCent> graph = null;
        try {
            graph = new EdgeReversedG<VUCent, EdgeCent>(
                    unweightedDirectedGraph());
        } catch (NoSuchMethodException ex) {
        }
        return graph;
    }

    /**
     * Creates an unweighted undirected graph.
     */
    private UndirectedG<VUCent, EdgeCent> unweightedUndirectedGraph() {
        KeyedGraph<? extends VCent, EdgeCent> graph = null;
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
    private DirectedG<VWCent, EdgeCent> weightedDirectedGraph() {
        WeightedKeyedGraph<? extends VCent, EdgeCent> graph = null;
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
    private DirectedG<VWCent, EdgeCent> weightedReversedGraph() {
        WeightedKeyedGraph<VWCent, EdgeCent> graph = null;
        try {
            graph = new WeightedEdgeReversedG<VWCent, EdgeCent>(
                    weightedDirectedGraph());
        } catch (NoSuchMethodException ex) {
        }
        return (DirectedG) graph;
    }

    /**
     * Creates a weighted undirected graph.
     */
    private UndirectedG<VWCent, EdgeCent> weightedUndirectedGraph() {
        WeightedKeyedGraph<? extends VCent, EdgeCent> graph = null;
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
    private KeyedGraph<? extends VCent, EdgeCent> initializeUnweightedGraph(
            int orientation) throws NoSuchMethodException {
        if (orientation != UNDIRECTED) {
            return new DirectedPseudoG<VUCent, EdgeCent>(
                    VUCent.class, EdgeCent.class);
        } else {
            return new PseudoG<VUCent, EdgeCent>(
                    VUCent.class, EdgeCent.class);
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
    private WeightedKeyedGraph<? extends VCent, EdgeCent> initializeWeightedGraph(
            String weightColumnName,
            int orientation) throws NoSuchMethodException {
        if (orientation != UNDIRECTED) {
            return new DirectedWeightedPseudoG<VWCent, EdgeCent>(
                    VWCent.class, EdgeCent.class);
        } else {
            return new WeightedPseudoG<VWCent, EdgeCent>(
                    VWCent.class, EdgeCent.class);
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
