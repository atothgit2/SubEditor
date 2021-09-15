package com.arpi.subeditor.parser;

public class SubEntry {
    private String index;
    private String timing;
    private String text = "";

//    refaktorálni, hogy a példány változókat ne lehessen közvetlenül módosítani (private), hanem csak a setter metódusokkal

    public SubEntry() {
    }

    // Setters
    public void setIndex(String position) {
        this.index = position;
    }

    public void setTiming (String timestamp) {
        this.timing = timestamp;
    }

    public void setTextConcat (String textLine) {
        this.text = text.concat(textLine + "\r\n");
    }

    // Getters
    public String getIndex() {
        return index;
    }

    public String getTiming() {
        return timing;
    }

    public String getText() {
        return text;
    }
}
