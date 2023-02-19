package com.example.visonofman.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.visonofman.R;
import com.example.visonofman.SigninFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        loadfregment(new SigninFragment(),0);


    }
    private void loadfregment(Fragment fragment, int flag) {
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (flag == 0){
            ft.add(R.id.nav_host_fragment_login,fragment);
        }else{
            ft.replace(R.id.nav_host_fragment_login,fragment);
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1){
            finish();
        }else {
            super.onBackPressed();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null){
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//            finish();
//        }
//    }
}