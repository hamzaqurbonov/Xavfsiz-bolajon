package com.example.xavfsizbolajon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.xavfsizbolajon.ui.dashboard.DashboardFragment;
import com.example.xavfsizbolajon.ui.home.HomeFragment;
import com.example.xavfsizbolajon.ui.home.IntroActivity;
import com.example.xavfsizbolajon.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    String oldID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        if (getIntent().getExtras() != null) {
//            getIntent().removeExtra("old_id");
//            getIntent().replaceExtras(new Bundle());  // Мавжуд Bundle ни янги бўш Bundle билан алмаштириш
//            Log.d("demo47", "onCreate: getIntent() " + getIntent());
//        }

        Bundle extras = getIntent().getExtras();
        Log.d("demo47", "onCreate: extras " + extras);
        if (extras != null) {
            oldID = extras.getString("old_id");
            Bundle doc = new Bundle();
            doc.putString("old_id", oldID);

            Fragment myFragment = new HomeFragment();
            myFragment.setArguments(doc);  // Fragment'га маълумотни юбориш
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.body_container, myFragment)
            .commit();


            Log.d("demo47", "onCreate: if " + oldID);
            setContentView(R.layout.activity_main);
            BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();



        } else  {
            Intent intent = new Intent(this, IntroActivity.class);
//            Bundle doc = new Bundle();
//            doc.putString("old_id", oldID);

            startActivity(intent);
            Log.d("demo47", "onCreate: else " + oldID);
        }



    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.nav_main) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_live) {
            selectedFragment = new DashboardFragment();
        } else if (itemId == R.id.nav_favorite) {
            selectedFragment = new NotificationsFragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
        }
        return true;
    };

}