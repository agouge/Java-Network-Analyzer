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
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.model.Edge;
import com.graphhopper.sna.model.GraphCreator;
import com.graphhopper.sna.model.UnweightedGraphCreator;
import com.graphhopper.sna.model.WeightedGraphCreator;
import com.graphhopper.sna.progress.ProgressMonitor;
import java.io.FileNotFoundException;
import java.util.Map;
import org.jgrapht.Graph;
import org.junit.Test;

/**
 * Tests weighted and unweighted graph analysis.
 *
 * @author Adam Gouge
 */
public abstract class GraphAnalyzerTest extends CentralityTest {

    /**
     * The name of this test.
     */
    protected final static String NAME = "Graph analysis";

    /**
     * Does unweighted graph analysis on the given graph.
     *
     * @param graph The graph.
     *
     * @return The result.
     */
    private Map<Integer, NodeBetweennessInfo> doUnweightedAnalysis(
            Graph<Integer, Edge> graph) {
        return doAnalysis(new UnweightedGraphAnalyzer(graph, progressMonitor()));
    }

    /**
     * Does weighted graph analysis on the given graph.
     *
     * @param graph The graph.
     *
     * @return The result.
     */
    private Map<Integer, NodeBetweennessInfo> doWeightedAnalysis(
            Graph<Integer, Edge> graph) {
        return doAnalysis(new WeightedGraphAnalyzer(graph, progressMonitor()));
    }

    /**
     * Executes {@link GraphAnalyzer#computeAll()}.
     *
     * @param analyzer The {@link GraphAnalyzer}.
     *
     * @return The results.
     */
    private Map<Integer, NodeBetweennessInfo> doAnalysis(
            GraphAnalyzer analyzer) {
        // Do network analysis.
        long start = System.currentTimeMillis();
        Map<Integer, NodeBetweennessInfo> result = analyzer.computeAll();
        printTime(System.currentTimeMillis() - start);
        if (printsResults()) {
            printResults(result);
        }
        return result;
    }

    /**
     * Does unweighted graph analysis on the directed graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void unweightedDirected() throws FileNotFoundException {
        doUnweightedAnalysis(unweightedGraph(GraphCreator.DIRECTED));
    }

    /**
     * Does unweighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void unweightedReversed() throws FileNotFoundException {
        doUnweightedAnalysis(unweightedGraph(GraphCreator.REVERSED));
    }

    /**
     * Does unweighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void unweightedUndirected() throws FileNotFoundException {
        doUnweightedAnalysis(unweightedGraph(GraphCreator.UNDIRECTED));
    }

    /**
     * Does weighted graph analysis on the directed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void weightedDirected() throws FileNotFoundException {
        doWeightedAnalysis(weightedGraph(GraphCreator.DIRECTED));
    }

    /**
     * Does weighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void weightedReversed() throws FileNotFoundException {
        doWeightedAnalysis(weightedGraph(GraphCreator.REVERSED));
    }

    /**
     * Does weighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void weightedUndirected() throws FileNotFoundException {
        doWeightedAnalysis(weightedGraph(GraphCreator.UNDIRECTED));
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
//        Map<Integer, NodeBetweennessInfo> unweightedResults = 
//                doUnweightedAnalysis(unweightedGraph(orientation));
//        Map<Integer, NodeBetweennessInfo> weightedResults = 
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
     */
    protected Graph unweightedGraph(int orientation) throws
            FileNotFoundException {
        return new UnweightedGraphCreator(getFilename(),
                                          orientation).loadGraph();
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
     */
    protected Graph weightedGraph(int orientation) throws
            FileNotFoundException {
        return new WeightedGraphCreator(getFilename(),
                                        getWeightColumnName(),
                                        orientation).loadGraph();
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
     */
    protected void printTime(double time) {
        System.out.println(TIME + time + " ms: "
                + NAME + " - " + getName() + ".");
    }
}
