/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.util;

/**
 * Default implementation of a progress monitor.
 *
 * @author Adam Gouge
 */
public class DefaultProgressMonitor implements ProgressMonitor {

    private boolean cancelled = false;
    private int percentage = 0;
    private int end = 100;

    /**
     * Does nothing.
     *
     * @see ProgressMonitor#startTask(java.lang.String, long).
     */
    @Override
    public void startTask(String taskName, long end) {
        System.out.println("STARTING TASK \"" + taskName
                + "\"");
    }

    /**
     * Does nothing.
     *
     * @see ProgressMonitor#endTask().
     */
    @Override
    public void endTask() {
        System.out.println("TASK FINISHED");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProgress(long progress) {
        percentage = (int) ((progress * 100) / end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getProgress() {
        return percentage;
    }
}
