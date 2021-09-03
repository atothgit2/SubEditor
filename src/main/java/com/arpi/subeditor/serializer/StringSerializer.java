package com.arpi.subeditor.serializer;

import com.arpi.subeditor.parser.SubEntry;
import java.util.List;

public class StringSerializer {
    public String convertToString(List<SubEntry> updatedSubentries) {
        String finalStringContent = "";

        for (SubEntry object : updatedSubentries) {
            finalStringContent = finalStringContent.concat(object.index + "\n");
            finalStringContent = finalStringContent.concat(object.timing + "\n");
            finalStringContent = finalStringContent.concat(object.text + "\n");
        }
        System.out.println("cx: " + finalStringContent);
        return finalStringContent;
    }
}