/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 * It is part of the OrbisGIS tool ecosystem.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * Java Network Analyzer is distributed under GPL 3 license.
 *
 * Copyright (C) 2012-2014 CNRS (IRSTV CNRS FR 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC CNRS UMR 6285)
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
 * 
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.javanetworkanalyzer.data;

/**
 * Unweighted centrality vertex.
 *
 * @author Adam Gouge
 */
public class VUCent<E> extends VBFS<VUCent, E> implements VCent<VUCent, E> {

    /**
     * Local helper vertex to which all centrality operations are delegated.
     */
    private VCentImpl vCent;

    /**
     * Constructor: sets the id and initializes a local {@link VCentImpl}.
     *
     * @param id Id
     */
    public VUCent(Integer id) {
        super(id);
        vCent = new VCentImpl(id);
    }

    /**
     * Clears the predecessor list, resets the distance to the default value and
     * resets the shortest path count and dependency to zero.
     */
    @Override
    public void reset() {
        // Clear the predecessor list and reset the distance to the default
        // distance.
        super.reset();
        // Reset the shortest path count and dependency to zero.
        vCent.reset();
    }

    /**
     * Clears the predecessor list, sets the distance and dependency to zero and
     * the shortest path count to one.
     */
    @Override
    public void setSource() {
        // Clear the predecessor list and set the distance to zero.
        super.setSource();
        // Set the shortest path count to one and the dependency to zero.
        vCent.setSource();
    }

    @Override
    public long getSPCount() {
        return vCent.getSPCount();
    }

    @Override
    public void accumulateSPCount(long additionalSPCount) {
        vCent.accumulateSPCount(additionalSPCount);
    }

    @Override
    public void setSPCount(long newSPCount) {
        vCent.setSPCount(newSPCount);
    }

    @Override
    public double getDependency() {
        return vCent.getDependency();
    }

    @Override
    public void accumulateDependency(double additionalDependency) {
        vCent.accumulateDependency(additionalDependency);
    }

    @Override
    public double getBetweenness() {
        return vCent.getBetweenness();
    }

    @Override
    public void accumulateBetweenness(double additionalBetweenness) {
        vCent.accumulateBetweenness(additionalBetweenness);
    }

    @Override
    public void setBetweenness(double betweenness) {
        vCent.setBetweenness(betweenness);
    }

    @Override
    public double getCloseness() {
        return vCent.getCloseness();
    }

    @Override
    public void setCloseness(double closeness) {
        vCent.setCloseness(closeness);
    }
}
