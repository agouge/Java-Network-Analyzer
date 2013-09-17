package org.javanetworkanalyzer.model;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleDirectedGraph;

/**
 * Shortest path "tree" (multiple shortest paths are allowed) for use in
 * {@link org.javanetworkanalyzer.alg.Dijkstra}.
 *
 * @author Adam Gouge
 */
public class ShortestPathTree<V, E> extends SimpleDirectedGraph<V, E> {

    private V root;

    /**
     * Constructor
     *
     * @param edgeClass class on which to base factory for edges
     * @param root      Root
     */
    public ShortestPathTree(Class<? extends E> edgeClass, V root) {
        super(edgeClass);
        this.root = root;
    }

    /**
     * Constructor
     *
     * @param ef   the edge factory of the new graph.
     * @param root Root
     */
    public ShortestPathTree(EdgeFactory<V, E> ef, V root) {
        super(ef);
        this.root = root;
    }

    /**
     * Return the root of this shortest path tree.
     *
     * @return The root of this shortest path tree
     */
    public V getRoot() {
        return root;
    }

}
