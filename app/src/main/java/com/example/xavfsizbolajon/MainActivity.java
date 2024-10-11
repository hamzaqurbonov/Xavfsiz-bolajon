package com.example.xavfsizbolajon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.xavfsizbolajon.ui.dashboard.DashboardFragment;
import com.example.xavfsizbolajon.ui.home.HomeFragment;
import com.example.xavfsizbolajon.ui.home.IntroActivity;
import com.example.xavfsizbolajon.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    Bundle doc = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        findViewById(R.id.old_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                doc.putString("old_id", "2");
                intent.putExtras(doc);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.old_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                doc.putString("old_id", "3");
                intent.putExtras(doc);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.old_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                doc.putString("old_id", "4");
                intent.putExtras(doc);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.old_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                doc.putString("old_id", "5");
                intent.putExtras(doc);
                startActivity(intent);
                finish();
            }
        });





    }
}



//    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
//
//        Fragment selectedFragment = null;
//        int itemId = item.getItemId();
//        if (itemId == R.id.nav_main) {
//            selectedFragment = new HomeFragment();
//        } else if (itemId == R.id.nav_live) {
//            selectedFragment = new DashboardFragment();
//        } else if (itemId == R.id.nav_favorite) {
//            selectedFragment = new NotificationsFragment();
//        }
//        if (selectedFragment != null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
//        }
//        return true;
//    };

