/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.storage.Graph;

/**
 * Root class for graph search algorithms, including BFS, Dijkstra, etc., and
 * their modified versions for centrality and connectedness calculations.
 *
 * @author Adam Gouge
 */
public class GraphSearchAlgorithm {

    /**
     * The graph on which to calculate shortest paths.
     */
    protected final Graph graph;
    /**
     * Start node.
     */
    protected final int startNode;

    /**
     * Constructs a new {@link GraphSearchAlgorithm} object.
     *
     * @param graph           The graph.
     * @param nodeBetweenness The hash map.
     * @param startNode       The start node.
     */
    public GraphSearchAlgorithm(Graph graph,
                                int startNode) {
        this.graph = graph;
        this.startNode = startNode;
    }
}
