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
package org.javanetworkanalyzer.graphcreators;

import org.javanetworkanalyzer.data.VCent;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.UndirectedG;
import java.io.FileNotFoundException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests loading the 2D graph under all possible configurations (weighted or
 * unweighted; directed, reversed or undirected).
 *
 * @author Adam Gouge
 */
public class Graph2DGraphCreatorTest {

    private static final String FILENAME = "./files/graph2D.edges.csv";
    private static final String WEIGHT = "length";
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Graph2DGraphCreatorTest.class);

    @Test
    public void unweightedDirected() throws FileNotFoundException,
            NoSuchMethodException {
        KeyedGraph<? extends VCent, Edge> graph =
                load2DGraph(false, GraphCreator.DIRECTED);
    }

    @Test
    public void unweightedReversed() throws FileNotFoundException,
            NoSuchMethodException {
        KeyedGraph<? extends VCent, Edge> graph =
                load2DGraph(false, GraphCreator.REVERSED);
    }

    @Test
    public void unweightedUndirected() throws FileNotFoundException,
            NoSuchMethodException {
        KeyedGraph<? extends VCent, Edge> graph =
                load2DGraph(false, GraphCreator.UNDIRECTED);
    }

    @Test
    public void weightedDirected() throws FileNotFoundException,
            NoSuchMethodException {
        KeyedGraph<? extends VCent, Edge> graph =
                load2DGraph(true, GraphCreator.DIRECTED);
    }

    @Test
    public void weightedReversed() throws FileNotFoundException,
            NoSuchMethodException {
        KeyedGraph<? extends VCent, Edge> graph =
                load2DGraph(true, GraphCreator.REVERSED);
    }

    @Test
    public void weightedUndirected() throws FileNotFoundException,
            NoSuchMethodException {
        KeyedGraph<? extends VCent, Edge> graph =
                load2DGraph(true, GraphCreator.UNDIRECTED);
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
    private KeyedGraph<? extends VCent, Edge> load2DGraph(
            boolean weighted,
            int orientation) throws FileNotFoundException,
            NoSuchMethodException {
        KeyedGraph<? extends VCent, Edge> graph;
        if (weighted) {
            graph = new WeightedGraphCreator<VWCent, Edge>(
                    FILENAME,
                    orientation,
                    VWCent.class,
                    Edge.class,
                    WEIGHT).loadGraph();
        } else {
            graph = new GraphCreator<VUCent, Edge>(
                    FILENAME,
                    orientation,
                    VUCent.class,
                    Edge.class).loadGraph();
        }
        if (LOGGER.isDebugEnabled()) {
            printEdges(graph);
        }
        return graph;
    }

    /**
     * Prints all edges of the graph.
     *
     * @param graph The graph.
     */
    private void printEdges(KeyedGraph<? extends VCent, Edge> graph) {
        String leftArrow;
        if (graph instanceof UndirectedG) {
            leftArrow = "<";
        } else {
            leftArrow = "";
        }
        for (Edge edge : graph.edgeSet()) {
            LOGGER.debug("{} {}--> {} ({})",
                         graph.getEdgeSource(edge).getID(),
                         leftArrow,
                         graph.getEdgeTarget(edge).getID(),
                         graph.getEdgeWeight(edge));
        }
        LOGGER.debug("");
    }
}
