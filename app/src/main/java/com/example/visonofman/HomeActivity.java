package com.example.visonofman;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

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


        FirebaseApp.initializeApp(this);


        setSupportActionBar(binding.appBarHome2.toolbar1);

         drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_chapters,R.id.nav_language)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Log.d("devin","navigation on item select lisener called :::::");
//                drawer.closeDrawer(GravityCompat.START);
//                int id= item.getItemId();
//                switch (id){
//                    case R.id.nav_chapters:{
//
//                        showFragment(new ChaptersFragment(HomeActivity.this),1);
//                        break;
//                    }
//                    case R.id.nav_language:{
//                        showFragment(new SlideshowFragment(),2);
//                        break;
//                    }
//                }
//
//                return true;
//            }
//        });


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
}