package com.example.xavfsizbolajon.ui.dashboard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xavfsizbolajon.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MainActivity2 extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    TextView nameTextView;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        nameTextView = findViewById(R.id.nameTextView);

        nameTextView.setText(getIntent().getExtras().getString("idUrl"));

        youTubePlayerView = findViewById(R.id.youtube_player_view2);
        initYouTubePlayerView();
    }





    public void initYouTubePlayerView() {
        getLifecycle().addObserver(youTubePlayerView);
//        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.layout_panel);

        YouTubePlayerListener listener = new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

//                CustomPlayerUiController customPlayerUiController = new CustomPlayerUiController(requireContext(), customPlayerUi, youTubePlayer, youTubePlayerView);
//                youTubePlayer.addListener(customPlayerUiController);
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
