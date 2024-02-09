package com.example.xavfsizbolajon.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xavfsizbolajon.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class LongAdapter extends FirestoreRecyclerAdapter<LongModel, LongAdapter.LongHolder> {
    private final FirestoreRecyclerOptions<LongModel> options;
    private OnItemClickListner listner;
    public LongAdapter(FirestoreRecyclerOptions<LongModel> options) {
        super(options);
        this.options=options;
    }

    @NonNull
    @Override
    public LongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash,
                parent, false);
        return new LongHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull LongHolder holder, int position, @NonNull LongModel noteModel) {

        holder.Url.setText(noteModel.getTitle());
        holder.videoName.setText(String.valueOf(noteModel.getTitle()));
    }

    public void  deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class LongHolder extends RecyclerView.ViewHolder {
        TextView Url, videoName;
        public LongHolder(View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.first_name);
            Url = itemView.findViewById(R.id.first_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listner != null) {
                        listner.onItemClick(getSnapshots().getSnapshot(position), position );
                    }
                }
            });
        }

    }

    public interface OnItemClickListner {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setItemClickListner(OnItemClickListner listner){
        this.listner = listner;
    }
}