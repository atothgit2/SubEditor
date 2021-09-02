package com.arpi.subeditor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {        // Utility class, nem kötődik példányhoz, inputot csak az argumentumoktól kap
    public static String extractFilanameFromPath(String filePath) {
        Pattern fileNamePattern = Pattern.compile("(?<=/).+(?=\\.srt)");
        Matcher fileNameMatcher = fileNamePattern.matcher((String) filePath);
        fileNameMatcher.find();

        return fileNameMatcher.group();
    }
}
