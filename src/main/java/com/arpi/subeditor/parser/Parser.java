package com.arpi.subeditor.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {                                                   //feladat: result-ot megtölteni SubEntry-kel
    public List<SubEntry> readAndParse (String fileLocation) {         // *************
        String filePath = fileLocation;
        ArrayList originalContentInStrings = new ArrayList();
        List<SubEntry> result = new ArrayList<SubEntry>();          // *************

        originalContentInStrings = readFileAndConvertSRT(filePath);
        result = linesToObjects(originalContentInStrings);

        return result;                                              // *************
    }

    public ArrayList readFileAndConvertSRT(String originalFileLocation) {                 // "subs/mysub.srt"
        Path fileName = Path.of(String.valueOf(originalFileLocation));
        ArrayList linesInStrings = new ArrayList();

        try {
            String readContent = Files.readString(fileName);
            String[] readContents = readContent.split("\\r?\\n", 0);

           for (String str : readContents) {
//                if (!str.isEmpty()) {
                    linesInStrings.add(str);
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(linesInStrings);
        return linesInStrings;
    }

    public List<SubEntry> linesToObjects(ArrayList contentStrings) {
        //Pattern timingPattern = Pattern.compile("^.*?-->.*?$");
        //Pattern timingPattern = Pattern.compile("\\d+.\\d+.\\d+.\\d+.-->.\\d+.\\d+.\\d+.\\d+");
        //System.out.println(contentStrings.get(i));

        List<SubEntry> results = new ArrayList<SubEntry>();
        SubEntry currentSubEntry = new SubEntry();

        Pattern positionPattern = Pattern.compile("^\\b\\d+\\b$");
        // ^\b\d+\b$
        // ^\d+$
        Pattern timingPattern = Pattern.compile("[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9].-->.[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9]");

        for (int i = 0; i < contentStrings.size(); i++) {
            System.out.println("i: " + i + " --- " + contentStrings.get(i));
            Matcher timingMatcher = timingPattern.matcher((String) contentStrings.get(i));
            Matcher positionMatcher = positionPattern.matcher((String) contentStrings.get(i));

            boolean isItPosition = positionMatcher.matches();
            boolean isItStamp = timingMatcher.matches();

            // valamiért az 1. poz else ágba fut
            if (isItPosition) {
                currentSubEntry.setIndex((String) contentStrings.get(i));
//              System.out.println("index: " + currentSubEntry.index);
            }
            else if (isItStamp) {
                currentSubEntry.setTiming((String) contentStrings.get(i));
//              System.out.println("timing: " + currentSubEntry.timing);
            }
            else if (!contentStrings.get(i).toString().isEmpty() && !isItPosition) {
                currentSubEntry.setTextConcat((String) contentStrings.get(i));
//              System.out.println("text: " + currentSubEntry.text);

            } else if (contentStrings.get(i).toString().isEmpty()) {
                results.add(currentSubEntry);//               System.out.println();
                System.out.println("OBJECT #" + results.size() + ": " + "\r\n" + currentSubEntry.index + "\r\n" + currentSubEntry.timing + "\r\n" + currentSubEntry.text);
//              System.out.println();
                currentSubEntry.text = "";
                // utolsó elemet nem olvassa be! --> egy if-et írni ami a max. i-nél még objectbe teszi az elemeket? Hack?
                // end of file felismerni?
                // lekezelni azt az esetet, h a text részben szám van, amit positionnak érzékelhet a program
            }
        }
        return results;
    }
}
