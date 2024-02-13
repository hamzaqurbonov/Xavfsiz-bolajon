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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MainActivity2 extends AppCompatActivity {

    private Activity2Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteDB = db.document("main/short");
    private CollectionReference hadRef = db.collection("Notebook");
    private RecyclerView recyclerView;
    private FragmentDashboardBinding binding;



    YouTubePlayerView youTubePlayerView;
    TextView nameTextView;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        nameTextView = findViewById(R.id.nameTextView);

        nameTextView.setText(getIntent().getExtras().getString("title"));
        youTubePlayerView = findViewById(R.id.youtube_player_view2);
        initYouTubePlayerView();

        recyclerView = findViewById(R.id.activity2RecyclerView);
        Activity2RecyclerView();
    }





    private void Activity2RecyclerView() {

        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Activity2Model> options2 = new FirestoreRecyclerOptions.Builder<Activity2Model>().setQuery(query, Activity2Model.class).build();

        adapter = new Activity2Adapter(options2);

//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3  ));
        recyclerView.setAdapter(adapter);

//        adapter.setItemClickListner(new LongAdapter.OnItemClickListner() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
//                String id = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
//                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();

//                String chapterName = adapter.getItem(position).getTitle();
//                String getIdUrl = adapter.getItem(position).getIdUrl();
//
//                Intent intent = new Intent(MainActivity2.this, DashboardFragment.class);
//                intent.putExtra("title", chapterName);
//                intent.putExtra("idUrl", getIdUrl);
//
//                startActivity(intent);

//            }
//        });



    }


    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void onStop() {
        super.onStop();
        adapter.stopListening();
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
