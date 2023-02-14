package com.example.visonofman.ui.chapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.visonofman.Adepters.CardView_Adepter;
import com.example.visonofman.CustomClasses.SecondFragment;
import com.example.visonofman.R;
import com.example.visonofman.VerseActivity;
import com.example.visonofman.databinding.FragmentChaptersBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class ChaptersFragment extends Fragment {
    GridView gridView;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
Context context;
private FragmentChaptersBinding binding;

    public ChaptersFragment( ) {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentChaptersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gridView = root.findViewById(R.id.gridlayout);

//        firebaseDatabase=FirebaseDatabase.getInstance();
//        databaseReference =firebaseDatabase.getReference("data/languages/0/chapters/0/data");


        List<String> data = Arrays.asList("Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4",
                "Chapter 5", "Chapter 6", "Chapter 7", "Chapter 8","Chapter 9", "Chapter 10", "Chapter 11", "Chapter 12",
                "Chapter 13", "Chapter 14", "Chapter 15", "Chapter 16","Chapter 17", "Chapter 18");
        CardView_Adepter adapter = new CardView_Adepter(getActivity(), data);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener((parent, view1, position, id) -> {

            Log.d("devin",""+id);
            Intent intent= new Intent(getContext(), VerseActivity.class);
            Bundle bundle=new Bundle();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}