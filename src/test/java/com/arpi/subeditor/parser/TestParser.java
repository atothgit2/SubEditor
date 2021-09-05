package com.arpi.subeditor.parser;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

// olv.: https://www.baeldung.com/junit-assertions

public class TestParser {
    @Test
    public void shouldParseLines() {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("4");
        lines.add("00:00:27,861 --> 00:00:31,657");
        lines.add("- Ahogy nálunk szokás.");
        lines.add("- Őrültek vagytok.");

        Parser parser = new Parser();
        List<SubEntry> results = parser.linesToObjects(lines);

        Assert.assertEquals(1,results.size());

        SubEntry subEntry = results.get(0);

        Assert.assertEquals("4", subEntry.index);
        Assert.assertEquals("00:00:27,861 --> 00:00:31,657", subEntry.timing);
        Assert.assertEquals("- Ahogy nálunk szokás." + "\r\n" + "- Őrültek vagytok." + "\r\n", subEntry.text);
    }

}
    /// HF: új fájlba a timing modifier service-hez tesztet írni