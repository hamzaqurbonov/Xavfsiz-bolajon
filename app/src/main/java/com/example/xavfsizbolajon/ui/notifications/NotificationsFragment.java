package com.example.xavfsizbolajon.ui.notifications;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xavfsizbolajon.R;

import com.example.xavfsizbolajon.databinding.FragmentHomeBinding;
import com.example.xavfsizbolajon.databinding.FragmentNotificationsBinding;
import com.example.xavfsizbolajon.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {



    EditText edit_short_id;
    Button add_button;
    String DocName, youngNumber, videoId, url;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer; // Бу ерда YouTubePlayer объектини сақлаймиз
    public static ArrayList<String> nextArrayList = new ArrayList<>();

    private ArrayList<NotificationsViewModel> modalArrayList;
    DbFavorite dbFavorite;
    RecyclerView recyclerView;
    FragmentNotificationsBinding binding;

    NotificationsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        dbFavorite = new DbFavorite(getActivity());
        recyclerView = view.findViewById(R.id.recyclerViewNotic);
        add_button = view.findViewById(R.id.add_button);
        edit_short_id = view.findViewById(R.id.edit_short_id);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


        AddButton();
        recyclerView();
        ItemClickVideo();
        return view;
    }

    private void recyclerView() {
        modalArrayList = new ArrayList<>();
        modalArrayList = dbFavorite.readCourses(); // SQLite маълумотларини ўқиш
        adapter = new NotificationsAdapter(modalArrayList, getActivity()); // Адаптерга тайинлаш

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        ItemClickVideo();
    }


    public void AddButton() {
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Киритилаётган Видео линки
                url = edit_short_id.getText().toString();

                // Линкни аниқлаш олиш учун
                String url_app_longV = "https://youtu.be/";
                String url_app_shorts = "https://youtube.com/shorts/";
                String url_pk_longV = "https://www.youtube.com/watch?v=";
                String url_pk_shorts = "https://www.youtube.com/shorts/";

                // Линкни филтирлаш учун
                if (url.startsWith(url_app_longV)) {
                    videoId = url.substring(url.indexOf("/youtu.be/") + 10, url.indexOf("?"));
                } else if (url.startsWith(url_app_shorts)) {
                    videoId = url.substring(url.indexOf("/shorts/") + 8, url.indexOf("?"));
                } else if (url.startsWith(url_pk_longV)) {

                    videoId = url.substring(url.indexOf("v=") + 2, url.indexOf("&") == -1 ? url.length() : url.indexOf("&"));
                    if (url.length() == url.indexOf("v=") + 2 + videoId.length()) {
                        // IDдан кейин бошқа белгилар йўқ
                        videoId = url.substring(url.indexOf("v=") + 2);
                    } else {
                        // IDдан кейин белгилар мавжуд
                        videoId = url.substring(url.indexOf("v=") + 2, url.indexOf("&"));
                    }
                } else if (url.startsWith(url_pk_shorts)) {
                    videoId = url.substring(url.lastIndexOf("/") + 1);
                } else {
                    Toast.makeText(getActivity(), "Youtube linkini to'lliq kiriting!", Toast.LENGTH_SHORT).show();
                    return; // Функцияни тугатади
                }

                dbFavorite.addNewCourse(videoId, edit_short_id.getText().toString());
                recyclerView();
                edit_short_id.setText("");


                Toast.makeText(getActivity(), "Видео сақланди " + DocName, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void ItemClickVideo() {
        adapter.setOnItemClickListener(new NotificationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NotificationsViewModel model = modalArrayList.get(position);
                String getVieoId = model.getVieoId();
                String getVideoName = model.getVideoName();

                // YouTube видеони юклаш
                if (youTubePlayer != null) {
                    // YouTubePlayer аллақачон инициализация қилинган, фақат видеони юклаймиз
                    youTubePlayer.loadVideo(getVieoId, 0f);
                } else {
                    // YouTubePlayer'ни инициализация қилиш
                    YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer initializedYouTubePlayer) {
                            youTubePlayer = initializedYouTubePlayer; // YouTubePlayer объектини сақлаб қўямиз
                            youTubePlayer.loadVideo(getVieoId, 0f);
                        }
                    };

                    IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
                    youTubePlayerView.initialize(listener, options);
                }

                Toast.makeText(getActivity(), "Видео2 " + getVieoId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}



//                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//
//                    @SuppressLint("ClickableViewAccessibility")
//                    @Override
//                    public void onReady(YouTubePlayer youTubePlayer) {
////                        NotificationsFragment.this.youTubePlayer = youTubePlayer;
////                        loadVideo(currentVideoIndex);
//                        youTubePlayer.loadVideo(getVieoId, 0);
//
//
//
//                        Toast.makeText(getActivity(), "Видео " + getVieoId + " " + getVideoName, Toast.LENGTH_SHORT).show();
//                    }
//                    });


//                Toast.makeText(getActivity(), "Видео2 " + getVieoId , Toast.LENGTH_SHORT).show();





//    @Override
//    public void onResume() {
//        super.onResume();
//        if (youTubePlayer != null && currentVideoIndex >= 0) {
//            youTubePlayer.play(); // Иловадан қайтиб кирганингизда видеони ўйнашни бошлаш
//        }
//    }
//
//    private void playVideoAtSelection() {
//        if (!(youTubePlayer == null)) {
//            youTubePlayer.play();
//        }
//    }
//
//    private void loadVideo(int index) {
//        if (index >= 0 && index < nextArrayList.size()) {
//            String videoId = nextArrayList.get(index);
//            youTubePlayer.loadVideo(videoId, 0);
//            Log.d("demo45", "videoId: " + videoId + " " + nextArrayList );
//        }
//    }
//    private void nextVideo() {
//        isGoingForward = true;
//        if (currentVideoIndex < nextArrayList.size() - 1) {
//            currentVideoIndex++;
//            loadVideo(currentVideoIndex);
//        } else {
//            Toast.makeText(getActivity(), "Барча видеолар кўрилди", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void previousVideo() {
//        isGoingForward = false;
//        if (currentVideoIndex > 0) {
//            currentVideoIndex--;
//            loadVideo(currentVideoIndex);
//        }
//    }
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }


//}