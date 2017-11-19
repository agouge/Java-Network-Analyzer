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
 * Vertex to be used in the BFS algorithm.
 *
 * @param <V> Vertex
 *
 * @author Adam Gouge
 */
public class VBFS<V extends VBFS, E> extends VPredImpl<V, E> implements VDist<Integer> {

    /**
     * The default distance assigned to all nodes at the beginning of the BFS
     * algorithm.
     */
    public static final int DEFAULT_DISTANCE = -1;
    /**
     * Number of steps on a shortest path from a certain source leading to this
     * node (BFS).
     */
    private int distance = DEFAULT_DISTANCE;

    /**
     * Constructor: sets the id.
     *
     * @param id Id
     */
    public VBFS(Integer id) {
        super(id);
    }

    @Override
    public Integer getDistance() {
        return distance;
    }

    @Override
    public void setDistance(Integer newDistance) {
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
        distance = 0;
    }
}
