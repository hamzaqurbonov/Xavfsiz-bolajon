package com.example.xavfsizbolajon.ui.notifications;

import android.content.Context;
import android.util.SparseArray;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private ArrayList<NotificationsViewModel> modalArrayList;
    private Context context;
    private OnItemClickListener listener;
    private SparseArray<YouTubePlayer> youTubePlayerArray = new SparseArray<>();
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

//        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer)  {
//
//
//                youTubePlayer.cueVideo(viewModel.getVieoId(), 0);
////                Log.d("demo20", String.valueOf(1));
////
//            }
//
//        });

        if (!holder.isPlayerInitialized) {
            holder.youTubePlayer_item.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    // Videoni cueVideo yordamida tayyorlab qo'yamiz, avtomatik o'ynatmaslik uchun
                    youTubePlayer.cueVideo(viewModel.getVieoId(), 0);
                    youTubePlayerArray.put(position, youTubePlayer);
                }
            }, true, new IFramePlayerOptions.Builder().controls(0).build());
            holder.isPlayerInitialized = true; // Инициализация қилинди деб белгиланади/
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });


        holder.deleteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbFavorite.deleteSelect(Integer.toString(viewModel.getId()));

                Toast.makeText(v.getContext(), "Text deleted!", Toast.LENGTH_SHORT).show();
                Refresh(dbFavorite.readCourses());
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
        YouTubePlayerView youTubePlayer_item;
        boolean isPlayerInitialized = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            getVideoName = itemView.findViewById(R.id.textLikeIdTraslate);
            getVieoId = itemView.findViewById(R.id.textLikeIdWord);
            deleteSelect = itemView.findViewById(R.id.delete_select);
            youTubePlayer_item = itemView.findViewById(R.id.youtube_player);

            ((FragmentActivity) itemView.getContext()).getLifecycle().addObserver(youTubePlayer_item);
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