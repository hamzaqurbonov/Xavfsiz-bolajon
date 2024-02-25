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
import com.example.xavfsizbolajon.ui.dashboard.LongCustomPlayerUiController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class LongChildTwo extends AppCompatActivity {

    private TwoChildAdapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    TextView nameText3;
    List<String> activityllist = new ArrayList<>();
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_child_two);

        nameText3 = findViewById(R.id.nameText3);

        String model = getIntent().getExtras().getString("id");
        String tag = getIntent().getExtras().getString("tag");

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

        nameText3.setText(tag);
        youTubePlayerView = findViewById(R.id.youtube_player_view2);
        initYouTubePlayerView();
    }


    private void initViews() {
        recyclerView = findViewById(R.id.two_long_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(LongChildTwo.this, 3));
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
                intent.putExtra( "tag",activityllist.get(position));
                intent.putExtra("id", getIntent().getExtras().getString("id"));
                startActivity(intent);
            }

        };

    }

    public void initYouTubePlayerView() {
        getLifecycle().addObserver(youTubePlayerView);
        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.long_panel);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                LongCustomPlayerUiController customPlayerUiController = new LongCustomPlayerUiController(LongChildTwo.this, customPlayerUi, youTubePlayer, youTubePlayerView);
                youTubePlayer.addListener(customPlayerUiController);
//                setPlayNextVideoButtonClickListener(youTubePlayer);
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        setText(getIntent().getExtras().getString("tag")),
                        0f
                );
//                Log.d("demo17", getSetText().toString());
            }
        };
        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }

    private String setText(String string) {
        return string;
    }


}