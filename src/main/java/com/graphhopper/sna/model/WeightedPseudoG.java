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
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * A keyed weighted pseudograph.
 *
 * @author Adam Gouge
 */
public class WeightedPseudoG<V extends IdInfo, E extends Edge>
        extends PseudoG<V, E>
        implements WeightedKeyedGraph<V, E> {

    /**
     * Creates a new weighted pseudograph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public WeightedPseudoG(Class<? extends V> vertexClass,
                           Class<? extends E> edgeClass)
            throws NoSuchMethodException {
        this(vertexClass, new ClassBasedEdgeFactory<V, E>(edgeClass));
    }

    /**
     * Creates a new weighted pseudograph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public WeightedPseudoG(Class<? extends V> vertexClass,
                           EdgeFactory<V, E> ef)
            throws NoSuchMethodException {
        super(vertexClass, ef);
    }

    @Override
    public void setEdgeWeight(E e, double weight) {
        super.setEdgeWeight(e, weight);
        e.setWeight(weight);
    }
}
