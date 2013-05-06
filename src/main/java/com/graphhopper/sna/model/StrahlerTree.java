/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.model;

import com.graphhopper.sna.data.StrahlerInfo;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

/**
 * A graph for use in the Strahler algorithm.
 *
 * @param <E> Edge class.
 *
 * @author Adam Gouge
 */
public class StrahlerTree<E> extends DirectedPseudograph<StrahlerInfo, E> {

    /**
     * The root vertex.
     */
    private StrahlerInfo rootVertex = null;
    /**
     * Map of ids to vertices.
     */
    private final Map<Integer, StrahlerInfo> nodeMap;

    /**
     * @see AbstractBaseGraph
     */
    public StrahlerTree(Class<? extends E> edgeClass) {
        super(edgeClass);
        this.nodeMap = new HashMap<Integer, StrahlerInfo>();
    }

    /**
     * @see AbstractBaseGraph
     */
    public StrahlerTree(EdgeFactory<StrahlerInfo, E> ef) {
        super(ef);
        this.nodeMap = new HashMap<Integer, StrahlerInfo>();
    }

    /**
     * Add a vertex with the given id.
     *
     * @param id Id
     *
     * @return True if the vertex was added.
     */
    private boolean addVertex(int id) {
        if (!nodeMap.containsKey(id)) {
            StrahlerInfo node = new StrahlerInfo(id);
            nodeMap.put(id, node);
            return addVertex(node);
        } else {
            throw new IllegalStateException(
                    "This vertex has already been added!");
        }
    }

    /**
     * Get the vertex with the given id.
     *
     * @param id Id.
     *
     * @return The vertex with the given id.
     */
    public StrahlerInfo getVertex(int id) {
        return nodeMap.get(id);
    }

    /**
     * Set the root vertex as the vertex with the given id.
     *
     * @param id Id.
     */
    public void setRootVertex(int id) {
        StrahlerInfo candidate = getVertex(id);
        if (candidate != null) {
            rootVertex = candidate;
        } else {
            throw new IllegalStateException(
                    "This vertex does not exist!");
        }
    }

    /**
     * Return the root vertex.
     *
     * @return The root vertex.
     */
    public StrahlerInfo getRootVertex() {
        return rootVertex;
    }

    /**
     * Add an edge from the vertex with id source to the vertex with id target.
     *
     * @param source Source id.
     * @param target Target Id.
     *
     * @return The newly added edge, or null if it was not added.
     */
    public E addEdge(int source, int target) {
        if (!containsVertex(getVertex(source))) {
            addVertex(source);
        }
        if (!containsVertex(getVertex(target))) {
            addVertex(target);
        }
        return addEdge(getVertex(source), getVertex(target));
    }
}
