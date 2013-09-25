package org.javanetworkanalyzer.model;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

/**
 * A simple directed graph for shortest path "trees" (multiple shortest paths
 * are allowed) in {@link org.javanetworkanalyzer.alg.Dijkstra} and {@link
 * org.javanetworkanalyzer.alg.BFS} as well as {@link
 * org.javanetworkanalyzer.alg.DFS} traversal graphs.
 *
 * @author Adam Gouge
 */
public class TraversalGraph<V, E> extends DirectedMultigraph<V, E> {

    private V root;

    /**
     * Constructor
     *
     * @param edgeClass class on which to base factory for edges
     * @param root      Root
     */
    public TraversalGraph(Class<? extends E> edgeClass, V root) {
        super(edgeClass);
        this.root = root;
        addVertex(root);
    }

    /**
     * Constructor
     *
     * @param ef   the edge factory of the new graph.
     * @param root Root
     */
    public TraversalGraph(EdgeFactory<V, E> ef, V root) {
        super(ef);
        this.root = root;
    }

    /**
     * Return the root.
     *
     * @return The root
     */
    public V getRoot() {
        return root;
    }
}
