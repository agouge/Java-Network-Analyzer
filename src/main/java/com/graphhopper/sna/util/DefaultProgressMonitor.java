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
    private int percentageComplete = 0;
    private long end;

    /**
     * Sets the end.
     *
     * @see ProgressMonitor#startTask(java.lang.String, long).
     */
    @Override
    public void startTask(String taskName, long end) {
        System.out.println("STARTING TASK \"" + taskName
                + "\"");
        this.end = end;
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
    public int setProgress(long count) {
        percentageComplete = (int) ((count * 100) / end);
        return percentageComplete;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPercentageComplete() {
        return percentageComplete;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getEnd() {
        return end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int setProgress(long count, long startTime) {

        // Update the progress.
        return setProgress(count);
    }
}
