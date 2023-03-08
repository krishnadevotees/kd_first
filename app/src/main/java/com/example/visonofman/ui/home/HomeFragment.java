package com.example.visonofman.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.visonofman.R;
import com.example.visonofman.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    DatabaseReference databaseReference;
    String Language;
    ArrayList<String> adhyay =new ArrayList<>();
    ArrayList<String> sloka =new ArrayList<>();
    private int spinner1Selection = -1;
    private int spinner2Selection = -1;
    AutoCompleteTextView textView1;
    AutoCompleteTextView textView2;
    int pch,psl;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();




//        Map<String, Object> chapter1 = new HashMap<>();
//        chapter1.put("Chapter 1","1. প্রথম অধ্যায় - 'অর্জুন বিষাদ যোগ'।");
//        chapter1.put("Chapter 2","2. দ্বিতীয় অধ্যায় - 'সাংখ্য যোগ'।");
//        chapter1.put("Chapter 3","3. তৃতীয় অধ্যায় - 'কর্মযোগ'।");
//        chapter1.put("Chapter 4","4. চতুর্থ অধ্যায় - 'জ্ঞান কর্ম সন্ন্যাস যোগ'।");
//        chapter1.put("Chapter 5","5. পঞ্চম অধ্যায় - 'কর্ম সন্ন্যাস যোগ'।");
//        chapter1.put("Chapter 6","6. ষষ্ঠ অধ্যায় - 'আত্মসংযম যোগ'।");
//        chapter1.put("Chapter 7","7. সপ্তম অধ্যায় - 'জ্ঞান জ্ঞান যোগ'।");
//        chapter1.put("Chapter 8","8. অষ্টম অধ্যায় - 'অক্ষর ব্রহ্ম যোগ'।");
//        chapter1.put("Chapter 9","9. নবম অধ্যায় - 'রাজা বিদ্যা রাজা গুহ্য যোগ'।");
//        chapter1.put("Chapter 10","10. দশম অধ্যায় - 'বিভূতি যোগ'।");
//        chapter1.put("Chapter 11","11. একাদশ অধ্যায় - 'বিশ্বরূপ দর্শন যোগ'।");
//        chapter1.put("Chapter 12","12. দ্বাদশ অধ্যায় - 'ভক্তি যোগ'।");
//        chapter1.put("Chapter 13","13. ত্রয়োদশ অধ্যায় - 'ক্ষেত্র ক্ষেত্রজ্ঞান বিভাগ যোগ'।");
//        chapter1.put("Chapter 14","14. চতুর্দশ অধ্যায় - 'গুনাত্রয় বিভাগ যোগ'।");
//        chapter1.put("Chapter 15","15. পঞ্চদশ অধ্যায় - 'পুরুষোত্তম যোগ'।");
//        chapter1.put("Chapter 16","16. ষোড়শ অধ্যায় - 'দৈবাসুরসম্পদ বিভাগ যোগ'।");
//        chapter1.put("Chapter 17","17. সপ্তদশ অধ্যায় - 'শ্রদ্ধাত্রয় বিভাগ যোগ'।");
//        chapter1.put("Chapter 18","18. অষ্টাদশ অধ্যায় - 'মোক্ষ সন্ন্যাস যোগ'।");
//        chapter1.put("verse","ਸ਼ਲੋਕ ");
//        chapter1.put("translate","ਅਨੁਵਾਦ ");
//        chapter1.put("description","ਅਰਥ ");
//        DocumentReference chapter1Ref = db.collection("display").document("Punjabi");
//
//        chapter1Ref.set(chapter1).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.d("devin", "DATA write completed");
//            }
//        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding=null;

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