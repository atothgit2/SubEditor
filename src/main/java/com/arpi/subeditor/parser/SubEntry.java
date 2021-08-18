package com.arpi.subeditor.parser;

public class SubEntry {
    public String index;
    public String timing;
    public String text = "";

    public SubEntry() {
    }

    public void setIndex(String position) {
            this.index = position;
    }

    public void setTiming (String timestamp) {
            this.timing = timestamp;
    }

    public void setTextConcat (String textLine) {

        this.text = text.concat(textLine + "\r\n");

    }
}
