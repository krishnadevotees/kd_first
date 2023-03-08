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
import com.google.android.gms.tasks.OnFailureListener;
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

//        ArrayList<String> chapters = new ArrayList<>();
//
//        chapters.add("1. మొదటి అధ్యాయం - 'అర్జున విషాద యోగం'.");
//        chapters.add("2. రెండవ అధ్యాయం - 'సాంఖ్య యోగా'.");
//        chapters.add("3. మూడవ అధ్యాయం - 'కర్మ యోగం'.");
//        chapters.add("4. నాల్గవ అధ్యాయం - 'జ్ఞాన కర్మ సన్యాస యోగం'.");
//        chapters.add("5. ఐదవ అధ్యాయం - 'కర్మ సన్యాస యోగం'.");
//        chapters.add("6. ఆరవ అధ్యాయం - 'ఆత్మసంయం యోగం'.");
//        chapters.add("7. ఏడవ అధ్యాయం - 'జ్ఞాన విజ్ఞాన యోగం'.");
//        chapters.add("8. ఎనిమిదవ అధ్యాయం - 'అక్షర బ్రహ్మ యోగం'.");
//        chapters.add("9. తొమ్మిదవ అధ్యాయం - 'రాజ విద్యా రాజ గుహ్య యోగం'.");
//        chapters.add("10. పదవ అధ్యాయం - 'విభూతి యోగం'.");
//        chapters.add("11. పదకొండవ అధ్యాయం - 'విశ్వరూప దర్శన యోగం'.");
//        chapters.add("12. పన్నెండవ అధ్యాయం - 'భక్తి యోగం'.");
//        chapters.add("13. పదమూడవ అధ్యాయం - 'క్షేత్ర క్షేత్రజ్ఞ విభాగ యోగం'.");
//        chapters.add("14. పద్నాలుగో అధ్యాయం - 'గుణత్రయ విభాగ యోగం'.");
//        chapters.add("15. పదిహేనవ అధ్యాయం - 'పురుషోత్తమ యోగం'.");
//        chapters.add("16. పదహారవ అధ్యాయం - 'దైవాసుర సంపద్ విభాగ యోగ'.");
//        chapters.add("17. పదిహేడవ అధ్యాయం - 'శ్రద్ధత్రయ విభాగ యోగం'.");
//        chapters.add("18. పద్దెనిమిదవ అధ్యాయం - 'మోక్ష సన్యాస యోగం'.");
//
//
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("chapters", chapters);
//
//        db.collection("Chapter")
//                .document("Telugu")
//                .set(data)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Data has been successfully written
//                        Log.d("devin", "DATA write completed");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("devin", "DATA write failed");
//                        // Handle any errors
//                    }
//                });


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