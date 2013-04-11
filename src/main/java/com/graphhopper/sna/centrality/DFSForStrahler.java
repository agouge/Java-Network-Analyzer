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
package com.graphhopper.sna.centrality;

import com.graphhopper.sna.data.StrahlerInfo;
import com.graphhopper.util.EdgeIterator;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Calculates the Strahler numbers of the nodes in the given tree.
 *
 * <p> The graph passed to the constructor is assumed to be a tree. If is is not
 * a tree, this algorithm will return results, but they will be false.
 *
 * @see http://en.wikipedia.org/wiki/Strahler_number
 *
 * @author Adam Gouge
 */
public class DFSForStrahler<E> extends DFSRootNode<StrahlerInfo, E> {

    /**
     * Constructor.
     *
     * @param graph    The graph.
     * @param rootNode The root node.
     */
    public DFSForStrahler(Graph<StrahlerInfo, E> graph,
                          StrahlerInfo rootNode) {
        super(graph, rootNode);
    }

    /**
     * Visit the given node and calculate its Strahler number.
     *
     * @param node The node.
     */
    @Override
    protected void visit(StrahlerInfo node) {
        super.visit(node);
        calculateStrahlerNumber(node);
    }

    /**
     * Calculate the Strahler number of the given node.
     *
     * @param node The node.
     */
    private void calculateStrahlerNumber(StrahlerInfo node) {

        if (node.getFinishingTime() == node.getDiscoveryTime() + 1) {
            // All leafs have a Strahler number of 1. 
            // (Leafs have the property that their finishing times are one 
            // more than their discovery times.)
            node.setStrahlerNumber(1);
        } else {
            // If this is not a leaf, we must consider the outdegree.
            int outDegree = GeneralizedGraphAnalyzer.outdegree(graph, node);

            if (outDegree == 1) {
                // If there is only one child, then the Strahler number is
                // the same as that of the child.
                EdgeIterator outgoingEdges =
                        GeneralizedGraphAnalyzer.outgoingEdges(graph, node);
                outgoingEdges.next();
                int child = outgoingEdges.adjNode();
                node.setStrahlerNumber(nodeMap.get(child).getStrahlerNumber());
            } else {

                // Otherwise the outdegree is >= 2, so consider the top two
                // Strahler numbers.
                int[] topTwo = topTwoStrahlerNumbers(node);
                int max = topTwo[0];
                int secondLargest = topTwo[1];

                if (max == secondLargest) {
                    // If the top two Strahler numbers are equal, then there
                    // are >= 2 children with the same Strahler number S, and
                    // all other children have Strahler number <= S. So the
                    // Strahler number is S + 1.
                    node.setStrahlerNumber(max + 1);
                } else {
                    // If they are not equal, then there is one child with
                    // Strahler number S, and all other children have Strahler
                    // number < S. So the Strahler number is S.
                    node.setStrahlerNumber(max);
                }
            }
        }
    }

    /**
     * Returns a 2-element array consisting of the largest and second largest
     * Strahler numbers of children of the given node.
     *
     * @param node The node
     *
     * @return The top two Strahler numbers of the node's children.
     */
    private int[] topTwoStrahlerNumbers(int node) {
        int max = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        for (EdgeIterator outgoingEdges =
                GeneralizedGraphAnalyzer.outgoingEdges(graph, node);
                outgoingEdges.next();) {
            int current = nodeMap.get(outgoingEdges.adjNode())
                    .getStrahlerNumber();
            if (current > max) {
                secondLargest = max;
                max = current;
            } else if (current > secondLargest) {
                secondLargest = current;
            }
        }
        return new int[]{max, secondLargest};
    }
}