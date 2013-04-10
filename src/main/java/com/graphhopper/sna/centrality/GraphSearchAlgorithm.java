/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.NeighborIndex;

/**
 * Root class for graph search algorithms, including BFS, Dijkstra, etc., and
 * their modified versions for centrality and connectedness calculations.
 *
 * @param <V> Vertices
 * @param <E> Edges
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
     * @param graph     The graph.
     * @param startNode The start node.
     */
    public GraphSearchAlgorithm(Graph<V, E> graph, V startNode) {
        this.graph = graph;
        this.startNode = startNode;
        this.neighborIndex = new NeighborIndex<V, E>(graph);
    }

    /**
     * Returns the outgoing edges of a node for directed graphs and all edges of
     * a node for undirected graphs.
     *
     * @param node The node.
     *
     * @return The outgoing edges of the node.
     */
    public Set<E> outgoingEdgesOf(V node) {
        if (graph instanceof DirectedGraph) {
            return ((DirectedGraph) graph).outgoingEdgesOf(node);
        } else {
            return graph.edgesOf(node);
        }
    }
}
