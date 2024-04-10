package com.example.xavfsizbolajon.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.databinding.FragmentHomeBinding;

import com.example.xavfsizbolajon.ui.dashboard.LongModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class HomeFragment extends Fragment  {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteDB = db.document("main/short");
    private CollectionReference hadRef = db.collection("Notebook");

    private FragmentHomeBinding binding;
    YouTubePlayerView youTubePlayerView;
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  static ArrayList<String> nextArrayList = new ArrayList<>();
    DatabaseReference myRef;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view1);
        initYouTubePlayerView();
        setUpRecyclerView();

        db.collection("main").document("short").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
//                        nextArrayList = (ArrayList<String>) document.get("nature");
                        Collections.shuffle( nextArrayList = (ArrayList<String>) document.get("nature"));
//
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

        db.collection("main").document("WorkshopParent").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        List<String> list = (ArrayList<String>) document.get("tagm");
//                        nextArrayList = (ArrayList<String>) document.get("nature");

//                        Log.d("demo24", String.valueOf(1));
//
                        Map<String, Object> map = document.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("workshopChild")) {
                                Log.d("demo24", entry.getValue().toString());
                               Log.d("demo24", entry.getKey().toString());
                                Log.d("demo24", entry.getKey().toString());
                            }
//                            if (entry.getKey().trim().equals("workshopChild")) {
//                                Log.d("demo24", entry.getValue().toString());
//                            }
                        }
                    }
                }
            }

        });


        return view;
    }


    private void setUpRecyclerView() {

        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<LongModel> options = new FirestoreRecyclerOptions.Builder<LongModel>().setQuery(query, LongModel.class).build();
//        Log.d("demo22", String.valueOf( query));
//        adapter = new LongAdapter(options);
//
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3  ));
//
//        recyclerView.setAdapter(adapter);
//
//
//        adapter.setItemClickListner(new LongAdapter.OnItemClickListner() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
//                String id = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
//                Toast.makeText(getActivity(),  position + path  + id , Toast.LENGTH_SHORT).show();

//                String chapterName = adapter.getItem(position).getTitle();
//                String getIdUrl = adapter.getItem(position).getIdUrl();
////                String getImageUrl = adapter.getItem(position).getImageUrl();
//                Intent intent = new Intent(getContext(), LongChildOne.class);
//                intent.putExtra("title", chapterName);
//                intent.putExtra("tag", getIdUrl);
//                intent.putExtra("id", id);
//                startActivity(intent);

//            }
//        });

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }




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