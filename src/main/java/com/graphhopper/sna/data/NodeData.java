/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.data;

import com.graphhopper.sna.centrality.GraphAnalyzer;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;

/**
 * Stores information about the distances and the number of shortest paths from
 * a given node to all other nodes in the network as well as the dependency of
 * the given node on all other nodes in the network.
 *
 * @author Adam Gouge
 */
public class NodeData {

    /**
     * The node we are talking about.
     */
    private int node;
    /**
     * The {@link com.graphhopper.sna.centrality.GraphAnalyzer} used in this
     * calculation.
     */
    private GraphAnalyzer analyzer;
    /**
     * Keeps track of the distances from this node to all other nodes.
     */
    private TIntIntHashMap distances;
    /**
     * Keeps track of the number of shortest paths from this node.
     */
    private TIntIntHashMap shortestPathsCount;
    /**
     * The dependency of this node on other nodes in the network.
     */
    private TIntDoubleHashMap dependency;

    /**
     * Construct a {@link NodeData} object for the given node.
     *
     * @param node     The given node.
     * @param analyzer The {@link GraphAnalyzer} used in this calculation.
     */
    public NodeData(int node, GraphAnalyzer analyzer) {
        this.node = node;
        this.analyzer = analyzer;
        this.distances = initDistances();
        this.shortestPathsCount = initShortestPathsCount();
        this.dependency = initDependencies();
    }

    /**
     * Gets the distances from this node to all other nodes at this stage in the
     * calculation.
     *
     * @return the distances
     */
    public TIntIntHashMap getDistances() {
        return distances;
    }

    /**
     * Gets the number of shortest paths from this node to all other nodes at
     * this stage in the calculation.
     *
     * @return the shortestPathsCount
     */
    public TIntIntHashMap getShortestPathsCount() {
        return shortestPathsCount;
    }

    /**
     * Gets the dependency of this node on all other nodes at this stage in the
     * calculation.
     *
     * @return the dependency
     */
    public TIntDoubleHashMap getDependency() {
        return dependency;
    }

    /**
     * Initialize the number of shortest paths from this node to itself to be 1,
     * and to all other nodes to be 0.
     *
     * @return The initialized shortest paths counts.
     */
    private TIntIntHashMap initShortestPathsCount() {
        return initTIntIntHashMap(0, node, 1);
    }

    /**
     * Initialize distance from this node to itself to 0 and to all other nodes
     * to -1.
     *
     * @return The initialized distances.
     */
    private TIntIntHashMap initDistances() {
        return initTIntIntHashMap(-1, node, 0);
    }

    /**
     * Initializes all dependencies of this node to 0.0.
     *
     * @return The initialized dependencies.
     */
    private TIntDoubleHashMap initDependencies() {
        return initTIntDoubleHashMap(0.0, node, 0.0);
    }

    /**
     * Initializes all keys of the given {@link TIntIntHashMap} to the given
     * default value except for the special key, which is initialized to the
     * special value.
     *
     * @param defaultValue default value
     * @param specialKey special key
     * @param specialValue special value
     *
     * @return The initialized {@link TIntIntHashMap}.
     */
    private TIntIntHashMap initTIntIntHashMap(
            int defaultValue,
            int specialKey,
            int specialValue) {
        TIntIntHashMap hashMap = new TIntIntHashMap();
        TIntIterator it = analyzer.nodeSet().iterator();
        while (it.hasNext()) {
            hashMap.put(
                    it.next(),
                    defaultValue);
        }
        hashMap.put(specialKey, specialValue);
        return hashMap;
    }

    /**
     * Initializes all keys of the given {@link TIntDoubleHashMap} to the given
     * default value except for the special key, which is initialized to the
     * special value.
     *
     * @param defaultValue default value
     * @param specialKey special key
     * @param specialValue special value
     *
     * @return The initialized {@link TIntDoubleHashMap}.
     */
    private TIntDoubleHashMap initTIntDoubleHashMap(
            double defaultValue,
            int startNode,
            double startNodeValue) {
        TIntDoubleHashMap hashMap = new TIntDoubleHashMap();
        TIntIterator it = analyzer.nodeSet().iterator();
        while (it.hasNext()) {
            hashMap.put(
                    it.next(),
                    defaultValue);
        }
        hashMap.put(startNode, startNodeValue);
        return hashMap;
    }
}
