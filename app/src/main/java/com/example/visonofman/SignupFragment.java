package com.example.visonofman;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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


public class SignupFragment extends Fragment {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    ImageView google;
    Button signup;
    EditText n, e, p;
    String name, email, pass;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    Dialog loadingDialog;


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        n = view.findViewById(R.id.editTextTextPersonName);
        e = view.findViewById(R.id.editTextTextEmailAddress);
        p = view.findViewById(R.id.editTextTextPassword);
        signup = view.findViewById(R.id.signup);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.login_dialog);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        TextView signintv = view.findViewById(R.id.signin);
        signintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadfregment(new SigninFragment(), 1);
            }
        });


        google = view.findViewById(R.id.imageView3);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .build();
                googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
                SignIn();

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingDialog.show();
                loadingDialog.setCancelable(false);

                name = n.getText().toString();
                email = e.getText().toString();
                pass = p.getText().toString();

                if (isValidName(name) && isValidEmail(email) && isValidPassword(pass)) {


                    createUser(email, pass, name);

//                    firebaseAuth.fetchSignInMethodsForEmail(email)
//                            .addOnCompleteListener(task -> {
//                                if (task.isSuccessful()) {
//                                    List<String> signInMethods = task.getResult().getSignInMethods();
//                                    if (signInMethods == null || signInMethods.isEmpty()) {
//
//                                        // Email is not registered
//                                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                            @Override
//                                            public void onSuccess(AuthResult authResult) {
//
//                                                loadingDialog.dismiss();
////                                                String displayName = email.substring(0, email.indexOf('@'));
//
//                                                String userId = firebaseAuth.getCurrentUser().getUid();
//                                                Log.d("firebase", userId);
//                                                FirebaseUser user1 = firebaseAuth.getCurrentUser();
//
//                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                                        .setDisplayName(name)
//                                                        .build();
//
//                                                user1.updateProfile(profileUpdates)
//                                                        .addOnCompleteListener(updateTask -> {
//                                                            if (updateTask.isSuccessful()) {
//                                                                // The user's display name has been updated.
//                                                            }
//                                                        });
//
//                                                Map<String, Object> user = new HashMap<>();
//                                                user.put("name", name);
//                                                user.put("email", email);
//                                                user.put("password", pass);
//
//                                                firestore.collection("users")
//                                                        .document(userId)
//                                                        .set(user)
//                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void unused) {
//                                                                Log.d("firebase", "User added to Firestore");
//
//                                                                Intent intent = new Intent(getContext(), HomeActivity.class);
//                                                                startActivity(intent);
//                                                                loadingDialog.dismiss();
//                                                                getActivity().finish();
//                                                            }
//                                                        }).addOnFailureListener(new OnFailureListener() {
//                                                            @Override
//                                                            public void onFailure(@NonNull Exception e) {
//                                                                loadingDialog.dismiss();
//                                                                Log.w("firebase", "Error adding user to Firestore", e);
//                                                                Toast.makeText(getContext(), "Error in creating user !!!", Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        });
//                                            }
//                                        });
//
//                                    } else {
//                                        // Email is already registered
//                                        e.setError("User already exists with this email");
//                                        Toast.makeText(getContext(), "User already exists with this email", Toast.LENGTH_SHORT).show();
//
//                                        loadingDialog.dismiss();
//                                    }
//                                } else {
//                                    // Some error occurred
//                                    loadingDialog.dismiss();
//                                    Toast.makeText(getContext(), "somthing wrong in signup ", Toast.LENGTH_SHORT).show();
//                                }
//                            });

                } else {
                    loadingDialog.dismiss();

                    if (!isValidName(name) || name.isEmpty()) {
                        n.requestFocus();
                        n.setError("Please enter a valid Name");

                    } else if (!isValidEmail(email) || email.isEmpty()) {
                        e.requestFocus();
                        e.setError("Please enter a valid Email");
                    } else if (!isValidPassword(pass) || pass.isEmpty()) {
                        p.requestFocus();
                        p.setError("Please enter a valid Password and it should contain 6 letters");
                    } else {
                        n.setError(null);
                        e.setError(null);
                        p.setError(null);
                        name = n.getText().toString();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            if (requestCode == 1) {
                Task<GoogleSignInAccount> t = GoogleSignIn.getSignedInAccountFromIntent(data);


                try {
                    GoogleSignInAccount account = t.getResult(ApiException.class);
                    String name = account.getDisplayName();
                    String email = account.getEmail();


                    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    List<String> signInMethods = task.getResult().getSignInMethods();
                                    if (signInMethods == null || signInMethods.isEmpty()) {
                                        // User does not exist with this email, proceed with sign up

                                        loadfregment(new passFragment(name, email, account), 1);

                                    } else {
                                        // User already exists with this email, show an error message
                                        signInMethods.clear();

                                        Toast.makeText(getContext(), "User Already Exists ! Please Choose Another Email", Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    // Failed to check if user exists, handle the error

                                    Toast.makeText(getContext(), "Somthing Went wrong !!! in result activity ", Toast.LENGTH_SHORT).show();

                                }
                            });


                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {

        }

    }

    void createUser(String email, String pass, String name) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> signInMethods = task.getResult().getSignInMethods();
                        if (signInMethods == null || signInMethods.isEmpty()) {

                            // Email is not registered
                            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

//                                    loadingDialog.dismiss();
//                                                String displayName = email.substring(0, email.indexOf('@'));

                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    Log.d("firebase", userId);
                                    FirebaseUser user1 = firebaseAuth.getCurrentUser();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();

                                    user1.updateProfile(profileUpdates)
                                            .addOnCompleteListener(updateTask -> {
                                                if (updateTask.isSuccessful()) {
                                                    // The user's display name has been updated.
                                                }
                                            });


                                    // Get the current date and time
                                    Date now = new Date();
                                    // Define a format for displaying the date and time
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    // Format the date and time using the defined format
                                    String dateTimeString = sdf.format(now);

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("email", email);
                                    user.put("password", pass);
                                    user.put("image", "");
                                    user.put("desc", "");
                                    user.put("selectedLanguage", "0");
                                    user.put("date", dateTimeString);

                                    firestore.collection("users")
                                            .document(userId)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("firebase", "User added to Firestore");

                                                    firebaseAuth.getCurrentUser().sendEmailVerification();
                                                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                                        startActivity(intent);
                                                        loadingDialog.dismiss();
                                                        getActivity().finish();
                                                    }else {
                                                        Toast.makeText(getContext(), "Email Verification link sent \nPlease Verify your Email", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    loadingDialog.dismiss();
                                                    Log.w("firebase", "Error adding user to Firestore", e);
                                                    Toast.makeText(getContext(), "Error in creating user !!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });

                        } else {
                            // Email is already registered
                            e.setError("User already exists with this email");
                            Toast.makeText(getContext(), "User already exists with this email", Toast.LENGTH_SHORT).show();

                            loadingDialog.dismiss();
                        }
                    } else {
                        // Some error occurred
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), "somthing wrong in signup ", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public boolean isValidName(String name) {
        String regex = "^[a-zA-Z ]+$";
        return name.matches(regex);
    }

    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        return password.length() > 5;
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

    @Override
    public void onDestroyView() {
       loadingDialog.dismiss();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        loadingDialog.dismiss();
        super.onPause();
    }
}