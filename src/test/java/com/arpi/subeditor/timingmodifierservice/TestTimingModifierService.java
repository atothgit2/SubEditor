package com.arpi.subeditor.timingmodifierservice;

import com.arpi.subeditor.parser.SubEntry;
import com.arpi.subeditor.parser.TimingModifierService;
import org.junit.Assert;
import java.util.List;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestTimingModifierService {
    @Test
    public void shouldModifyTiming() {
        SubEntry subEntry = new SubEntry();
        subEntry.setIndex("4");
        subEntry.setTiming("00:00:27,861 --> 00:00:31,657");
        subEntry.setTextConcat("- Ahogy nálunk szokás." + "\r\n" + "- Őrültek vagytok.");

        List<SubEntry> subEntries = new ArrayList<SubEntry>();
        subEntries.add(subEntry);

        double addTimeInSecs = 3661;

        TimingModifierService timingModifierService = new TimingModifierService();
        List<SubEntry> results = timingModifierService.modifySubentriesTiming(subEntries, addTimeInSecs);

        Assert.assertEquals(1, subEntries.size());
        Assert.assertEquals("4", results.get(0).index);
        Assert.assertEquals("01:01:28,860 --> 01:01:32,657", results.get(0).timing);
        Assert.assertEquals("- Ahogy nálunk szokás." + "\r\n" + "- Őrültek vagytok." + "\r\n", results.get(0).text);
        }
    }