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
import com.graphhopper.sna.graphcreators.UnweightedGraphCreator;
import com.graphhopper.sna.graphcreators.WeightedGraphCreator;
import com.graphhopper.sna.progress.ProgressMonitor;
import java.io.FileNotFoundException;
import org.jgrapht.Graph;
import org.junit.Test;

/**
 * Tests weighted and unweighted graph analysis.
 *
 * @author Adam Gouge
 */
public abstract class GraphAnalyzerTest
        extends CentralityTest {

    private static final String WEIGHTED = "Weighted";
    private static final String UNWEIGHTED = "Unweighted";
    private static final String DIRECTED = "Directed";
    private static final String REVERSED = "Reversed";
    private static final String UNDIRECTED = "Undirected";
    /**
     * The name of this test.
     */
    protected final static String NAME = "Graph analysis";

    /**
     * Does unweighted graph analysis on the given graph.
     *
     * @param graph       The graph.
     *
     * @param orientation The orientation.
     */
    protected void doUnweightedAnalysis(
            Graph<UnweightedNodeBetweennessInfo, Edge> graph,
            String orientation) {
        try {
            doAnalysis(new UnweightedGraphAnalyzer(graph, progressMonitor()),
                       UNWEIGHTED + " " + orientation);
        } catch (Exception ex) {
        }
        if (printsResults()) {
            printResults(graph);
        }
    }

    /**
     * Does weighted graph analysis on the given graph.
     *
     * @param graph       The graph.
     *
     * @param orientation The orientation.
     */
    protected void doWeightedAnalysis(
            Graph<WeightedNodeBetweennessInfo, Edge> graph,
            String orientation) {
        try {
            doAnalysis(new WeightedGraphAnalyzer(graph, progressMonitor()),
                       WEIGHTED + " " + orientation);
        } catch (Exception ex) {
        }
        if (printsResults()) {
            printResults(graph);
        }
    }

    /**
     * Executes {@link GraphAnalyzer#computeAll()}.
     *
     * @param analyzer     The {@link GraphAnalyzer}.
     * @param analysisType Describes the type of analysis.
     */
    private void doAnalysis(
            GraphAnalyzer<?, Edge, ?> analyzer,
            String analysisType) {
        // Do network analysis.
        System.out.println("    _" + analysisType + "_");
        long start = System.currentTimeMillis();
        try {
            analyzer.computeAll();
        } catch (Exception ex) {
        }
        printTime(System.currentTimeMillis() - start, analysisType);
    }

    private void printResults(Graph<? extends NodeBetweennessInfo, Edge> graph) {
        for (NodeBetweennessInfo node : graph.vertexSet()) {
            System.out.println(node.getID() + " "
                    + node.getBetweenness()
                    + " " + node.getCloseness());
        }
    }

    /**
     * Does unweighted graph analysis on the directed graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    @Test
    public void unweightedDirected() throws FileNotFoundException,
            NoSuchMethodException {
        doUnweightedAnalysis(unweightedGraph(GraphCreator.DIRECTED),
                             DIRECTED);
    }

    /**
     * Does unweighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    @Test
    public void unweightedReversed() throws FileNotFoundException,
            NoSuchMethodException {
        doUnweightedAnalysis(unweightedGraph(GraphCreator.REVERSED),
                             REVERSED);
    }

    /**
     * Does unweighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    @Test
    public void unweightedUndirected() throws FileNotFoundException,
            NoSuchMethodException {
        doUnweightedAnalysis(unweightedGraph(GraphCreator.UNDIRECTED),
                             UNDIRECTED);
    }

    /**
     * Does weighted graph analysis on the directed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    @Test
    public void weightedDirected() throws FileNotFoundException,
            NoSuchMethodException {
        doWeightedAnalysis(weightedGraph(GraphCreator.DIRECTED),
                           DIRECTED);
    }

    /**
     * Does weighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    @Test
    public void weightedReversed() throws FileNotFoundException,
            NoSuchMethodException {
        doWeightedAnalysis(weightedGraph(GraphCreator.REVERSED),
                           REVERSED);
    }

    /**
     * Does weighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    @Test
    public void weightedUndirected() throws FileNotFoundException,
            NoSuchMethodException {
        doWeightedAnalysis(weightedGraph(GraphCreator.UNDIRECTED),
                           UNDIRECTED);
    }

    /**
     * Does unweighted analysis on an unweighted graph (loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)} and weighted analysis on a
     * weighted graph WITH ALL WEIGHTS EQUAL TO 1.0 (loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}) and makes sure the results
     * are the same.
     *
     * @throws FileNotFoundException
     */
    // TODO: Implement this method.
    @Test
    public void undirectedComparison() throws FileNotFoundException {
//        int orientation = GraphCreator.UNDIRECTED;
//        
//        Map<UnweightedNodeBetweennessInfo, Edge> unweightedResults = 
//                doUnweightedAnalysis(unweightedGraph(orientation));
//        Map<WeightedNodeBetweennessInfo, Ege> weightedResults = 
//                doWeightedAnalysis(weightedGraph(orientation));
    }

    /**
     * Loads an unweighted graph with the given orientation from
     * {@link GraphAnalyzerTest#getFilename()}.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    protected Graph unweightedGraph(int orientation) throws
            FileNotFoundException, NoSuchMethodException {
        return new UnweightedGraphCreator(getFilename(),
                                          orientation,
                                          UnweightedNodeBetweennessInfo.class,
                                          Edge.class).loadGraph();
    }

    /**
     * Loads a weighted graph with the given orientation from
     * {@link GraphAnalyzerTest#getFilename()}.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    protected Graph weightedGraph(int orientation) throws
            FileNotFoundException, NoSuchMethodException {
        return new WeightedGraphCreator(getFilename(),
                                        getWeightColumnName(),
                                        orientation,
                                        WeightedNodeBetweennessInfo.class,
                                        Edge.class).loadGraph();
    }

    /**
     * Returns a boolean indicating whether the results should be printed.
     *
     * @return A boolean indicating whether the results should be printed.
     */
    protected abstract boolean printsResults();

    /**
     * Returns a {@link ProgressMonitor} to be used during graph analysis.
     *
     * @return
     */
    protected abstract ProgressMonitor progressMonitor();

    /**
     * Returns the filename from which the graph edges should be loaded.
     *
     * @return The filename from which the graph edges should be loaded.
     */
    protected abstract String getFilename();

    /**
     * Returns the weight column name.
     *
     * @return The weight column name.
     */
    protected abstract String getWeightColumnName();

    /**
     * Prints the amount of time graph analysis took.
     *
     * @param time
     * @param analysisType
     */
    protected void printTime(double time, String analysisType) {
        System.out.println(TIME + time + " ms: "
                + NAME + " - " + getName()
                + " " + analysisType + ".");
    }
}
