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

import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.storage.Graph;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.set.hash.TIntHashSet;
import java.util.HashMap;

/**
 * Calculates various centrality measures on a weighted graph.
 *
 * @author Adam Gouge
 */
// TODO: Implement betweenness centrality.
public class WeightedGraphAnalyzer extends GraphAnalyzer {

    /**
     * Initializes a new instance of a weighted graph analyzer.
     *
     * @param graph The graph to be analyzed.
     */
    public WeightedGraphAnalyzer(Graph graph) {
        super(graph);
    }

    /**
     * Computes the closeness centrality indices of all vertices of the graph
     * (assumed to be connected) and stores them in a hash map, where the keys
     * are the vertices and the values are the closeness.
     *
     * <p> This method first computes contraction hierarchies on the given
     * graph, which greatly reduces the shortest paths computation time.
     *
     * @return The closeness centrality hash map.
     */
    @Override
    public TIntDoubleHashMap computeCloseness() {
        ClosenessCentrality closenessCentrality =
                new ClosenessCentrality(graph);
        return closenessCentrality.calculateUsingContractionHierarchies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashMap<Integer, NodeBetweennessInfo> computeAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
