package com.example.visonofman.Adepters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VerseListAdepter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private List<String> Verse;


    public VerseListAdepter(List<String> verse) {
        Verse = verse;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String data= Verse.get(position);

    }

    @Override
    public int getItemCount() {
        return Verse.size();
    }
}
