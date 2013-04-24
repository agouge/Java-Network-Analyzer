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

import static com.graphhopper.sna.analyzers.CentralityTest.TOLERANCE;
import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.data.UnweightedNodeBetweennessInfo;
import com.graphhopper.sna.data.WeightedNodeBetweennessInfo;
import com.graphhopper.sna.model.Edge;
import com.graphhopper.sna.graphcreators.GraphCreator;
import com.graphhopper.sna.graphcreators.UnweightedGraphCreator;
import com.graphhopper.sna.graphcreators.WeightedGraphCreator;
import com.graphhopper.sna.model.KeyedGraph;
import com.graphhopper.sna.progress.ProgressMonitor;
import java.io.FileNotFoundException;
import org.jgrapht.Graph;
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
     */
    protected KeyedGraph<UnweightedNodeBetweennessInfo, Edge> doUnweightedAnalysis(
            KeyedGraph<UnweightedNodeBetweennessInfo, Edge> graph,
            String orientation) {
        try {
            doAnalysis(new UnweightedGraphAnalyzer(graph, progressMonitor()),
                       UNWEIGHTED + " " + orientation);
        } catch (Exception ex) {
        }
        if (printsResults()) {
            printResults(graph);
        }
        return graph;
    }

    /**
     * Does weighted graph analysis on the given graph.
     *
     * @param graph       The graph.
     *
     * @param orientation The orientation.
     */
    protected KeyedGraph<WeightedNodeBetweennessInfo, Edge> doWeightedAnalysis(
            KeyedGraph<WeightedNodeBetweennessInfo, Edge> graph,
            String orientation) {
        try {
            doAnalysis(new WeightedGraphAnalyzer(graph, progressMonitor()),
                       WEIGHTED + " " + orientation);
        } catch (Exception ex) {
        }
        if (printsResults()) {
            printResults(graph);
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
    public KeyedGraph<UnweightedNodeBetweennessInfo, Edge> unweightedDirected()
            throws FileNotFoundException, NoSuchMethodException {
        return doUnweightedAnalysis(unweightedGraph(GraphCreator.DIRECTED),
                                    DIRECTED);
    }

    /**
     * Does unweighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    public KeyedGraph<UnweightedNodeBetweennessInfo, Edge> unweightedReversed()
            throws FileNotFoundException, NoSuchMethodException {
        return doUnweightedAnalysis(unweightedGraph(GraphCreator.REVERSED),
                                    REVERSED);
    }

    /**
     * Does unweighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#unweightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    public KeyedGraph<UnweightedNodeBetweennessInfo, Edge> unweightedUndirected()
            throws FileNotFoundException, NoSuchMethodException {
        return doUnweightedAnalysis(unweightedGraph(GraphCreator.UNDIRECTED),
                                    UNDIRECTED);
    }

    /**
     * Does weighted graph analysis on the directed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    public KeyedGraph<WeightedNodeBetweennessInfo, Edge> weightedDirected()
            throws FileNotFoundException, NoSuchMethodException {
        return doWeightedAnalysis(weightedGraph(GraphCreator.DIRECTED),
                                  DIRECTED);
    }

    /**
     * Does weighted graph analysis on the edge reversed graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    public KeyedGraph<WeightedNodeBetweennessInfo, Edge> weightedReversed()
            throws FileNotFoundException, NoSuchMethodException {
        return doWeightedAnalysis(weightedGraph(GraphCreator.REVERSED),
                                  REVERSED);
    }

    /**
     * Does weighted graph analysis on the undirected graph loaded by
     * {@link GraphAnalyzerTest#weightedGraph(int)}.
     *
     * @throws FileNotFoundException
     * @throws NoSuchMethodException
     */
    public KeyedGraph<WeightedNodeBetweennessInfo, Edge> weightedUndirected()
            throws FileNotFoundException, NoSuchMethodException {
        return doWeightedAnalysis(weightedGraph(GraphCreator.UNDIRECTED),
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
    protected KeyedGraph<UnweightedNodeBetweennessInfo, Edge> unweightedGraph(
            int orientation)
            throws FileNotFoundException, NoSuchMethodException {
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
    protected KeyedGraph<WeightedNodeBetweennessInfo, Edge> weightedGraph(
            int orientation)
            throws FileNotFoundException, NoSuchMethodException {
        return new WeightedGraphCreator(getFilename(),
                                        getWeightColumnName(),
                                        orientation,
                                        WeightedNodeBetweennessInfo.class,
                                        Edge.class).loadGraph();
    }

    /**
     * Facilitates obtaining references to vertices.
     *
     * @param graph Input graph.
     *
     * @return An array with the vertex with index i is in position i-1.
     */
    protected NodeBetweennessInfo[] indexVertices(
            KeyedGraph<? extends NodeBetweennessInfo, Edge> graph) {
        NodeBetweennessInfo[] vertices = new NodeBetweennessInfo[getNumberOfNodes()];
        for (int i = 0; i < getNumberOfNodes(); i++) {
            vertices[i] = graph.getVertex(i + 1);
        }
        return vertices;
    }

    /**
     * Checks the betweenness values of the given vertices against the given
     * expected betweenness values.
     *
     * @param vertices            The vertices
     * @param expectedBetweenness The expected betweenness values
     */
    protected void checkBetweenness(NodeBetweennessInfo[] vertices,
                                    double[] expectedBetweenness) {
        for (int i = 0; i < getNumberOfNodes(); i++) {
            assertEquals(vertices[i].getBetweenness(), expectedBetweenness[i],
                         TOLERANCE);
        }
    }

    /**
     * Checks the closeness values of the given vertices against the given
     * expected closeness values.
     *
     * @param vertices          The vertices
     * @param expectedCloseness The expected closeness values
     */
    protected void checkCloseness(NodeBetweennessInfo[] vertices,
                                  double[] expectedCloseness) {
        for (int i = 0; i < getNumberOfNodes(); i++) {
            assertEquals(vertices[i].getCloseness(), expectedCloseness[i],
                         TOLERANCE);
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
