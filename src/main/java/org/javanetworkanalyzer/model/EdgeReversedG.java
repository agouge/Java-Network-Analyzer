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
package org.javanetworkanalyzer.model;

import org.javanetworkanalyzer.data.IdInfo;
import org.jgrapht.graph.EdgeReversedGraph;

/**
 * Provides an edge-reversed view of a directed graph.
 *
 * @author Adam Gouge
 */
public class EdgeReversedG<V extends IdInfo, E>
        //        extends GraphDelegator<V, E>
        //        implements DirectedG<V, E> {
        extends EdgeReversedGraph<V, E>
        implements DirectedG<V, E> {

    /**
     * The graph to which operations are delegated.
     */
    private DirectedG<V, E> delegate;

    public EdgeReversedG(DirectedG<V, E> g)
            throws NoSuchMethodException {
        super(g);
        this.delegate = g;
    }

    @Override
    public boolean addVertex(int id) {
        throw new UnsupportedOperationException("Adding vertices to "
                + " an edge-reversed graph is not currently supported.");
    }

    @Override
    public V getVertex(int id) {
        return delegate.getVertex(id);
    }

    @Override
    public E addEdge(int source, int target) {
        throw new UnsupportedOperationException("Adding edges to "
                + " an edge-reversed graph is not currently supported.");
    }
}
