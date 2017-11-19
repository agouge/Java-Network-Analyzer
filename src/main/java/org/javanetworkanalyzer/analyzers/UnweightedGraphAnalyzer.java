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

import org.javanetworkanalyzer.alg.BFSForCentrality;
import org.javanetworkanalyzer.data.VUCent;
import org.javanetworkanalyzer.data.UnweightedPathLengthData;
import org.javanetworkanalyzer.model.EdgeCent;
import org.javanetworkanalyzer.progress.NullProgressMonitor;
import org.javanetworkanalyzer.progress.ProgressMonitor;
import java.lang.reflect.InvocationTargetException;
import org.jgrapht.Graph;

/**
 * Calculates various centrality measures on unweighted graphs <b>assumed to be
 * connected</b> using a Breadth-First Search (BFS) to calculate all possible
 * shortest paths.
 *
 * @author Adam Gouge
 * @param <E> edge
 */
public class UnweightedGraphAnalyzer<E extends EdgeCent>
        extends GraphAnalyzer<VUCent, E, UnweightedPathLengthData> {

    private final BFSForCentrality<E> bfs;

    /**
     * Initializes a new instance of an unweighted graph analyzer with the given
     * {@link ProgressMonitor}.
     *
     * @param graph The graph to be analyzed.
     * @param pm    The {@link ProgressMonitor} to be used.
     */
    public UnweightedGraphAnalyzer(
            Graph<VUCent, E> graph,
            ProgressMonitor pm) throws
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        super(graph, pm);
        this.bfs = new BFSForCentrality<E>(graph, stack);
    }

    /**
     * Initializes a new instance of an unweighted graph analyzer that doesn't
     * keep track of progress.
     *
     * @param graph The graph to be analyzed.
     */
    public UnweightedGraphAnalyzer(Graph<VUCent, E> graph)
            throws NoSuchMethodException,
            InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        this(graph, new NullProgressMonitor());
    }

    /**
     * Calculates the number of shortest paths from startNode to every other
     * node and the lengths of these paths using a Breadth-First Search (BFS),
     * storing them in the appropriate node info of {@link #nodeBetweenness};
     * also updates the predecessor sets.
     *
     * @param startNode          The start node.
     *
     */
    @Override
    protected BFSForCentrality<E> calculateShortestPathsFromNode(
            VUCent startNode) {
        bfs.calculate(startNode);
        return bfs;
    }

    @Override
    public void computeAll() throws InstantiationException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        pm.startTask("Unweighted graph analysis", nodeCount);
        super.computeAll();
        pm.endTask();
    }
}
