package com.arpi.subeditor;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deconstructor {

    public Deconstructor(String pos, String s1, String s2, String txt) {
        String position = pos;
        String firstTimeStamp = s1;
        String secondTimeStamp = s2;
        String text = txt;
    }

    public ArrayList Deconstruct(String s) {                        // "subs/mysub.srt"
        Path fileName = Path.of(String.valueOf(s));
        ArrayList<String> tempContainer = new ArrayList<String>();

        try {
            String rawContent = Files.readString(fileName);
            String[] outputContent;

            // Break down content to strings at line breaks
            outputContent = rawContent.split("\\r?\\n", 0);

            for (String str : outputContent) {
                if (!str.isEmpty()) {
                    tempContainer.add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempContainer;
    }

    public void FindTimestamps(ArrayList list) {
        Pattern linePattern = Pattern.compile("^.*?-->.*?$");
        String stampRegex = new String("[0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9]");
        Pattern stampPattern = Pattern.compile(stampRegex);

        //az is jo amit irtal, hogy elotte egy metodussal szetszeded
        //erre erdemes a String object metodusait megnezegetned, pl: split, indexOf, substring
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ". elem: " + list.get(i));

            Matcher lineMatcher = linePattern.matcher((String) list.get(i));
            boolean doesItMatch = lineMatcher.matches();

            if (doesItMatch) {
                Matcher stampMatcher = stampPattern.matcher((String) list.get(i)); // (String) miért kell? (cast...)

                while(stampMatcher.find()) {
                    //System.out.println("found: " + count + " : " + stampMatcher.start() + " - " + stampMatcher.end());
                    //String holder  = ((String) list.get(i)).subSequence(stampMatcher.start(), stampMatcher.end()). + " -1";
                    //System.out.println(((String) list.get(i)).subSequence(stampMatcher.start(), stampMatcher.end()));

                    String s1 = (String) ((String) list.get(i)).subSequence(stampMatcher.start(), stampMatcher.end());
                    System.out.println("s1 is: " + s1);

                    //System.out.println(s1.replaceAll(stampRegex,"xxx" ));
                    // System.out.println(list.get(i));
                }
            }
        }
    }
}