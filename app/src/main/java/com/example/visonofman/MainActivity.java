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

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView ;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout =findViewById(R.id.dl);
        navigationView =findViewById( R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        HomeFragment fragment=new HomeFragment();
        loadfregment(fragment,0);


        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id= item.getItemId();
                switch (id){
                    case R.id.home:
                        Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.chapters:
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



//                Log.d("devin", String.valueOf(id));
//                if (id==R.id.home){
//                    Toast.makeText(getApplication(), "Your message", Toast.LENGTH_SHORT).show();
//                    Log.d("devin","home");
//                }else if (id == R.id.chapters){
//                    Log.d("devin","chapters");
//                } else if (id == R.id.fav_sloka) {
//                    Log.d("devin","fav_sloka");
//                } else if (id == R.id.verseoftheday) {
//                    Log.d("devin","verseoftheday");
//                } else if (id == R.id.logout) {
//                    Log.d("devin","logout");
//                } else if (id == R.id.dm_language) {
//                    Log.d("devin","dm_language");
//                } else if (id == R.id.setting) {
//                    Log.d("devin","setting");
//                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


//        NavigationView navigationView = findViewById(R.id.navigationview);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.home:
//                        Toast.makeText(MainActivity.this, "Item 1 selected", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.chapters:
//                        Toast.makeText(MainActivity.this, "Item 2 selected", Toast.LENGTH_SHORT).show();
//                        break;
//
//                }
//                return true;
//            }
//        });

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadfregment(Fragment fragment, int flag) {
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        if (flag == 0){
            ft.add(R.id.frame,fragment);
        }else{
//            ft.replace(R.id.frame,fragment);
            ft.replace(R.id.frame,fragment);

        }
        ft.addToBackStack(null);
        ft.commit();

    }
}