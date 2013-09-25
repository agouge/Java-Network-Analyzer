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
 * Vertex used during accessibility analysis.
 *
 * @author Adam Gouge
 */
public class VAccess<E> extends VDijkstra<VAccess, E> {

    /**
     * Closest destination id.
     */
    private int closestDestinationId = -1;
    /**
     * Distance to the closest destination.
     */
    private double distanceToClosestDestination = Double.POSITIVE_INFINITY;

    /**
     * Constructor: sets the id.
     *
     * @param id Id
     */
    public VAccess(Integer id) {
        super(id);
    }

    /**
     * Gets the id of the closest destination.
     *
     * @return The id of the closest destination
     */
    public int getClosestDestinationId() {
        return closestDestinationId;
    }

    /**
     * Sets the id of the closest destination.
     *
     * @param id New id
     */
    public void setClosestDestinationId(int id) {
        this.closestDestinationId = id;
    }

    /**
     * Gets the distance to the closest destination.
     *
     * @return The distance to the closest destination.
     */
    public double getDistanceToClosestDestination() {
        return distanceToClosestDestination;
    }

    /**
     * Sets the distance to the closest destination.
     *
     * @param newDistance New distance.
     */
    public void setDistanceToClosestDestination(double newDistance) {
        this.distanceToClosestDestination = newDistance;
    }
}
