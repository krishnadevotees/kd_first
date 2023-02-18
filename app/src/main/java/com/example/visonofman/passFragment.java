package com.example.visonofman;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.visonofman.Activity.HomeActivity;
import com.example.visonofman.ModelClass.user;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class passFragment extends Fragment {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    String name,email,pass;
    EditText password;
    Button signup;

    public passFragment(String name,String email) {
        this.name=name;
        this.email=email;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_pass, container, false);

       signup=view.findViewById(R.id.signup);
       password=view.findViewById(R.id.editTextTextPassword);


        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass=password.getText().toString();

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String userId = firebaseAuth.getCurrentUser().getUid();
                        Log.d("firebase",userId);

                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name);
                        user.put("email", email);
                        user.put("password", pass);

                        firestore.collection("users")
                                .document(userId)
                                .set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("firebase", "User added to Firestore");
                                        Intent intent=new Intent(getContext(), HomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("firebase", "Error adding user to Firestore", e);
                                    }
                                });
                    }
                });

            }
        });




        return view;
    }
}