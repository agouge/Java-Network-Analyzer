/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.graphhopper.sna.util;

/**
 * Keeps track of the progress of a calculation.
 *
 * @author adam
 */
public interface ProgressMonitor {

    /**
     * Starts a new task with the given name and end time.
     *
     * @param taskName The task name.
     * @param end      The end time.
     */
    void startTask(String taskName, long end);

    /**
     * Ends the currently running task.
     */
    void endTask();

    /**
     * Returns {@code true} if the process is canceled and should end as quickly
     * as possible.
     *
     * @return
     */
    boolean isCancelled();

    /**
     * Sets the progress of the last added task.
     *
     * @param progress
     */
    void setProgress(long progress);
}