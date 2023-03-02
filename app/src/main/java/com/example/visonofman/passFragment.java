package com.example.visonofman;

import android.app.Dialog;
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
import android.widget.Toast;

import com.example.visonofman.Activity.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class passFragment extends Fragment {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    String name, email, pass;
    EditText password;
    Button signup;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    Dialog loadingDialog;
    GoogleSignInAccount acct;

    public passFragment(String name, String email, GoogleSignInAccount acct) {
        this.name = name;
        this.email = email;
        this.acct = acct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pass, container, false);

        signup = view.findViewById(R.id.signup);
        password = view.findViewById(R.id.editTextTextPassword);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.login_dialog);

//        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                loadingDialog.show();
                loadingDialog.setCancelable(false);

                pass = password.getText().toString();


                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("firebase", "signInWithCredential:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    Log.d("firebase", userId);

                                    // Get the current date and time
                                    Date now = new Date();
                                    // Define a format for displaying the date and time
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    // Format the date and time using the defined format
                                    String dateTimeString = sdf.format(now);

                                    Map<String, Object> user1 = new HashMap<>();
                                    user1.put("name", name);
                                    user1.put("email", email);
                                    user1.put("password", pass);
                                    user1.put("idToken", acct.getIdToken());
                                    user1.put("date", dateTimeString);
                                    user1.put("image", "");
                                    user1.put("desc", "");


                                    firestore.collection("users")
                                            .document(userId)
                                            .set(user1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("firebase", "User added to Firestore");
                                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                                    Toast.makeText(getContext(), "Log in as " + name, Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                    loadingDialog.dismiss();
                                                    getActivity().finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    loadingDialog.dismiss();
                                                    Log.w("firebase", "Error adding user to Firestore", e);
                                                }
                                            });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("firebase", "signInWithCredential:failure", task.getException());

                                }
                            }
                        });

//                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        String userId = firebaseAuth.getCurrentUser().getUid();
//                        Log.d("firebase",userId);
//
//                        Map<String, Object> user = new HashMap<>();
//                        user.put("name", name);
//                        user.put("email", email);
//                        user.put("password", pass);
//
//                        firestore.collection("users")
//                                .document(userId)
//                                .set(user)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Log.d("firebase", "User added to Firestore");
//                                        Intent intent=new Intent(getContext(), HomeActivity.class);
//                                        Toast.makeText(getContext(), "Log in as "+ name, Toast.LENGTH_SHORT).show();
//                                        startActivity(intent);
//                                        loadingDialog.dismiss();
//                                        getActivity().finish();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        loadingDialog.dismiss();
//                                        Log.w("firebase", "Error adding user to Firestore", e);
//                                    }
//                                });
//                    }
//                });

            }
        });


        return view;
    }

}