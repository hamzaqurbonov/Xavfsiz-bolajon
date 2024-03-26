package com.example.xavfsizbolajon.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.xavfsizbolajon.MainActivity;
import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.databinding.FragmentHomeBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.util.Objects;

public class HomeFragment extends Fragment  {

    private FragmentHomeBinding binding;
    YouTubePlayerView youTubePlayerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  static ArrayList<String> nextArrayList = new ArrayList<>();
    DatabaseReference myRef;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view1);
        initYouTubePlayerView();

        db.collection("main").document("short").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
                        nextArrayList = (ArrayList<String>) document.get("nature");

                        Log.d("demo21", String.valueOf(nextArrayList));
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



//        final ArrayAdapter<String> myArrayAdaptrer = new ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,nextArrayList);
//
//        myRef = FirebaseDatabase.getInstance().getReference();
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                String value =  snapshot.getValue(String.class);
//                nextArrayList.add(value);
//                myArrayAdaptrer.notifyDataSetChanged();
//
////                Log.d("demo21", String.valueOf(nextArrayList));
//            }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                myArrayAdaptrer.notifyDataSetChanged();
//            }
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        return view;
    }



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayerView.matchParent();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayerView.wrapContent();
        }
    }

    private void initYouTubePlayerView() {
        getLifecycle().addObserver(youTubePlayerView);
        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.layout_panel);

            YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                    CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(requireContext(), customPlayerUi, youTubePlayer, youTubePlayerView);
                    youTubePlayer.addListener(customPlayerUiController);
                    setPlayNextVideoButtonClickListener(youTubePlayer);
                    YouTubePlayerUtils.loadOrCueVideo(
                            youTubePlayer, getLifecycle(),
                            NextModel.getNextVideoId(), 0f
                    );
//                    "FbjyUl0qqZc"
                }
            };


            IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
            youTubePlayerView.initialize(listener, options);
        }

    private void setPlayNextVideoButtonClickListener(final YouTubePlayer youTubePlayer) {

        LinearLayout previousVideoLinner = getView().findViewById(R.id.previous_video_linner);
        LinearLayout nextVideoLinner = getView().findViewById(R.id.next_video_linner);

        previousVideoLinner.setOnClickListener(view ->

                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        PreviousModel.getPreviousVideoId(),
                        0f
                )

        );
        nextVideoLinner.setOnClickListener(view ->


                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer,
                        getLifecycle(),
                        NextModel.getNextVideoId(),
                        0f
                )

        );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}