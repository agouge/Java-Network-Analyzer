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

import java.util.Set;

/**
 * Vertex for centrality computations (used by graph analyzers).
 *
 * @param <V> Vertex
 *
 * @author Adam Gouge
 */
public interface VCent<V extends VCent, E> extends VPred<V, E> {

    /**
     * Resets the shortest path count and dependency to zero.
     */
    void reset();

    /**
     * Sets the shortest path count to one and the dependency to zero.
     */
    void setSource();

    /**
     * Returns the id of this node.
     *
     * @return The id of this node.
     */
    int getID();

    /**
     * Returns the predecessors.
     *
     * @return The predecessors.
     */
    Set<V> getPredecessors();

    /**
     * Gets the number of shortest paths to this node
     *
     * @return spCount Number of shortest paths to this node
     */
    long getSPCount();

    /**
     * Accumulates the number of shortest paths leading to this node
     *
     * @param additionalSPCount Number of further shortest paths leading to this
     *                          node
     */
    void accumulateSPCount(long additionalSPCount);

    /**
     * Resets the number of shortest paths leading to this node
     *
     * @param newSPCount New number shortest paths leading to this node
     */
    void setSPCount(long newSPCount);

    /**
     * Gets the dependency of this node to any other vertex
     *
     * @return dependency Dependency of this node to any other vertex
     */
    double getDependency();

    /**
     * Accumulates the dependency of this node
     *
     * @param additionalDependency New dependency to be added
     */
    void accumulateDependency(double additionalDependency);

    /**
     * Gets the betweenness value for this node.
     *
     * @return The betweenness value of this node.
     */
    double getBetweenness();

    /**
     * Accumulates the betweenness value in each run by adding the new
     * betweenness value to the old
     *
     * @param additionalBetweenness betweenness value from a run starting at
     *                              certain source node
     */
    void accumulateBetweenness(double additionalBetweenness);

    /**
     * Sets the betweenness.
     *
     * @param betweenness The betweenness to set.
     */
    void setBetweenness(double betweenness);

    /**
     * Returns the closeness.
     *
     * @return The closeness.
     */
    double getCloseness();

    /**
     * Sets the closeness.
     *
     * @param closeness The closeness to set.
     */
    void setCloseness(double closeness);
}
