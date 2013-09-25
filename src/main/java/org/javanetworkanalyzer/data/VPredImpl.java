package org.javanetworkanalyzer.data;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of the {@link VPred} interface.
 *
 * @author Adam Gouge
 */
public class VPredImpl<V extends VPred, E> extends VId implements VPred<V, E> {

    /**
     * List of the predecessors of this node.
     *
     * I.e., the nodes lying on the shortest path to this node
     */
    private Set<V> predecessors = new HashSet<V>();
    private Set<E> predecessorEdges = new HashSet<E>();

    /**
     * Constructor: sets the id.
     *
     * @param id Id
     */
    public VPredImpl(Integer id) {
        super(id);
    }

    @Override
    public Set<V> getPredecessors() {
        return predecessors;
    }

    @Override
    public void addPredecessor(V pred) {
        predecessors.add(pred);
    }

    @Override
    public Set<E> getPredecessorEdges() {
        return predecessorEdges;
    }

    @Override
    public void addPredecessorEdge(E pred) {
        predecessorEdges.add(pred);
    }

    @Override
    public void clear() {
        predecessors.clear();
        predecessorEdges.clear();
    }
}
