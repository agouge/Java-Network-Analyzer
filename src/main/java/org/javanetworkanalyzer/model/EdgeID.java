package org.javanetworkanalyzer.model;

/**
 * Interface for edges indexed by an integer id.
 *
 * @author Adam Gouge
 */
public interface EdgeID {

    /**
     * Get this edge's id.
     *
     * @return This edge's id
     */
    int getID();

    /**
     * Set this edge's id.
     *
     * @param id The id to set
     */
    void setID(int id);
}
