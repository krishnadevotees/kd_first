package com.example.visonofman;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visonofman.Activity.HomeActivity;
import com.example.visonofman.ModelClass.user;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class SignupFragment extends Fragment {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    ImageView google;



    public SignupFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signup, container, false);


        TextView signin =view.findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadfregment(new SigninFragment(),1);
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(getContext(),googleSignInOptions);

        google=view.findViewById(R.id.imageView3);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });






        return view;
    }

    void SignIn(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 ){
            Task<GoogleSignInAccount> t = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account=t.getResult(ApiException.class);
                String name = account.getDisplayName();
                String email =account.getEmail();


                FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<String> signInMethods = task.getResult().getSignInMethods();
                                if (signInMethods == null || signInMethods.isEmpty()) {
                                    // User does not exist with this email, proceed with sign up

                                    loadfregment(new passFragment(name,email),1);

//                                    Intent intent=new Intent(getContext(), HomeActivity.class);
//                                    startActivity(intent);
//                                    getActivity().finish();
                                } else {
                                    // User already exists with this email, show an error message
                                    Toast.makeText(getContext(), "User Already Exists ! Please Choose Another Email", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Failed to check if user exists, handle the error
                                // ...
                            }
                        });


                
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadfregment(Fragment fragment, int flag) {
        FragmentManager fm =getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (flag == 0){
            ft.add(R.id.nav_host_fragment_login,fragment);
        }else{
            ft.replace(R.id.nav_host_fragment_login,fragment);
        }
        ft.commit();
    }
}