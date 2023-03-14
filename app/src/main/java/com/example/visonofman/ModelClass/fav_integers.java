package com.example.visonofman.ModelClass;

import java.util.Objects;

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
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof fav_integers)) return false;
        fav_integers other = (fav_integers) obj;
        return this.language == other.language && this.chapter == other.chapter && this.verse == other.verse;
    }

    // Override hashCode() to be consistent with equals()
    @Override
    public int hashCode() {
        return Objects.hash(language, chapter, verse);
    }
}
