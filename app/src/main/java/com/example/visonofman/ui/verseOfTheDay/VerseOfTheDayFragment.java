package com.example.visonofman.ui.verseOfTheDay;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.visonofman.ModelClass.DisplayVerse;
import com.example.visonofman.ModelClass.fav_integers;
import com.example.visonofman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class VerseOfTheDayFragment extends Fragment {
    int chapter,verse,size,index;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<DisplayVerse> data =new ArrayList<>();
    TextView Verse,Translate,Description,tv1,tv2,tv3;

    FloatingActionButton play,stop;
    String Language;
    String key1,key2,key3;
    TextToSpeech textToSpeech;
    boolean isFavorite = false;
    private boolean isPlaying = false;
    Set<String> myList = new HashSet<>();
    int Vid ;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String lang;
    int l;
    String sloka="", tran,desc;
    Button fav_button;
    FirebaseUser currentUser;
    List<fav_integers> integers=new ArrayList<>();
    List<fav_integers> favorites = new ArrayList<>();
    MenuItem favoriteItem;
    Drawable myDrawable0,myDrawable2;

    public VerseOfTheDayFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_verse_of_the_day, container, false);


        Verse =view.findViewById(R.id.verse);
        Translate=view.findViewById(R.id.translate);
        Description=view.findViewById(R.id.desc);
        play=view.findViewById(R.id.playbtn);
        tv1=view.findViewById(R.id.textView);
        tv2=view.findViewById(R.id.textView2);
        tv3=view.findViewById(R.id.textView3);

        firebaseDatabase= FirebaseDatabase.getInstance();
        firestore= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SharedPreferences sharedPreferences= getContext().getSharedPreferences("language",0);
        Language = sharedPreferences.getString("lan","0");
        Log.d("dev","selectedLanguage :::  "+Language);

        firestore.collection("vod").document("vod").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Log.d("devin", "onSuccess: chapter verse get  " );

                    chapter = Objects.requireNonNull(document.getLong("chapter")).intValue();
                    verse = Objects.requireNonNull(document.getLong("verse")).intValue();
                    Log.d("devin", "Chapter :::==>   " + chapter);
                    Log.d("devin", "Verse :::==>   " + verse);










                    databaseReference = firebaseDatabase.getReference("data/languages/"+Language+"/chapters/"+chapter+"/data/");
                    firestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            lang = documentSnapshot.getString("selectedLanguage");
                            Log.d("devin", "onSuccess: selectedLanguage in display fragment   " + lang);

                            firestore.collection("display").document(documentSnapshot.getString("selectedLanguage")+"").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    sloka = documentSnapshot.getString("verse");
                                    tran = documentSnapshot.getString("translate");
                                    desc = documentSnapshot.getString("discription");

                                    tv1.setText(sloka);
                                    tv2.setText(tran);
                                    if (key3 == ""){
                                        tv3.setText("");
                                    }else {
                                        tv3.setText(desc);
                                    }


                                }
                            });
                        }
                    });

                    databaseReference.child(String.valueOf(verse)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {

                                AppCompatActivity activity = (AppCompatActivity) getActivity();

                                // Set the title of the ActionBar
                                if (activity != null) {
                                    SpannableString s = new SpannableString("Adhyay "+(chapter+1)+"    "+tv1.getText().toString() + " " + (verse + 1));
                                    s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.textColor)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    Objects.requireNonNull(activity.getSupportActionBar()).setTitle(s);

                                }


                                Verse.setText("");
                                Translate.setText("");
                                Description.setText("");

                                key1 = snapshot.child("VERSE").getValue(String.class);
                                key2 = snapshot.child("TRANSLATE").getValue(String.class);
                                key3 = snapshot.child("DESCRIPTION").getValue(String.class);
                                Vid= snapshot.child("ID").getValue(Integer.class);

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
                    progressDialog.dismiss();






                }
            }
        });



        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(new Locale("sa_IN"));

                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    textToSpeech.stop();
                    Drawable myDrawable3 =  ResourcesCompat.getDrawable(getResources(), R.drawable.bx_play, null);
                    play.setImageDrawable(myDrawable3);
                    isPlaying = false;
                } else {

                    textToSpeech.setSpeechRate(0.7f);
                    textToSpeech.setLanguage(new Locale("hi"));

                    HashMap<String, String> params = new HashMap<>();
                    params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SPEECH_ID");

                    textToSpeech.speak(key1,TextToSpeech.QUEUE_FLUSH,params);

                    Drawable myDrawable =  ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_stop_24, null);
                    play.setImageDrawable(myDrawable);
                    isPlaying = true;
                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {

                        }

                        @Override
                        public void onDone(String s) {

                            Drawable myDrawable =  ResourcesCompat.getDrawable(getResources(), R.drawable.bx_play, null);
                            play.setImageDrawable(myDrawable);
                            isPlaying = false;
                        }

                        @Override
                        public void onError(String s) {

                        }
                    });
                }


            }
        });






        return view;
    }
    public void showData(int index){
//        checkFav();


    }

    private void rendom(){

        int[][] verses = {
                {0, 45},    // Chapter 0
                {0, 71},    // Chapter 1
                {0, 42},    // Chapter 2
                {0, 41},    // Chapter 3
                {0, 28},    // Chapter 4
                {0, 46},    // Chapter 5
                {0, 29},    // Chapter 6
                {0, 27},    // Chapter 7
                {0, 33},    // Chapter 8
                {0, 41},    // Chapter 9
                {0, 54},    // Chapter 10
                {0, 18},    // Chapter 11
                {0, 33},    // Chapter 12
                {0, 26},    // Chapter 13
                {0, 19},    // Chapter 14
                {0, 23},    // Chapter 15
                {0, 27},    // Chapter 16
                {0, 77}     // Chapter 17
        };


        Random random = new Random();

        int chapter1 = 0;
        int verse1 = 0;



            chapter = random.nextInt(verses.length);
            verse = random.nextInt(verses[chapter1][1] - verses[chapter1][0] + 1) + verses[chapter1][0];
            Map<String, Object> data = new HashMap<>();
            data.put("chapter",chapter);
            data.put("verse",verse);
            firestore.collection("vod").document("vod").update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("devin","chapter and verse updated");
                }
            });




        int[][] array = new int[verses.length][];
        for (int i = 0; i < verses.length; i++) {
            int chapterLength = verses[i][1] - verses[i][0] + 1;
            array[i] = new int[chapterLength];
            for (int j = 0; j < chapterLength; j++) {
                array[i][j] = verses[i][0] + j;
            }
        }
        // Print the randomly selected chapter and verse
        System.out.println("Chapter: " + chapter1);
        System.out.println("Verse: " + verse1);
        Log.d("devin", "Chapter :::==>   " + chapter);
        Log.d("devin", "Verse :::==>   " + verse);

    }

}