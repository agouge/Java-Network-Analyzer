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
package com.graphhopper.sna.analyzers.examples;

import com.graphhopper.sna.analyzers.GraphAnalyzerTest;
import com.graphhopper.sna.data.NodeBetweennessInfo;
import com.graphhopper.sna.progress.NullProgressMonitor;
import com.graphhopper.sna.progress.ProgressMonitor;
import java.io.FileNotFoundException;
import org.junit.Test;

/**
 * Tests graph analysis (betweenness, closeness) on an undirected 2D graph.
 *
 * @author Adam Gouge
 */
public class Graph2DAnalyzerTest extends GraphAnalyzerTest {

    private final static String GRAPH2D = "2D Graph";
    private static final String FILENAME = "./files/graph2D.edges.csv";
    private static final String LENGTH = "length";
    private static final boolean PRINT_RESULTS = false;
    private static final int numberOfNodes = 6;

    @Override
    protected ProgressMonitor progressMonitor() {
        return new NullProgressMonitor();
    }

    @Test
    public void unweightedDirectedTest()
            throws FileNotFoundException, NoSuchMethodException {
        NodeBetweennessInfo[] vertices =
                indexVertices(super.unweightedDirected());

        checkBetweenness(vertices,
                         new double[]{
            1.0, 0.0, 1.0,
            0.0, 0.0, 0.6666666666666666});
        checkCloseness(vertices,
                       new double[]{
            1.0, 0.4166666666666667, 0.5714285714285714,
            0.0, 0.0, 0.6666666666666666});
    }

    @Test
    public void unweightedReversedTest()
            throws FileNotFoundException, NoSuchMethodException {
        NodeBetweennessInfo[] vertices =
                indexVertices(super.unweightedReversed());

        checkBetweenness(vertices,
                         new double[]{
            0.375, 0.0, 1.0,
            0.0, 0.0, 1.0});
        checkCloseness(vertices,
                       new double[]{
            0.5, 0.0, 1.0,
            0.4, 0.6666666666666666, 0.6666666666666666});
    }

    @Test
    public void unweightedUndirectedTest()
            throws FileNotFoundException, NoSuchMethodException {
        NodeBetweennessInfo[] vertices =
                indexVertices(super.unweightedUndirected());

        checkBetweenness(vertices,
                         new double[]{
            0.5, 0.0, 1.0,
            0.0, 0.0, 0.75});
        checkCloseness(vertices,
                       new double[]{
            0.5, 0.4166666666666667, 0.625,
            0.35714285714285715, 0.4166666666666667, 0.625});
    }

    @Test
    public void weightedDirectedTest()
            throws FileNotFoundException, NoSuchMethodException {
        NodeBetweennessInfo[] vertices =
                indexVertices(super.weightedDirected());

        checkBetweenness(vertices,
                         new double[]{
            0.75, 0.0, 1.0,
            0.0, 0.0, 1.0});
        checkCloseness(vertices,
                       new double[]{
            0.017755520605710874, 0.0035327735482214143, 0.005213986222707472,
            0.0, 0.0, 0.004169637060665741});
    }

    @Test
    public void weightedReversedTest()
            throws FileNotFoundException, NoSuchMethodException {
        NodeBetweennessInfo[] vertices =
                indexVertices(super.weightedReversed());

        checkBetweenness(vertices,
                         new double[]{
            0.75, 0.0, 1.0,
            0.0, 0.0, 1.0});
        checkCloseness(vertices,
                       new double[]{
            0.003458851142284291, 0.0, 0.007714249189763772,
            0.0036609182136564987, 0.005043646871278617, 0.00860830779189523});
    }

    @Test
    public void weightedUndirectedTest()
            throws FileNotFoundException, NoSuchMethodException {
        NodeBetweennessInfo[] vertices =
                indexVertices(super.weightedUndirected());

        checkBetweenness(vertices,
                         new double[]{
            0.5714285714285714, 0.0, 1.0,
            0.0, 0.0, 0.8571428571428571});

        checkCloseness(vertices, new double[]{
            0.003787491035823884, 0.0035327735482214143, 0.0055753940798198886,
            0.0032353723348164448, 0.003495002741097083, 0.0055753940798198886
        });
    }

    @Override
    protected boolean printsResults() {
        return PRINT_RESULTS;
    }

    @Override
    protected String getFilename() {
        return FILENAME;
    }

    @Override
    protected String getWeightColumnName() {
        return LENGTH;
    }

    @Override
    protected String getName() {
        return GRAPH2D;
    }
    
    @Override
    protected int getNumberOfNodes() {
        return numberOfNodes;
    }
}
