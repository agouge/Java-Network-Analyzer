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

/**
 * Interface for node info containing the distance from a source node as well as
 * the ability to set this as the source node.
 *
 * @author Adam Gouge
 */
public interface DistanceInfo<T extends Number> {

    /**
     * Sets this to be the source node.
     */
    void setSource();

    /**
     * Returns the length of the shortest path from a source node to this node.
     *
     * @return The length of the shortest path from a source node to this node.
     */
    T getDistance();

    /**
     * Sets the new length of a shortest path from a source node to this node.
     *
     * @param newDistance Length of a shortest path to this node.
     */
    void setDistance(T newDistance);
}
