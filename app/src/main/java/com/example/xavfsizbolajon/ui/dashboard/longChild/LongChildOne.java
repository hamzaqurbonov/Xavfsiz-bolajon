package com.example.xavfsizbolajon.ui.dashboard.longChild;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.ui.dashboard.LongAdapter;
import com.example.xavfsizbolajon.ui.dashboard.LongCustomPlayerUiController;
import com.example.xavfsizbolajon.ui.dashboard.LongModel;
import com.example.xavfsizbolajon.ui.dashboard.MainActivity2;
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
import java.util.Map;

public class LongChildOne extends AppCompatActivity {

    private OneChildAdapter.RecyclerViewClickListner listner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    TextView nameText;
    String getName;
    String dokumentIdModel;
    String partId;

    User city;
    List<String> activityllist = new ArrayList<>();
    YouTubePlayerView youTubePlayerViewOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_child_one);


        dokumentIdModel = getIntent().getExtras().getString("dokumentId");
        getName = getIntent().getExtras().getString("getName");
        partId = getIntent().getExtras().getString("part");



//        db.collection("FullVideo").document(dokumentIdModel).get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//                if (document.exists()) {
//                    List<Map<String, Object>> users = (List<Map<String, Object>>) document.get("part");
//
////                    City city = document.toObject(City.class);
//
//                    List<User> users2 = document.toObject(UserDocument.class).users;
//
//
////                    Log.d("demo1", "UserModel getName " + users3);
//
//                }
//            }
//        });




        db.collection("FullVideo").document(dokumentIdModel).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
//                        activityllist = (List<String>) document.get("tagm");


                        ArrayList<String> arrayMapList = (ArrayList<String>) document.get("part");
                        for (Object transaction: arrayMapList) {
                            Map values = (Map)transaction;
                            activityllist.add((String) values.get("id"));
//                            city = arrayMapList.equals(User.class);



                        }
//                        Log.d("demo1", "UserModel getName " + city.getNomi());


//                        PartModel city = document.toObject(PartModel.class);

//                        Log.d("demo1", "PartModel getName " + transactions.getName());
//                        Log.d("demo1", "PartModel getPart " + transactions.getPart());
//                        Log.d("demo1", "PartModel getImg " + city.getImg());


                        initViews();
                        setOnClickListner();
                        refreshAdapter(activityllist);
//
//                        Map<String, Object> map = document.getData();
//                        for (Map.Entry<String, Object> entry : map.entrySet()) {
//                            if (entry.getKey().equals("idUrl")) {
//                                Log.d("demo22", entry.getValue().toString());
//                            }
//                            if (entry.getKey().equals("tagm")) {
//                                Log.d("demo22", entry.getValue().toString());
//                            }
//                        }
                    }
                }
            }

        });
        nameText = findViewById(R.id.nameTextView);
        nameText.setText(getName);

        youTubePlayerViewOne = findViewById(R.id.youtube_player_view_one);
        initYouTubePlayerViewOne();
    }

//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            youTubePlayerView.matchParent();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            youTubePlayerView.wrapContent();
//        }
//    }

    private void initViews() {
        recyclerView = findViewById(R.id.one_long_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(LongChildOne.this, 3));
//        Log.d("demo22", activityllist.toString());
    }


    private void refreshAdapter(List<String> activityllist) {

        OneChildAdapter adapter = new OneChildAdapter(this, activityllist, listner);
        recyclerView.setAdapter(adapter);
    }


    private void setOnClickListner() {

        listner = new OneChildAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(LongChildOne.this, LongChildTwo.class);
//                cueVideoInitYouTubePlayer();

//                Toast.makeText(LongChildOne.this, "ID " + activityllist.get(position), Toast.LENGTH_SHORT).show();
                Log.d("demo1", activityllist.get(position));
                intent.putExtra( "part",activityllist.get(position));

                intent.putExtra("dokumentId", dokumentIdModel);
                intent.putExtra("getName",  getName);
                startActivity(intent);
                finish();
            }

        };

    }



    public void initYouTubePlayerViewOne() {
        getLifecycle().addObserver(youTubePlayerViewOne);
        View customPlayerUi = youTubePlayerViewOne.inflateCustomPlayerUi(R.layout.long_panel);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                LongCustomPlayerUiController customPlayerUiController = new LongCustomPlayerUiController(LongChildOne.this, customPlayerUi, youTubePlayer, youTubePlayerViewOne);
                youTubePlayer.addListener(customPlayerUiController);
//                setPlayNextVideoButtonClickListener(youTubePlayer);
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        setText(partId),
                        0f
                );
//                Log.d("demo17", getSetText().toString());
            }
        };
        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerViewOne.initialize(listener, options);

    }

    private String setText(String string) {
        return string;
    }


//    public void cueVideoInitYouTubePlayer() {
//        getLifecycle().addObserver(youTubePlayerView);
//        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.long_panel);
//
//        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
//
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//
//                LongCustomPlayerUiController customPlayerUiController = new LongCustomPlayerUiController(LongChildOne.this, customPlayerUi, youTubePlayer, youTubePlayerView);
//                youTubePlayer.addListener(customPlayerUiController);
////                setPlayNextVideoButtonClickListener(youTubePlayer);
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer, getLifecycle(),
//                        "aa",
//                        0f
//                );
////                Log.d("demo17", getSetText().toString());
//            }
//        };
//        // disable web ui
//        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
//        youTubePlayerView.initialize(listener, options);
//
//    }


}