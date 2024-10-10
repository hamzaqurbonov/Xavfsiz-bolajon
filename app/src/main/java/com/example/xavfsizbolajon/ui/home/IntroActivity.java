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

import com.example.xavfsizbolajon.MainActivity;
import com.example.xavfsizbolajon.R;

public class IntroActivity extends AppCompatActivity {

//    private static final String PREFS_NAME = "MyPrefsFile";
//    private static final String FIRST_TIME_KEY = "firstTime";
    Bundle doc = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);

        findViewById(R.id.old_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                doc.putString("old_id", "2");
                intent.putExtras(doc);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.old_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                doc.putString("old_id", "3");
                intent.putExtras(doc);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.old_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                doc.putString("old_id", "4");
                intent.putExtras(doc);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.old_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                doc.putString("old_id", "5");
                intent.putExtras(doc);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


    }
}
