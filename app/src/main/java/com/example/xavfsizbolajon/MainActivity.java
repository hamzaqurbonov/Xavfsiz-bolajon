package com.example.xavfsizbolajon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.xavfsizbolajon.ui.dashboard.DashboardFragment;
import com.example.xavfsizbolajon.ui.home.HomeFragment;
import com.example.xavfsizbolajon.ui.home.IntroActivity;
import com.example.xavfsizbolajon.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_TIME_KEY = "firstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean firstTime = settings.getBoolean(FIRST_TIME_KEY, true);

        if (firstTime) {
            // Агар биринчи марта очилган бўлса, интро ёки маълумот экранини кўрсатиш
            Intent intent = new Intent(this, IntroActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Агар аввал очилган бўлса, тўғридан-тўғри асосий экранга ўтиш
            setContentView(R.layout.activity_main);
            BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();

        }

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.nav_main) {
            selectedFragment = new HomeFragment();

        } else if (itemId == R.id.nav_live) {
            selectedFragment = new DashboardFragment();

        } else if (itemId == R.id.nav_favorite) {
            selectedFragment = new NotificationsFragment();

        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
        }
        return true;
    };

}