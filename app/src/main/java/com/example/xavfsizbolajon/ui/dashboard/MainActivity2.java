package com.example.xavfsizbolajon.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.databinding.FragmentDashboardBinding;
import com.example.xavfsizbolajon.ui.home.CustomPlayerUiController;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private Activity2Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteDB = db.document("main/short");
    private CollectionReference hadRef = db.collection("Notebook2");
    private RecyclerView recyclerView;
    YouTubePlayerView youTubePlayerView;
    TextView nameTextView;
    static List<String> activityllist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        nameTextView = findViewById(R.id.nameTextView);
        youTubePlayerView = findViewById(R.id.youtube_player_view2);
        initYouTubePlayerView();


        String model = getIntent().getExtras().getString("id");
        db.collection("Notebook").document(model).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
                        activityllist = (List<String>) document.get("tagm");


                    }
                }
            }

        });
        nameTextView.setText(model);

        initViews();
        refreshAdapter(activityllist);

    }

    private void initViews() {
        recyclerView = findViewById(R.id.activity2RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity2.this, 3));

    }


    private void refreshAdapter(List<String> activityllist) {

        Activity2Adapter adapter = new Activity2Adapter(MainActivity2.this, activityllist);
        recyclerView.setAdapter(adapter);
    }

    public void initYouTubePlayerView() {
        getLifecycle().addObserver(youTubePlayerView);
        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.long_panel);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                LongCustomPlayerUiController customPlayerUiController = new LongCustomPlayerUiController(MainActivity2.this, customPlayerUi, youTubePlayer, youTubePlayerView);
                youTubePlayer.addListener(customPlayerUiController);
//                setPlayNextVideoButtonClickListener(youTubePlayer);
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        setText(getIntent().getExtras().getString("idUrl")),
                        0f
                );
//                Log.d("demo17", getSetText().toString());
            }
        };
        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }

    private String setText(String kurbanov) {
        return kurbanov;
    }
}
