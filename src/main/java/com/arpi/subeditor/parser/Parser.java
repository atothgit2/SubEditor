package com.arpi.subeditor.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public List<SubEntry> readAndParse (String fileLocation) {
        String filePath = fileLocation;
        ArrayList originalContentInStrings = new ArrayList();
        List<SubEntry> result = new ArrayList<SubEntry>();

        originalContentInStrings = readFileAndConvertSRT(filePath);
        result = linesToObjects(originalContentInStrings);

        return result;
    }

    public ArrayList readFileAndConvertSRT(String originalFileLocation) {                 // "subs/mysub.srt"
        Path fileName = Path.of(String.valueOf(originalFileLocation));
        ArrayList linesInStrings = new ArrayList();

        try {
            String readContent = Files.readString(fileName);
            String[] readContents = readContent.split("\\r?\\n", 0);

            // Remove \ufeff tag from beginning of first string
            // https://stackoverflow.com/questions/3255993/how-do-i-remove-%C3%AF-from-the-beginning-of-a-file
            Pattern ufeffPattern = Pattern.compile("\\ufeff[0-9]+");

            for (String str : readContents) {
                Matcher ufeffMatcher = ufeffPattern.matcher((String) str);
                boolean isItUfeff = ufeffMatcher.matches();

               if (isItUfeff) {
                   linesInStrings.add(str.replaceAll("\\ufeff",""));
                   continue;
               } else {
                   linesInStrings.add(str);
               }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesInStrings;
    }
    public List<SubEntry> linesToObjects(ArrayList contentStrings) {
        SubEntry currentSubEntry = new SubEntry();
        List<SubEntry> results = new ArrayList<SubEntry>();

        Pattern positionPattern = Pattern.compile("\\A\\d+$");
        // ^\b\d+\b$
        // ^\d+$
        Pattern timingPattern = Pattern.compile("[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9].-->.[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9]");

        for (int i = 0; i < contentStrings.size(); i++) {
            Matcher timingMatcher = timingPattern.matcher((String) contentStrings.get(i));
            Matcher positionMatcher = positionPattern.matcher((String) contentStrings.get(i));

            boolean isItPosition = positionMatcher.matches();
            boolean isItStamp = timingMatcher.matches();

            if (currentSubEntry.index == null || currentSubEntry.timing == null || currentSubEntry.text == "") {
                if (isItPosition) {
                    currentSubEntry.setIndex((String) contentStrings.get(i));
                } else if (isItStamp) {
                    currentSubEntry.setTiming((String) contentStrings.get(i));
                } else if (!isItPosition && !isItStamp && !contentStrings.get(i).toString().isEmpty()) {
                    currentSubEntry.setTextConcat((String) contentStrings.get(i));
                }
            }
            else if (currentSubEntry.index != null && currentSubEntry.timing != null && !isItPosition && !isItStamp && !contentStrings.get(i).toString().isEmpty()) {
                currentSubEntry.setTextConcat((String) contentStrings.get(i));
            }
            else if (currentSubEntry.index != null && currentSubEntry.timing != null && currentSubEntry.text != "") {
                results.add(currentSubEntry);
                currentSubEntry = new SubEntry();
            }
        }
        results.add(currentSubEntry);
        return results;
    }
}