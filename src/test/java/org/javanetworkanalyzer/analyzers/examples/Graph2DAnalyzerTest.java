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
package org.javanetworkanalyzer.analyzers.examples;

import org.javanetworkanalyzer.analyzers.GraphAnalyzerTest;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.model.DirectedG;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.EdgeCent;
import org.javanetworkanalyzer.model.UndirectedG;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import org.junit.Test;

/**
 * Tests graph analysis (betweenness, closeness) on an undirected 2D graph.
 *
 * @author Adam Gouge
 */
public class Graph2DAnalyzerTest extends GraphAnalyzerTest {

    private final static String GRAPH2D = "2D Graph";
    private static final String FILENAME = "./files/graph2D.edges.csv";
    private static final String LENGTH = "length";
    private static final boolean PRINT_RESULTS = false;
    private static final boolean CHECK_RESULTS = true;
    private static final int NUMBER_OF_NODES = 6;

    @Override
    protected ProgressMonitor progressMonitor() {
        return new NullProgressMonitor();
    }

    @Test
    public void unweightedDirectedTest() {
        DirectedG<VUCent, EdgeCent> graph =
                super.unweightedDirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                1.0, 0.0, 1.0,
                0.0, 0.0, 0.6666666666666666}, graph);
            checkCloseness(new double[]{
                0.0, 0.4166666666666667, 0.0,
                0.0, 0.0, 0.0}, graph);
        }
    }

    @Test
    public void unweightedReversedTest() {
        DirectedG<VUCent, EdgeCent> graph =
                super.unweightedReversedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.375, 0.0, 1.0,
                0.0, 0.0, 1.0}, graph);
            checkCloseness(new double[]{
                0.0, 0.0, 0.0,
                0.0, 0.0, 0.0}, graph);
        }
    }

    @Test
    public void unweightedUndirectedTest() {
        UndirectedG<VUCent, EdgeCent> graph =
                super.unweightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.5, 0.0, 1.0,
                0.0, 0.0, 0.75}, graph);
            checkCloseness(new double[]{
                0.5, 0.4166666666666667, 0.625,
                0.35714285714285715, 0.4166666666666667, 0.625}, graph);
        }
    }

    @Test
    public void weightedDirectedTest() {
        DirectedG<VWCent, EdgeCent> graph =
                super.weightedDirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.75, 0.0, 1.0,
                0.0, 0.0, 1.0}, graph);
            checkCloseness(new double[]{
                0.0, 0.0035327735482214143, 0.0,
                0.0, 0.0, 0.0}, graph);
        }
    }

    @Test
    public void weightedReversedTest() {
        DirectedG<VWCent, EdgeCent> graph =
                super.weightedReversedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.75, 0.0, 1.0,
                0.0, 0.0, 1.0}, graph);
            checkCloseness(new double[]{
                0.0, 0.0, 0.0,
                0.0, 0.0, 0.0}, graph);
        }
    }

    @Test
    public void weightedUndirectedTest() {
        UndirectedG<VWCent, EdgeCent> graph =
                super.weightedUndirectedAnalysis();
        if (CHECK_RESULTS) {
            checkBetweenness(new double[]{
                0.5714285714285714, 0.0, 1.0,
                0.0, 0.0, 0.8571428571428571}, graph);

            checkCloseness(new double[]{
                0.003787491035823884, 0.0035327735482214143, 0.0055753940798198886,
                0.0032353723348164448, 0.003495002741097083, 0.0055753940798198886
            }, graph);
        }
    }

    @Override
    protected boolean printsResults() {
        return PRINT_RESULTS;
    }

    @Override
    protected String getFilename() {
        return FILENAME;
    }

    @Override
    protected String getWeightColumnName() {
        return LENGTH;
    }

    @Override
    public String getName() {
        return GRAPH2D;
    }

    @Override
    protected int getNumberOfNodes() {
        return NUMBER_OF_NODES;
    }
}
