/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.util;

/**
 * A {@link ProgressMonitor} which does nothing.
 *
 * @author Adam Gouge
 */
public class NullProgressMonitor implements ProgressMonitor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTask(String taskName, long end) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTask() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProgress(long progress) {
    }
}
