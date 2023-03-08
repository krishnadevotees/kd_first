package com.example.visonofman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visonofman.Adepters.VerseListAdepter;
import com.example.visonofman.ModelClass.VerseList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class VerseListFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    int flag;
    String Language;
    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ArrayList<VerseList> data = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    VerseListAdepter adepter;
    String lang;

    public VerseListFragment() {
        // Required empty public constructor
    }

    public VerseListFragment(Context context, int flag) {
        this.flag = flag;
        this.context = context;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_verse_list, container, false);
        setHasOptionsMenu(true);


//        Sharedprefrence_Language sharedprefrence_language= new Sharedprefrence_Language(getContext());
//        String selectedLanguage = sharedprefrence_language.getString("lan","1");

//        Language =selectedLanguage;
//        sharedprefrence_language.clear();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("language", 0);
        Language = sharedPreferences.getString("lan", "0");
        Log.d("dev", "selectedLanguage :::  " + Language);


        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.hasFixedSize();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("data/languages/" + Language + "/chapters/" + flag + "/data/");
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                data.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    list.add(snapshot.getKey());

                }

                firestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        lang = documentSnapshot.getString("selectedLanguage");
                        Log.d("devin", "onSuccess: selectedLanguage in display fragment   " + lang);

                        firestore.collection("display").document(documentSnapshot.getString("selectedLanguage") + "").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String sloka = documentSnapshot.getString("verse");

                                for (int i = 0; i < list.size(); i++) {
                                    int a = i + 1;
                                    data.add(new VerseList(sloka + " " + a));
                                }

                                Log.d("devin", "" + data.size());
                                adepter = new VerseListAdepter(getContext(), data, getFragmentManager(), flag);
                                recyclerView.setAdapter(adepter);
                                progressDialog.dismiss();
                            }
                        });
                    }
                });


                Log.d("devin", "List Size :::  " + list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("devin", "" + error);
            }
        });


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.custom_menu, menu);
        MenuItem favoriteMenuItem = menu.findItem(R.id.action_favorite);
        favoriteMenuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onResume() {
        super.onResume();
        // set the new title for the ActionBar
        getActivity().setTitle("Verse");
    }

    private void showFragment(Fragment fragment, int flag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (flag == 0) {
            transaction.add(R.id.nav_host_fragment_content_home2, fragment);
        } else {
            transaction.replace(R.id.nav_host_fragment_content_home2, fragment);

        }
        transaction.addToBackStack(null);
        transaction.commit();
    }
}