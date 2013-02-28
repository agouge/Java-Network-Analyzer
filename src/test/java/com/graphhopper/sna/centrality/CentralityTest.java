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
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.NodeBetweennessInfo;
import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.hash.TIntDoubleHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Parent class in the centrality tests hierarchy.
 *
 * @author Adam Gouge
 */
public abstract class CentralityTest {

    /**
     * Used for printing out timing messages.
     */
    protected final static String TIME = " [time] ";
    /**
     * Used for checking results of centrality index computations.
     */
    protected final static double TOLERANCE = 0.000000000001;

    /**
     * Prints the title of this test.
     *
     * @param title The title.
     */
    protected void printTitle(
            String title) {
        System.out.println("\n***** " + getName() + " - " + title + " *****");
    }

    /**
     * Gets the name of this type of test.
     *
     * @return The name of this type of test.
     */
    protected abstract String getName();

    /**
     * Prints the results of graph analysis.
     *
     * @param result The result.
     */
    protected void printResults(Map<Integer, NodeBetweennessInfo> result) {
        // Print results.
        System.out.format("%-12s%-12s%-12s",
                          "v",
                          "Betweenness",
                          "Closeness");
        System.out.println("");
        Iterator<Map.Entry<Integer, NodeBetweennessInfo>> iterator =
                result.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, NodeBetweennessInfo> next = iterator.next();
            final Integer id = next.getKey();
            final NodeBetweennessInfo info = next.getValue();
            System.out.format("%-12d%-12f%-12f",
                              next.getKey(),
                              info.getBetweenness(),
                              info.getCloseness());
            System.out.println("");
        }
    }

    /**
     * Prints the results of a closeness computation.
     *
     * @param result The result.
     */
    protected void printResults(TIntDoubleHashMap result) {
        // Print results.
        System.out.format("%-3s%-12s",
                          "v",
                          "Closeness");
        System.out.println("");
        TIntDoubleIterator iterator = result.iterator();
        while (iterator.hasNext()) {
            iterator.advance();
            final int id = iterator.key();
            final double closeness = iterator.value();
            System.out.println(id + ",  " + closeness);
        }
    }
}
