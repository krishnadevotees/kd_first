package com.example.visonofman.ui.favrate;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;


import com.example.visonofman.Adepters.favAdepter;

import com.example.visonofman.ModelClass.favModel;
import com.example.visonofman.ModelClass.fav_integers;
import com.example.visonofman.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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


    public fav_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_, container, false);

//        DisplayFragment fragment = (DisplayFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.displayFragment);
//        List<favModel> list= fragment.getData();
//        for (int i =0; i<list.size(); i++){
//            Log.d("devin",""+list.get(i)+"\n");
//        }

        textView=view.findViewById(R.id.tv_fa);
//        recyclerView=view.findViewById(R.id.fav_rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("favorite", MODE_PRIVATE);
        Set<String> myList = sharedPreferences.getStringSet("favorites", null);
        Log.d("devin","favoriteList  "+myList+"\n");


        firestore.collection("users").document(auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // retrieve the favorite list from the document
                            List<HashMap<String, Object>> favoritesMap = (List<HashMap<String, Object>>) documentSnapshot.get("favorite");

                            for (HashMap<String, Object> hashMap : favoritesMap) {
                                Long l = (Long) hashMap.get("language");
                                Long chapter = (Long) hashMap.get("chapter");
                                Long verse = (Long) hashMap.get("verse");

                                fav_integers favorite = new fav_integers(Math.toIntExact(l), Math.toIntExact(chapter), Math.toIntExact(verse));
                                favorites.add(favorite);
                            }

                            for (fav_integers fav : favorites) {
                                textView.setText(fav.getLanguage()+" "+ fav.getChapter()+" "+ fav.getVerse());
                                Log.d("devin",  "data from map !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   "+fav.getLanguage()+" "+ fav.getChapter()+" "+ fav.getVerse());
                            }
                        } else {
                            Log.d("devin", "No such document");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("devin", "Error getting document", e);
                    }
                });



//        String text = String.join(",\n\n\n\n ", myList);
//        textView.setText(text);

//        List<favModel> itemList = new ArrayList<>();
//        for (String favorite : myList) {
//            String[] parts = favorite.split(",");
//            int id = Integer.parseInt(parts[0]);
//            String verse = parts[1];
//            String translate = parts[1];
//            String description = parts[2];
//            itemList.add(new favModel(id, verse,translate, description));
//        }


//        favAdepter adapter = new favAdepter(itemList);
//        Log.d("devin","favoriteList  2  "+itemList+"\n");
//        recyclerView.setAdapter(adapter);


//        String jsonArrayString = String.valueOf(sharedPreferences.getStringSet("favoriteList", null));
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(jsonArrayString);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        ArrayList<Set<String>> myList1 = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONArray setArray = null;
//            try {
//                setArray = jsonArray.getJSONArray(i);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//            Set<String> set = new HashSet<>();
//            for (int j = 0; j < setArray.length(); j++) {
//                try {
//                    set.add(setArray.getString(j));
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            myList1.add(set);
//        }
//        StringBuilder sb = new StringBuilder();
//
//        for (Set<String> set : myList1) {
//            for (String element : set) {
//                sb.append(element).append(" ");
//            }
//            sb.append("\n"); // Add a newline character to separate sets
//        }
//
//        String result = sb.toString();
//        textView.setText(result);

        return view;
    }
}