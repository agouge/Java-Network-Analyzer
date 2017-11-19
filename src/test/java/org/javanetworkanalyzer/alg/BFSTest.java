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
import org.javanetworkanalyzer.model.PseudoG;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.TraversalGraph;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Tests the BFS algorithm to calculate distances from a given source vertex.
 *
 * @author Adam Gouge
 */
public class BFSTest extends TestCase {

    private PseudoG<VUCent, Edge> graph;
    private BFS<VUCent, Edge> bfs;
    private TraversalGraph<VUCent,Edge> sPT;
    private VUCent v1;
    private VUCent v2;
    private VUCent v3;
    private VUCent v4;
    private VUCent v5;
    private VUCent v6;
    private VUCent v7;
    private VUCent v8;

    @Test
    public void testBFSFromVertexThree() {

        bfs.calculate(v3);
        assertTrue(graph.getVertex(3).getDistance() == 0);
        assertTrue(graph.getVertex(2).getDistance() == 1);
        assertTrue(graph.getVertex(4).getDistance() == 1);
        assertTrue(graph.getVertex(1).getDistance() == 2);
        assertTrue(graph.getVertex(5).getDistance() == 2);
        assertTrue(graph.getVertex(6).getDistance() == 2);
        assertTrue(graph.getVertex(7).getDistance() == 3);
        assertTrue(graph.getVertex(8).getDistance() == 3);

        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v3));
        assertTrue(sPT.edgeSet().size() == 8);
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v2, v1));
        assertTrue(sPT.containsEdge(v3, v4));
        assertTrue(sPT.containsEdge(v4, v6));
        assertTrue(sPT.containsEdge(v6, v8));
        assertTrue(sPT.containsEdge(v4, v5));
        assertTrue(sPT.containsEdge(v5, v7));
        assertTrue(sPT.containsEdge(v5, v8));
    }

    @Test
    public void testBFSFromVertexEight() {

        bfs.calculate(v8);

        assertTrue(graph.getVertex(8).getDistance() == 0);
        assertTrue(graph.getVertex(5).getDistance() == 1);
        assertTrue(graph.getVertex(6).getDistance() == 1);
        assertTrue(graph.getVertex(7).getDistance() == 1);
        assertTrue(graph.getVertex(4).getDistance() == 2);
        assertTrue(graph.getVertex(3).getDistance() == 3);
        assertTrue(graph.getVertex(2).getDistance() == 4);
        assertTrue(graph.getVertex(1).getDistance() == 5);

        sPT = bfs.reconstructTraversalGraph();
        assertTrue(sPT.getRoot().equals(v8));
        assertTrue(sPT.edgeSet().size() == 8);
        assertTrue(sPT.containsEdge(v8, v7));
        assertTrue(sPT.containsEdge(v8, v5));
        assertTrue(sPT.containsEdge(v5, v4));
        assertTrue(sPT.containsEdge(v8, v6));
        assertTrue(sPT.containsEdge(v6, v4));
        assertTrue(sPT.containsEdge(v4, v3));
        assertTrue(sPT.containsEdge(v3, v2));
        assertTrue(sPT.containsEdge(v2, v1));
    }

    @Override
    public void setUp() {
        graph = new PseudoG<VUCent, Edge>(VUCent.class, Edge.class);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 6);
        graph.addEdge(5, 6);
        graph.addEdge(5, 7);
        graph.addEdge(5, 8);
        graph.addEdge(6, 8);
        graph.addEdge(7, 8);
        v1= graph.getVertex(1);
        v2= graph.getVertex(2);
        v3= graph.getVertex(3);
        v4= graph.getVertex(4);
        v5= graph.getVertex(5);
        v6= graph.getVertex(6);
        v7= graph.getVertex(7);
        v8= graph.getVertex(8);
        bfs = new BFS<VUCent, Edge>(graph);
    }
}
