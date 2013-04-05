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
package com.graphhopper.sna.data;

import java.util.HashSet;

/**
 * Node info for DFS algorithms containing the discovery and finishing times.
 *
 * @author Adam Gouge
 */
public class DFSInfo implements PredecessorInfo<DFSInfo> {

    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    protected HashSet<DFSInfo> predecessors;
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
    public DFSInfo() {
        predecessors = new HashSet<DFSInfo>();
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
    public HashSet<DFSInfo> getPredecessors() {
        return predecessors;
    }

    @Override
    public void addPredecessor(DFSInfo pred) {
        predecessors.add(pred);
    }
}
