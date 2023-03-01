package com.example.visonofman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Display2Fragment extends Fragment {
    int position ;

    public Display2Fragment(int flag) {
        this.position=flag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_display2, container, false);




        return view;
    }

    @Override
    public void onDestroyView() {
//        getActivity().recreate();
        super.onDestroyView();
    }
}