/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.alg;

import org.jgrapht.Graph;

/**
 * Graph search algorithms that are launched from a given start node.
 *
 * @author Adam Gouge
 */
public class GraphSearchAlgorithmStartNode<V, E> extends GraphSearchAlgorithm<V, E> {

    /**
     * Start node.
     */
    protected final V startNode;

    public GraphSearchAlgorithmStartNode(Graph<V, E> graph, V startNode) {
        super(graph);
        this.startNode = startNode;
    }
}
