package com.arpi.subeditor.serializer;

import com.arpi.subeditor.parser.SubEntry;
import java.util.List;

public class StringSerializer {
    public String convertToString(List<SubEntry> updatedSubentries) {
        String finalStringContent = "";

        for (SubEntry object : updatedSubentries) {
            finalStringContent = finalStringContent.concat(object.getIndex() + "\n");
            finalStringContent = finalStringContent.concat(object.getTiming() + "\n");
            finalStringContent = finalStringContent.concat(object.getText() + "\n");
        }
        return finalStringContent;
    }
}