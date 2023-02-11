package com.example.visonofman;

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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    Toolbar toolbar;

    private  AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout =findViewById(R.id.dl);
        NavigationView navigationView  =findViewById( R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        HomeFragment fragment=new HomeFragment();
        loadfregment(fragment,0);


        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.secondFragment)
                .setOpenableLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.frame);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.d("devin",""+item.getItemId());
                switch (item.getItemId()){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.chapters:
                        HomeFragment fragment=new HomeFragment();
                        FragmentManager fm =getSupportFragmentManager();
                        FragmentTransaction ft =fm.beginTransaction();
                        ft.replace(R.id.frame,fragment);
                        ft.addToBackStack(null);
                        ft.commit();

                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fav_sloka:
                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.verseoftheday:
                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Chapter", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.dm_language:
                        Toast.makeText(MainActivity.this, "language", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


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
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                getFragmentManager().popBackStack();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void loadfregment(Fragment fragment, int flag) {
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (flag == 0){
            ft.add(R.id.frame,fragment);
        }else{
//            ft.replace(R.id.frame,fragment);
            ft.replace(R.id.frame,fragment);

        }
//        ft.addToBackStack(null);
        ft.commit();

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.frame);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}