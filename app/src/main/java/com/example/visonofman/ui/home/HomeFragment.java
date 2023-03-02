package com.example.visonofman.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.visonofman.Display2Fragment;
import com.example.visonofman.R;
import com.example.visonofman.databinding.FragmentHomeBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Context context;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
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

//        textView1=binding.autoCompleteTxtCh;
//        textView2=binding.autoCompleteTxtSloka;


        SharedPreferences sharedPreferences= getContext().getSharedPreferences("language",0);
        Language= sharedPreferences.getString("lan","0");
        Log.d("dev","selectedLanguage :::  "+Language);
//        Toast.makeText(getContext(), "language "+Language, Toast.LENGTH_SHORT).show();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("data/languages/");
        databaseReference.child(Language+"/chapters/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=1;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    adhyay.add("Adhyay "+i);
                    i++;
                    Log.d("devin", String.valueOf(snapshot.getChildren()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "database refrence error oncancle", Toast.LENGTH_SHORT).show();
            }
        });


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, adhyay);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//            textView1.setAdapter(adapter);


//
//        binding.autoCompleteTxtSloka.setEnabled(false);
//        binding.autoCompleteTxtCh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//                pch=position;
////                Toast.makeText(getContext(),"Item: "+item+" position "+position,Toast.LENGTH_SHORT).show();
//                if (item != null){
//                    binding.autoCompleteTxtSloka.setEnabled(true);


//
//                    databaseReference.child(Language+"/chapters/"+position+ "/data/").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            int i=1;
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                sloka.add("Sloka "+i);
//                                i++;
//                                Log.d("devin", String.valueOf(snapshot.getChildren()));
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, sloka);
//                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//                        textView2.setAdapter(adapter);
//
//
//                     sloka.clear();//clear if it is not used
//
//                    binding.autoCompleteTxtSloka.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            String item2 = adapterView.getItemAtPosition(i).toString();
//                            psl=i;
//                            if (item2 != null){
//                                //replace frag with item 2 params
//                                showFragment(new Display2Fragment(i),3);
//
//                            }
//                        }
//                    });
//
//
//
//
//
//                }
//            }
//        });



        return root;
    }
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        // Save the selected item and options for spinner1
//        outState.putInt("spinner1Selection", pch);
//        outState.putStringArrayList("spinner1Options", new ArrayList<>(adhyay));
//
//        // Save the selected item and options for spinner2
//        outState.putInt("spinner2Selection", psl);
//        outState.putStringArrayList("spinner2Options", new ArrayList<>(sloka));
//    }
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) {
//            // Restore the selected item and options for spinner1
//            spinner1Selection = savedInstanceState.getInt("spinner1Selection", -1);
//            adhyay = savedInstanceState.getStringArrayList("spinner1Options");
//            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, adhyay);
//            textView1.setAdapter(adapter1);
//            if (spinner1Selection >= 0) {
//                textView1.setSelection(spinner1Selection);
//            }
//
//            // Restore the selected item and options for spinner2
//            spinner2Selection = savedInstanceState.getInt("spinner2Selection", -1);
//            sloka = savedInstanceState.getStringArrayList("spinner2Options");
//            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, sloka);
//            textView2.setAdapter(adapter2);
//            if (spinner2Selection >= 0) {
//                textView2.setSelection(spinner2Selection);
//            }
//        }
//    }
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