/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 * It is part of the OrbisGIS tool ecosystem.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * Java Network Analyzer is distributed under GPL 3 license.
 *
 * Copyright (C) 2012-2014 CNRS (IRSTV CNRS FR 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC CNRS UMR 6285)
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
 * 
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.javanetworkanalyzer.data;

/**
 * Stores information about the shortest path lengths from one node to the other
 * nodes in the network.
 *
 * @author Adam Gouge
 * @param <T>
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
