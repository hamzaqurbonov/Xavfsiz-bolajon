package com.example.xavfsizbolajon.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.xavfsizbolajon.MainActivity;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;




import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment  {
    private SeekBar customSeekBar;
    private float videoDuration;  // Видео умумий давомийлиги
    View view;
    private boolean isGoingForward = true; // Кейингига ўтиш ёки орқага қайтиш ҳолатини аниқлаш
    private int currentVideoIndex = 0;
    GestureDetector gestureDetector;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer;
    FrameLayout frameLayout;
    FragmentHomeBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  static ArrayList<String> nextArrayList = new ArrayList<>();

    ImageView play, stop, old ;
    View customOverlay;
    String oldID;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        customOverlay = view.findViewById(R.id.customOverlay);
        frameLayout = view.findViewById(R.id.frame_layout);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        customSeekBar = view.findViewById(R.id.customSeekBar);
        play = view.findViewById(R.id.play);
        stop = view.findViewById(R.id.stop);
        old = view.findViewById(R.id.old);
        getLifecycle().addObserver(youTubePlayerView);

        Bundle bundle = getArguments();
        if (bundle != null) {
            oldID = bundle.getString("old_id");
            // Олган қийматдан фойдаланиш
            Log.d("demo47", "Fragment ID: " + oldID);
        }

        youTubePlayer();
        nextArrayList();
        play();
        stop();
        Old();

        return view;
    }
    private void Old() {
        old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                nextArrayList.clear();
                Log.d("demo47", "nextArrayList ID: " + nextArrayList);
            }
        });
    }


    private void nextArrayList() {
        // Birinchi holati ishlaydi Arreyni shakillantirib beradi

//        db.collection("Shorts").orderBy("key").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//
//                        ArrayList<String> arrayMapList = (ArrayList<String>) document.get("key");
//                        for (Object transaction: arrayMapList) {
//                            Map values = (Map)transaction;
//                            nextArrayList.add((String) values.get("id"));
//                        }
//
//
//                    }
//                    Log.d("demo47", "Fragment ID: " + nextArrayList);
//                    Collections.shuffle( nextArrayList);
//                } else {
//                }
//            }
//        });

        db.collection("Shorts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // "key" массивини оламиз
                                List<Map<String, Object>> keys = (List<Map<String, Object>>) document.get("key");

                                // Массив ичидаги youngNumber бўйича фильтрлаш
                                for (Map<String, Object> key : keys) {
//                                    long youngNumber = (long) key.get("youngNumber"); // youngNumberни оламиз

//                                    if (youngNumber ==  2) { // youngNumber 0 дан катта бўлса
//                                        Log.d("demo47", "Found: " + key.get("id")); // Мос келганларини логга чиқарамиз
//                                    }
                                    Object youngNumberObj = key.get("youngNumber");
                                    if (youngNumberObj != null && !youngNumberObj.toString().isEmpty()) {
                                        try {
                                            // Агар youngNumber String бўлса, уни long га айлантирамиз
                                            long youngNumber = Long.parseLong(youngNumberObj.toString());

                                            if (youngNumber == Integer.parseInt(oldID)) {

                                                nextArrayList.add((String) key.get("id"));
                                                Log.d("demo47", "Found: " + key.get("id"));
                                            }
                                        } catch (NumberFormatException e) {
//                                            Log.e("demo47", "Маълумотни long га айлантиришда хато: " + youngNumberObj.toString());
                                        }
                                    } else {
                                        Log.w("demo47", "youngNumber null ёки бўш, текширинг: " + key.toString());
                                    }


                                }
                            }
                            Collections.shuffle( nextArrayList);
                        } else {
                            Log.w("demo47", "Маълумот олишда хато юз берди.", task.getException());
                        }
                    }
                });


    }


    private void youTubePlayer() {
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.layout_panel);


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                HomeFragment.this.youTubePlayer = youTubePlayer;
                loadVideo(currentVideoIndex);

                customSeekBar.setMax(100);
                // Видео умумий давомийлигини оламиз
                youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onVideoDuration(YouTubePlayer youTubePlayer, float duration) {
                        videoDuration = duration;
                    }

                    @Override
                    public void onCurrentSecond(YouTubePlayer youTubePlayer, float second) {
                        // Видео пайтига қараб SeekBar'ни янгилаймиз
                        if (videoDuration > 0) {
                            customSeekBar.setProgress((int) (second / videoDuration * 100));
                        }
                    }
                });

                // SeekBar'га ўзгартириш киритилганда видеонинг вақтини ўзгартириш
                customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            // Фойдаланувчи қўлда видеонинг вақтига ўтишда SeekBar'ни янгилайди
                            float seekTo = (progress / 100f) * videoDuration;
                            youTubePlayer.seekTo(seekTo);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Ҳеч нарса қўшилмайди
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Ҳеч нарса қўшилмайди
                    }
                });

//                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(requireContext(), customPlayerUi, youTubePlayer, youTubePlayerView);
//                youTubePlayer.addListener(customPlayerUiController);
                // GestureDetector инициализацияси
                gestureDetector = new GestureDetector(requireActivity(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        Log.d("demo45", "onFling: ");
                        if (velocityY > 500) { // Pastga сурилганда
                            previousVideo();
                            Toast.makeText(requireActivity(), "Кейинги видео", Toast.LENGTH_SHORT).show();
                        } else if (velocityY < -500) { // Yuqoriga сурилганда
                            nextVideo();
                            Toast.makeText(requireActivity(), "Олдинги видео", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

                // onTouchListener ни тўғри қўллаш

                frameLayout.setOnTouchListener((v, event) -> {
                    gestureDetector.onTouchEvent(event);
                    return true; // Ҳаракатни қабул қилишини тасдиқлаш
                });

            }

            @Override
            public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
                // Агар хато юз берса, кейинги видеога ўтиш
                if (error == PlayerConstants.PlayerError.VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER) {
                    Toast.makeText(requireActivity(), "Видео ўйнаш мумкин эмас, кейинги видео ўтказилмоқда", Toast.LENGTH_SHORT).show();
                    if (isGoingForward) {
                        // Агар кейинги видеога ўтиш вақтида хато бўлса
                        nextVideo();
                    } else {
                        // Агар орқага қайтиш вақтида хато бўлса
                        previousVideo();
                    }
                }
            }

            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                if (state == PlayerConstants.PlayerState.ENDED) {
                    // Видео тугаганда қайта ўйнатиш
                    playVideoAtSelection();
                }
//                if (state.equals(PlayerConstants.PlayerState.PAUSED)) {
//                    // Паузада overlay ёки маска кўрсатиш
//                    Log.d("demo45", "customOverlay VISIBLE: ");
//                    customOverlay.setVisibility(View.VISIBLE);
//                } else if (state.equals(PlayerConstants.PlayerState.PLAYING)) {
//                    // Видео ўйнаб турганда overlay ёки маскани олиб ташлаш
//                    Log.d("demo45", "customOverlay GONE:: ");
//                    customOverlay.setVisibility(View.GONE);
//                }

            }


        });



    }

    private void play() {
      play.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              stop.setVisibility(View.VISIBLE);
              play.setVisibility(View.GONE);
              youTubePlayer.play();
          }
      });
    }
    private void stop() {
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                youTubePlayer.pause(); // Пауза қўйилади
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (youTubePlayer != null && currentVideoIndex >= 0) {
            youTubePlayer.play(); // Иловадан қайтиб кирганингизда видеони ўйнашни бошлаш
        }
    }

    private void playVideoAtSelection() {
        if (!(youTubePlayer == null)) {
            youTubePlayer.play();
        }
    }

    private void loadVideo(int index) {
        if (index >= 0 && index < nextArrayList.size()) {
            String videoId = nextArrayList.get(index);
            youTubePlayer.loadVideo(videoId, 0);
            Log.d("demo45", "videoId: " + videoId + " " + nextArrayList );
        }
    }
    private void nextVideo() {
        isGoingForward = true;
        if (currentVideoIndex < nextArrayList.size() - 1) {
            currentVideoIndex++;
            loadVideo(currentVideoIndex);
        } else {
            Toast.makeText(getActivity(), "Барча видеолар кўрилди", Toast.LENGTH_SHORT).show();
        }
    }

    private void previousVideo() {
        isGoingForward = false;
        if (currentVideoIndex > 0) {
            currentVideoIndex--;
            loadVideo(currentVideoIndex);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}



//        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
//        initYouTubePlayerView();
//        setUpRecyclerView();




//        db.collection("main").document("WorkshopParent").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
////                        List<String> list = (ArrayList<String>) document.get("tagm");
////                        nextArrayList = (ArrayList<String>) document.get("nature");
//
////                        Log.d("demo24", String.valueOf(1));
////
//                        Map<String, Object> map = document.getData();
//                        for (Map.Entry<String, Object> entry : map.entrySet()) {
//                            if (entry.getKey().equals("workshopChild")) {
//                                Log.d("demo24", entry.getValue().toString());
//                               Log.d("demo24", entry.getKey().toString());
//                                Log.d("demo24", entry.getKey().toString());
//                            }
////                            if (entry.getKey().trim().equals("workshopChild")) {
////                                Log.d("demo24", entry.getValue().toString());
////                            }
//                        }
//                    }
//                }
//            }
//
//        });


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


