package com.example.xavfsizbolajon.ui.dashboard.longChild;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.xavfsizbolajon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LongChildTwo extends AppCompatActivity {

    private TwoChildAdapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    TextView nameText;
    List<String> activityllist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_child_two);
        String model = getIntent().getExtras().getString("id");

        db.collection("Notebook").document(model).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
                        activityllist = (List<String>) document.get("tagm");

                        initViews();
                        setOnClickListner();
                        refreshAdapter(activityllist);
//
//
                    }
                }
            }

        });

//        nameText.setText(model);

    }


    private void initViews() {
        recyclerView = findViewById(R.id.two_long_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(LongChildTwo.this, 1));
    }


    private void refreshAdapter(List<String> activityllist) {

        TwoChildAdapter adapter = new TwoChildAdapter(this, activityllist, listner);
        recyclerView.setAdapter(adapter);
    }


    private void setOnClickListner() {

        listner = new TwoChildAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(LongChildTwo.this, LongChildOne.class);
//                Toast.makeText(MainActivity2.this,  "MainActivity2", Toast.LENGTH_SHORT).show();
                intent.putExtra("id", getIntent().getExtras().getString("id"));
                startActivity(intent);
            }

        };

    }


}