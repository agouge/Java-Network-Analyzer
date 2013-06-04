/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is distributed under the GPL 3 license. It is produced
 * by the "Atelier SIG" team of the <a href="http://www.irstv.fr">IRSTV
 * Institute</a>, CNRS FR 2488.
 *
 * Copyright 2013 IRSTV (CNRS FR 2488).
 *
 * Java Network Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Java Network Analyzer is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Java Network Analyzer. If not, see <http://www.gnu.org/licenses/>.
 */
package org.javanetworkanalyzer.data;

import java.util.HashSet;
import java.util.Set;

/**
 * Vertices which have an id and predecessor(s) on shortest paths from a source
 * node.
 *
 * @param <V> Vertex
 *
 * @author Adam Gouge
 */
public class VPred<V extends VPred> extends VId {

    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    private Set<V> predecessors = new HashSet<V>();

    /**
     * Constructor: sets the id.
     *
     * @param id Id
     */
    public VPred(Integer id) {
        super(id);
    }

    /**
     * Returns the predecessors.
     *
     * @return The predecessors.
     */
    public Set<V> getPredecessors() {
        return predecessors;
    }

    /**
     * Adds a predecessor to the predecessor list of this node
     *
     * @param pred Node to be added since it is a predecessor of this node
     */
    public void addPredecessor(V pred) {
        predecessors.add(pred);
    }

    /**
     * Clears the predecessor list of this node.
     */
    public void clear() {
        predecessors.clear();
    }
}
