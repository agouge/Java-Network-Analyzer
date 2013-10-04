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
package org.javanetworkanalyzer.graphcreators;

import org.javanetworkanalyzer.data.VId;
import org.javanetworkanalyzer.model.AsUndirectedG;
import org.javanetworkanalyzer.model.AsUnweightedDirectedG;
import org.javanetworkanalyzer.model.AsUnweightedG;
import org.javanetworkanalyzer.model.DirectedG;
import org.javanetworkanalyzer.model.DirectedWeightedPseudoG;
import org.javanetworkanalyzer.model.Edge;
import org.javanetworkanalyzer.model.UndirectedG;
import org.javanetworkanalyzer.model.WeightedEdgeReversedG;
import org.jgrapht.GraphPath;

/**
 * Creates (un)weighted (un)directed/reversed graphs from a single weighted
 * directed graph; also contains methods for expected distance matrices.
 *
 * The user need only enter the directed weighted edges in
 * {@link GraphPrep#weightedDirected()}, the expected distances for the
 * (un)weighted directed graphs and the upper triangle of the expected distance
 * matrices for the (un)weighted undirected graphs.
 *
 * @author Adam Gouge
 */
public abstract class GraphPrep<V extends VId, E extends Edge<E>> {

    /**
     * Gets the number of vertices contained in this graph.
     *
     * @return The number of vertices contained in this graph
     */
    public abstract int getNumberOfVertices();

    /**
     * The original weighted directed graph; it is up to the user to provide
     * this.
     *
     * @return The original weighted directed graph
     *
     * @throws NoSuchMethodException
     */
    public abstract DirectedWeightedPseudoG<V, E> weightedDirected()
            throws NoSuchMethodException;

    /**
     * Returns a weighted edge-reversed view.
     *
     * @return A weighted edge-reversed view
     *
     * @throws NoSuchMethodException
     */
    public WeightedEdgeReversedG<V, E> weightedReversed() throws
            NoSuchMethodException {
        return new WeightedEdgeReversedG<V, E>(weightedDirected());
    }

    /**
     * Returns a weighted undirected view.
     *
     * @return A weighted undirected view
     *
     * @throws NoSuchMethodException
     */
    public UndirectedG<V, E> weightedUndirected() throws
            NoSuchMethodException {
        return new AsUndirectedG<V, E>(weightedDirected());
    }

    /**
     * Returns an unweighted directed view.
     *
     * @return An unweighted directed view
     *
     * @throws NoSuchMethodException
     */
    public DirectedG<V, E> directed()
            throws NoSuchMethodException {
        return new AsUnweightedDirectedG<V, E>(weightedDirected());
    }

    /**
     * Returns an unweighted edge-reversed view.
     *
     * @return An unweighted edge-reversed view
     *
     * @throws NoSuchMethodException
     */
    public DirectedG<V, E> reversed()
            throws NoSuchMethodException {
        return new AsUnweightedDirectedG<V, E>(weightedReversed());
    }

    /**
     * Returns an unweighted undirected view.
     *
     * @return An unweighted undirected view
     *
     * @throws NoSuchMethodException
     */
    public UndirectedG<V, E> undirected()
            throws NoSuchMethodException {
        return (UndirectedG<V, E>) new AsUnweightedG<V, E>(weightedUndirected());
    }

    /**
     * The expected distance matrix for the original weighted directed graph.
     *
     * @return The expected distance matrix for the original weighted directed
     *         graph
     */
    public abstract Double[][] expectedDistancesWD();

    /**
     * The expected distance matrix for the weighted edge-reversed view.
     *
     * @return The expected distance matrix for the weighted edge-reversed view
     */
    public Double[][] expectedDistancesWR() {
        return transpose(expectedDistancesWD());
    }

    /**
     * The expected distance matrix for the weighted undirected view.
     *
     * @return The expected distance matrix for the weighted undirected view
     */
    public Double[][] expectedDistancesWU() {
        Double[][] d = upperRightTriangleWU();
        return add(d, transpose(d));
    }

    /**
     * The expected distance matrix for the unweighted directed view.
     *
     * @return The expected distance matrix for the unweighted directed view
     */
    public abstract Double[][] expectedDistancesD();

    /**
     * The expected distance matrix for the unweighted edge-reversed view.
     *
     * @return The expected distance matrix for the unweighted edge-reversed
     *         view
     */
    public Double[][] expectedDistancesR() {
        return transpose(expectedDistancesD());
    }

    /**
     * The expected distance matrix for the unweighted undirected view.
     *
     * @return The expected distance matrix for the unweighted undirected view
     */
    public Double[][] expectedDistancesU() {
        Double[][] d = upperRightTriangleU();
        return add(d, transpose(d));
    }

    /**
     * Returns an expected distance matrix for the weighted undirected view with
     * only the upper right triangle filled in.
     *
     * @return An expected distance matrix for the weighted undirected view with
     *         only the upper right triangle filled in
     */
    public abstract Double[][] upperRightTriangleWU();

    /**
     * Returns an expected distance matrix for the unweighted undirected view
     * with only the upper right triangle filled in.
     *
     * @return An expected distance matrix for the unweighted undirected view
     *         with only the upper right triangle filled in
     */
    public abstract Double[][] upperRightTriangleU();

    /**
     * Returns the |V| by |V| zero matrix.
     *
     * @return The |V| by |V| zero matrix
     */
    public Double[][] zeroMatrix() {
        Double[][] d = new Double[getNumberOfVertices()][getNumberOfVertices()];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                d[i][j] = 0.0;
            }
        }
        return d;
    }

    /**
     * Returns the transpose of the given matrix.
     *
     * @param m Matrix
     *
     * @return Transpose
     */
    public Double[][] transpose(Double[][] m) {
        Double[][] n = new Double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                n[i][j] = m[j][i];
            }
        }
        return n;
    }

    /**
     * Adds the given matrix
     *
     * @param a Matrix
     * @param b Matrix
     *
     * @return Sum
     */
    public Double[][] add(Double[][] a, Double[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Cannot add matrices "
                                               + "of unequal dimension.");
        }
        Double[][] aPlusB = new Double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                aPlusB[i][j] = a[i][j] + b[i][j];
            }
        }
        return aPlusB;
    }
}
