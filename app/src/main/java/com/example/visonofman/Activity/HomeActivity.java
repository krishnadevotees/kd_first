package com.example.visonofman.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visonofman.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.visonofman.databinding.ActivityHome2Binding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    private ActivityHome2Binding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection("users").document(firebaseUser.getUid());
Log.d("firebase",firebaseUser.getUid());

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(HomeActivity.this, googleSignInOptions);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        FirebaseApp.initializeApp(this);

        setSupportActionBar(binding.appBarHome2.toolbar1);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerview = navigationView.getHeaderView(0);
        ImageView imageView=headerview.findViewById(R.id.image);
        TextView name =headerview.findViewById(R.id.name);
        TextView email =headerview.findViewById(R.id.email);


        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    name.setText(documentSnapshot.getString("name"));
                    email.setText(documentSnapshot.getString("email"));
                    if (documentSnapshot.getString("image") != ""){
                        Picasso.get().load(documentSnapshot.getString("image")).into(imageView);
                    }
                }
            }
        });
//        if (firebaseUser.getPhotoUrl() != null){
//            Picasso.get().load(firebaseUser.getPhotoUrl()).into(imageView);
//        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_chapters, R.id.nav_profile,R.id.fav_Fragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        binding.navView.setItemIconTintList(null);


    }

    private void showFragment(Fragment fragment, int flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag == 0) {
            transaction.add(R.id.nav_host_fragment_content_home2, fragment);
        } else {
            transaction.replace(R.id.nav_host_fragment_content_home2, fragment);

        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cLanguage:
                showDialog();
                break;
            case R.id.logout:

                FirebaseAuth.getInstance().signOut();
                finish();

                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                        Intent signInIntent = googleSignInClient.getSignInIntent();
//                        startActivityForResult(signInIntent, 1);
                        finish();

                    }
                });


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (count == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        dialog.setContentView(R.layout.bottamsheet_layout);


        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        Button okbutton = dialog.findViewById(R.id.ok);
        Button cancle = dialog.findViewById(R.id.cancle);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        int selectedRadioButtonId = prefs.getInt("selectedRadioButtonId", -1);
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = dialog.findViewById(selectedRadioButtonId);
            selectedRadioButton.setChecked(true);
        }else {
            Toast.makeText(this, "error ", Toast.LENGTH_SHORT).show();
        }


        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String language = "";

                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioId == -1) {
                    return;
                }
                Log.d("dev", selectedRadioId + "  :::::  " + R.id.hindi);
                switch (selectedRadioId) {
                    case R.id.hindi:
                        language = "0";
                        break;
                    case R.id.gujarati:
                        language = "1";
                        break;
                    case R.id.english:
                        language = "2";
                        break;
                    case R.id.marathi:
                        language = "3";
                        break;
                    case R.id.punjabi:
                        language = "4";
                        break;
                    case R.id.tamil:
                        language = "5";
                        break;
                    case R.id.telugu:
                        language = "6";
                        break;
                    case R.id.kannada:
                        language = "7";
                        break;
                    case R.id.malayalam:
                        language = "8";
                        break;
                    case R.id.bangla:
                        language = "9";
                        break;
                }


                SharedPreferences sharedPreferences = HomeActivity.this.getSharedPreferences("language", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("lan", language);
                editor.apply();
                Log.d("devin", "language code :::: " + language);

               

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                SharedPreferences.Editor editor1 = prefs.edit();
                editor1.putInt("selectedRadioButtonId", radioGroup.getCheckedRadioButtonId());
                editor1.apply();

                dialog.dismiss();

                recreate();
                Toast.makeText(HomeActivity.this, "Language Changed ", Toast.LENGTH_SHORT).show();

            }
        });


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.Dialoganimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();


    }


}