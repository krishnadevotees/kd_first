package com.example.visonofman.ui.favrate;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visonofman.Activity.FavoriteActivity;
import com.example.visonofman.ui.chapters.ChaptersFragment;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;


import com.example.visonofman.Adepters.favAdepter;

import com.example.visonofman.ModelClass.fav_integers;
import com.example.visonofman.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Set;



public class fav_Fragment extends Fragment {

TextView textView;
RecyclerView recyclerView;
FirebaseFirestore firestore;
FirebaseAuth auth;
    List<fav_integers> favorites = new ArrayList<>();
    Dialog dialog;
    TextView textView1;
    Boolean isGetdata =false;
    favAdepter favAdepter1;


    public fav_Fragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Indicate that the fragment has its own options menu
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_, container, false);

//        DisplayFragment fragment = (DisplayFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.displayFragment);
//        List<favModel> list= fragment.getData();
//        for (int i =0; i<list.size(); i++){
//            Log.d("devin",""+list.get(i)+"\n");
//        }

//        textView=view.findViewById(R.id.tv_fa);
        recyclerView=view.findViewById(R.id.fav_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
         textView1=view.findViewById(R.id.textv);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("favorite", MODE_PRIVATE);
        Set<String> myList = sharedPreferences.getStringSet("favorites", null);
        Log.d("devin","favoriteList  "+myList+"\n");

        getData();






        return view;
    }

    private void getData(){
        firestore.collection("users").document(auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()  && documentSnapshot.get("favorite") != null) {
                            // retrieve the favorite list from the document

                            favorites.clear();

//                                textView1.setVisibility(View.GONE);
                            List<HashMap<String, Object>> favoritesMap = (List<HashMap<String, Object>>) documentSnapshot.get("favorite");

                            if (favoritesMap.isEmpty()){
//                                Toast.makeText(getContext(), "no favorites yet!", Toast.LENGTH_SHORT).show();
                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                for (HashMap<String, Object> hashMap : favoritesMap) {
                                    Long l = (Long) hashMap.get("language");
                                    Long chapter = (Long) hashMap.get("chapter");
                                    Long verse = (Long) hashMap.get("verse");

                                    fav_integers favorite = new fav_integers(Math.toIntExact(l), Math.toIntExact(chapter), Math.toIntExact(verse));
                                    favorites.add(favorite);
                                }
                                isGetdata = true;
                                 favAdepter1 = new favAdepter(favorites, getContext(), new favAdepter.ItemClickLisener() {
                                    @Override
                                    public void onItemClick(fav_integers fav_integers) {

                                        Intent intent = new Intent(getContext(), FavoriteActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("l", fav_integers.getLanguage());
                                        bundle.putInt("v", fav_integers.getVerse());
                                        bundle.putInt("c", fav_integers.getChapter());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                                recyclerView.setAdapter(favAdepter1);

                                textView1.setVisibility(View.GONE);
                                for (fav_integers fav : favorites) {
//                                textView.setText(fav.getLanguage()+" "+ fav.getChapter()+" "+ fav.getVerse());
                                    Log.d("devin", "data from map !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   " + fav.getLanguage() + " " + fav.getChapter() + " " + fav.getVerse());
                                }
                            }


                        } else {
                            Log.d("devin", "No such document");
                            Toast.makeText(getContext(), "no favorites document!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("devin", "Error getting document", e);
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        favorites.clear();
        getData();

//        if (isGetdata){
//            favAdepter1.notifyDataSetChanged();
//        }
    }



    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
}