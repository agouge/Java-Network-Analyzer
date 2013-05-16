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
 * Vertex to be used during the betweenness calculation.
 *
 * @author Adam Gouge
 */
public abstract class VBetw<V, D extends Number>
        extends VId implements VSearch<V, D> {

    /**
     * Id
     */
    protected int id;
    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    protected HashSet<V> predecessors;
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
     * Initializes a new instance of {@link VBetw}, setting the
     * predecessor list to be empty; the shortest paths count, dependency,
     * betweenness and closeness to 0; the distance variable is initialized in
     * the appropriate subclass.
     */
    public VBetw(int id) {
//        outedges = new TIntLinkedList();
        super(id);
        predecessors = new HashSet<V>();
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
    public HashSet<V> getPredecessors() {
        return predecessors;
    }

    @Override
    public void addPredecessor(V pred) {
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
