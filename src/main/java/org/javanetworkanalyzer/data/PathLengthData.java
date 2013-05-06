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
package org.javanetworkanalyzer.data;

/**
 * Stores information about the shortest path lengths from one node to the other
 * nodes in the network.
 *
 * @author Adam Gouge
 */
public abstract class PathLengthData<T extends Number> {

    /**
     * Number of shortest path lengths accumulated in this instance.
     */
    protected int count;
    /**
     * Error message to be given when no shortest path lengths have been
     * accumulated.
     */
    protected static final String SPL_ERROR = "No shortest path lengths "
            + "accumulated in this instance.";

    /**
     * Initializes a new instance of {@link PathLengthData}.
     */
    public PathLengthData() {
        count = 0;
    }

    /**
     * Clears all path length data information.
     */
    public void clear() {
        count = 0;
    }

    /**
     * Gets the number of shortest path lengths.
     *
     * @return Number of shortest path lengths accumulated in this instance.
     */
    public int getCount() {
        return count;
    }

    /**
     * Accumulates a new shortest path length to this data instance.
     *
     * @param length Length of a new shortest path to be accumulated.
     */
    public abstract void addSPLength(T length);

    /**
     * Longest among the shortest path lengths added to this data instance.
     *
     * @return Maximum length of a shortest path accumulated in this instance.
     */
    public abstract T getMaxLength();

    /**
     * Gets the total length of shortest paths.
     *
     * @return Sum of all shortest path lengths accumulated in this instance.
     */
    public abstract T getTotalLength();

    /**
     * Average shortest path length.
     *
     * @return Average length of a shortest path accumulated in this instance.
     *
     * @throws IllegalStateException If no SPLs were accumulated in this
     *                               instance.
     */
    // TODO: If the maximum length is "infinite", then return an infinite 
    // average path length.
    //        if (maxLength >= Integer.MAX_VALUE) {
    //            return Double.POSITIVE_INFINITY;
    //        }
    public abstract double getAverageLength();
}
