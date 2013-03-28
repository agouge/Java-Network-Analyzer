/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.DFSInfo;
import com.graphhopper.storage.Graph;
import java.lang.reflect.InvocationTargetException;
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
                       Class<? extends T> infoClass,
                       int rootNode) throws NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        super(graph, infoClass);
        this.rootNode = rootNode;
    }

    /**
     * Start a DFS search from the root node.
     */
    @Override
    public Map<Integer, T> calculate() {
        visit(rootNode);
        return nodeMap;
    }
}
