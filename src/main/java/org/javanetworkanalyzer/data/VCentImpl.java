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

/**
 * Centrality vertex implementation.
 *
 * @author Adam Gouge
 */
public class VCentImpl extends VPredImpl implements VCent {

    /**
     * Number of shortest paths leading to this node starting from a certain
     * source.
     */
    protected long spCount = 0;
    /**
     * Dependency of this node on any other vertex.
     */
    protected double dependency = 0.0;
    /**
     * Betweenness value of this node.
     */
    private double betweenness = 0.0;
    /**
     * Closeness value of this node.
     */
    private double closeness = 0.0;

    /**
     * Constructor: sets the id; sets the shortest paths count, dependency,
     * betweenness and closeness to zero.
     *
     * @param id Node id
     */
    public VCentImpl(Integer id) {
        super(id);
    }

    @Override
    public void reset() {
        spCount = 0;
        dependency = 0.0;
    }

    @Override
    public void setSource() {
        spCount = 1;
        dependency = 0.0;
    }

    @Override
    public long getSPCount() {
        return spCount;
    }

    @Override
    public void accumulateSPCount(long additionalSPCount) {
        spCount += additionalSPCount;
    }

    @Override
    public void setSPCount(long newSPCount) {
        spCount = newSPCount;
    }

    @Override
    public double getDependency() {
        return dependency;
    }

    @Override
    public void accumulateDependency(double additionalDependency) {
        dependency += additionalDependency;
    }

    @Override
    public double getBetweenness() {
        return betweenness;
    }

    @Override
    public void accumulateBetweenness(double additionalBetweenness) {
        setBetweenness(getBetweenness() + additionalBetweenness);
    }

    @Override
    public void setBetweenness(double betweenness) {
        this.betweenness = betweenness;
    }

    @Override
    public double getCloseness() {
        return closeness;
    }

    @Override
    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }
}
