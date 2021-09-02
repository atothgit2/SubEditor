package com.arpi.subeditor.parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.Files.*;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Setter {
    public void modifyTimestampsAndExport(List<SubEntry> subEntriesFromParser, double modifierInSecs, String originalName) {
        for (SubEntry entry : subEntriesFromParser) {
            String timingLine = entry.timing;
            ArrayList<String> timestamps = new ArrayList<String>();
            timestamps = getTimestampsAsStrings(timingLine);
            String adjustedTimestamp1 = adjustTime(timestamps.get(0), modifierInSecs);
            String adjustedTimestamp2 = adjustTime(timestamps.get(1), modifierInSecs);

            entry.timing = adjustedTimestamp1 + " --> " + adjustedTimestamp2;
        }
        exportFile(objectsToString(subEntriesFromParser), originalName);
    }

    public ArrayList<String> getTimestampsAsStrings(String timestampsWithArrow) {
        ArrayList<String> timestamps = new ArrayList<String>();
        // get timestamps as strings and put them in array
        Pattern singleTimestampPattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2},\\d{3}");
        // Pattern singleTimestampPattern = Pattern.compile("[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9]");
        Matcher singleTimestampMatcher = singleTimestampPattern.matcher(timestampsWithArrow);

        while (singleTimestampMatcher.find()) {
            String timestamp = singleTimestampMatcher.group();
            timestamps.add(timestamp);
        }
        //System.out.println(timestamps);
        return timestamps;
    }

    public String adjustTime(String originalTimestamp, double adjustBySecs) {
        String newTimestamp = "";
//        System.out.println(originalTimestamp);

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

        /*Replace with proper exception handling*/
        if (timestampSum < 0) {
           return "00:00:00,000";
        } else{
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

//            System.out.println(newTimestamp);
//            System.out.println();

        }
        return newTimestamp;
    }

    public String objectsToString (List<SubEntry> updatedSubentries) {
        String finalStringContent = "";

        for (SubEntry object : updatedSubentries) {
            finalStringContent = finalStringContent.concat(object.index + "\n");
            finalStringContent = finalStringContent.concat(object.timing + "\n");
            finalStringContent = finalStringContent.concat(object.text + "\n");
        }
        System.out.println("cx: " + finalStringContent);
        return finalStringContent;
    }

    public void exportFile (String contentToWrite, String fileNameOriginal) {
        SimpleDateFormat dateFormatForFilename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Timestamp timestampForFilename = new Timestamp(System.currentTimeMillis());

        Path outputPath = Paths.get("C:\\dev\\JavaTraining\\SubEditor\\outputfiles\\"+ fileNameOriginal + "_" + dateFormatForFilename.format(timestampForFilename) + ".srt");

        try {
            writeString(outputPath, contentToWrite, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}