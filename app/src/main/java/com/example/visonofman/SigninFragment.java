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

import com.example.visonofman.Activity.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SigninFragment extends Fragment {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    ImageView google;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_signin, container, false);


        TextView signin =view.findViewById(R.id.signup);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadfregment(new SignupFragment(),1);
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
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Intent intent=new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
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