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

import java.util.Arrays;

import org.javanetworkanalyzer.data.VDijkstra;
import org.javanetworkanalyzer.graphcreators.GraphPrep;
import org.javanetworkanalyzer.model.DirectedG;
import org.javanetworkanalyzer.model.DirectedWeightedPseudoG;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.KeyedGraph;
import org.javanetworkanalyzer.model.UndirectedG;
import org.javanetworkanalyzer.model.WeightedEdgeReversedG;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests {@link Dijkstra} on all possible graph configurations via a
 * {@link GraphPrep}.
 *
 * @author Adam Gouge
 */
public abstract class DijkstraTest {

    /**
     * Returns the {@link GraphPrep} that prepares the graphs on which to
     * perform the tests.
     *
     * @return The {@link GraphPrep}
     */
    public abstract GraphPrep getGraphPrep();

    @Test
    public void testWD() throws Exception {
        DirectedWeightedPseudoG<VDijkstra, Edge> g =
                getGraphPrep().weightedDirected();
        assertTrue(Arrays.deepEquals(getGraphPrep().expectedDistancesWD(),
                                     actualDistances(g)));
    }

    @Test
    public void testWR() throws Exception {
        WeightedEdgeReversedG<VDijkstra, Edge> g =
                getGraphPrep().weightedReversed();
        assertTrue(Arrays.deepEquals(getGraphPrep().expectedDistancesWR(),
                                     actualDistances(g)));
    }

    @Test
    public void testWU() throws Exception {
        UndirectedG<VDijkstra, Edge> g = getGraphPrep().weightedUndirected();
        assertTrue(Arrays.deepEquals(getGraphPrep().expectedDistancesWU(),
                                     actualDistances(g)));
    }

    @Test
    public void testD() throws Exception {
        DirectedG<VDijkstra, Edge> g = getGraphPrep().directed();
        assertTrue(Arrays.deepEquals(getGraphPrep().expectedDistancesD(),
                                     actualDistances(g)));
    }

    @Test
    public void testR() throws Exception {
        DirectedG<VDijkstra, Edge> g = getGraphPrep().reversed();
        assertTrue(Arrays.deepEquals(getGraphPrep().expectedDistancesR(),
                                     actualDistances(g)));
    }

    @Test
    public void testU() throws Exception {
        UndirectedG<VDijkstra, Edge> g = getGraphPrep().undirected();
        assertTrue(Arrays.deepEquals(getGraphPrep().expectedDistancesU(),
                                     actualDistances(g)));
    }

    /**
     * Executes {@link Dijkstra} on the given graph and returns the distance
     * matrix.
     *
     * @param g Graph
     *
     * @return Distance matrix
     *
     * @throws Exception
     */
    public Double[][] actualDistances(KeyedGraph<VDijkstra, Edge> g)
            throws Exception {
        Double[][] d = new Double[getGraphPrep().getNumberOfVertices()][getGraphPrep().
                getNumberOfVertices()];

        Dijkstra dijkstra = new Dijkstra(g);
        for (int i = 1; i < getGraphPrep().getNumberOfVertices() + 1; i++) {
            dijkstra.calculate(g.getVertex(i));
            for (int j = 1; j < getGraphPrep().getNumberOfVertices() + 1; j++) {
                d[i - 1][j - 1] = g.getVertex(j).getDistance();
            }
        }

        return d;
    }
}
