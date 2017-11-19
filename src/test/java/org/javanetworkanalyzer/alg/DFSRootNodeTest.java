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
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.VDFS;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.TraversalGraph;
import org.junit.Test;

/**
 * Tests the DFS algorithm from all possible start vertices on a directed graph.
 *
 * @author Adam Gouge
 */
// TODO: Write the tests for undirected graphs as well.
public class DFSRootNodeTest extends DFSTest {

    private TraversalGraph<VDFS, Edge> traverse;

    @Test
    public void testDFSDirectedFromVertexOne() {
        dfs.calculate(v1);
        assertTrue(v1.getDiscoveryTime() == 1);
        assertTrue(v1.getFinishingTime() == 8);
        assertTrue(v2.getDiscoveryTime() == 2);
        assertTrue(v2.getFinishingTime() == 7);
        assertTrue(v3.getDiscoveryTime() == 3);
        assertTrue(v3.getFinishingTime() == 6);
        assertTrue(v4.getDiscoveryTime() == 4);
        assertTrue(v4.getFinishingTime() == 5);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);
        traverse = dfs.reconstructTraversalGraph();
        assertTrue(traverse.getRoot().equals(v1));
        assertTrue(traverse.edgeSet().size() == 3);
        assertTrue(traverse.containsEdge(v1, v2));
        assertTrue(traverse.containsEdge(v2, v3));
        assertTrue(traverse.containsEdge(v3, v4));
    }

    @Test
    public void testDFSDirectedFromVertexTwo() {
        dfs.calculate(v2);
        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 1);
        assertTrue(v2.getFinishingTime() == 6);
        assertTrue(v3.getDiscoveryTime() == 2);
        assertTrue(v3.getFinishingTime() == 5);
        assertTrue(v4.getDiscoveryTime() == 3);
        assertTrue(v4.getFinishingTime() == 4);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);
        traverse = dfs.reconstructTraversalGraph();
        assertTrue(traverse.getRoot().equals(v2));
        assertTrue(traverse.edgeSet().size() == 2);
        assertTrue(traverse.containsEdge(v2, v3));
        assertTrue(traverse.containsEdge(v3, v4));
    }

    @Test
    public void testDFSDirectedFromVertexThree() {
        dfs.calculate(v3);
        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 3);
        assertTrue(v2.getFinishingTime() == 4);
        assertTrue(v3.getDiscoveryTime() == 1);
        assertTrue(v3.getFinishingTime() == 6);
        assertTrue(v4.getDiscoveryTime() == 2);
        assertTrue(v4.getFinishingTime() == 5);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);
        traverse = dfs.reconstructTraversalGraph();
        assertTrue(traverse.getRoot().equals(v3));
        assertTrue(traverse.edgeSet().size() == 2);
        assertTrue(traverse.containsEdge(v3, v4));
        assertTrue(traverse.containsEdge(v4, v2));
    }

    @Test
    public void testDFSDirectedFromVertexFour() {
        dfs.calculate(v4);
        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 2);
        assertTrue(v2.getFinishingTime() == 5);
        assertTrue(v3.getDiscoveryTime() == 3);
        assertTrue(v3.getFinishingTime() == 4);
        assertTrue(v4.getDiscoveryTime() == 1);
        assertTrue(v4.getFinishingTime() == 6);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == -1);
        assertTrue(v6.getFinishingTime() == -1);
        traverse = dfs.reconstructTraversalGraph();
        assertTrue(traverse.getRoot().equals(v4));
        assertTrue(traverse.edgeSet().size() == 2);
        assertTrue(traverse.containsEdge(v4, v2));
        assertTrue(traverse.containsEdge(v2, v3));
    }

    @Test
    public void testDFSDirectedFromVertexFive() {
        dfs.calculate(v5);
        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == 3);
        assertTrue(v2.getFinishingTime() == 6);
        assertTrue(v3.getDiscoveryTime() == 4);
        assertTrue(v3.getFinishingTime() == 5);
        assertTrue(v4.getDiscoveryTime() == 2);
        assertTrue(v4.getFinishingTime() == 7);
        assertTrue(v5.getDiscoveryTime() == 1);
        assertTrue(v5.getFinishingTime() == 10);
        assertTrue(v6.getDiscoveryTime() == 8);
        assertTrue(v6.getFinishingTime() == 9);
        traverse = dfs.reconstructTraversalGraph();
        assertTrue(traverse.getRoot().equals(v5));
        assertTrue(traverse.edgeSet().size() == 4);
        assertTrue(traverse.containsEdge(v5, v4));
        assertTrue(traverse.containsEdge(v4, v2));
        assertTrue(traverse.containsEdge(v2, v3));
        assertTrue(traverse.containsEdge(v5, v6));
    }

    @Test
    public void testDFSDirectedFromVertexSix() {
        dfs.calculate(v6);
        assertTrue(v1.getDiscoveryTime() == -1);
        assertTrue(v1.getFinishingTime() == -1);
        assertTrue(v2.getDiscoveryTime() == -1);
        assertTrue(v2.getFinishingTime() == -1);
        assertTrue(v3.getDiscoveryTime() == -1);
        assertTrue(v3.getFinishingTime() == -1);
        assertTrue(v4.getDiscoveryTime() == -1);
        assertTrue(v4.getFinishingTime() == -1);
        assertTrue(v5.getDiscoveryTime() == -1);
        assertTrue(v5.getFinishingTime() == -1);
        assertTrue(v6.getDiscoveryTime() == 1);
        assertTrue(v6.getFinishingTime() == 2);
        traverse = dfs.reconstructTraversalGraph();
        assertTrue(traverse.getRoot().equals(v6));
        assertTrue(traverse.edgeSet().size() == 0);
    }
}
