package com.example.visonofman.Activity;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.visonofman.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    FrameLayout frameLayout;

    Toolbar toolbar;
    private  AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout =findViewById(R.id.dl);
//        navigationView  = findViewById( R.id.navview);
        toolbar = findViewById(R.id.toolbar);
        frameLayout =findViewById(R.id.container);
//        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host);

        setSupportActionBar(toolbar);



//        appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.homeFragment)
//                .setOpenableLayout(drawerLayout)
//                .build();
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                drawerLayout.closeDrawers();

                Log.d("devin",""+item.getItemId());
//                switch (item.getItemId()){
//                    case R.id.home:
//                        loadfregment(new SecondFragment(MainActivity.this,1),1);
//                        Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.chapters:
//                        loadfregment(new SecondFragment(MainActivity.this,1),1);
//                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.fav_sloka:
////                        loadfregment(new SecondFragment(MainActivity.this,1),1);
//                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.verseoftheday:
////                        loadfregment(new SecondFragment(MainActivity.this,1),1);
//                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.logout:
////                        loadfregment(new SecondFragment(MainActivity.this,1),1);
//                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.dm_language:
//                        Toast.makeText(MainActivity.this, "language", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.setting:
//                        Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
//                        break;
//                }
                return true;
            }
        });

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (count == 1){
            finish();
        }else {
            super.onBackPressed();
        }

    }

    private void loadfregment(Fragment fragment, int flag) {
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (flag == 0){
            ft.add(R.id.container,fragment);
        }else{
            ft.replace(R.id.container,fragment);

        }
        ft.addToBackStack(null);
        ft.commit();

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.container);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}