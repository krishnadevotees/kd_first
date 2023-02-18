package com.example.visonofman.Activity;

import android.app.Dialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.visonofman.R;
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

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
private ActivityHome2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


     binding = ActivityHome2Binding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        FirebaseApp.initializeApp(this);


        setSupportActionBar(binding.appBarHome2.toolbar1);

         drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_chapters)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }
    private void showFragment(Fragment fragment,int flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag == 0 ){
            transaction.add(R.id.nav_host_fragment_content_home2, fragment);
        }else {
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
        switch (item.getItemId()){
            case R.id.action_cLanguage:
                showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (count == 1){
            finish();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        dialog.setContentView(R.layout.bottamsheet_layout);




        RadioGroup radioGroup= dialog.findViewById(R.id.radioGroup);
        Button okbutton = dialog.findViewById(R.id.ok);
        Button cancle = dialog.findViewById(R.id.cancle);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        int selectedRadioButtonId = prefs.getInt("selectedRadioButtonId", -1);
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = dialog.findViewById(selectedRadioButtonId);
            selectedRadioButton.setChecked(true);
        }


        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String language = ""; 

                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioId == -1 ){
                    return;
                }
                Log.d("dev",selectedRadioId+"  :::::  "+R.id.hindi);
                switch (selectedRadioId){
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


               SharedPreferences sharedPreferences= HomeActivity.this.getSharedPreferences("language",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("lan",language);
                editor.apply();
                Log.d("devin","language code :::: "+language);

                dialog.dismiss();

                recreate();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                SharedPreferences.Editor editor1 = prefs.edit();
                editor1.putInt("selectedRadioButtonId", radioGroup.getCheckedRadioButtonId());
                editor1.apply();


            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.Dialoganimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();



    }


}