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
 * Node info needed by the node betweenness calculation. <p> An instance of this
 * class is assigned to every node during the computation of node betweenness.
 * </p>
 *
 * @author Adam Gouge
 */
public abstract class NodeBetweennessInfo<N, T extends Number>
        extends IdInfo implements SearchInfo<N, T> {

    /**
     * IdInfo
     */
    protected int id;
    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    protected HashSet<N> predecessors;
    /**
     * Number of shortest paths leading to this node starting from a certain
     * source.
     */
    protected long spCount;
    /**
     * Dependency of this node on any other vertex.
     */
    protected double dependency;
    /**
     * Betweenness value of this node.
     */
    private double betweenness;
    /**
     * Closeness value of this node.
     */
    private double closeness;

    /**
     * Initializes a new instance of {@link NodeBetweennessInfo}, setting the
     * predecessor list to be empty; the shortest paths count, dependency,
     * betweenness and closeness to 0; the distance variable is initialized in
     * the appropriate subclass.
     */
    public NodeBetweennessInfo(int id) {
//        outedges = new TIntLinkedList();
        super(id);
        predecessors = new HashSet<N>();
        spCount = 0;
        betweenness = 0.0;
        dependency = 0.0;
        closeness = 0.0;
    }

    /**
     * Resets all variables for the calculation of edge and node betweenness to
     * their default values except the betweenness and closeness.
     */
    public void reset() {
        spCount = 0;
        dependency = 0.0;
        predecessors.clear();
    }

    /**
     * Sets this node as the source node during initialization.
     */
    @Override
    public void setSource() {
        spCount = 1;
        dependency = 0.0;
    }

// ************************** PREDECESSORS **************************
    @Override
    public HashSet<N> getPredecessors() {
        return predecessors;
    }

    @Override
    public void addPredecessor(N pred) {
        predecessors.add(pred);
    }

// ************************** SP COUNT *****************************
    /**
     * Gets the number of shortest paths to this node
     *
     * @return spCount Number of shortest paths to this node
     */
    public long getSPCount() {
        return spCount;
    }

    /**
     * Accumulates the number of shortest paths leading to this node
     *
     * @param additionalSPCount Number of further shortest paths leading to this
     *                          node
     */
    public void accumulateSPCount(long additionalSPCount) {
        spCount += additionalSPCount;
    }

    /**
     * Resets the number of shortest paths leading to this node
     *
     * @param newSPCount New number shortest paths leading to this node
     */
    public void setSPCount(long newSPCount) {
        spCount = newSPCount;
    }

// ************************** DEPENDENCY **************************
    /**
     * Gets the dependency of this node to any other vertex
     *
     * @return dependency Dependency of this node to any other vertex
     */
    public double getDependency() {
        return dependency;
    }

    /**
     * Accumulates the dependency of this node
     *
     * @param additionalDependency New dependency to be added
     */
    public void accumulateDependency(double additionalDependency) {
        dependency += additionalDependency;
    }

// ************************** BETWEENNESS **************************
    /**
     * Gets the betweenness value for this node.
     *
     * @return The betweenness value of this node.
     */
    public double getBetweenness() {
        return betweenness;
    }

    /**
     * Accumulates the betweenness value in each run by adding the new
     * betweenness value to the old
     *
     * @param additionalBetweenness betweenness value from a run starting at
     *                              certain source node
     */
    public void accumulateBetweenness(double additionalBetweenness) {
        setBetweenness(getBetweenness() + additionalBetweenness);
    }

    /**
     * Sets the betweenness.
     *
     * @param betweenness The betweenness to set.
     */
    public void setBetweenness(double betweenness) {
        this.betweenness = betweenness;
    }

// ************************** CLOSENESS **************************
    /**
     * Returns the closeness.
     *
     * @return The closeness.
     */
    public double getCloseness() {
        return closeness;
    }

    /**
     * Sets the closeness.
     *
     * @param closeness The closeness to set.
     */
    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }
}
