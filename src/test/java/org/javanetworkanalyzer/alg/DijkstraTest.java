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
package org.javanetworkanalyzer.alg;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.javanetworkanalyzer.data.VWBetw;
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
public class DijkstraTest {

    private static final double TOLERANCE = 0.0;

    @Test
    public void testOneToOne() {
        try {
            WeightedPseudoG<VWBetw, Edge> g = prepareGraph();
            Map<VWBetw, Map<VWBetw, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWBetw, Edge> dijkstra = new Dijkstra<VWBetw, Edge>(g);

            for (VWBetw source : g.vertexSet()) {
                for (VWBetw target : g.vertexSet()) {
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
            WeightedPseudoG<VWBetw, Edge> g = prepareGraph();
            Map<VWBetw, Map<VWBetw, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWBetw, Edge> dijkstra = new Dijkstra<VWBetw, Edge>(g);

            for (VWBetw source : g.vertexSet()) {
                // Note: This is a oneToAll. It should be equivalent to
                // dijkstra.calculate(source);
                Map<VWBetw, Double> distances =
                        dijkstra.oneToMany(source, g.vertexSet());
                for (Entry<VWBetw, Double> e : distances.entrySet()) {
                    VWBetw target = e.getKey();
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
            WeightedPseudoG<VWBetw, Edge> g = prepareGraph();
            Map<VWBetw, Map<VWBetw, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWBetw, Edge> dijkstra = new Dijkstra<VWBetw, Edge>(g);

            for (VWBetw target : g.vertexSet()) {
                // Note: This is an allToOne.
                Map<VWBetw, Double> distances =
                        dijkstra.manyToOne(g.vertexSet(), target);
                for (Entry<VWBetw, Double> e : distances.entrySet()) {
                    VWBetw source = e.getKey();
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
            WeightedPseudoG<VWBetw, Edge> g = prepareGraph();
            Map<VWBetw, Map<VWBetw, Double>> expectedDistances =
                    expectedDistances(g);

            Dijkstra<VWBetw, Edge> dijkstra = new Dijkstra<VWBetw, Edge>(g);

            // Note: This is an allToAll.
            Map<VWBetw, Map<VWBetw, Double>> distances =
                    dijkstra.manyToMany(g.vertexSet(), g.vertexSet());
            for (Entry<VWBetw, Map<VWBetw, Double>> e : distances.entrySet()) {
                VWBetw source = e.getKey();
                Map<VWBetw, Double> distancesKeyedByTarget = e.getValue();
                for (Entry<VWBetw, Double> f : distancesKeyedByTarget.entrySet()) {
                    VWBetw target = f.getKey();
                    double distance = f.getValue();
                    assertEquals(expectedDistances.get(source).get(target),
                                 distance, TOLERANCE);
                }
            }
        } catch (NoSuchMethodException ex) {
        }
    }

    private WeightedPseudoG<VWBetw, Edge> prepareGraph()
            throws NoSuchMethodException {
        WeightedPseudoG<VWBetw, Edge> graph = new WeightedPseudoG<VWBetw, Edge>(
                VWBetw.class, Edge.class);
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

    private Map<VWBetw, Map<VWBetw, Double>> expectedDistances(
            WeightedPseudoG<VWBetw, Edge> g) {
        Map<VWBetw, Map<VWBetw, Double>> distances =
                new HashMap<VWBetw, Map<VWBetw, Double>>();

        Map<VWBetw, Double> dFromOne = new HashMap<VWBetw, Double>();
        dFromOne.put(g.getVertex(1), 0.0);
        dFromOne.put(g.getVertex(2), 7.0);
        dFromOne.put(g.getVertex(3), 8.0);
        dFromOne.put(g.getVertex(4), 5.0);
        dFromOne.put(g.getVertex(5), 7.0);

        Map<VWBetw, Double> dFromTwo = new HashMap<VWBetw, Double>();
        dFromTwo.put(g.getVertex(1), 7.0);
        dFromTwo.put(g.getVertex(2), 0.0);
        dFromTwo.put(g.getVertex(3), 1.0);
        dFromTwo.put(g.getVertex(4), 2.0);
        dFromTwo.put(g.getVertex(5), 4.0);

        Map<VWBetw, Double> dFromThree = new HashMap<VWBetw, Double>();
        dFromThree.put(g.getVertex(1), 8.0);
        dFromThree.put(g.getVertex(2), 1.0);
        dFromThree.put(g.getVertex(3), 0.0);
        dFromThree.put(g.getVertex(4), 3.0);
        dFromThree.put(g.getVertex(5), 4.0);

        Map<VWBetw, Double> dFromFour = new HashMap<VWBetw, Double>();
        dFromFour.put(g.getVertex(1), 5.0);
        dFromFour.put(g.getVertex(2), 2.0);
        dFromFour.put(g.getVertex(3), 3.0);
        dFromFour.put(g.getVertex(4), 0.0);
        dFromFour.put(g.getVertex(5), 2.0);

        Map<VWBetw, Double> dFromFive = new HashMap<VWBetw, Double>();
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
