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
package org.javanetworkanalyzer.analyzers;

import org.javanetworkanalyzer.alg.DijkstraForCentrality;
import org.javanetworkanalyzer.data.VWCent;
import org.javanetworkanalyzer.data.WeightedPathLengthData;
import org.javanetworkanalyzer.model.EdgeCent;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import java.lang.reflect.InvocationTargetException;
import org.jgrapht.WeightedGraph;

/**
 * Calculates various centrality measures on weighted graphs which are
 * <b>assumed to be connected</b>.
 *
 * @author Adam Gouge
 * @param <E> edge
 */
public class WeightedGraphAnalyzer<E extends EdgeCent>
        extends GraphAnalyzer<VWCent, E, WeightedPathLengthData> {

    private final DijkstraForCentrality<E> dijkstra;

    /**
     * Initializes a new instance of a weighted graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph The graph to be analyzed.
     * @param pm    The {@link ProgressMonitor} to be used.
     */
    public WeightedGraphAnalyzer(
            WeightedGraph<VWCent, E> graph,
            ProgressMonitor pm)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        super(graph, pm);
        this.dijkstra = new DijkstraForCentrality<E>(graph, stack);
    }

    /**
     * Initializes a new instance of a weighted graph analyzer that doesn't keep
     * track of progress.
     *
     * @param graph The graph to be analyzed.
     */
    public WeightedGraphAnalyzer(
            WeightedGraph<VWCent, E> graph)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        this(graph, new NullProgressMonitor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DijkstraForCentrality<E> calculateShortestPathsFromNode(
            VWCent startNode) {
        // Need to compute all shortest paths from s=startNode.
        //
        // Once a SP from s to a node "current"=w is found, we need to
        // (in the NodeBetweennessInfo of w):
        //     1. Increment appropriately the sPCount of w.
        //     2. Store d(s,w) in the distance of w.
        //     3. Append the predecessor v of w on the SP s->...->v->w.
        //
        // We also need to push s to the stack and push all the other nodes
        // to the stack in order of non-decreasing distance from s (so that
        // when we pop from the stack in the dependency calculation
        // {@link GraphAnalyzer.accumulateDependencies(int, TIntArrayStack)},
        // the nodes are popped in order of non-increasing distance from s.
        // This is IMPORTANT.
        dijkstra.calculate(startNode);
        return dijkstra;
    }

    @Override
    public void computeAll() throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        pm.startTask("Weighted graph analysis", nodeCount);
        super.computeAll();
        pm.endTask();
    }
}
