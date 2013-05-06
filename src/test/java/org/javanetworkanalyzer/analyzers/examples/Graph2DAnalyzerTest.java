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
package org.javanetworkanalyzer.analyzers.examples;

import org.javanetworkanalyzer.analyzers.GraphAnalyzerTest;
import org.javanetworkanalyzer.data.VUBetw;
import org.javanetworkanalyzer.data.VWBetw;
import org.javanetworkanalyzer.model.DirectedG;
import org.javanetworkanalyzer.model.Edge;
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
        DirectedG<VUBetw, Edge> graph =
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
        DirectedG<VUBetw, Edge> graph =
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
        UndirectedG<VUBetw, Edge> graph =
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
        DirectedG<VWBetw, Edge> graph =
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
        DirectedG<VWBetw, Edge> graph =
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
        UndirectedG<VWBetw, Edge> graph =
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
    protected String getName() {
        return GRAPH2D;
    }

    @Override
    protected int getNumberOfNodes() {
        return NUMBER_OF_NODES;
    }
}
