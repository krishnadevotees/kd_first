package com.example.visonofman.Adepters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visonofman.ModelClass.VerseList;
import com.example.visonofman.R;

import java.util.ArrayList;

public class VerseListAdepter extends RecyclerView.Adapter<VerseListAdepter.ViewHolder>{
    Context context;
    ArrayList<VerseList> verseLists ;
    public VerseListAdepter(Context context, ArrayList<VerseList> verseLists) {
        this.context=context;
        this.verseLists=verseLists;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_verse,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(verseLists.get(position).getVerse());

    }

    @Override
    public int getItemCount() {
        return verseLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_tv);
        }
    }
}
