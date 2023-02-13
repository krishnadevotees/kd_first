package com.example.visonofman.CustomClasses;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.visonofman.Adepters.VerseListAdepter;
import com.example.visonofman.ModelClass.VerseList;
import com.example.visonofman.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    int flag;

    public SecondFragment(Context context,int flag) {
        // Required empty public constructor
        this.context=context;
        this.flag=flag;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_second, container, false);
        setHasOptionsMenu(true);


        ArrayList<VerseList> data =new ArrayList<>();
        ArrayList<String> list =new ArrayList<>();
        HashMap<String,String> hashMap=new HashMap<String,String>();


        recyclerView=rootView.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("data/languages/0/chapters/"+flag+"/data/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Log.e(snapshot.getKey(),snapshot.getChildrenCount() + "");
                    list.add(snapshot.getKey());
                }


                for (int i=0; i < list.size(); i++){
                    int a=i+1;
                    data.add(new VerseList("verse "+a));

                }
                Log.d("devin",""+data.size());
                VerseListAdepter  adepter=new VerseListAdepter(getContext(),data);
                recyclerView.setAdapter(adepter);


                Log.d("devin","List Size :::  "+list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("devin",""+error);
            }
        });

        return rootView;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                getActivity().onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.home_as_up, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }
}