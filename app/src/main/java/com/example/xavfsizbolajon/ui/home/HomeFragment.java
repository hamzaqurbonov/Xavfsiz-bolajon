package com.example.xavfsizbolajon.ui.home;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment  {

    private int currentVideoIndex = 0;
    GestureDetector gestureDetector;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer;
    FrameLayout frameLayout;

    private List<String> videoIds;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteDB = db.document("main/short");
    private CollectionReference hadRef = db.collection("Notebook");

    private FragmentHomeBinding binding;

//    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  static ArrayList<String> nextArrayList = new ArrayList<>();
    DatabaseReference myRef;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        frameLayout = view.findViewById(R.id.frame_layout);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);




        videoIds = new ArrayList<>();
        videoIds.add("NwDCIg2JXxY"); // биринчи видео ID
        videoIds.add("lXZzRJHd5ME"); // иккинчи видео ID
        videoIds.add("Cne9F0kQ-p0");
        videoIds.add("pMkOsVKT7jA");
        videoIds.add("ZIXUZnzt8e4");
        videoIds.add("CAORY3_3fDM");
        videoIds.add("NhWq3F73UIE");
        videoIds.add("jasOdSLBJo0");
        videoIds.add("xOgCfMT6XfY");
        videoIds.add("5qgwbHxe2PU");
        videoIds.add("7xhcz_8P1I4");
        videoIds.add("LiM8IXH5eWE");// учинчи видео ID
        gestureDetector();

//        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
//        initYouTubePlayerView();
//        setUpRecyclerView();

        db.collection("Shorts").orderBy("key").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ArrayList<String> arrayMapList = (ArrayList<String>) document.get("key");
                                for (Object transaction: arrayMapList) {
                                    Map values = (Map)transaction;
                                     nextArrayList.add((String) values.get("id"));
                                }
                            }
                            Collections.shuffle( nextArrayList);
//                            Log.d("demo1", "Map1 " + nextArrayList);
                        } else {
                            Log.d("demo1", "Error getting documents: ", task.getException());
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



    private void gestureDetector() {
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                HomeFragment.this.youTubePlayer = youTubePlayer;
                loadVideo(currentVideoIndex);
                Log.d("demo45", "youTubePlayerView: ready");

                // GestureDetector инициализацияси
                gestureDetector = new GestureDetector(requireActivity(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        Log.d("demo45", "onFling: ");
                        if (velocityY > 500) { // Pastga сурилганда
                            nextVideo();
                            Toast.makeText(requireActivity(), "Кейинги видео", Toast.LENGTH_SHORT).show();
                        } else if (velocityY < -500) { // Yuqoriga сурилганда
                            previousVideo();
                            Toast.makeText(requireActivity(), "Олдинги видео", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

                // onTouchListener ни тўғри қўллаш
                frameLayout.setOnTouchListener((v, event) -> {
                    Log.d("demo45", "Touch Event Detected: " + event.getAction()); // Текширув лог
                    gestureDetector.onTouchEvent(event);
                    return true; // Ҳаракатни қабул қилишини тасдиқлаш
                });
            }
        });

    }




    private void loadVideo(int index) {
        Log.d("demo45", "loadVideo: ");
        if (index >= 0 && index < videoIds.size()) {
            String videoId = videoIds.get(index);
            youTubePlayer.loadVideo(videoId, 0);
        }
    }

    private void nextVideo() {
        Log.d("demo45", "nextVideo: ");
        if (currentVideoIndex < videoIds.size() - 1) {
            currentVideoIndex++;
            loadVideo(currentVideoIndex);
        } else {
            Toast.makeText(getActivity(), "Барча видеолар кўрилди", Toast.LENGTH_SHORT).show();
        }
    }

    private void previousVideo() {
        Log.d("demo45", "previousVideo: ");
        if (currentVideoIndex > 0) {
            currentVideoIndex--;
            loadVideo(currentVideoIndex);
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        youTubePlayerView.release();
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }









//
//
//
//    private void setUpRecyclerView() {
//
//        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);
//
//        FirestoreRecyclerOptions<LongModel> options = new FirestoreRecyclerOptions.Builder<LongModel>().setQuery(query, LongModel.class).build();
//
//
//    }
//
//
//
//
//
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
//
//    private void initYouTubePlayerView() {
//        getLifecycle().addObserver(youTubePlayerView);
//        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.layout_panel);
//
//            YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
//                @Override
//                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//
//                    CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(requireContext(), customPlayerUi, youTubePlayer, youTubePlayerView);
//                    youTubePlayer.addListener(customPlayerUiController);
//                    setPlayNextVideoButtonClickListener(youTubePlayer);
//                    YouTubePlayerUtils.loadOrCueVideo(
//                            youTubePlayer, getLifecycle(),
//                            NextModel.getNextVideoId(), 0f
//                    );
//                }
//            };
//
//            IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
//            youTubePlayerView.initialize(listener, options);
//        }
//
//    private void setPlayNextVideoButtonClickListener(final YouTubePlayer youTubePlayer) {
//
//        LinearLayout previousVideoLinner = getView().findViewById(R.id.previous_video_linner);
//        LinearLayout nextVideoLinner = getView().findViewById(R.id.next_video_linner);
//
//        previousVideoLinner.setOnClickListener(view ->
//
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer,
//                        getLifecycle(),
//                        PreviousModel.getPreviousVideoId(),
//                        0f
//                )
//
//        );
//        nextVideoLinner.setOnClickListener(view ->
//
//
//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer,
//                        getLifecycle(),
//                        NextModel.getNextVideoId(),
//                        0f
//                )
//
//        );
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }


}