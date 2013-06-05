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
package org.javanetworkanalyzer.graphcreators;

import org.javanetworkanalyzer.data.VDijkstra;
import org.javanetworkanalyzer.model.DirectedWeightedPseudoG;
import org.javanetworkanalyzer.model.Edge;

/**
 * Prepares the Cormen graph.
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
public class CormenGraphPrep extends GraphPrep<VDijkstra, Edge> {

    @Override
    public int getNumberOfVertices() {
        return 5;
    }

    @Override
    public DirectedWeightedPseudoG<VDijkstra, Edge> weightedDirected() throws
            NoSuchMethodException {
        DirectedWeightedPseudoG<VDijkstra, Edge> g =
                new DirectedWeightedPseudoG<VDijkstra, Edge>(VDijkstra.class,
                                                           Edge.class);
        g.addEdge(1, 2).setWeight(10);
        g.addEdge(1, 4).setWeight(5);
        g.addEdge(5, 1).setWeight(7);
        g.addEdge(2, 4).setWeight(2);
        g.addEdge(4, 2).setWeight(3);
        g.addEdge(3, 5).setWeight(4);
        g.addEdge(2, 3).setWeight(1);
        g.addEdge(4, 3).setWeight(9);
        g.addEdge(5, 3).setWeight(6);
        g.addEdge(4, 5).setWeight(2);
        return g;
    }

    @Override
    public Double[][] expectedDistancesWD() {
        Double[][] d = new Double[getNumberOfVertices()][getNumberOfVertices()];

        d[0][0] = 0.0;
        d[0][1] = 8.0;
        d[0][2] = 9.0;
        d[0][3] = 5.0;
        d[0][4] = 7.0;

        d[1][0] = 11.0;
        d[1][1] = 0.0;
        d[1][2] = 1.0;
        d[1][3] = 2.0;
        d[1][4] = 4.0;

        d[2][0] = 11.0;
        d[2][1] = 19.0;
        d[2][2] = 0.0;
        d[2][3] = 16.0;
        d[2][4] = 4.0;

        d[3][0] = 9.0;
        d[3][1] = 3.0;
        d[3][2] = 4.0;
        d[3][3] = 0.0;
        d[3][4] = 2.0;

        d[4][0] = 7.0;
        d[4][1] = 15.0;
        d[4][2] = 6.0;
        d[4][3] = 12.0;
        d[4][4] = 0.0;
        return d;
    }

    @Override
    public Double[][] upperRightTriangleWU() {
        Double[][] d = zeroMatrix();

        d[0][1] = 7.0;
        d[0][2] = 8.0;
        d[0][3] = 5.0;
        d[0][4] = 7.0;

        d[1][2] = 1.0;
        d[1][3] = 2.0;
        d[1][4] = 4.0;

        d[2][3] = 3.0;
        d[2][4] = 4.0;

        d[3][4] = 2.0;

        return d;
    }

    @Override
    public Double[][] expectedDistancesD() {
        Double[][] d = new Double[getNumberOfVertices()][getNumberOfVertices()];

        d[0][0] = 0.0;
        d[0][1] = 1.0;
        d[0][2] = 2.0;
        d[0][3] = 1.0;
        d[0][4] = 2.0;

        d[1][0] = 3.0;
        d[1][1] = 0.0;
        d[1][2] = 1.0;
        d[1][3] = 1.0;
        d[1][4] = 2.0;

        d[2][0] = 2.0;
        d[2][1] = 3.0;
        d[2][2] = 0.0;
        d[2][3] = 3.0;
        d[2][4] = 1.0;

        d[3][0] = 2.0;
        d[3][1] = 1.0;
        d[3][2] = 1.0;
        d[3][3] = 0.0;
        d[3][4] = 1.0;

        d[4][0] = 1.0;
        d[4][1] = 2.0;
        d[4][2] = 1.0;
        d[4][3] = 2.0;
        d[4][4] = 0.0;
        return d;
    }

    @Override
    public Double[][] upperRightTriangleU() {
        Double[][] d = zeroMatrix();

        d[0][1] = 1.0;
        d[0][2] = 2.0;
        d[0][3] = 1.0;
        d[0][4] = 1.0;

        d[1][2] = 1.0;
        d[1][3] = 1.0;
        d[1][4] = 2.0;

        d[2][3] = 1.0;
        d[2][4] = 1.0;

        d[3][4] = 1.0;

        return d;
    }
}
