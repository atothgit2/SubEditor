package com.arpi.subeditor;

import com.arpi.subeditor.parser.Parser;
import com.arpi.subeditor.parser.TimingModifierService;
import com.arpi.subeditor.parser.SubEntry;
import com.arpi.subeditor.serializer.StringSerializer;
import com.arpi.subeditor.utils.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import static java.nio.file.Files.writeString;

public class Main {

    public static void main(String[] arg) {
       Parser parser = new Parser();
       String fileLocation = "subs/mysub1.srt";
       List<SubEntry> subEntries = parser.readAndParse(fileLocation);
       String originalFileName = FileUtils.extractFilanameFromPath(fileLocation); // utility-be kiszervezni, nem a parser része

       TimingModifierService timingModifierService = new TimingModifierService();
        List<SubEntry> modifiedSubentries = timingModifierService.modifySubentriesTiming(subEntries, 3600, originalFileName);
        StringSerializer stringSerializer = new StringSerializer();
        String result = stringSerializer.convertToString(modifiedSubentries);
    }

    private void exportFile (String contentToWrite, String fileNameOriginal) {
        SimpleDateFormat dateFormatForFilename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Timestamp timestampForFilename = new Timestamp(System.currentTimeMillis());

        Path outputPath = Paths.get("C:\\dev\\JavaTraining\\SubEditor\\outputfiles\\"+ fileNameOriginal + "_" + dateFormatForFilename.format(timestampForFilename) + ".srt");

        try {
            writeString(outputPath, contentToWrite, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

// HF: fileExporter class-t csinálni a stringserilalizer mintájára, és a mainben használni, ami kiírja egy filebe.
// HF: utilba írni egy metódust ami kideríti az eredeti fájlnevet, ez csak a végleges fájl nevet és a tartalamt kérje és írja egy fájlbe be
