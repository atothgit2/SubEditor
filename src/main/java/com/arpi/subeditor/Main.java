package com.arpi.subeditor;

import com.arpi.subeditor.fileExporter.FileExporter;
import com.arpi.subeditor.parser.Parser;
import com.arpi.subeditor.parser.TimingModifierService;
import com.arpi.subeditor.parser.SubEntry;
import com.arpi.subeditor.serializer.StringSerializer;
import com.arpi.subeditor.utils.FileUtils;
import java.util.List;

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
        FileExporter exporter = new FileExporter();
        exporter.exportStringToFile(result, originalFileName);
    }
}

// HF: fileExporter class-t csinálni a stringserilalizer mintájára, és a mainben használni, ami kiírja egy fileba. -- OK
// HF: utilba írni egy metódust ami kideríti az eredeti fájlnevet, ez csak a végleges fájl nevet és a tartalamt kérje és írja egy fájlbe be -- ???
