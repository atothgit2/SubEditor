package com.arpi.subeditor.fileExporter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static java.nio.file.Files.writeString;

public class FileExporter {
    public void exportStringToFile (String contentToWrite, String originalFilename) {
        SimpleDateFormat dateFormatForFilename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Timestamp timestampForFilename = new Timestamp(System.currentTimeMillis());

        Path outputPath = Paths.get("C:\\dev\\JavaTraining\\SubEditor\\outputfiles\\"+ originalFilename + "_" + dateFormatForFilename.format(timestampForFilename) + ".srt");

        try {
            writeString(outputPath, contentToWrite, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
