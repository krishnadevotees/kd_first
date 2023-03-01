package com.example.visonofman;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visonofman.CustomClasses.Sharedprefrence_Language;
import com.example.visonofman.ModelClass.DisplayVerse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class DisplayFragment extends Fragment {

    int chapter,verse,size,index;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<DisplayVerse> data =new ArrayList<>();
    TextView Verse,Translate,Description;
    ArrayList<String> list = new ArrayList<>();
    FloatingActionButton play,stop;
    String Language;
    String key1,key2,key3;
    TextToSpeech textToSpeech;

    public DisplayFragment(int chapter,int verse,int size) {
        this.chapter=chapter;
        this.verse=verse;
        this.size=size;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_display, container, false);
        setHasOptionsMenu(true);


        SharedPreferences sharedPreferences= getContext().getSharedPreferences("language",0);
        Language = sharedPreferences.getString("lan","0");
        Log.d("dev","selectedLanguage :::  "+Language);


        Log.d("devin","chapter no and verse no"+chapter+" "+verse);

        Verse =view.findViewById(R.id.verse);
        Translate=view.findViewById(R.id.translate);
        Description=view.findViewById(R.id.desc);
        play=view.findViewById(R.id.playbtn);
        stop=view.findViewById(R.id.stopbtn);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("data/languages/"+Language+"/chapters/"+chapter+"/data/");

        showData(verse);


        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(new Locale("sa_IN"));

                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {

                        }

                        @Override
                        public void onDone(String s) {

                        }

                        @Override
                        public void onError(String s) {

                        }
                    });
                }

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    textToSpeech.setSpeechRate(0.7f);
                    textToSpeech.setLanguage(new Locale("hi"));

                HashMap<String, String> params = new HashMap<String, String>();
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SPEECH_ID");

                    textToSpeech.speak(key1,TextToSpeech.QUEUE_FLUSH,params);

                    play.setVisibility(View.GONE);
                    stop.setVisibility(View.VISIBLE);


            }
        });



        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.stop();
                stop.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
            }
        });

        FloatingActionButton nextfab= view.findViewById(R.id.right_fab);

        FloatingActionButton prevfab = view.findViewById(R.id.left_fab);
        nextfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int next = verse + 1;
                prevfab.setVisibility(View.VISIBLE);
                if (verse < size -1 ){


                    verse = next;
                    showData(next);

                    Log.d("devin","size from adepter"+size);
                    Log.d("devin","verse index from adepter"+verse);

                }
                else {
                    nextfab.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Go to next chapter ", Toast.LENGTH_SHORT).show();
                }

                Log.d("devin","size from adepter"+size);
                Log.d("devin","verse index from adepter"+verse);

            }
        });



        prevfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextfab.setVisibility(View.VISIBLE);
                int next = verse - 1;

                if (verse < 1){

                    prevfab.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "This is the first verse", Toast.LENGTH_SHORT).show();
                }else {
                    verse = next;
                    showData(next);
                }

            }
        });






        return view;
    }
    public void showData(int index){

        databaseReference.child(String.valueOf(index)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    AppCompatActivity activity = (AppCompatActivity) getActivity();

                    // Set the title of the ActionBar
                    if (activity != null) {
                        activity.getSupportActionBar().setTitle("श्लोक  "+(index+1));
                    }

                    Verse.setText("");
                    Translate.setText("");
                    Description.setText("");

                    key1 = snapshot.child("VERSE").getValue(String.class);
                    key2 = snapshot.child("TRANSLATE").getValue(String.class);
                    key3 = snapshot.child("DESCRIPTION").getValue(String.class);

                    Verse.setText(key1);
                    Translate.setText(key2);
                    Description.setText(key3);
                    Description.setPadding(0,0,0,50);

                    Log.d("Firebase", "Key 1 verse: " + key1);
                    Log.d("Firebase", "Key 2 translate: " + key2);
                    Log.d("Firebase", "Key 3 description: " + key3);
                }



                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Failed to read Next value.", error.toException());
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.custom_menu, menu);
        MenuItem favoriteMenuItem = menu.findItem(R.id.action_favorite);
        favoriteMenuItem.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        key1=key2=key3 =null;
        super.onDestroyView();
    }
}