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
package org.javanetworkanalyzer.model;

import org.javanetworkanalyzer.data.VStrahler;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleGraph;

/**
 * A tree for use in the Strahler algorithm.
 *
 * @param <E> Edge class.
 *
 * @author Adam Gouge
 */
public class StrahlerTree<E> extends SimpleGraph<VStrahler, E> {

    /**
     * The root vertex.
     */
    private VStrahler rootVertex = null;
    /**
     * Map of ids to vertices.
     */
    private final Map<Integer, VStrahler> nodeMap;

    /**
     * @see AbstractBaseGraph
     */
    public StrahlerTree(Class<? extends E> edgeClass) {
        super(edgeClass);
        this.nodeMap = new HashMap<Integer, VStrahler>();
    }

    /**
     * @see AbstractBaseGraph
     */
    public StrahlerTree(EdgeFactory<VStrahler, E> ef) {
        super(ef);
        this.nodeMap = new HashMap<Integer, VStrahler>();
    }

    /**
     * Add a vertex with the given id.
     *
     * @param id Id
     *
     * @return True if the vertex was added.
     */
    private boolean addVertex(int id) {
        if (!nodeMap.containsKey(id)) {
            VStrahler node = new VStrahler(id);
            nodeMap.put(id, node);
            return addVertex(node);
        } else {
            throw new IllegalStateException(
                    "This vertex has already been added!");
        }
    }

    /**
     * Get the vertex with the given id.
     *
     * @param id Id.
     *
     * @return The vertex with the given id.
     */
    public VStrahler getVertex(int id) {
        return nodeMap.get(id);
    }

    /**
     * Set the root vertex as the vertex with the given id.
     *
     * @param id Id.
     */
    public void setRootVertex(int id) {
        VStrahler candidate = getVertex(id);
        if (candidate != null) {
            rootVertex = candidate;
        } else {
            throw new IllegalStateException(
                    "This vertex does not exist!");
        }
    }

    /**
     * Return the root vertex.
     *
     * @return The root vertex.
     */
    public VStrahler getRootVertex() {
        return rootVertex;
    }

    /**
     * Add an edge from the vertex with id source to the vertex with id target.
     *
     * @param source Source id.
     * @param target Target Id.
     *
     * @return The newly added edge, or null if it was not added.
     */
    public E addEdge(int source, int target) {
        if (!containsVertex(getVertex(source))) {
            addVertex(source);
        }
        if (!containsVertex(getVertex(target))) {
            addVertex(target);
        }
        return addEdge(getVertex(source), getVertex(target));
    }
}
