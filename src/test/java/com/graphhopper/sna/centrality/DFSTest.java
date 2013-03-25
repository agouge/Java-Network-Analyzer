/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.DFSInfo;
import com.graphhopper.storage.Graph;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Tests the Depth First Search (DFS).
 *
 * @author Adam Gouge
 */
public class DFSTest {

    /**
     * Tests the {@link Graphs#weightedDirected()} graph.
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test2DGraph() throws FileNotFoundException {

        Graph graph = Graphs.weightedDirected();
        Map<Integer, DFSInfo> nodeMap = init(graph);

        DFS dfs = new DFS(graph, nodeMap);
        dfs.calculate();
    }

    /**
     * Initializes the node info map.
     *
     * @param graph The input graph.
     *
     * @return The newly initialized node info map.
     */
    private Map<Integer, DFSInfo> init(Graph graph) {
        Map<Integer, DFSInfo> nodeMap = new HashMap<Integer, DFSInfo>();
        TIntHashSet nodeSet = GeneralizedGraphAnalyzer.nodeSet(graph);
        TIntIterator it = nodeSet.iterator();
        while (it.hasNext()) {
            nodeMap.put(it.next(), new DFSInfo());
        }
        return nodeMap;
    }
}
