package com.example.xavfsizbolajon.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xavfsizbolajon.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private ArrayList<NotificationsViewModel> modalArrayList;
    private Context context;
    private OnItemClickListener listener;
    DbFavorite dbFavorite;

    public NotificationsAdapter(ArrayList<NotificationsViewModel> modalArrayList, Context context) {
        this.modalArrayList = modalArrayList;
        this.context = context;
        dbFavorite = new DbFavorite(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationsViewModel viewModel  = modalArrayList.get(position);
        holder.getVieoId.setText(viewModel.getVieoId());
        holder.getVideoName.setText(viewModel.getVideoName());

        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer)  {


                youTubePlayer.cueVideo(viewModel.getVieoId(), 0);
//                Log.d("demo20", String.valueOf(1));
//
            }

        });

        if (!holder.isPlayerInitialized) {
            holder.youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    // Videoni cueVideo yordamida tayyorlab qo'yamiz, avtomatik o'ynatmaslik uchun
                    youTubePlayer.cueVideo(viewModel.getVieoId(), 0);
                }
            });
            holder.isPlayerInitialized = true; // Инициализация қилинди деб белгиланади
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });

    }


    void Refresh(ArrayList<NotificationsViewModel> events) {
        modalArrayList.clear();
        modalArrayList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return modalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView getVideoName, getVieoId ;
        ImageView deleteSelect;
        YouTubePlayerView youTubePlayerView;
        boolean isPlayerInitialized = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            getVideoName = itemView.findViewById(R.id.textLikeIdTraslate);
            getVieoId = itemView.findViewById(R.id.textLikeIdWord);
            deleteSelect = itemView.findViewById(R.id.delete_select);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player);

            ((FragmentActivity) itemView.getContext()).getLifecycle().addObserver(youTubePlayerView);
        }
    }

    // Fragmentга click қилиш
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}