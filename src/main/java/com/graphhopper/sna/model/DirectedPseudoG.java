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
package com.graphhopper.sna.model;

import com.graphhopper.sna.data.IdInfo;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

/**
 * A keyed directed pseudograph.
 *
 * @author Adam Gouge
 */
public class DirectedPseudoG<V extends IdInfo, E>
        extends DirectedPseudograph<V, E>
        implements DirectedG<V, E> {

    /**
     * Map of ids to vertices.
     */
    private final Map<Integer, V> nodeMap;
    /**
     * Constructor for {@link V} objects.
     */
    private final Constructor<? extends V> vConstructor;

    /**
     * Creates a new directed pseudograph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public DirectedPseudoG(Class<? extends V> vertexClass,
                           Class<? extends E> edgeClass) throws
            NoSuchMethodException {
        this(vertexClass, new ClassBasedEdgeFactory<V, E>(edgeClass));
    }

    /**
     * Creates a new directed pseudograph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public DirectedPseudoG(Class<? extends V> vertexClass,
                           EdgeFactory<V, E> ef) throws NoSuchMethodException {
        super(ef);
        this.nodeMap = new HashMap<Integer, V>();
        this.vConstructor = vertexClass.getConstructor(Integer.class);
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
        if (!containsVertex(getVertex(source))) {
            addVertex(source);
        }
        if (!containsVertex(getVertex(target))) {
            addVertex(target);
        }
        return addEdge(getVertex(source), getVertex(target));
    }
}
