package com.example.visonofman.ui.chapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.visonofman.Activity.HomeActivity;
import com.example.visonofman.Adepters.CardView_Adepter;
import com.example.visonofman.R;
import com.example.visonofman.Activity.VerseActivity;
import com.example.visonofman.databinding.FragmentChaptersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChaptersFragment extends Fragment {
    GridView gridView;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String lang;
    ArrayList<Object> dataList;
    List<String> chaptersList = new ArrayList<>();
    private FragmentChaptersBinding binding;

    public ChaptersFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentChaptersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gridView = root.findViewById(R.id.gridlayout);

//        firebaseDatabase=FirebaseDatabase.getInstance();
//        databaseReference =firebaseDatabase.getReference("data/languages/0/chapters/0/data");

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("language", 0);
//        String lan=sharedPreferences.getString("lan","0");



        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        firestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                lang = documentSnapshot.getString("selectedLanguage");
                Log.d("devin", "onSuccess: selectedLanguage   " + lang);

                firestore.collection("Chapter").document(documentSnapshot.getString("selectedLanguage")+"").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        chaptersList = (ArrayList<String>) documentSnapshot.get("chapters");

//                    List<String> data = new ArrayList<>(chaptersList);
                        Log.d("devin", "Document data:data " + chaptersList);
                        CardView_Adepter adapter = new CardView_Adepter(getActivity(), chaptersList);
                        gridView.setAdapter(adapter);
                        progressDialog.dismiss();
                    }
                });
            }
        });










//        List<String> data = Arrays.asList("Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4",
//                "Chapter 5", "Chapter 6", "Chapter 7", "Chapter 8","Chapter 9", "Chapter 10", "Chapter 11", "Chapter 12",
//                "Chapter 13", "Chapter 14", "Chapter 15", "Chapter 16","Chapter 17", "Chapter 18");



        gridView.setOnItemClickListener((parent, view1, position, id) -> {

            Log.d("devin", "" + id);
            Intent intent = new Intent(getContext(), VerseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("chapter", String.valueOf(id));
            intent.putExtras(bundle);
            startActivity(intent);

        });


        return root;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.add(R.id.nav_host_fragment_content_home2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item1=menu.findItem(R.id.action_cLanguage);
        if (new ChaptersFragment().isVisible()){
            item1.setVisible(true);
        }else {
            item1.setVisible(false);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}