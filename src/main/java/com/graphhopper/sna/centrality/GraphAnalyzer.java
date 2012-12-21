/**
 * GraphHopper-SNA implements a collection of social network analysis
 * algorithms. It is based on the <a
 * href="http://graphhopper.com/">GraphHopper</a> library.
 *
 * GraphHopper-SNA is distributed under the GPL 3 license. It is produced by the
 * "Atelier SIG" team of the <a href="http://www.irstv.fr">IRSTV Institute</a>,
 * CNRS FR 2488.
 *
 * Copyright 2012 IRSTV (CNRS FR 2488)
 *
 * GraphHopper-SNA is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * GraphHopper-SNA is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * GraphHopper-SNA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.PathLengthData;
import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.set.hash.TIntHashSet;

/**
 * Calculates various centrality measures on the given graph.
 *
 * @author Adam Gouge
 */
public abstract class GraphAnalyzer {

    /**
     * The graph to be analyzed.
     */
    protected final Graph graph;
    /**
     * The number of nodes in this graph.
     */
    protected final int nodeCount;

    /**
     * Initializes a new instance of a graph analyzer.
     *
     * @param graph The graph to be analyzed.
     */
    public GraphAnalyzer(Graph graph) {
        this.graph = graph;
        this.nodeCount = graph.getNodes();
    }

    /**
     * Computes the closeness centrality indices of all vertices of the graph
     * (assumed to be connected) and stores them in a hash map, where the keys
     * are the vertices and the values are the closeness.
     *
     * @return The closeness centrality hash map.
     */
    public abstract TIntDoubleHashMap computeCloseness();

    /**
     * Computes the shortest path lengths from the given node to all other nodes
     * in the graph and stores them in a {@link PathLengthData} object.
     *
     * @param startNode Start node of the shortest paths to be found.
     *
     * @return Data on the shortest path lengths from the current node to all
     *         other reachable nodes in the graph.
     */
    protected abstract PathLengthData computeShortestPathsData(int startNode);

    /**
     * Returns a {@link TIntHashSet} of nodes of this graph.
     *
     * @return a {@link TIntHashSet} of nodes of this graph.
     */
    protected TIntHashSet nodeSet() {
        // Initialize the Set.
        TIntHashSet nodeSet = new TIntHashSet();
        // Get all the edges.
        EdgeIterator iter = graph.getAllEdges();
        // Add each source and destination node to the set.
        while (iter.next()) {
            nodeSet.add(iter.baseNode());
            nodeSet.add(iter.node());
        }
        return nodeSet;
    }
}
