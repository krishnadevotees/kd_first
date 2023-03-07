package com.example.visonofman;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;


public class WaitFragment extends Fragment {

    TextView textView;

    public WaitFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_wait, container, false);

         textView =view.findViewById(R.id.tv);

        ObjectAnimator moveAnimator = ObjectAnimator.ofFloat(
                textView,
                "translationX",
                0f,
                -textView.getWidth()
        );
        moveAnimator.setDuration(5000);
        moveAnimator.setInterpolator(new LinearInterpolator());
        moveAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        moveAnimator.start();


        return view;
    }
}