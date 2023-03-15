package com.example.visonofman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
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

import com.example.visonofman.ModelClass.DisplayVerse;
import com.example.visonofman.ModelClass.fav_integers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;


public class Display2Fragment extends Fragment {
    int position ;
    int chapter,verse,language,index;
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
    String sloka,tran,desc;
    FirebaseUser currentUser;
    List<fav_integers> integers=new ArrayList<>();
    List<fav_integers> favorites = new ArrayList<>();
    MenuItem favoriteItem;
    Drawable myDrawable0,myDrawable2;
    int currentIndex;
    public Display2Fragment(int chapter,int verse,int language) {
        this.chapter=chapter;
        this.verse=verse;
        this.language=language;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_display2, container, false);
        setHasOptionsMenu(true);

        myDrawable0 =  ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_favorite_24, null);
        myDrawable2 =  ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_favorite__fill_24, null);

        SharedPreferences sharedPreferences= getContext().getSharedPreferences("language",0);
        Language = sharedPreferences.getString("lan","0");
        Log.d("dev","selectedLanguage :::  "+Language);
        sloka="श्लोक ";
        l= Integer.parseInt(Language);

        Log.d("devin","chapter no and verse no"+chapter+" "+verse);

        Verse =view.findViewById(R.id.verse);
        Translate=view.findViewById(R.id.translate);
        Description=view.findViewById(R.id.desc);
        play=view.findViewById(R.id.playbtn);
        tv1=view.findViewById(R.id.textView);
        tv2=view.findViewById(R.id.textView2);
        tv3=view.findViewById(R.id.textView3);

        FloatingActionButton nextfab= view.findViewById(R.id.right_fab);
        FloatingActionButton prevfab = view.findViewById(R.id.left_fab);


        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data/languages/"+Language+"/chapters/"+chapter+"/data/");

        firestore= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        firestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists() && documentSnapshot.get("favorite") != null) {

                    int[] myArray = new int[0];

                    // retrieve the favorite list from the document
                    if(documentSnapshot.get("favorite").equals(myArray)){
                        // no fav
                        Log.d("devin", "documentSnapshot.get(\"favorite\").equals(myArray) ==>> True");
                    }else {

                        List<HashMap<String, Object>> favoritesMap = (List<HashMap<String, Object>>) documentSnapshot.get("favorite");

                        for (HashMap<String, Object> hashMap : favoritesMap) {
                            Long l = (Long) hashMap.get("language");
                            Long chapter = (Long) hashMap.get("chapter");
                            Long verse = (Long) hashMap.get("verse");

                            fav_integers favorite = new fav_integers(Math.toIntExact(l), Math.toIntExact(chapter), Math.toIntExact(verse));
                            favorites.add(favorite);
                        }
//                        integers.clear();
                        integers = favorites;
                        currentIndex = integers.indexOf(new fav_integers(language,chapter,verse));
                        if (currentIndex == integers.size() - 1){
                            nextfab.setVisibility(View.GONE);
                        }else if (currentIndex ==0 &&  integers.size()-1 == 0){
                            nextfab.setVisibility(View.GONE);
                            prevfab.setVisibility(View.GONE);
                        }
                        Log.d("devin", "Current Index is ==> "+ currentIndex);
                        Log.d("devin", "size of integers ==> "+ integers.size());

//                        for (fav_integers fav : favorites) {
//                            Log.d("devin", "data from map in display frag  favoritesfavorites favo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   " + fav.getLanguage() + " " + fav.getChapter() + " " + fav.getVerse());
//                        }
                        for (fav_integers fav : integers) {
                            Log.d("devin", "data from map in display frag integersintegers  inte!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   " + fav.getLanguage() + " " + fav.getChapter() + " " + fav.getVerse());
                        }
                    }
                } else {
                    Log.d("devin", "No such document");
                }
            }
        });







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


        showData(verse);
        progressDialog.dismiss();


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



        nextfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentIndex < integers.size() - 1){
                    fav_integers fav_integers1= integers.get(currentIndex+1);
                    language= fav_integers1.getLanguage();
                    chapter=fav_integers1.getChapter();
                    verse= fav_integers1.getVerse();
                    currentIndex++;
                    showData(verse);
                    prevfab.setVisibility(View.VISIBLE);

                }else {
                    nextfab.setVisibility(View.GONE);
                }


            }
        });

        prevfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex > 0){
                    fav_integers fav_integers1= integers.get(currentIndex-1);
                    language= fav_integers1.getLanguage();
                    chapter=fav_integers1.getChapter();
                    verse= fav_integers1.getVerse();
                    currentIndex--;
                    showData(verse);
                    nextfab.setVisibility(View.VISIBLE);
                }else {
                    prevfab.setVisibility(View.GONE);
                }

            }
        });



















        return view;
    }

    public void showData(int index){
//        checkFav();



        databaseReference.child(String.valueOf(index)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    AppCompatActivity activity = (AppCompatActivity) getActivity();

                    // Set the title of the ActionBar
                    if (activity != null) {
                        Objects.requireNonNull(activity.getSupportActionBar()).setTitle(sloka+" "+(index+1));
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
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.custom_menu, menu);
        MenuItem favoriteMenuItem = menu.findItem(R.id.action_share);
        favoriteMenuItem.setVisible(true);
        favoriteItem = menu.findItem(R.id.action_favorite);
        favoriteItem.setIcon(R.drawable.baseline_favorite__fill_24);
        favoriteMenuItem.setVisible(true);
        favoriteItem.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                if (integers.size() > 0) {
                    remove();
                    if (currentIndex > 0) {
                        // show previous item
                        currentIndex--;
                        fav_integers integers1 = integers.get(currentIndex);
                        language=integers1.getLanguage();
                        chapter=integers1.getChapter();
                        verse=integers1.getVerse();
                        showData(verse);
                        getActivity().invalidateOptionsMenu();
                    } else if (currentIndex < integers.size()) {
                        // show next item
                        fav_integers integers2 = integers.get(currentIndex);
                        language=integers2.getLanguage();
                        chapter=integers2.getChapter();
                        verse=integers2.getVerse();
                        showData(verse);
                        getActivity().invalidateOptionsMenu();
                    } else {
                        getActivity().finish();
                    }
                } else {
                    getActivity().finish();
                }


                return true;
            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, ""+sloka+" "+(index+1));
                shareIntent.putExtra(Intent.EXTRA_TEXT, "\n"+""+sloka+" : "+"\n\n\b"+key1+"\n\n\n"+""+tran+" : "+"\n\n"+key2+"\n\n\n"+""+desc+" : "+"\n\n"+key3+"\n\n");
                startActivity(Intent.createChooser(shareIntent, "Share Via"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void remove() {
        if (integers.contains(new fav_integers(Integer.parseInt(Language),chapter,verse))){
            integers.remove(new fav_integers(l,chapter,verse));

            firestore.collection("users").document(auth.getCurrentUser().getUid()).update("favorite", integers)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("devin", "Favorite map updated for current user! Remooved");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("devin", "Error updating favorite map for current user remove ", e);
                        }
                    });

            Toast.makeText(getContext(), "Removed from favorite", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), R.string.language_change_warning, Toast.LENGTH_SHORT).show();
            Log.d("devin", "Remove else error");
        }
    }








    @Override
    public void onDestroyView() {
//        getActivity().recreate();
        super.onDestroyView();
    }
}