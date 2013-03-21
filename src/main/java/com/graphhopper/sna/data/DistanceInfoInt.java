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
 * Interface with a getter and a setter for integer distances.
 *
 * @author adam
 */
public interface DistanceInfoInt extends SourceInfo {

    /**
     * Returns the number of steps from a source node to this node on a shortest
     * path (BFS).
     *
     * @return The number of steps from a source node to this node on a shortest
     *         path (BFS).
     */
    public int getDistance();

    /**
     * Sets the number of steps on a shortest path from a certain source leading
     * to this node.
     *
     * @param newDistance Number of steps to this node.
     */
    public void setDistance(int newDistance);
}
