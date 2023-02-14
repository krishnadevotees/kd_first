package com.example.visonofman.ModelClass;

public class DisplayVerse {
    String Verse, Translate,Description;

    public DisplayVerse() {
    }

    public DisplayVerse(String verse, String translate, String description) {
        Verse = verse;
        Translate = translate;
        Description = description;
    }

    public String getVerse() {
        return Verse;
    }

    public void setVerse(String verse) {
        Verse = verse;
    }

    public String getTranslate() {
        return Translate;
    }

    public void setTranslate(String translate) {
        Translate = translate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
