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

/**
 * Vertex containing discovery and finishing times for DFS.
 *
 * @author Adam Gouge
 */
public class VDFS extends VId implements VPred<VDFS> {

    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    protected HashSet<VDFS> predecessors;
    /**
     * The time this node was discovered.
     */
    private int discoveryTime;
    /**
     * The time at which this node finished processing.
     */
    private int finishingTime;

    /**
     * Constructor.
     */
    public VDFS(Integer id) {
        super(id);
        predecessors = new HashSet<VDFS>();
        this.discoveryTime = -1;
        this.finishingTime = -1;
    }

    /**
     * Returns the time this node was discovered.
     *
     * @return The time this node was discovered.
     */
    public int getDiscoveryTime() {
        return discoveryTime;
    }

    /**
     * Sets the time this node was discovered.
     *
     * @param newTime The time this node was discovered.
     */
    public void setDiscoveryTime(int newTime) {
        discoveryTime = newTime;
    }

    /**
     * Returns the time at which this node finished processing.
     *
     * @return The time at which this node finished processing.
     */
    public int getFinishingTime() {
        return finishingTime;
    }

    /**
     * Sets the time at which this node finished processing.
     *
     * @param newTime The time at which this node finished processing.
     */
    public void setFinishingTime(int newTime) {
        finishingTime = newTime;
    }

    @Override
    public HashSet<VDFS> getPredecessors() {
        return predecessors;
    }

    @Override
    public void addPredecessor(VDFS pred) {
        predecessors.add(pred);
    }
}
