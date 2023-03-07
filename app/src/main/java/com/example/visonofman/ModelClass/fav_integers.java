package com.example.visonofman.ModelClass;

public class fav_integers {
    int language,chapter,verse;

    public fav_integers() {
    }

    public fav_integers(int language, int chapter, int verse) {
        this.language = language;
        this.chapter = chapter;
        this.verse = verse;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getVerse() {
        return verse;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }
}
