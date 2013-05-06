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

import org.javanetworkanalyzer.data.VStrahler;
import org.javanetworkanalyzer.model.StrahlerTree;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests calculating the Strahler numbers of the nodes in a tree.
 *
 * @author Adam Gouge
 */
public class StrahlerTest {

    /**
     * Tests the {@link DFSTest#prepareTree()} tree.
     *
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Test
    public void testStrahler() {

        StrahlerTree<DefaultEdge> tree = prepareTree();

        new DFSForStrahler<DefaultEdge>(tree)
                .calculate(tree.getRootVertex());

        // We know what the answers should be.
        Set<Integer> s1 = new HashSet<Integer>(
                Arrays.asList(2, 6, 7, 9, 10, 11, 14, 15, 17, 19, 20));
        Set<Integer> s2 = new HashSet<Integer>(
                Arrays.asList(5, 8, 12, 13, 16, 18));
        Set<Integer> s3 = new HashSet<Integer>(
                Arrays.asList(1, 3, 4));
        // Check the Strahler numbers.
        for (VStrahler node : tree.vertexSet()) {
            int id = node.getID();
            int strahlerNumber = node.getStrahlerNumber();
            if (s1.contains(id)) {
                assertEquals(1, strahlerNumber);
            } else if (s2.contains(id)) {
                assertEquals(2, strahlerNumber);
            } else if (s3.contains(id)) {
                assertEquals(3, strahlerNumber);
            }
        }
    }

    /**
     * Prepare a tree with root node 1.
     *
     * @return A tree with root node 1.
     */
    protected StrahlerTree<DefaultEdge> prepareTree() {

        StrahlerTree<DefaultEdge> graph =
                new StrahlerTree<DefaultEdge>(DefaultEdge.class);

        graph.addEdge(1, 2);
        graph.addEdge(1, 3);

        graph.addEdge(3, 4);
        graph.addEdge(3, 12);

        graph.addEdge(4, 5);
        graph.addEdge(4, 8);

        graph.addEdge(5, 6);
        graph.addEdge(5, 7);

        graph.addEdge(8, 9);
        graph.addEdge(8, 10);
        graph.addEdge(8, 11);

        graph.addEdge(12, 13);

        graph.addEdge(13, 14);
        graph.addEdge(13, 16);

        graph.addEdge(14, 15);

        graph.addEdge(16, 17);
        graph.addEdge(16, 18);

        graph.addEdge(18, 19);
        graph.addEdge(18, 20);

        // Set the root vertex.
        graph.setRootVertex(1);

        return graph;
    }
}
