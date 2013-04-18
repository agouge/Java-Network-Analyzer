/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.alg;

import java.util.Set;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DirectedNeighborIndex;
import org.jgrapht.alg.NeighborIndex;

/**
 * Root class for graph search algorithms, including BFS, DFS, Dijkstra, etc.,
 * and their modified versions for centrality and connectedness calculations.
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
     * Neighbor index.
     */
    protected final NeighborIndex<V, E> neighborIndex;
    /**
     * Directed neighbor index.
     */
    protected final DirectedNeighborIndex<V, E> directedNeighborIndex;

    /**
     * Constructs a new {@link GraphSearchAlgorithm} object.
     *
     * @param graph The graph.
     */
    public GraphSearchAlgorithm(Graph<V, E> graph) {
        this.graph = graph;
        this.directedNeighborIndex = 
                new DirectedNeighborIndex<V, E>((DirectedGraph) graph);
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

    /**
     * Returns the outdegree (or degree for undirected graphs) of the given
     * node.
     *
     * @param node The node.
     *
     * @return The outdegree (or degree for undirected graphs) of the given
     *         node.
     */
    // 
    public int outdegree(V node) {
        if (graph instanceof DirectedGraph) {
            return ((DirectedGraph) graph).outDegreeOf(node);
        } else if (graph instanceof UndirectedGraph) {
            return ((UndirectedGraph) graph).degreeOf(node);
        }
        return -1;
    }
}
