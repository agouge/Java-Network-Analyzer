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
package org.javanetworkanalyzer.analyzers;

import org.javanetworkanalyzer.data.VCent;
import org.javanetworkanalyzer.model.Edge;
import org.jgrapht.Graph;

/**
 * Parent class in the centrality tests hierarchy.
 *
 * @author Adam Gouge
 */
public abstract class CentralityTest<V extends VCent> {

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

    protected void printResults(Graph<? extends VCent, Edge> graph) {
        System.out.format("%-6s%-20s%-20s\n",
                          "v",
                          "Betweenness",
                          "Closeness");
        for (VCent node : graph.vertexSet()) {
            System.out.format("%-6d%-20.11f%-20.11f\n",
                              node.getID(),
                              node.getBetweenness(),
                              node.getCloseness());
        }
    }
}
