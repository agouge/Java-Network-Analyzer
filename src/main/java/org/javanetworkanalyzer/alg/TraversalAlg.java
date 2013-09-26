package org.javanetworkanalyzer.alg;

import org.javanetworkanalyzer.data.VPred;
import org.javanetworkanalyzer.model.EdgeSPT;
import org.javanetworkanalyzer.model.TraversalGraph;

/**
 * Interface for algorithms that traverse a graph.
 *
 * @author Adam Gouge
 */
public interface TraversalAlg<V extends VPred, E extends EdgeSPT> {

    /**
     * Performs the graph search algorithm from the given start node.
     *
     * @param startNode Start node
     */
    void calculate(V startNode);

    /**
     * Returns the SPT (BFS/Dijkstra) or traversal graph (DFS) from the last
     * start node {@link #calculate} was called on. For BFS/Dijkstra, the
     * shortest path "tree" we return may contain multiple shortest paths.
     *
     * @return The SPT/traversal graph from the last start node {@link #calculate}
     *         was called on
     */
    TraversalGraph<V, E> reconstructTraversalGraph();
}
