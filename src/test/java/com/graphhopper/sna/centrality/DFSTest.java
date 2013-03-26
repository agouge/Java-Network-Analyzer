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

import com.graphhopper.storage.Graph;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.storage.RAMDirectory;

/**
 * Tests the Depth First Search (DFS).
 *
 * @author Adam Gouge
 */
public class DFSTest {

    private static final double ONE = 1.0;

    /**
     * Prepare a tree with root node 1.
     *
     * @return A tree with root node 1.
     */
    protected Graph prepareTree() {

        GraphStorage graph = new GraphStorage(new RAMDirectory());
        graph.createNew(10);

        graph.edge(1, 2, ONE, false);
        graph.edge(1, 3, ONE, false);

        graph.edge(3, 4, ONE, false);
        graph.edge(3, 12, ONE, false);

        graph.edge(4, 5, ONE, false);
        graph.edge(4, 8, ONE, false);

        graph.edge(5, 6, ONE, false);
        graph.edge(5, 7, ONE, false);

        graph.edge(8, 9, ONE, false);
        graph.edge(8, 10, ONE, false);
        graph.edge(8, 11, ONE, false);

        graph.edge(12, 13, ONE, false);

        graph.edge(13, 14, ONE, false);
        graph.edge(13, 16, ONE, false);

        graph.edge(14, 15, ONE, false);

        graph.edge(16, 17, ONE, false);
        graph.edge(16, 18, ONE, false);

        graph.edge(18, 19, ONE, false);
        graph.edge(18, 20, ONE, false);

        return graph;
    }
}
