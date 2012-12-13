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
package com.graphhoppersna.data;

import gnu.trove.list.linked.TIntLinkedList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Storage class for information needed by node betweenness calculation. <p> An
 * instance of this class is assigned to every node during the computation of
 * node and edge betweenness. </p>
 *
 * @author Adam Gouge
 */
public class NodeBetweennessInfo {

    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    private TIntLinkedList predecessors;
    /**
     * List of outgoing edges of this node visited by exploring the network.
     */
    private TIntLinkedList outedges;
    /**
     * Number of shortest paths leading to this node starting from a certain
     * source.
     */
    private long spCount;
    /**
     * Length of shortest path starting from certain source and leading to this
     * node.
     */
    private int spLength;
    /**
     * Dependency of this node on any other vertex.
     */
    private double dependency;
    /**
     * Betweenness value of this node.
     */
    private double betweenness;

    /**
     * Initializes a new instance of {@link NodeBetweennessInfo}.
     *
     * @param initCount       Number of shortest paths to this node. Default
     *                        value is usually <code>0</code>.
     * @param initLength      Length of a shortest path to this node Default
     *                        value is usually <code>-1</code>.
     * @param initBetweenness Node betweenness value of this node. Default value
     *                        is usually <code>0</code>.
     */
    public NodeBetweennessInfo(long initCount, int initLength,
            double initBetweenness) {
        spCount = initCount;
        spLength = initLength;
        dependency = 0.0;
        betweenness = initBetweenness;
        predecessors = new TIntLinkedList();
        outedges = new TIntLinkedList();
    }

    /**
     * Gets the length of the shortest path from a source node to this node
     *
     * @return spLength Shortest path length to this node
     */
    public int getSPLength() {
        return spLength;
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
     * Gets the betweenness value for this node
     *
     * @return betweenness Betweenness value of this node
     */
    public double getBetweenness() {
        return betweenness;
    }

    /**
     * Retrieves the next predecessor of this node.
     *
     * @return predecessor First one of the predecessors of this node. The
     *         returned element is removed from the list of precedecessors.
     *
     * @throws NoSuchElementException If this node does not have predecessors.
     */
    public int pullPredecessor() {
//        return predecessors.removeFirst();
        return predecessors.removeAt(0);
    }

    /**
     * Gets the list of outgoing edges of this node visited by exploring the
     * network
     *
     * @return outedges List of outgoing edges of this node visited by exploring
     *         the network
     */
    public TIntLinkedList getOutEdges() {
        return outedges;
    }

    /**
     * Checks if the predecessor list is empty
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmptyPredecessors() {
        if (predecessors.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Sets the new length of the shortest path to this node from a source node
     *
     * @param newLength Length of the shortest path to this node
     */
    public void setSPLength(int newLength) {
        spLength = newLength;
    }

    /**
     * Accumulates the number of shortest paths leading to this node
     *
     * @param newSPCount Number of further shortest paths leading to this node
     */
    public void addSPCount(long newSPCount) {
        spCount += newSPCount;
    }

    /**
     * Accumulates the dependency of this node
     *
     * @param newDependency New dependency to be added
     */
    public void addDependency(double newDependency) {
        dependency += newDependency;
    }

    /**
     * Adds a predecessor to the predecessor list of this node
     *
     * @param pred Node to be added since it is a predecessor of this node
     */
    public void addPredecessor(int pred) {
        predecessors.add(pred);
    }

    /**
     * Adds a further visited outgoing edge for this node
     *
     * @param outedge Visited outgoing edge of this node
     */
    public void addOutedge(int outedge) {
        outedges.add(outedge);
    }

    /**
     * Accumulates the betweenness value in each run by adding the new
     * betweenness value to the old
     *
     * @param newBetweenness betweenness value from a run starting at certain
     *                       source node
     */
    public void addBetweenness(double newBetweenness) {
        betweenness += newBetweenness;
    }

    /**
     * Resets all variables for the calculation of edge and node betweenness to
     * their default values except the node betweenness.
     */
    public void reset() {
        spCount = 0;
        spLength = -1;
        dependency = 0.0;
        predecessors = new TIntLinkedList();
        outedges = new TIntLinkedList();
    }

    /**
     * Changes the shortest path count and length for the source node of this
     * run if BFS
     */
    public void setSource() {
        spCount = 1;
        spLength = 0;
        dependency = 0.0;
        predecessors = new TIntLinkedList();
        outedges = new TIntLinkedList();
    }
}
