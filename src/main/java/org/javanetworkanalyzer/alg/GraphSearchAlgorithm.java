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
package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.VPred;
import org.javanetworkanalyzer.model.TraversalGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;

import java.util.List;
import java.util.Set;

/**
 * Root class for graph search algorithms, including BFS, DFS, Dijkstra, etc.,
 * and their modified versions for centrality and connectedness calculations.
 *
 * @param <V> Vertices
 * @param <E> Edges
 * @author Adam Gouge
 */
public abstract class GraphSearchAlgorithm<V extends VPred, E>
        implements TraversalAlg<V, E> {

    /**
     * The graph on which to calculate shortest paths.
     */
    protected final Graph<V, E> graph;
    /**
     * Current start node
     */
    private V currentStartNode;

    /**
     * Constructor. The user can specify whether SPTs/traversal graphs are
     * calculated.
     *
     * @param graph The graph
     */
    public GraphSearchAlgorithm(Graph<V, E> graph) {
        this.graph = graph;
    }

    /**
     * Performs any initializations to be done at the start of the
     * {@link #calculate} method.
     *
     * @param startNode Start node
     */
    protected void init(V startNode) {
        this.currentStartNode = startNode;
    }

    @Override
    public TraversalGraph<V, E> reconstructTraversalGraph() {

        if (currentStartNode == null) {
            throw new IllegalStateException("You must call #calculate before " +
                    "reconstructing the traversal graph.");
        }

        TraversalGraph<V, E> traversalGraph = new TraversalGraph<V, E>(
                graph.getEdgeFactory(), currentStartNode);
        for (V v : graph.vertexSet()) {
            Set<E> predEdges = (Set<E>) v.getPredecessorEdges();
            for (E e : predEdges) {
                V source = graph.getEdgeSource(e);
                V target = graph.getEdgeTarget(e);
                traversalGraph.addVertex(source);
                traversalGraph.addVertex(target);
                if (v.equals(source)) {
                    traversalGraph.addEdge(target, source);
                } else if (v.equals(target)) {
                    traversalGraph.addEdge(source, target);
                } else {
                    System.out.println("equals neither");
                }
            }
        }

        return traversalGraph;
    }

    /**
     * Returns the outgoing edges of a node for directed graphs and all edges of
     * a node for undirected graphs. Used in Dijkstra.
     *
     * @param node The node.
     * @return The outgoing edges of the node.
     */
    public Set<E> outgoingEdgesOf(V node) {
        return outgoingEdgesOf(graph, node);
    }

    public static Set outgoingEdgesOf(Graph g, Object node) {
        if (g instanceof DirectedGraph) {
            return ((DirectedGraph) g).outgoingEdgesOf(node);
        } else {
            return g.edgesOf(node);
        }
    }

    /**
     * Returns the successor list of a node for directed graphs or the neighbor
     * list of a node for undirected graphs. Used in BFS, DFS, Strahler.
     *
     * @param node The node.
     * @return The outgoing edges of the node.
     */
    public List<V> successorListOf(V node) {
        if (graph instanceof DirectedGraph) {
            return Graphs.successorListOf((DirectedGraph) graph, node);
        } else {
            return Graphs.neighborListOf(graph, node);
        }
    }

    /**
     * Returns the outdegree (or degree for undirected graphs) of the given
     * node. Used in Strahler.
     *
     * @param node The node.
     * @return The outdegree (or degree for undirected graphs) of the given
     *         node.
     */
    // 
    public int outdegree(V node) {
        if (graph instanceof DirectedGraph) {
            return ((DirectedGraph) graph).outDegreeOf(node);
        } else if (graph instanceof UndirectedGraph) {
            return ((UndirectedGraph) graph).degreeOf(node);
        }
        return -1;
    }
}
