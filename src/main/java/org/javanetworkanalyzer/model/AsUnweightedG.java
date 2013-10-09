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
package org.javanetworkanalyzer.model;

import org.javanetworkanalyzer.data.VId;
import org.jgrapht.graph.AsUnweightedGraph;

/**
 * An unweighted view of the backing weighted graph specified in the
 * constructor.
 *
 * @author Adam Gouge
 */
public class AsUnweightedG<V extends VId, E extends EdgeID>
        //        extends GraphDelegator<V, E>
        //        implements Serializable 
        extends AsUnweightedGraph<V, E>
        implements UndirectedG<V, E> {

    /**
     * The graph to which operations are delegated.
     */
    private KeyedGraph<V, E> delegate;

    public AsUnweightedG(KeyedGraph<V, E> g)
            throws NoSuchMethodException {
        super(g);
        this.delegate = g;
    }

    @Override
    public boolean addVertex(int id) {
        throw new UnsupportedOperationException(
                "Adding vertices to an as-unweighted graph is not "
                + "supported.");
    }

    @Override
    public V getVertex(int id) {
        return delegate.getVertex(id);
    }

    @Override
    public E addEdge(int source, int target) {
        throw new UnsupportedOperationException(
                "Adding edges to an as-unweighted graph is not "
                + "supported.");
    }

    @Override
    public E addEdge(int source, int target, int edgeID) {
        throw new UnsupportedOperationException(
                "Adding edges to an as-unweighted graph is not "
                        + "supported.");
    }
}
