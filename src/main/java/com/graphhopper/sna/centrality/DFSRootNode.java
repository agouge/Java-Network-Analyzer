/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.DFSInfo;
import java.util.Map;
import org.jgrapht.Graph;

/**
 * Does a DFS from a given root node.
 *
 * @author Adam Gouge
 */
public class DFSRootNode<V extends DFSInfo, E> extends DFS<V, E> {

    /**
     * The root node.
     */
    protected final V rootNode;

    /**
     * Constructor.
     *
     * @param graph    The graph.
     * @param nodeMap  Maps nodes to their info.
     * @param rootNode The root node.
     */
    public DFSRootNode(Graph<V, E> graph, V rootNode) {
        super(graph);
        this.rootNode = rootNode;
    }

    /**
     * Start a DFS search from the root node.
     */
    @Override
    public void calculate() {
        visit(rootNode);
    }
}
