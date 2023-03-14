package com.example.visonofman.Activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.visonofman.DisplayFragment;
import com.example.visonofman.R;
import com.example.visonofman.VerseListFragment;

import java.util.Objects;

public class VerseActivity extends AppCompatActivity {
    int id;
    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verse);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.textColor), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = Integer.parseInt(bundle.getString("chapter"));

        }
        Log.d("devin","String id in verse activity"+id);

        showFragment(new VerseListFragment(VerseActivity.this,id),0);


        DisplayFragment displayFragment =(DisplayFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment);
        if (displayFragment != null && displayFragment.isVisible()) {

        } else {

        }

    }
    private void showFragment(Fragment fragment, int flag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_favorite:
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
                if (fragment != null && fragment.isVisible()) {
                    return fragment.onOptionsItemSelected(item);
                }
//                Toast.makeText(this, "action_favorite clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof DisplayFragment) {
            // Set the title and other properties of the ActionBar for the second fragment
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            // Reset the title and other properties of the ActionBar for other fragments
            SpannableString s = new SpannableString("List Of Slokas");
            s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.textColor)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Objects.requireNonNull(getSupportActionBar()).setTitle(s);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
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
}