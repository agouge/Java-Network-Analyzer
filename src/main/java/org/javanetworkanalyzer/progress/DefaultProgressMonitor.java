/**
 * Java Network Analyzer provides a collection of graph theory and social
 * network analysis algorithms implemented on mathematical graphs using the
 * <a href="http://www.jgrapht.org/">JGraphT</a> library.
 *
 * Java Network Analyzer is distributed under the GPL 3 license. It is produced
 * by the "Atelier SIG" team of the <a href="http://www.irstv.fr">IRSTV
 * Institute</a>, CNRS FR 2488.
 *
 * Copyright 2013 IRSTV (CNRS FR 2488).
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
 */
package org.javanetworkanalyzer.progress;

/**
 * Default implementation of a progress monitor that prints a progress bar to
 * the console.
 *
 * @author Adam Gouge
 */
public class DefaultProgressMonitor implements ProgressMonitor {

    private boolean cancelled = false;
    private int percentageComplete = 0;
    private long end;
    private ConsoleProgressBar consoleProgressBar;

    /**
     * Sets the end and instantiates a {@link ConsoleProgressBar} for this task.
     *
     * @see ProgressMonitor#startTask(java.lang.String, long).
     */
    @Override
    public void startTask(String taskName, long end) {
        System.out.println("STARTING TASK \"" + taskName
                + "\"");
        this.end = end;
        // The console progress bar will have a width of 40 characters
        // and will update every second.
        this.consoleProgressBar = new ConsoleProgressBar(this, 40, 1);
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
    public void setProgress(long count) {
        percentageComplete = (int) ((count * 100) / end);
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
    public void setProgress(long count, long startTime) {

        // Print the progress to the console.
        String bar = consoleProgressBar.progressBar(count, startTime);
        if (!bar.equals("")) {
            System.out.println(bar);
        }

        // Update the percentage complete.
        setProgress(count);
    }
}
