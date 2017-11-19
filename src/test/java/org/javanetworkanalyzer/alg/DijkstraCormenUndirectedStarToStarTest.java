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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.WeightedPseudoG;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests one-to-one, one-to-many, many-to-one and many-to-many Dijkstra searches
 * on the Cormen graph.
 *
 * In each test we consider all possible combinations, so that all distances
 * between pairs of nodes are calculated.
 *
 * @author Adam Gouge
 */
//                   1
//           >2 ------------>3
//          / |^           ->|^
//       10/ / |      9   / / |
//        / 2| |3    -----  | |
//       /   | |    /      4| |6
//      1<---------------   | |
//       \   | |  /     7\  | |
//       5\  | / /        \ | /
//         \ v| /    2     \v|
//          > 4 -----------> 5
//               CORMEN
public class DijkstraCormenUndirectedStarToStarTest {

    private static final double TOLERANCE = 0.0;

    @Test
    public void testOneToOne() {
        try {
            WeightedPseudoG<VWCent, Edge> g = prepareGraph();
            Map<VWCent, Map<VWCent, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWCent, Edge> dijkstra = new Dijkstra<VWCent, Edge>(g);

            for (VWCent source : g.vertexSet()) {
                for (VWCent target : g.vertexSet()) {
                    double distance = dijkstra.oneToOne(source, target);
                    assertEquals(expectedDistances.get(source).get(target),
                                 distance, TOLERANCE);
                }
            }
        } catch (NoSuchMethodException ex) {
        }
    }

    @Test
    public void testOneToMany() {
        try {
            WeightedPseudoG<VWCent, Edge> g = prepareGraph();
            Map<VWCent, Map<VWCent, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWCent, Edge> dijkstra = new Dijkstra<VWCent, Edge>(g);

            for (VWCent source : g.vertexSet()) {
                // Note: This is a oneToAll. It should be equivalent to
                // dijkstra.calculate(source);
                Map<VWCent, Double> distances =
                        dijkstra.oneToMany(source, g.vertexSet());
                for (Entry<VWCent, Double> e : distances.entrySet()) {
                    VWCent target = e.getKey();
                    Double distance = e.getValue();
                    assertEquals(expectedDistances.get(source).get(target),
                                 distance, TOLERANCE);
                }
            }
        } catch (NoSuchMethodException ex) {
        }
    }

    @Test
    public void testManyToOne() {
        try {
            WeightedPseudoG<VWCent, Edge> g = prepareGraph();
            Map<VWCent, Map<VWCent, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWCent, Edge> dijkstra = new Dijkstra<VWCent, Edge>(g);

            for (VWCent target : g.vertexSet()) {
                // Note: This is an allToOne.
                Map<VWCent, Double> distances =
                        dijkstra.manyToOne(g.vertexSet(), target);
                for (Entry<VWCent, Double> e : distances.entrySet()) {
                    VWCent source = e.getKey();
                    Double distance = e.getValue();
                    assertEquals(expectedDistances.get(source).get(target),
                                 distance, TOLERANCE);
                }
            }
        } catch (NoSuchMethodException ex) {
        }
    }

    @Test
    public void testManyToMany() {
        try {
            WeightedPseudoG<VWCent, Edge> g = prepareGraph();
            Map<VWCent, Map<VWCent, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWCent, Edge> dijkstra = new Dijkstra<VWCent, Edge>(g);

            // Note: This is an allToAll.
            Map<VWCent, Map<VWCent, Double>> distances =
                    dijkstra.manyToMany(g.vertexSet(), g.vertexSet());
            for (Entry<VWCent, Map<VWCent, Double>> e : distances.entrySet()) {
                VWCent source = e.getKey();
                Map<VWCent, Double> distancesKeyedByTarget = e.getValue();
                for (Entry<VWCent, Double> f : distancesKeyedByTarget.entrySet()) {
                    VWCent target = f.getKey();
                    double distance = f.getValue();
                    assertEquals(expectedDistances.get(source).get(target),
                                 distance, TOLERANCE);
                }
            }
        } catch (NoSuchMethodException ex) {
        }
    }

    private WeightedPseudoG<VWCent, Edge> prepareGraph()
            throws NoSuchMethodException {
        WeightedPseudoG<VWCent, Edge> graph = new WeightedPseudoG<VWCent, Edge>(
                VWCent.class, Edge.class);
        graph.addEdge(1, 2).setWeight(10);
        graph.addEdge(1, 4).setWeight(5);
        graph.addEdge(5, 1).setWeight(7);
        graph.addEdge(2, 4).setWeight(2);
        graph.addEdge(4, 2).setWeight(3);
        graph.addEdge(3, 5).setWeight(4);
        graph.addEdge(2, 3).setWeight(1);
        graph.addEdge(4, 3).setWeight(9);
        graph.addEdge(5, 3).setWeight(6);
        graph.addEdge(4, 5).setWeight(2);
        return graph;
    }

    private Map<VWCent, Map<VWCent, Double>> expectedDistances(
            WeightedPseudoG<VWCent, Edge> g) {
        Map<VWCent, Map<VWCent, Double>> distances =
                new HashMap<VWCent, Map<VWCent, Double>>();

        Map<VWCent, Double> dFromOne = new HashMap<VWCent, Double>();
        dFromOne.put(g.getVertex(1), 0.0);
        dFromOne.put(g.getVertex(2), 7.0);
        dFromOne.put(g.getVertex(3), 8.0);
        dFromOne.put(g.getVertex(4), 5.0);
        dFromOne.put(g.getVertex(5), 7.0);

        Map<VWCent, Double> dFromTwo = new HashMap<VWCent, Double>();
        dFromTwo.put(g.getVertex(1), 7.0);
        dFromTwo.put(g.getVertex(2), 0.0);
        dFromTwo.put(g.getVertex(3), 1.0);
        dFromTwo.put(g.getVertex(4), 2.0);
        dFromTwo.put(g.getVertex(5), 4.0);

        Map<VWCent, Double> dFromThree = new HashMap<VWCent, Double>();
        dFromThree.put(g.getVertex(1), 8.0);
        dFromThree.put(g.getVertex(2), 1.0);
        dFromThree.put(g.getVertex(3), 0.0);
        dFromThree.put(g.getVertex(4), 3.0);
        dFromThree.put(g.getVertex(5), 4.0);

        Map<VWCent, Double> dFromFour = new HashMap<VWCent, Double>();
        dFromFour.put(g.getVertex(1), 5.0);
        dFromFour.put(g.getVertex(2), 2.0);
        dFromFour.put(g.getVertex(3), 3.0);
        dFromFour.put(g.getVertex(4), 0.0);
        dFromFour.put(g.getVertex(5), 2.0);

        Map<VWCent, Double> dFromFive = new HashMap<VWCent, Double>();
        dFromFive.put(g.getVertex(1), 7.0);
        dFromFive.put(g.getVertex(2), 4.0);
        dFromFive.put(g.getVertex(3), 4.0);
        dFromFive.put(g.getVertex(4), 2.0);
        dFromFive.put(g.getVertex(5), 0.0);

        distances.put(g.getVertex(1), dFromOne);
        distances.put(g.getVertex(2), dFromTwo);
        distances.put(g.getVertex(3), dFromThree);
        distances.put(g.getVertex(4), dFromFour);
        distances.put(g.getVertex(5), dFromFive);

        return distances;
    }
}
