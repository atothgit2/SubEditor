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
        ArrayList<String> originalContentInStrings = new ArrayList();
        List<SubEntry> result = new ArrayList<SubEntry>();
        originalContentInStrings = readFileAndConvertSRT(fileLocation);
        result = linesToObjects(originalContentInStrings);

        return result;
    }

    private ArrayList<String> readFileAndConvertSRT(String originalFileLocation) {                 // "subs/mysub.srt"
        Path fileName = Path.of(String.valueOf(originalFileLocation));
        ArrayList<String> linesInStrings = new ArrayList<String>();

        try {
            String readContent = Files.readString(fileName);
            String[] readContents = readContent.split("\\r?\\n", 0);

            // Remove \ufeff tag from beginning of first string
            // https://stackoverflow.com/questions/3255993/how-do-i-remove-%C3%AF-from-the-beginning-of-a-file
            Pattern ufeffPattern = Pattern.compile("\\ufeff[0-9]+");

            // elég a legelső sort megvizsgálni a readContentsben, nem kell minden soron végig menni
            // a linesInStrings-be ne rakjuk be az első sort, ha BOM-es element van benne ('for' nem kell)

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
        } catch (IOException e) { // file írás vagy olvasás az kötelezően kezelendő, később fixáljuk
            e.printStackTrace();
        }
        return linesInStrings;
    }

    // erre unit teszteket csinálni később
    public List<SubEntry> linesToObjects(ArrayList<String> contentStrings) {
        SubEntry currentSubEntry = new SubEntry();
        List<SubEntry> results = new ArrayList<SubEntry>();

        Pattern positionPattern = Pattern.compile("\\A\\d+$");
        Pattern timingPattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2},\\d{3}.-->.\\d{2}:\\d{2}:\\d{2},\\d{3}");

        for (int i = 0; i < contentStrings.size(); i++) {
            Matcher timingMatcher = timingPattern.matcher((String) contentStrings.get(i));
            Matcher positionMatcher = positionPattern.matcher((String) contentStrings.get(i));

            boolean isItPosition = positionMatcher.matches();
            boolean isItStamp = timingMatcher.matches();

            if (currentSubEntry.getIndex() == null || currentSubEntry.getTiming() == null || currentSubEntry.getText() == "") {
                if (isItPosition) {
                    currentSubEntry.setIndex((String) contentStrings.get(i));
                } else if (isItStamp) {
                    currentSubEntry.setTiming((String) contentStrings.get(i));
                } else if (!isItPosition && !isItStamp && !contentStrings.get(i).toString().isEmpty()) {
                    currentSubEntry.setTextConcat((String) contentStrings.get(i));
                }
            }
            else if (currentSubEntry.getIndex() != null && currentSubEntry.getTiming() != null && !isItPosition && !isItStamp && !contentStrings.get(i).toString().isEmpty()) {
                currentSubEntry.setTextConcat((String) contentStrings.get(i));
            }
            else if (currentSubEntry.getIndex() != null && currentSubEntry.getTiming() != null && currentSubEntry.getText() != "") {
                results.add(currentSubEntry);
                currentSubEntry = new SubEntry();
            }
        }
        results.add(currentSubEntry);
        return results;
    }
}