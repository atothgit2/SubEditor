package com.arpi.subeditor.utils;

import com.arpi.subeditor.model.Pair;
import com.arpi.subeditor.parser.SubEntry;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
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

    public static Pair<String> extractTimestampsPairsFromSubEntries (String originalTimestampLine) {
        Pair<String> extractedTimestamps = new Pair<>();



        return extractedTimestamps;
    }
}