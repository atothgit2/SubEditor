package com.arpi.subeditor.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

// olv.: https://www.baeldung.com/junit-assertions

public class TestParser {
    @Test
    public void shouldParseLines() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("4");
        lines.add("00:00:27,861 --> 00:00:31,657");
        lines.add("- Ahogy nálunk szokás.");
        lines.add("- Orultek vagytok.");

        Parser parser = new Parser();
        List<SubEntry> results = parser.linesToObjects(lines);

        Assertions.assertEquals(1,results.size());

        SubEntry subEntry = results.get(0);

        Assertions.assertEquals("4", subEntry.getIndex());
        Assertions.assertEquals("00:00:27,861 --> 00:00:31,657", subEntry.getTiming());
        Assertions.assertEquals("- Ahogy nálunk szokás." + "\r\n" + "- Orultek vagytok." + "\r\n", subEntry.getText());

    }
}