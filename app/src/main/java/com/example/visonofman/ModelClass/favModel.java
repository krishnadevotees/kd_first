package com.example.visonofman.ModelClass;

public class favModel {
    String verse,translate,description;
    int id;

    public favModel() {
    }

    public favModel(int id,String verse, String translate, String description) {
        this.verse = verse;
        this.translate = translate;
        this.description = description;
        this.id = id;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
