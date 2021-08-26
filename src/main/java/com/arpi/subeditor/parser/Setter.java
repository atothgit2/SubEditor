package com.arpi.subeditor.parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.Files.*;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.List;

//Egy olyan metódust akarunk, aminek 2 inputja van (List<SubEntry> subEntries, módosító)
//és a visszatérési értéke pedig 1 srt file (vagy csak egy string?) a módosított időzítésekkel
//
//public List<SubEntry> readAndParse (String fileName)

public class Setter {
    public void modifyTimestampsAndExport(List<SubEntry> rawSubEntriesFromParser, double modifierInSecs) {
        List<SubEntry> rawEntires = rawSubEntriesFromParser;
        String finalSubEntriesAsString = resetTimestamps(rawEntires, modifierInSecs);
        exportFile(finalSubEntriesAsString);
    }

    public String resetTimestamps(List<SubEntry> finals, double timestampModifierInSecs) {
        String finalString = new String();

        return finalString;
    }

    public void exportFile (String contentToWrite) {
        String content = contentToWrite;
        SimpleDateFormat dateFormatForFilename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Timestamp timestampForFilename = new Timestamp(System.currentTimeMillis());

        Path outputPath = Paths.get("C:\\dev\\JavaTraining\\SubEditor\\outputfiles\\output_" + dateFormatForFilename.format(timestampForFilename) + ".srt");

        try {
            writeString(outputPath, content, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}