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

import static com.graphhopper.sna.data.PathLengthData.SPL_ERROR;

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
