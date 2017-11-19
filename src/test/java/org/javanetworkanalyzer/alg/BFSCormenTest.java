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

import org.javanetworkanalyzer.data.VBFS;
import org.javanetworkanalyzer.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link BFS} on the Cormen graph.
 *
 * @author Adam Gouge
 */
public class BFSCormenTest {

    private DirectedPseudoG<VBFS, Edge> graph;
    private BFS<VBFS, Edge> bfs;
    private TraversalGraph<VBFS, Edge> sPT;
    private VBFS v1;
    private VBFS v2;
    private VBFS v3;
    private VBFS v4;
    private VBFS v5;
    private static final double TOLERANCE = 0.0;


    @Test
    public void testD() throws NoSuchMethodException {

        bfs = new BFS<VBFS, Edge>(graph);

        bfs.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 2);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v2);
        assertEquals(3, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 2);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(3, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(3, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.outDegreeOf(v1) == 2);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 0);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v4);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 3);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v5);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.outDegreeOf(v1) == 2);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 0);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }

    @Test
    public void testR() throws NoSuchMethodException {

        bfs = new BFS<VBFS, Edge>(new EdgeReversedG<VBFS, Edge>(graph));

        bfs.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(3, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(2, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);

        bfs.calculate(v2);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(3, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 2);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 0);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v3, v4));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 3);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v4);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(3, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 4);
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v5);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 2);
    }

    @Test
    public void testU() throws NoSuchMethodException {

        bfs = new BFS<VBFS, Edge>(new AsUndirectedG<VBFS, Edge>(graph));

        bfs.calculate(v1);
        assertEquals(0, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(2, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v1));
        assertTrue(sPT.edgeSet().size() == 7);
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v1, v4));
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.getAllEdges(v5, v3).size() == 2);
        assertTrue(sPT.outDegreeOf(v1) == 3);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 2);

        bfs.calculate(v2);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(0, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(2, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v2));
        assertTrue(sPT.edgeSet().size() == 8);
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v2, v3));
        assertTrue(sPT.containsEdge(v2, v4));
        assertTrue(sPT.getAllEdges(v2, v4).size() == 2);
        assertTrue(sPT.containsEdge(v1, v5));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.getAllEdges(v3, v5).size() == 2);
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 4);
        assertTrue(sPT.outDegreeOf(v3) == 2);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v3);
        assertEquals(2, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(0, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 7);
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v3, v4));
        assertTrue(sPT.containsEdge(v3, v5));
        assertTrue(sPT.getAllEdges(v3, v5).size() == 2);
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 1);
        assertTrue(sPT.outDegreeOf(v3) == 4);
        assertTrue(sPT.outDegreeOf(v4) == 1);
        assertTrue(sPT.outDegreeOf(v5) == 1);

        bfs.calculate(v4);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(1, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(0, v4.getDistance(), TOLERANCE);
        assertEquals(1, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v4));
        assertTrue(sPT.edgeSet().size() == 5);
        assertTrue(sPT.containsEdge(v4, v1));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.getAllEdges(v4, v2).size() == 2);
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.outDegreeOf(v1) == 0);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 0);
        assertTrue(sPT.outDegreeOf(v4) == 5);
        assertTrue(sPT.outDegreeOf(v5) == 0);

        bfs.calculate(v5);
        assertEquals(1, v1.getDistance(), TOLERANCE);
        assertEquals(2, v2.getDistance(), TOLERANCE);
        assertEquals(1, v3.getDistance(), TOLERANCE);
        assertEquals(1, v4.getDistance(), TOLERANCE);
        assertEquals(0, v5.getDistance(), TOLERANCE);
        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v5));
        assertTrue(sPT.edgeSet().size() == 8);
        assertTrue(sPT.containsEdge(v5, v1));
        assertTrue(sPT.containsEdge(v5, v3));
        assertTrue(sPT.getAllEdges(v5, v3).size() == 2);
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v1, v2));
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v4, v2));
        assertTrue(sPT.getAllEdges(v4, v2).size() == 2);
        assertTrue(sPT.outDegreeOf(v1) == 1);
        assertTrue(sPT.outDegreeOf(v2) == 0);
        assertTrue(sPT.outDegreeOf(v3) == 1);
        assertTrue(sPT.outDegreeOf(v4) == 2);
        assertTrue(sPT.outDegreeOf(v5) == 4);
    }

    @Before
    public void setUp() {

        graph = new DirectedPseudoG<VBFS, Edge>(VBFS.class, Edge.class);

        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(5, 1);
        graph.addEdge(2, 4);
        graph.addEdge(4, 2);
        graph.addEdge(3, 5);
        graph.addEdge(2, 3);
        graph.addEdge(4, 3);
        graph.addEdge(5, 3);
        graph.addEdge(4, 5);

        v1 = graph.getVertex(1);
        v2 = graph.getVertex(2);
        v3 = graph.getVertex(3);
        v4 = graph.getVertex(4);
        v5 = graph.getVertex(5);
    }
}

