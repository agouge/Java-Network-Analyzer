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
package com.graphhopper.sna.progress;

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
     * Sets the progress and returns the percentage complete.
     *
     * @param progress
     *
     * @return The percentage complete.
     */
    int setProgress(long progress);

    /**
     * Returns the percentage complete.
     */
    int getPercentageComplete();

    /**
     * Gets the end value of the current task.
     *
     * @return The end value of the current task.
     */
    long getEnd();

    /**
     * Sets the progress, keeping track of the start time, and returns the
     * percentage complete.
     *
     * @param count     A counter to keep track of which iteration in a loop.
     * @param startTime The System time in milliseconds when this task was
     *                  started.
     *
     * @return The percentage complete.
     */
    int setProgress(long count, long startTime);
}