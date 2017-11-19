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

import junit.framework.TestCase;
import org.javanetworkanalyzer.data.VDFS;
import org.javanetworkanalyzer.model.AsUndirectedG;
import org.javanetworkanalyzer.model.DirectedPseudoG;
import org.javanetworkanalyzer.model.Edge;
import org.junit.Test;

/**
 * Tests the DFS algorithm on a(n) (un)directed graph. Note: Both the choice of
 * start vertex and the order in which neighbors are visited depend on the
 * underlying graph structure. (In the below examples, the start node seems to
 * be the first node added to the graph when the graph is created.) To specify
 * the start vertex, use {@link DFS#calculate(org.javanetworkanalyzer.data.VDFS)}.
 *
 * @author Adam Gouge
 */
public class DFSTest extends TestCase {

    protected DirectedPseudoG<VDFS, Edge> graph;
    protected DFS<VDFS, Edge> dfs;
    protected VDFS v1;
    protected VDFS v2;
    protected VDFS v3;
    protected VDFS v4;
    protected VDFS v5;
    protected VDFS v6;

    @Test
    public void testDFSDirected() {
        dfs.calculate();
        assertTrue(v1.getDiscoveryTime() == 1);
        assertTrue(v1.getFinishingTime() == 8);
        assertTrue(v2.getDiscoveryTime() == 2);
        assertTrue(v2.getFinishingTime() == 7);
        assertTrue(v3.getDiscoveryTime() == 3);
        assertTrue(v3.getFinishingTime() == 6);
        assertTrue(v4.getDiscoveryTime() == 4);
        assertTrue(v4.getFinishingTime() == 5);
        assertTrue(v5.getDiscoveryTime() == 9);
        assertTrue(v5.getFinishingTime() == 12);
        assertTrue(v6.getDiscoveryTime() == 10);
        assertTrue(v6.getFinishingTime() == 11);
    }

    @Test
    public void testDFSUndirected() throws NoSuchMethodException {
        dfs = new DFS<VDFS, Edge>(new AsUndirectedG<VDFS, Edge>(graph));
        dfs.calculate();
        assertTrue(v1.getDiscoveryTime() == 1);
        assertTrue(v1.getFinishingTime() == 12);
        assertTrue(v2.getDiscoveryTime() == 2);
        assertTrue(v2.getFinishingTime() == 11);
        assertTrue(v3.getDiscoveryTime() == 4);
        assertTrue(v3.getFinishingTime() == 5);
        assertTrue(v4.getDiscoveryTime() == 3);
        assertTrue(v4.getFinishingTime() == 10);
        assertTrue(v5.getDiscoveryTime() == 6);
        assertTrue(v5.getFinishingTime() == 9);
        assertTrue(v6.getDiscoveryTime() == 7);
        assertTrue(v6.getFinishingTime() == 8);
    }

    @Override
    public void setUp() {
        graph = new DirectedPseudoG<VDFS, Edge>(VDFS.class, Edge.class);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 2);
        graph.addEdge(5, 4);
        graph.addEdge(5, 6);
        graph.addEdge(6, 6);
        v1 = graph.getVertex(1);
        v2 = graph.getVertex(2);
        v3 = graph.getVertex(3);
        v4 = graph.getVertex(4);
        v5 = graph.getVertex(5);
        v6 = graph.getVertex(6);
        dfs = new DFS<VDFS, Edge>(graph);
    }
}
