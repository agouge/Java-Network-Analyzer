/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import org.jgrapht.Graph;
import org.jgrapht.alg.NeighborIndex;

/**
 * Root class for graph search algorithms, including BFS, Dijkstra, etc., and
 * their modified versions for centrality and connectedness calculations.
 *
 * @author Adam Gouge
 */
public class GraphSearchAlgorithm<V, E> {

    /**
     * The graph on which to calculate shortest paths.
     */
    protected final Graph<V, E> graph;
    /**
     * Start node.
     */
    protected final V startNode;
    /**
     * Neighbor index.
     */
    protected final NeighborIndex<V, E> neighborIndex;

    /**
     * Constructs a new {@link GraphSearchAlgorithm} object.
     *
     * @param graph           The graph.
     * @param nodeBetweenness The hash map.
     * @param startNode       The start node.
     */
    public GraphSearchAlgorithm(Graph<V, E> graph,
                                V startNode) {
        this.graph = graph;
        this.startNode = startNode;
        this.neighborIndex = new NeighborIndex<V, E>(graph);
    }
}
