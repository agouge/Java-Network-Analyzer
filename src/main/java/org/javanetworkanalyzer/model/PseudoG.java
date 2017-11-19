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

import org.javanetworkanalyzer.data.VId;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.Pseudograph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A keyed pseudograph.
 *
 * @author Adam Gouge
 */
public class PseudoG<V extends VId, E extends EdgeID> extends Pseudograph<V, E>
        implements UndirectedG<V, E> {

    /**
     * Map of ids to vertices.
     */
    private final Map<Integer, V> nodeMap;
    /**
     * Constructor for {@link V} objects.
     */
    private Constructor<? extends V> vConstructor;
    /**
     * A logger.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(PseudoG.class);

    /**
     * Creates a new pseudograph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public PseudoG(Class<? extends V> vertexClass,
                   Class<? extends E> edgeClass) {
        this(vertexClass, new ClassBasedEdgeFactory<V, E>(edgeClass));
    }

    /**
     * Creates a new pseudograph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public PseudoG(Class<? extends V> vertexClass,
                   EdgeFactory<V, E> ef) {
        super(ef);
        this.nodeMap = new HashMap<Integer, V>();
        try {
            this.vConstructor = vertexClass.getConstructor(Integer.class);
        } catch (NoSuchMethodException ex) {
            LOGGER.error("The vertex must have a V(Integer) constructor.", ex);
        } catch (SecurityException ex) {
            LOGGER.trace(ex.toString());
        }
    }

    @Override
    public boolean addVertex(int id) {
        if (!nodeMap.containsKey(id)) {
            try {
                V node = vConstructor.newInstance(id);
                nodeMap.put(id, node);
                return addVertex(node);
            } catch (Exception ex) {
                return false;
            }
        } else {
            throw new IllegalStateException(
                    "This vertex has already been added!");
        }
    }

    @Override
    public V getVertex(int id) {
        return nodeMap.get(id);
    }

    @Override
    public E addEdge(int source, int target) {
        return addEdge(source, target, -1);
    }

    @Override
    public E addEdge(int source, int target, int edgeID) {
        if (!containsVertex(getVertex(source))) {
            addVertex(source);
        }
        if (!containsVertex(getVertex(target))) {
            addVertex(target);
        }
        final E e = addEdge(getVertex(source), getVertex(target));
        e.setID(edgeID);
        return e;
    }
}
