package org.javanetworkanalyzer.model;

/**
 * An {@link Edge} for calculating edge betweenness centrality.
 *
 * @author Adam Gouge
 */
public class EdgeCent extends Edge<EdgeCent> {

    /**
     * Dependency of this node on any other vertex.
     */
    protected double dependency = 0.0;
    /**
     * Betweenness value of this node.
     */
    private double betweenness = 0.0;

    public double getDependency() {
        return dependency;
    }

    public void accumulateDependency(double additionalDependency) {
        dependency += additionalDependency;
    }

    public double getBetweenness() {
        return betweenness;
    }

    public void accumulateBetweenness(double additionalBetweenness) {
        setBetweenness(getBetweenness() + additionalBetweenness);
    }

    public void setBetweenness(double betweenness) {
        this.betweenness = betweenness;
    }

    @Override
    public EdgeCent setWeight(double newWeight) {
        super.setWeight(newWeight);
        return this;
    }
}
