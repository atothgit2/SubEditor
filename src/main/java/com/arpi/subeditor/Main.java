package com.arpi.subeditor;

import com.arpi.subeditor.parser.Parser;
import com.arpi.subeditor.parser.SubEntry;
import java.util.List;

public class Main {

    public static void main(String[] arg) {
       Parser parser = new Parser();
       List<SubEntry> subEntries = parser.readAndParse("subs/mysub.srt");

    }
}

