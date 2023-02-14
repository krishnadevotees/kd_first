package com.example.visonofman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visonofman.ModelClass.DisplayVerse;
import com.example.visonofman.ModelClass.VerseList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayFragment extends Fragment {

    int chapter,verse;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<DisplayVerse> data =new ArrayList<>();
    public DisplayFragment(int chapter,int verse) {
        this.chapter=chapter;
        this.verse=verse;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_display, container, false);


        Log.d("devin","chapter no and verse no"+chapter+" "+verse);
        TextView Verse,Translate,Description;
        Verse =view.findViewById(R.id.verse);
        Translate=view.findViewById(R.id.translate);
        Description=view.findViewById(R.id.desc);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("data/languages/0/chapters/"+chapter+"/data/"+verse+"/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                DisplayVerse v =snapshot.getValue(DisplayVerse.class);
//                Log.d("devin","From Firebase data v :::"+v);
//                Verse.setText(v.getVerse());
//                Translate.setText(v.getTranslate());
//                Description.setText(v.getDescription());

                if (snapshot.exists()) {
                        String key1 = snapshot.child("VERSE").getValue(String.class);
                        String key2 = snapshot.child("TRANSLATE").getValue(String.class);
                        String key3 = snapshot.child("DESCRIPTION").getValue(String.class);

                        Verse.setText(key1);
                        Translate.setText(key2);
                        Description.setText(key3);

                        Log.d("Firebase", "Key 1 verse: " + key1);
                        Log.d("Firebase", "Key 2 translate: " + key2);
                        Log.d("Firebase", "Key 3 description: " + key3);

                }

//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    if (dataSnapshot.getKey().equals("VERSE")){
//                        String v =dataSnapshot.getValue();
//                    }
//
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });








        return view;
    }
}