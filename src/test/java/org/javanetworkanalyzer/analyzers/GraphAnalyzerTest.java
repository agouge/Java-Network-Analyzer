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
package org.javanetworkanalyzer.analyzers;

import org.javanetworkanalyzer.analyzers.UnweightedGraphAnalyzer;
import org.javanetworkanalyzer.analyzers.WeightedGraphAnalyzer;
import org.javanetworkanalyzer.analyzers.GraphAnalyzer;
import static org.javanetworkanalyzer.analyzers.CentralityTest.TOLERANCE;
import org.javanetworkanalyzer.data.VBetw;
import org.javanetworkanalyzer.data.VUBetw;
import org.javanetworkanalyzer.data.VWBetw;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.graphcreators.GraphCreator;
import org.javanetworkanalyzer.graphcreators.WeightedGraphCreator;
import org.javanetworkanalyzer.model.DirectedG;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.UndirectedG;
import org.javanetworkanalyzer.model.WeightedKeyedGraph;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import static org.junit.Assert.assertEquals;
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
     *
     * @return The graph.
     */
    protected KeyedGraph<VUBetw, Edge> doUnweightedAnalysis(
            KeyedGraph<VUBetw, Edge> graph,
            String orientation) {
        try {
            doAnalysis(new UnweightedGraphAnalyzer(graph, progressMonitor()),
                       UNWEIGHTED + " " + orientation);
        } catch (Exception ex) {
        }
        if (printsResults()) {
            printResults(graph);
            System.out.println("");
        }
        return graph;
    }

    /**
     * Does weighted graph analysis on the given graph.
     *
     * @param graph       The graph.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     */
    protected WeightedKeyedGraph<VWBetw, Edge> doWeightedAnalysis(
            WeightedKeyedGraph<VWBetw, Edge> graph,
            String orientation) {
        try {
            doAnalysis(new WeightedGraphAnalyzer(graph, progressMonitor()),
                       WEIGHTED + " " + orientation);
        } catch (Exception ex) {
        }
        if (printsResults()) {
            printResults(graph);
            System.out.println("");
        }
        return graph;
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

    /**
     * Does unweighted graph analysis on the directed graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @return The graph.
     */
    public DirectedG<VUBetw, Edge> unweightedDirectedAnalysis() {
        try {
            return (DirectedG) doUnweightedAnalysis(unweightedGraph(
                    GraphCreator.DIRECTED), DIRECTED);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Does unweighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @return The graph.
     */
    public DirectedG<VUBetw, Edge> unweightedReversedAnalysis() {
        try {
            return (DirectedG) doUnweightedAnalysis(unweightedGraph(
                    GraphCreator.REVERSED), REVERSED);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Does unweighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @return The graph.
     */
    public UndirectedG<VUBetw, Edge> unweightedUndirectedAnalysis() {
        try {
            return (UndirectedG) doUnweightedAnalysis(unweightedGraph(
                    GraphCreator.UNDIRECTED), UNDIRECTED);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Does weighted graph analysis on the directed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @return The graph.
     */
    public DirectedG<VWBetw, Edge> weightedDirectedAnalysis() {
        try {
            return (DirectedG) doWeightedAnalysis(weightedGraph(
                    GraphCreator.DIRECTED), DIRECTED);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Does weighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @return The graph.
     */
    public DirectedG<VWBetw, Edge> weightedReversedAnalysis() {
        try {
            return (DirectedG) doWeightedAnalysis(weightedGraph(
                    GraphCreator.REVERSED), REVERSED);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Does weighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @return The graph.
     */
    public UndirectedG<VWBetw, Edge> weightedUndirectedAnalysis() {
        try {
            return (UndirectedG) doWeightedAnalysis(weightedGraph(
                    GraphCreator.UNDIRECTED), UNDIRECTED);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Does unweighted analysis on an unweighted graph (loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)} and weighted analysis on a
     * weighted graph WITH ALL WEIGHTS EQUAL TO 1.0 (loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}) and makes sure the results
     * are the same.
     */
    // TODO: Implement this method.
    @Test
    public void undirectedComparison() {
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
     */
    protected KeyedGraph<VUBetw, Edge> unweightedGraph(
            int orientation) {
        try {
            return new GraphCreator(getFilename(),
                                    orientation,
                                    VUBetw.class,
                                    Edge.class).loadGraph();
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Loads a weighted graph with the given orientation from
     * {@link GraphAnalyzerTest#getFilename()}.
     *
     * @param orientation The orientation.
     *
     * @return The graph.
     *
     */
    protected WeightedKeyedGraph<VWBetw, Edge> weightedGraph(
            int orientation) {
        try {
            return new WeightedGraphCreator(getFilename(),
                                            orientation,
                                            VWBetw.class,
                                            Edge.class,
                                            getWeightColumnName()).loadGraph();
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Checks the betweenness values of the given vertices against the given
     * expected betweenness values.
     *
     * @param graph               The graph
     * @param expectedBetweenness The expected betweenness values
     */
    protected void checkBetweenness(
            double[] expectedBetweenness,
            KeyedGraph<? extends VBetw, Edge> graph) {
        if (graph != null) {
            VBetw vertex;
            for (int i = 0; i < getNumberOfNodes(); i++) {
                vertex = graph.getVertex(i + 1);
                if (vertex != null) {
                    assertEquals(expectedBetweenness[i],
                                 vertex.getBetweenness(),
                                 TOLERANCE);
                } else {
                    throw new IllegalStateException("Null vertex " + (i + 1));
                }
            }
        } else {
            throw new IllegalStateException("Null graph");
        }
    }

    /**
     * Checks the closeness values of the given vertices against the given
     * expected closeness values.
     *
     * @param graph             The graph
     * @param expectedCloseness The expected closeness values
     */
    protected void checkCloseness(
            double[] expectedCloseness,
            KeyedGraph<? extends VBetw, Edge> graph) {
        if (graph != null) {
            VBetw vertex;
            for (int i = 0; i < getNumberOfNodes(); i++) {
                vertex = graph.getVertex(i + 1);
                if (vertex != null) {
                    assertEquals(expectedCloseness[i],
                                 vertex.getCloseness(),
                                 TOLERANCE);
                } else {
                    throw new IllegalStateException("Null vertex " + (i + 1));
                }
            }
        } else {
            throw new IllegalStateException("Null graph");
        }
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
     * Returns the number of nodes in this graph.
     *
     * @return The number of nodes in this graph.
     */
    protected abstract int getNumberOfNodes();

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
