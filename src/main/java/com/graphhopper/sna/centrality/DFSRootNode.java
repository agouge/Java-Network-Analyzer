/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.DFSInfo;
import com.graphhopper.sna.data.StrahlerInfo;
import com.graphhopper.storage.Graph;
import java.util.Map;

/**
 * Does a DFS from a given root node.
 *
 * @author Adam Gouge
 */
public class DFSRootNode<T extends DFSInfo> extends DFS<T> {

    /**
     * The root node.
     */
    protected final int rootNode;

    /**
     * Constructor.
     *
     * @param graph     The graph.
     * @param nodeMap   Maps nodes to their info.
     * @param startNode The start node.
     */
    public DFSRootNode(Graph graph,
                       final Map<Integer, T> nodeMap,
                       int rootNode) {
        super(graph, nodeMap);
        this.rootNode = rootNode;
    }

    /**
     * Start a DFS search from the root node.
     */
    @Override
    protected void calculate() {
        visit(rootNode);
    }
}
