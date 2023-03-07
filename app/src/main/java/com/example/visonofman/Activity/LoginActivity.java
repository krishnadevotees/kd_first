package com.example.visonofman.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.visonofman.R;
import com.example.visonofman.SigninFragment;
import com.example.visonofman.WaitFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Dialog loadingDialog;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        loadingDialog = new Dialog(LoginActivity.this);
        loadingDialog.setContentView(R.layout.login_dialog);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null && currentUser.isEmailVerified()){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        loadfregment(new SigninFragment(), 0);



//        Toast.makeText(this, " "+mAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();


    }

    private void loadfregment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0) {
            ft.add(R.id.nav_host_fragment_login, fragment);
        } else {
            ft.replace(R.id.nav_host_fragment_login, fragment);
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
        googleSignInClient.signOut();
    }

    @Override
    protected void onStart() {

        super.onStart();


    }
}