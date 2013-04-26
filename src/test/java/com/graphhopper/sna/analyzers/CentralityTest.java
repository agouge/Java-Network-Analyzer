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
package com.graphhopper.sna.analyzers;

import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.model.Edge;
import org.jgrapht.Graph;

/**
 * Parent class in the centrality tests hierarchy.
 *
 * @author Adam Gouge
 */
public abstract class CentralityTest<V extends NodeBetweennessInfo> {

    /**
     * Used for printing out timing messages.
     */
    protected final static String TIME = " [time] ";
    /**
     * Used for checking results of centrality index computations. We guarantee
     * the results to 10 decimal places.
     */
    protected final static double TOLERANCE = 1E-10;

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

    protected void printResults(Graph<? extends NodeBetweennessInfo, Edge> graph) {
        System.out.format("%-6s%-20s%-20s\n",
                          "v",
                          "Betweenness",
                          "Closeness");
        for (NodeBetweennessInfo node : graph.vertexSet()) {
            System.out.format("%-6d%-20.11f%-20.11f\n",
                              node.getID(),
                              node.getBetweenness(),
                              node.getCloseness());
        }
    }
}
