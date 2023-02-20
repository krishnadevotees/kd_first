package com.example.visonofman;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visonofman.Activity.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.function.ObjIntConsumer;

public class SigninFragment extends Fragment {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    ImageView google;
    Button login;
    EditText e, p;
    String email, pass;
    Dialog loadingDialog;
    FirebaseAuth firebaseAuth;
    TextView fp1;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.login_dialog);
        login = view.findViewById(R.id.login);

        e = view.findViewById(R.id.editTextTextEmailAddress);
        p = view.findViewById(R.id.editTextTextPassword);

        TextView signup = view.findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadfregment(new SignupFragment(), 1);
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        firebaseAuth=FirebaseAuth.getInstance();

        google = view.findViewById(R.id.imageView3);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                SignIn();
            }
        });

        fp1=view.findViewById(R.id.fp);
        fp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = e.getText().toString();
                if (isValidEmail(email)){
                    firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "Password Reset Email sent ", Toast.LENGTH_SHORT).show();
                                        startTimer();
                                        fp1.setVisibility(View.GONE);
                                        
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Email is not regesterd ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Enter valid Email", Toast.LENGTH_SHORT).show();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                loadingDialog.setCancelable(false);

                email = e.getText().toString();
                pass = p.getText().toString();

                if (isValidEmail(email) && isValidPassword(pass)) {
                    firebaseAuth = FirebaseAuth.getInstance();

//                    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
//                    firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String name =user.getEmail();
                                Toast.makeText(getContext(), "Log in as "+ name, Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                // Login failed
                                loadingDialog.dismiss();
                                Exception exception = task.getException();
                                Toast.makeText(getContext(), "exception at oncomplete signin frag"+ exception, Toast.LENGTH_SHORT).show();
                                Log.d("firebase", " signin exc ::::  " + exception);
                            }
                        }
                    });
                } else {
                    loadingDialog.dismiss();

                    if (!isValidEmail(email) || email.isEmpty() ) {
                        e.requestFocus();
                        e.setError("Please enter a valid Email");
                    } else if (!isValidPassword(pass) || pass.isEmpty()) {
                        p.requestFocus();
                        p.setError("Please enter a valid Password");
                    } else {
                        e.setError(null);
                        p.setError(null);

                        email = e.getText().toString();
                        pass = p.getText().toString();
                    }


                }


            }
        });


        return view;
    }

    void SignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 1);
    }


    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        return password.length() > 5;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    String name =user.getEmail();
                    Toast.makeText(getContext(), "Log in as "+ name, Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getContext(), HomeActivity.class);
                loadingDialog.dismiss();
                startActivity(intent);
                getActivity().finish();
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadfregment(Fragment fragment, int flag) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0) {
            ft.add(R.id.nav_host_fragment_login, fragment);
        } else {
            ft.replace(R.id.nav_host_fragment_login, fragment);
        }
        ft.commit();
    }
    private void startTimer() {
        new CountDownTimer(30000, 2000) {
            public void onTick(long millisUntilFinished) {
                // Do nothing.
                Toast.makeText(getContext(), "Please Wait 15 seconds ", Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
                fp1.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}