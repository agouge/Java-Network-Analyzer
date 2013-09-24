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
 * Vertex to be used in the Dijkstra algorithm.
 *
 * @param <V> Vertex
 *
 * @author Adam Gouge
 */
public class VDijkstra<V extends VDijkstra, E>
        extends VPredImpl<V, E>
        implements VDist<Double> {

    /**
     * The default distance assigned to all nodes at the beginning of the
     * Dijkstra algorithm.
     */
    public static final Double DEFAULT_DISTANCE = Double.POSITIVE_INFINITY;
    /**
     * Length of a shortest path starting from a certain source leading to this
     * node (Dijkstra).
     */
    private double distance;

    /**
     * Constructor: Sets the id.
     *
     * @param id Id
     */
    public VDijkstra(Integer id) {
        super(id);
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void setDistance(Double newDistance) {
        distance = newDistance;
    }

    /**
     * Clears the predecessor list and resets the distance to the default
     * distance.
     */
    @Override
    public void reset() {
        // Clear the predecessor list.
        super.clear();
        // Reset the distance to the default distance.
        distance = DEFAULT_DISTANCE;
    }

    /**
     * Clears the predecessor list and sets the distance to zero.
     */
    @Override
    public void setSource() {
        // Clear the predecessor list.
        super.clear();
        // Set the distance to zero.
        distance = 0.0;
    }
}
