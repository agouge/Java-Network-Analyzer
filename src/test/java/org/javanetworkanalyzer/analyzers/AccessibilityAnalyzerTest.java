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
package org.javanetworkanalyzer.analyzers;

import java.util.HashSet;
import java.util.Set;
import org.javanetworkanalyzer.data.VAccess;
import org.javanetworkanalyzer.graphcreators.CormenGraphPrep;
import org.javanetworkanalyzer.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the {@link AccessibilityAnalyzer} on the Cormen graph under all
 * possible configurations.
 *
 * @author Adam Gouge
 */
public class AccessibilityAnalyzerTest extends CormenGraphPrep {

    private static final double TOLERANCE = 0.0;

    @Test
    public void testD() throws Exception {
        DirectedG<VAccess, EdgeCent> g = directed();
        test(g);
        assertEquals(4, g.getVertex(1).getClosestDestinationId());
        assertEquals(1.0, g.getVertex(1).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(2).getClosestDestinationId());
        assertEquals(1.0, g.getVertex(2).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(3).getClosestDestinationId());
        assertEquals(1.0, g.getVertex(3).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(4).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(4).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(5).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(5).getDistanceToClosestDestination(),
                     TOLERANCE);
    }

    @Test
    public void testR() throws Exception {
        DirectedG<VAccess, EdgeCent> g = reversed();
        test(g);
        assertEquals(5, g.getVertex(1).getClosestDestinationId());
        assertEquals(1.0, g.getVertex(1).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(2).getClosestDestinationId());
        assertEquals(1.0, g.getVertex(2).getDistanceToClosestDestination(),
                     TOLERANCE);
        // The closest destination could be either 4 or 5.
        if (g.getVertex(3).getClosestDestinationId() != 4
            && g.getVertex(3).getClosestDestinationId() != 5) {
            assertTrue(false);
        }
        assertEquals(1.0, g.getVertex(3).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(4).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(4).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(5).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(5).getDistanceToClosestDestination(),
                     TOLERANCE);
    }

    @Test
    public void testU() throws Exception {
        UndirectedG<VAccess, EdgeCent> g = undirected();
        test(g);
        // The closest destination could be either 4 or 5.
        if (g.getVertex(1).getClosestDestinationId() != 4
            && g.getVertex(1).getClosestDestinationId() != 5) {
            assertTrue(false);
        }
        assertEquals(1.0, g.getVertex(1).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(2).getClosestDestinationId());
        assertEquals(1.0, g.getVertex(2).getDistanceToClosestDestination(),
                     TOLERANCE);
        // The closest destination could be either 4 or 5.
        if (g.getVertex(3).getClosestDestinationId() != 4
            && g.getVertex(3).getClosestDestinationId() != 5) {
            assertTrue(false);
        }
        assertEquals(1.0, g.getVertex(3).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(4).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(4).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(5).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(5).getDistanceToClosestDestination(),
                     TOLERANCE);
    }

    @Test
    public void testWD() throws Exception {
        DirectedWeightedPseudoG<VAccess, EdgeCent> g = weightedDirected();
        test(g);
        assertEquals(4, g.getVertex(1).getClosestDestinationId());
        assertEquals(5.0, g.getVertex(1).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(2).getClosestDestinationId());
        assertEquals(2.0, g.getVertex(2).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(3).getClosestDestinationId());
        assertEquals(4.0, g.getVertex(3).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(4).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(4).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(5).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(5).getDistanceToClosestDestination(),
                     TOLERANCE);
    }

    @Test
    public void testWR() throws Exception {
        WeightedEdgeReversedG<VAccess, EdgeCent> g = weightedReversed();
        test(g);
        assertEquals(5, g.getVertex(1).getClosestDestinationId());
        assertEquals(7.0, g.getVertex(1).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(2).getClosestDestinationId());
        assertEquals(3.0, g.getVertex(2).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(3).getClosestDestinationId());
        assertEquals(4.0, g.getVertex(3).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(4).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(4).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(5).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(5).getDistanceToClosestDestination(),
                     TOLERANCE);
    }

    @Test
    public void testWU() throws Exception {
        UndirectedG<VAccess, EdgeCent> g = weightedUndirected();
        test(g);
        assertEquals(4, g.getVertex(1).getClosestDestinationId());
        assertEquals(5.0, g.getVertex(1).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(2).getClosestDestinationId());
        assertEquals(2.0, g.getVertex(2).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(3).getClosestDestinationId());
        assertEquals(3.0, g.getVertex(3).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(4, g.getVertex(4).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(4).getDistanceToClosestDestination(),
                     TOLERANCE);
        assertEquals(5, g.getVertex(5).getClosestDestinationId());
        assertEquals(0.0, g.getVertex(5).getDistanceToClosestDestination(),
                     TOLERANCE);
    }

    public void test(KeyedGraph<VAccess, EdgeCent> g) throws Exception {
        // Prepare the destinations.
        Set<VAccess> destinations = new HashSet<VAccess>();
        destinations.add(g.getVertex(5));
        destinations.add(g.getVertex(4));
        // Do accessibility analysis.
        new AccessibilityAnalyzer<EdgeCent>(g, destinations).compute();
    }
}
