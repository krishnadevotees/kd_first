package com.example.visonofman;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.visonofman.Adepters.CardView_Adepter;
import com.example.visonofman.ModelClass.CardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {
    GridView gridView;

    public HomeFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = rootView.findViewById(R.id.gridlayout);


        List<String> data = Arrays.asList("Title 1", "Title 2", "Title 3", "Title 4",
                "Title 5", "Title 6", "Title 7", "Title 8","Title 9", "Title 10", "Title 11", "Title 12",
                "Title 13", "Title 14", "Title 15", "Title 16","Title 17", "Title 18");
        CardView_Adepter adapter = new CardView_Adepter(getActivity(), data);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener((parent, view1, position, id) -> {


        });


        return  rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        List<CardData> cardDataList = new ArrayList<>();
//        cardDataList.add(new CardData("Title 1"));
//        cardDataList.add(new CardData("Title 2"));
//        cardDataList.add(new CardData("Title 3"));
// Add more items to the list as needed


    }
}