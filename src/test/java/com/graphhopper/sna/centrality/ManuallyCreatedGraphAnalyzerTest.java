/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.model.Edge;
import com.graphhopper.sna.model.GraphCreator;
import static com.graphhopper.sna.model.GraphCreator.UNDIRECTED;
import java.io.FileNotFoundException;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.EdgeReversedGraph;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.WeightedMultigraph;

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
    protected abstract void addVertices(Graph<Integer, Edge> graph);

    /**
     * Manually adds edges to the graph.
     *
     * @param graph The graph.
     */
    protected abstract void addEdges(Graph<Integer, Edge> graph);

    /**
     * Manually adds weighted edges to the graph.
     *
     * @param graph The graph.
     */
    protected abstract void addWeightedEdges(WeightedGraph<Integer, Edge> graph);

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
    protected Graph unweightedGraph(int orientation) throws
            FileNotFoundException {
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
    protected Graph weightedGraph(int orientation) throws
            FileNotFoundException {
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
    private DirectedGraph unweightedDirectedGraph() {

        Graph<Integer, Edge> graph =
                initializeGraph(null, GraphCreator.DIRECTED);

        addVertices(graph);
        addEdges(graph);

        return (DirectedGraph) graph;
    }

    /**
     * Creates an unweighted edge reversed graph.
     */
    private DirectedGraph unweightedReversedGraph() {
        return new EdgeReversedGraph(unweightedDirectedGraph());
    }

    /**
     * Creates an unweighted undirected graph.
     */
    private UndirectedGraph unweightedUndirectedGraph() {

        Graph<Integer, Edge> graph =
                initializeGraph(null, GraphCreator.UNDIRECTED);

        addVertices(graph);
        addEdges(graph);

        return (UndirectedGraph) graph;
    }

    /**
     * Creates a weighted directed graph.
     */
    private DirectedGraph weightedDirectedGraph() {

        Graph<Integer, Edge> graph =
                initializeGraph(getWeightColumnName(), GraphCreator.DIRECTED);

        addVertices(graph);
        addWeightedEdges((WeightedGraph) graph);

        return (DirectedGraph) graph;
    }

    /**
     * Creates a weighted edge reversed graph.
     */
    private DirectedGraph weightedReversedGraph() {
        return new EdgeReversedGraph(weightedDirectedGraph());
    }

    /**
     * Creates a weighted undirected graph.
     */
    private UndirectedGraph weightedUndirectedGraph() {

        Graph<Integer, Edge> graph =
                initializeGraph(getWeightColumnName(), GraphCreator.UNDIRECTED);

        addVertices(graph);
        addWeightedEdges((WeightedGraph) graph);

        return (UndirectedGraph) graph;
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
    private Graph<Integer, Edge> initializeGraph(
            String weightColumnName,
            int orientation) {
        // Unweighted
        if (weightColumnName == null) {
            if (orientation != UNDIRECTED) {
                return new DirectedMultigraph<Integer, Edge>(Edge.class);
            } else {
                return new Multigraph<Integer, Edge>(Edge.class);
            }
        } // Weighted
        else {
            if (orientation != UNDIRECTED) {
                return new DirectedWeightedMultigraph<Integer, Edge>(Edge.class);
            } else {
                return new WeightedMultigraph<Integer, Edge>(Edge.class);
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
