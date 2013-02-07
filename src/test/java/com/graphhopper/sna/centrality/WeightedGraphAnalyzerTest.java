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
import com.graphhopper.storage.Graph;
import java.util.HashMap;

/**
 * Contains a method for doing (possibly verbose) weighted graph analysis.
 *
 * @author Adam Gouge
 */
public abstract class WeightedGraphAnalyzerTest extends GraphAnalyzerTest {

    /**
     * {@inheritDoc}
     */
    @Override
    protected HashMap<Integer, NodeBetweennessInfo> doAnalysis(
            Graph graph,
            boolean verbose,
            boolean printResults) {
        // Prepare the unweighted graph analyzer.
        WeightedGraphAnalyzer analyzer = verbose
                ? new WeightedGraphAnalyzerVerbose(graph)
                : new WeightedGraphAnalyzer(graph);
        // Do network analysis.
        return computeAll(analyzer, printResults);
    }
}
