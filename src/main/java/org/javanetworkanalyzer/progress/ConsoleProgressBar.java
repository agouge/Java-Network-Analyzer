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
package org.javanetworkanalyzer.progress;

import java.util.concurrent.TimeUnit;

/**
 * Contains methods for printing a progress bar to the console using a
 * {@link ProgressMonitor} passed to the constructor.
 *
 * @author Adam Gouge
 */
public class ConsoleProgressBar {

    /**
     * The progress monitor.
     */
    private final ProgressMonitor pm;
    /**
     * The width of the bar.
     */
    private final int width;
    /**
     * The update frequency.
     */
    private final int frequency;

    /**
     * Constructs a {@link ConsoleProgressBar} based on the given
     * {@link ProgressMonitor} with the specified character width of the bar and
     * the update frequency in seconds (1, 2, ...).
     *
     * @param pm        The progress monitor.
     * @param width     The width.
     * @param frequency The update frequency.
     */
    public ConsoleProgressBar(ProgressMonitor pm, int width, int frequency) {
        this.pm = pm;
        this.width = width;
        this.frequency = frequency;
    }

    /**
     * Returns a formatted String representing a progress bar.
     *
     * @param count     The count.
     * @param startTime The start time.
     *
     * @return The formatted progress bar String.
     */
    public String progressBar(long count, long startTime) {

        // The progress bar to print.
        StringBuilder progressBar = new StringBuilder();

        // Get the current progress.
        int percentageComplete = pm.getPercentageComplete();

        // (1) Print at 0%
        if (count == 0) {
            progressBar.append(bar(0, width)).append(percentage(0));
            // Carriage return.
            progressBar.append("\r");
        } else {
            // Get the elapsed time.
            long elapsed = (System.currentTimeMillis() - startTime);
            // Get mod value. This is used to update the progress bar at the
            // given frequency. We make sure the elapsed time is positive
            // and set the default mod value to 1.
            long mod = (elapsed > 0)
                    ? (long) (1000 * count * frequency) / elapsed
                    : 1;
            // If the calculation progresses too slowly, the mod value will
            // be zero, so we need to set it to one to make the progress
            // bar print at least once during each loop iteration.
            mod = (mod == 0) ? 1 : mod;
            // (2) Print the progress at the given frequency.
            if (count != pm.getEnd()) {
                // See if we should update the progress bar.
                if ((count % mod) == 0) {
                    progressBar.append(bar(percentageComplete, width));
                    progressBar.append(percentage(percentageComplete));
                    progressBar.append(time(count, elapsed));
                    // Carriage return.
                    progressBar.append("\r");
                }
            } // (3) Print at 100%.
            else {
                progressBar.append(bar(100, width)).append(percentage(100));
                progressBar.append(time(count, elapsed));
                // When done, print a new line.
                progressBar.append("\n");
            }
        }

        // Return the progress bar String.
        return progressBar.toString();
    }

    /**
     * Returns a formatted String representing just the bar part of the progress
     * bar.
     *
     * @param percentageComplete The percentage complete.
     * @param width              The width of the bar.
     *
     * @return The formatted bar String.
     */
    private String bar(long percentageComplete, int width) {

        StringBuilder bar = new StringBuilder();

        int numberOfEqualSigns = (int) ((percentageComplete * width) / 100);
        int numberOfBlankSpaces = width - numberOfEqualSigns;

        bar.append("  [");

        for (int i = 0; i < numberOfEqualSigns; i++) {
            bar.append("=");
        }

        if (percentageComplete == 0) {
            bar.append(" ");
        } else if (percentageComplete < 100) {
            bar.append(">");
        } else {
            bar.append("=");
        }

        for (int i = 0; i < numberOfBlankSpaces; i++) {
            bar.append(" ");
        }

        bar.append("] ");

        return bar.toString();
    }

    /**
     * Returns a formatted String representing just the percentage part of the
     * progress bar.
     *
     * @param percentageComplete The percentage complete.
     *
     * @return The formatted percentage String.
     */
    private String percentage(long percentageComplete) {

        StringBuilder percentage = new StringBuilder();

        if (percentageComplete < 10) {
            percentage.append("  ");
        } else if ((percentageComplete >= 10) && (percentageComplete < 100)) {
            percentage.append(" ");
        }
        percentage.append(percentageComplete).append("% ");

        return percentage.toString();
    }

    /**
     * Returns a formatted String representing just the time part of the
     * progress bar.
     *
     * @param count   The counter value.
     * @param elapsed The amount of time elapsed in milliseconds.
     *
     * @return The formatted time String.
     */
    private String time(long count, long elapsed) {

        StringBuilder time = new StringBuilder();

        long[] elapsedHMS = millisecondsToHoursMinutesSeconds(elapsed);
        time.append(formatHMSString(elapsedHMS[0],
                                    elapsedHMS[1],
                                    elapsedHMS[2]));

        // The remaining time is the average time per count multiplied by
        // the number of counts remaining.
        long remaining = (elapsed / count) * (pm.getEnd() - count);
        long[] remainingHMS = millisecondsToHoursMinutesSeconds(remaining);
        time.append(" (").append(formatHMSString(remainingHMS[0],
                                                 remainingHMS[1],
                                                 remainingHMS[2])).append(")");

        return time.toString();
    }

    /**
     * Takes a given time in milliseconds and returns a three-element array of
     * longs representing the same amount of time in hours, minutes and seconds
     * (format 00h00m00s).
     *
     * @param time The time.
     *
     * @return The hours, minutes and seconds.
     */
    private long[] millisecondsToHoursMinutesSeconds(long time) {
        // Convert the total time to milliseconds.
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        // Convert the total time to minutes and subtract the hours in minutes.
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time)
                - TimeUnit.HOURS.toMinutes(hours);
        // Convert the total time to seconds and subtract the hours
        // and minutes in seconds.
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time)
                - TimeUnit.HOURS.toSeconds(hours)
                - TimeUnit.MINUTES.toSeconds(minutes);
        return new long[]{hours, minutes, seconds};
    }

    /**
     * Returns a {@link String} formatting the given hours, minutes and seconds
     * to 00h00m00s.
     *
     * @param hours   The hours.
     * @param minutes The minutes.
     * @param seconds The seconds.
     *
     * @return The formatted {@link String}.
     */
    private String formatHMSString(long hours, long minutes, long seconds) {
        return addZeroIfLessThanTen(hours)
                + ":" + addZeroIfLessThanTen(minutes)
                + ":" + addZeroIfLessThanTen(seconds);
    }

    /**
     * Returns a {@link String} consisting of just the given number (if greater
     * than or equal to 10) or of the number prefixed by 0 (if less than 10).
     *
     * @param number The number.
     *
     * @return The formatted {@link String}.
     */
    private String addZeroIfLessThanTen(long number) {
        if (number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
}
