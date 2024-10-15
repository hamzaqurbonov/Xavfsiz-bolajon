package com.example.xavfsizbolajon.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.xavfsizbolajon.MainActivity;
import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.ui.dashboard.DashboardFragment;
import com.example.xavfsizbolajon.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        Bundle extras = getIntent().getExtras();
        String  oldID = extras.getString("old_id");
        Bundle doc = new Bundle();
        doc.putString("old_id", oldID);

        Fragment myFragment = new HomeFragment();
        myFragment.setArguments(doc);  // Fragment'га маълумотни юбориш
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.body_container, myFragment)
                .commit();

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
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
        } else if (itemId == R.id.nav_old) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
        }
        return true;
    };
}
