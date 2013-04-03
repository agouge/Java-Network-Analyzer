/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.routing.util.AllEdgesIterator;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.DefaultEdgeFilter;
import static com.graphhopper.sna.centrality.GraphAnalyzer.nodeSet;
import com.graphhopper.sna.progress.ProgressMonitor;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import gnu.trove.set.hash.TIntHashSet;

/**
 * Root class of all classes that do some type of analysis on graphs
 * (centrality, connectedness, etc.).
 *
 * @author Adam Gouge
 */
public abstract class GeneralizedGraphAnalyzer {

    /**
     * The graph to be analyzed.
     */
    protected final Graph graph;
    /**
     * The set of nodes of this graph.
     */
    protected final TIntHashSet nodeSet;
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
    public GeneralizedGraphAnalyzer(Graph graph) {
        this.graph = graph;
        this.nodeSet = nodeSet(this.graph);
        this.nodeCount = this.nodeSet.size();
    }

    /**
     * Returns a {@link TIntHashSet} of the nodes of the given graph.
     *
     * @param graph The graph.
     *
     * @return a {@link TIntHashSet} of the nodes of the given graph.
     */
    // TODO: Optimize this (by making use of the data structure).
    protected static TIntHashSet nodeSet(Graph graph) {
        // Initialize the Set.
        TIntHashSet set = new TIntHashSet();
        // Get all the edges.
        AllEdgesIterator iter = graph.getAllEdges();
        // Add each source and destination node to the set.
        while (iter.next()) {
            set.add(iter.baseNode());
            set.add(iter.adjNode());
        }
        return set;
    }

    /**
     * Returns the outdegree of the given node of the given graph.
     *
     * @param graph The graph.
     * @param node  The node.
     *
     * @return The outdegree.
     */
    protected static int outDegree(Graph graph, int node) {
        int outDegree = 0;
        EdgeIterator outgoingEdges = outgoingEdges(graph, node);
        while (outgoingEdges.next()) {
            outDegree++;
        }
        return outDegree;
    }

    /**
     * Get an iterator on the outgoing edges of the given node.
     *
     * @param graph The graph
     * @param node  The node
     *
     * @return An iterator on the outgoing edges of the given node.
     */
    protected static EdgeIterator outgoingEdges(Graph graph, int node) {
        return graph.getEdges(node,
                              new DefaultEdgeFilter(new CarFlagEncoder(),
                                                    false,
                                                    true));
    }
}
