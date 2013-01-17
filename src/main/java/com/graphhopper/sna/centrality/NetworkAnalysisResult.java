/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;

/**
 * A data structure to hold the results of {@link GraphAnalyzer#computeAll()}.
 *
 * For now, we store betweenness and closeness centrality.
 *
 * @author Adam Gouge
 */
public class NetworkAnalysisResult {

    /**
     * Betweenness centrality.
     */
    private TIntDoubleHashMap betweennessCentrality = new TIntDoubleHashMap();
    /**
     * Closeness centrality.
     */
    private TIntDoubleHashMap closenessCentrality = new TIntDoubleHashMap();
    /**
     * The {@link GraphAnalyzer} used to do graph analysis.
     */
    private GraphAnalyzer analyzer;

    /**
     * Initializes a {@link NetworkAnalysisResult} to be returned by the given
     * {@link GraphAnalyzer}.
     *
     * @param analyzer The given {@link GraphAnalyzer}.
     */
    public NetworkAnalysisResult(GraphAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.betweennessCentrality = initBetweenness();
        this.closenessCentrality = new TIntDoubleHashMap();
    }

    /**
     * Initializes all betweenness centrality values to 0.0.
     *
     * @return The initialized betweenness hash map.
     */
    private TIntDoubleHashMap initBetweenness() {
        TIntIterator it = analyzer.nodeSet().iterator();
        while (it.hasNext()) {
            betweennessCentrality.put(
                    it.next(),
                    0.0);
        }
        return betweennessCentrality;
    }

    /**
     * Gets the betweenness centrality.
     *
     * @return The betweenness Centrality
     */
    public TIntDoubleHashMap getBetweenness() {
        return betweennessCentrality;
    }

    /**
     * Gets the closeness centrality.
     *
     * @return The closeness Centrality
     */
    public TIntDoubleHashMap getCloseness() {
        return closenessCentrality;
    }
}
