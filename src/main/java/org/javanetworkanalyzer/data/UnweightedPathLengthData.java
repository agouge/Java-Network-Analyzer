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
 * {@link PathLengthData} for unweighted graphs.
 *
 * @author Adam Gouge
 */
public class UnweightedPathLengthData extends PathLengthData<Integer> {

    /**
     * Maximum shortest path step number accumulated in this instance.
     */
    private int maxLength;
    /**
     * Sum of all shortest path steps accumulated in this instance.
     */
    // TODO: Should this be a long?
    private int totalLength;

    /**
     * Constructor.
     */
    public UnweightedPathLengthData() {
        super();
        totalLength = 0;
        maxLength = 0;
    }
    
    @Override
    public void clear() {
        super.clear();
        totalLength = 0;
        maxLength = 0;
    }

    @Override
    public void addSPLength(Integer length) {
        count++;
        totalLength += length;
        if (maxLength < length) {
            maxLength = length;
        }
    }

    @Override
    public Integer getMaxLength() {
        return maxLength;
    }

    @Override
    public Integer getTotalLength() {
        return totalLength;
    }

    @Override
    public double getAverageLength() {
        if (getCount() == 0) {
            throw new IllegalStateException(SPL_ERROR);
        }
        return ((double) getTotalLength()) / getCount();
    }
}
