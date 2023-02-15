package com.example.visonofman.Adepters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visonofman.DisplayFragment;
import com.example.visonofman.ModelClass.VerseList;
import com.example.visonofman.R;

import java.util.ArrayList;

public class VerseListAdepter extends RecyclerView.Adapter<VerseListAdepter.ViewHolder>{
    Context context;
    ArrayList<VerseList> verseLists ;
    String id;
    int chapter;
    FragmentManager mfragmentManager;

    public VerseListAdepter(Context context, ArrayList<VerseList> verseLists, FragmentManager fragmentManager, int flag) {
        this.context=context;
        this.verseLists=verseLists;
        this.mfragmentManager=fragmentManager;
        this.chapter=flag;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_verse,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(verseLists.get(position).getVerse());
        holder.view.startAnimation(AnimationUtils.loadAnimation(holder.textView.getContext(),R.anim.anim_one));

         id =String.valueOf(position);
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("devin"," verse clicked ::"+position);
//
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return verseLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.list_tv);
            view = itemView.findViewById(R.id.card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        FragmentTransaction transaction = mfragmentManager.beginTransaction();
                        transaction.replace(R.id.container, new DisplayFragment(chapter,position,getItemCount()));
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }
    }

}
