package com.arpi.subeditor;

import com.arpi.subeditor.parser.Parser;
import com.arpi.subeditor.parser.Setter;
import com.arpi.subeditor.parser.SubEntry;
import java.util.List;

public class Main {

    public static void main(String[] arg) {
       Parser parser = new Parser();
       String fileLocation = "subs/mysub1.srt";
       List<SubEntry> subEntries = parser.readAndParse(fileLocation);
       String originalFileName = parser.storeOriginalFileName(fileLocation);

       Setter setter = new Setter();
       setter.modifyTimestampsAndExport(subEntries, -3600, originalFileName);
    }
}