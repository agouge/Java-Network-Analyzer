package org.javanetworkanalyzer.model;

/**
 * Interface for edges to be used in a
 * {@link org.javanetworkanalyzer.alg.TraversalAlg}.
 *
 * @author Adam Gouge
 */
public interface EdgeSPT<E extends EdgeSPT> {

    E getBaseGraphEdge();

    void setBaseGraphEdge(E e);
}
