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

import gnu.trove.set.hash.TIntHashSet;

/**
 * Storage class for information needed by node betweenness calculation. <p> An
 * instance of this class is assigned to every node during the computation of
 * node and edge betweenness. </p>
 *
 * @author Adam Gouge
 */
public abstract class NodeBetweennessInfo {

    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    protected TIntHashSet predecessors;
    /**
     * Number of shortest paths leading to this node starting from a certain
     * source.
     */
    protected long spCount;
    /**
     * Number of steps on a shortest path from a certain source leading to this
     * node (BFS).
     */
    private int steps;
    /**
     * Length of a shortest path starting from a certain source leading to this
     * node (Dijkstra).
     */
    private double distance;
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
    public NodeBetweennessInfo() {
//        outedges = new TIntLinkedList();
        predecessors = new TIntHashSet();
        steps = -1;
        distance = Double.POSITIVE_INFINITY;
        spCount = 0;
        betweenness = 0.0;
        dependency = 0.0;
        closeness = 0.0;
    }

    /**
     * Gets the number of shortest paths to this node
     *
     * @return spCount Number of shortest paths to this node
     */
    public long getSPCount() {
        return spCount;
    }

    /**
     * Gets the dependency of this node to any other vertex
     *
     * @return dependency Dependency of this node to any other vertex
     */
    public double getDependency() {
        return dependency;
    }

    /**
     * Returns the length of the shortest path from a source node to this node.
     *
     * @return The length of the shortest path from a source node to this node.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns the number of steps from a source node to this node on a shortest
     * path (BFS).
     *
     * @return The number of steps from a source node to this node on a shortest
     *         path (BFS).
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Gets the betweenness value for this node.
     *
     * @return The betweenness value of this node.
     */
    public double getBetweenness() {
        return betweenness;
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

    /**
     * Accumulates the dependency of this node
     *
     * @param additionalDependency New dependency to be added
     */
    public void accumulateDependency(double additionalDependency) {
        dependency += additionalDependency;
    }

    /**
     * Adds a predecessor to the predecessor list of this node
     *
     * @param pred Node to be added since it is a predecessor of this node
     */
    public void addPredecessor(int pred) {
        getPredecessors().add(pred);
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
     * Resets all variables for the calculation of edge and node betweenness to
     * their default values except the betweenness and closeness.
     */
    public void reset() {
        spCount = 0;
        steps = -1;
        distance = Double.POSITIVE_INFINITY;
        dependency = 0.0;
        predecessors.clear();
    }

    /**
     * Sets this node as the source node during initialization.
     */
    public void setSource() {
        spCount = 1;
        steps = 0;
        distance = 0.0;
        dependency = 0.0;
    }

    /**
     * Returns the predecessors.
     *
     * @return The predecessors.
     */
    public TIntHashSet getPredecessors() {
        return predecessors;
    }

    /**
     * Sets the closeness.
     *
     * @param closeness The closeness to set.
     */
    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }

    /**
     * Returns the closeness.
     *
     * @return The closeness.
     */
    public double getCloseness() {
        return closeness;
    }

    /**
     * Sets the betweenness.
     *
     * @param betweenness The betweenness to set.
     */
    public void setBetweenness(double betweenness) {
        this.betweenness = betweenness;
    }

    /**
     * Sets the new length of a shortest path to this node from a source node.
     *
     * @param newDistance Length of a shortest path to this node.
     */
    public void setDistance(double newDistance) {
        distance = newDistance;
    }

    /**
     * Sets the number of steps on a shortest path from a certain source leading
     * to this node.
     *
     * @param newSteps Number of steps to this node.
     */
    public void setSteps(int newSteps) {
        steps = newSteps;
    }
}
