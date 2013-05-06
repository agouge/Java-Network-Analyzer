/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javanetworkanalyzer.analyzers;

import org.javanetworkanalyzer.progress.ProgressMonitor;
import java.util.Set;
import org.jgrapht.Graph;

/**
 * Root class of all classes that do some type of analysis on graphs
 * (centrality, connectedness, etc.).
 *
 * @author Adam Gouge
 */
public abstract class GeneralizedGraphAnalyzer<V, E> {

    /**
     * The graph to be analyzed.
     */
    protected final Graph<V, E> graph;
    /**
     * The set of nodes of this graph.
     */
    protected final Set<V> nodeSet;
    /**
     * The number of nodes in this graph.
     */
    protected final int nodeCount;

    /**
     * Initializes a new instance of a graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph The graph to be analyzed.
     * @param pm    The {@link ProgressMonitor} to be used.
     */
    public GeneralizedGraphAnalyzer(Graph<V, E> graph) {
        this.graph = graph;
        this.nodeSet = graph.vertexSet();
        this.nodeCount = this.nodeSet.size();
    }
}