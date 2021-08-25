package com.arpi.subeditor;

import java.util.Arrays;

class TimeReseter {

    public void TimeReseter(String orinalTime, double adjustBy) {

        double[] timeStamp = new double[3];
        double hours;
        double minutes;
        double seconds;
        double adjustByInSec = adjustBy;

        System.out.println();
        //System.out.println("Modified by (secs): " + adjustByInSec);

        if (timeStamp[0] + adjustByInSec > 3600) {
            timeStamp[0] = Math.floor(((timeStamp[0] * 3600) + adjustByInSec) / 3600);
            timeStamp[1] = Math.floor(((timeStamp[1] * 3600) + adjustByInSec) / 3600);
            timeStamp[2] = Math.floor(((timeStamp[2] * 3600) + adjustByInSec) / 3600);

        } else if ((timeStamp[1] + adjustByInSec <= 3600) && (timeStamp[1] + adjustByInSec >= 60)) {
            timeStamp[1] = Math.floor(((timeStamp[1] * 60) + adjustByInSec) / 60);
            timeStamp[2] = Math.floor(((timeStamp[2] * 60) + adjustByInSec) / 60);

        } else {
            timeStamp[2] = Math.floor(timeStamp[2] + adjustByInSec);
        }

        System.out.println("New time is: " + Arrays.toString(timeStamp));
        // ide kell a timer formater
    }
}
