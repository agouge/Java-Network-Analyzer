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

import com.graphhopper.sna.data.StrahlerInfo;
import com.graphhopper.storage.Graph;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.TIntHashSet;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests calculating the Strahler numbers of the nodes in a tree.
 *
 * @author Adam Gouge
 */
public class StrahlerTest extends DFSTest {

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
    public void testTree() throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        Graph graph = prepareTree();
//        Map<Integer, StrahlerInfo> nodeMap =
//                new DFSForStrahler(graph, StrahlerInfo.class, 1)
//                .calculate();
//
//        assertEquals(nodeMap.get(1).getStrahlerNumber(), 3);
//        assertEquals(nodeMap.get(2).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(3).getStrahlerNumber(), 3);
//        assertEquals(nodeMap.get(4).getStrahlerNumber(), 3);
//        assertEquals(nodeMap.get(5).getStrahlerNumber(), 2);
//        assertEquals(nodeMap.get(6).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(7).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(8).getStrahlerNumber(), 2);
//        assertEquals(nodeMap.get(9).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(10).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(11).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(12).getStrahlerNumber(), 2);
//        assertEquals(nodeMap.get(13).getStrahlerNumber(), 2);
//        assertEquals(nodeMap.get(14).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(15).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(16).getStrahlerNumber(), 2);
//        assertEquals(nodeMap.get(17).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(18).getStrahlerNumber(), 2);
//        assertEquals(nodeMap.get(19).getStrahlerNumber(), 1);
//        assertEquals(nodeMap.get(20).getStrahlerNumber(), 1);
    }
}
