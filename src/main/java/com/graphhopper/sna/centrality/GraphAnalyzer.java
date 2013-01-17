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

import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
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
     * Returns a {@link TIntHashSet} of the nodes of this graph.
     *
     * @return a {@link TIntHashSet} of the nodes of this graph.
     */
    public TIntHashSet nodeSet() {
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

    /**
     * Print the distances of all nodes from the given node using the given
     * distance hash map.
     *
     * @param distances The distance hash map.
     * @param node      The given node.
     */
    protected void printDistancesFromNode(TIntIntHashMap distances, int node) {
        TIntIntIterator distanceIter = distances.iterator();
        while (distanceIter.hasNext()) {
            distanceIter.advance();
            System.out.print("Distance from " + node
                    + " to " + distanceIter.key()
                    + ": ");
            if (distanceIter.value() == Integer.MAX_VALUE) {
                System.out.println("---");
            } else {
                System.out.println(distanceIter.value());
            }
        }
    }

    /**
     * Print the betweenness centrality using the given hash map.
     *
     * @param distances The betweenness hash map.
     */
    protected void printBetweenness(TIntDoubleHashMap betweenness) {
        TIntDoubleIterator it = betweenness.iterator();
        while (it.hasNext()) {
            it.advance();
            System.out.println("Betweenness of " + it.key()
                    + " is " + it.value() + ".");
        }
    }
}
