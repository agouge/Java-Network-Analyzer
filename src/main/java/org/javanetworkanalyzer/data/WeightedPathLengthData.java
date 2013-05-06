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

import static org.javanetworkanalyzer.data.PathLengthData.SPL_ERROR;

/**
 * {@link PathLengthData} for weighted graphs.
 *
 * @author Adam Gouge
 */
public class WeightedPathLengthData extends PathLengthData<Double> {

    /**
     * Maximum length of a shortest path accumulated in this instance.
     */
    private double maxLength;
    /**
     * Sum of all shortest path lengths accumulated in this instance.
     */
    private double totalLength;

    /**
     * Constructor.
     */
    public WeightedPathLengthData() {
        super();
        totalLength = 0.0;
        maxLength = 0.0;
    }

    @Override
    public void clear() {
        super.clear();
        totalLength = 0.0;
        maxLength = 0.0;
    }

    @Override
    public void addSPLength(Double length) {
        count++;
        totalLength += length;
        if (maxLength < length) {
            maxLength = length;
        }
    }

    @Override
    public Double getMaxLength() {
        return maxLength;
    }

    @Override
    public Double getTotalLength() {
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
