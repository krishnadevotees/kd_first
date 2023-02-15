package com.example.visonofman.CustomClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedprefrence_Language {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Sharedprefrence_Language(Context context) {
        sharedPreferences = context.getSharedPreferences("Languages", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
    public void clear(){
        editor.clear();
        editor.apply();
    }


}
