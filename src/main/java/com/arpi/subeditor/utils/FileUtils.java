package com.arpi.subeditor.utils;

import com.arpi.subeditor.model.Pair;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {        // Utility class, nem kötődik példányhoz, inputot csak az argumentumoktól kap
    public static String extractFilanameFromPath(String filePath) {
        Pattern fileNamePattern = Pattern.compile("(?<=/).+(?=\\.srt)");
        Matcher fileNameMatcher = fileNamePattern.matcher((String) filePath);
        fileNameMatcher.find();

        return fileNameMatcher.group();
    }

    public static Path fileNominator(String originalFilename) {
        SimpleDateFormat dateFormatForFilename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Timestamp timestampForFilename = new Timestamp(System.currentTimeMillis());

        Path outputPath = Paths.get("C:\\dev\\JavaTraining\\SubEditor\\outputfiles\\" + originalFilename + "_" + dateFormatForFilename.format(timestampForFilename) + ".srt");

        return outputPath;
    }

    public static Pair getTimestampsAsStrings(String timestampsWithArrow) {
        Pair<String> timestamps = new Pair<String>();
        // get timestamps as strings and put them in array
        Pattern singleTimestampPattern = Pattern.compile("(\\d{2}:\\d{2}:\\d{2},\\d{3}) --> (\\d{2}:\\d{2}:\\d{2},\\d{3})");
        Matcher singleTimestampMatcher = singleTimestampPattern.matcher(timestampsWithArrow);

        singleTimestampMatcher.find();
        timestamps.setFirst(singleTimestampMatcher.group(1));
        timestamps.setSecond(singleTimestampMatcher.group(2));

        return timestamps;
    }

    public static String adjustTime(String originalTimestamp, double adjustBySecs) {
        String newTimestamp = "";

        Pattern hoursPattern = Pattern.compile("\\d+(?=:\\d+:)");
        Pattern minutesPattern = Pattern.compile("(?<=:)\\d+(?=:)");
        Pattern secondsPattern = Pattern.compile("[0-9][0-9],[0-9][0-9][0-9]");

        Matcher hoursMatcher = hoursPattern.matcher(originalTimestamp);
        Matcher minutesMatcher = minutesPattern.matcher(originalTimestamp);
        Matcher secondsMatcher = secondsPattern.matcher(originalTimestamp);

        hoursMatcher.find();
        minutesMatcher.find();
        secondsMatcher.find();

        String hours = hoursMatcher.group();
        String minutes = minutesMatcher.group();
        String seconds = secondsMatcher.group().replaceAll(",",".");

        double timestampSum = (Double.parseDouble(hours) * 3600 + Double.parseDouble(minutes) * 60 + Double.parseDouble(seconds)) + adjustBySecs;
        double hour = Math.floor(timestampSum / 3600);
        double minute = Math.floor((timestampSum - (hour * 3600)) / 60);
        double second = timestampSum - (hour * 3600 + minute * 60);

        /* Replace with proper exception handling */
        if (timestampSum < 0) {
            return "00:00:00,000";
        } else {
            String hoursFinal = "";
            String minutesFinal = "";
            String secondsFinal = "";

            /* Format segment ot HH:mm:ss,sss */
            if ((int) hour < 10) {
                hoursFinal = "0" + (int) hour;
            } else {
                hoursFinal = String.valueOf((int) hour);
            }

            if ((int) minute < 10) {
                minutesFinal = "0" + (int) minute;
            } else {
                minutesFinal = String.valueOf((int) minute);
            }

            if (second < 10.0) {
                secondsFinal = "0" + String.valueOf(second).replaceAll("(?<=.\\d{3})\\d+", "");
            } else {
                secondsFinal = String.valueOf(second).replaceAll("(?<=.\\d{3})\\d+", "");
            }

            newTimestamp = hoursFinal + ":" + minutesFinal + ":" + secondsFinal.replace(".", ",");

        }
        return newTimestamp;
    }

}