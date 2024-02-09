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

public class NoteAdapter extends FirestoreRecyclerAdapter<LongModel, NoteAdapter.NoteHolder> {
    private final FirestoreRecyclerOptions<LongModel> options;

    private OnItemClickListner listner;
    public NoteAdapter(FirestoreRecyclerOptions<LongModel> options) {
        super(options);
        this.listner=listner;
        this.options=options;
//        super(options);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash,
                parent, false);
        return new NoteHolder(v);
    }



    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull LongModel noteModel) {



        holder.textViewTitle.setText(noteModel.getTitle());
//        holder.textViewDescription.setText(model.getDescription());
        holder.textViewPriority.setText(String.valueOf(noteModel.getTitle()));



    }

    public void  deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }



    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewPriority = itemView.findViewById(R.id.first_name);
//            textViewDescription = itemView.findViewById(R.id.last_name);
            textViewTitle = itemView.findViewById(R.id.first_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posirion = getAdapterPosition();
                    if(posirion != RecyclerView.NO_POSITION && listner != null) {
                        listner.onItemClick(getSnapshots().getSnapshot(posirion), posirion );
                    }
                }
            });
        }
//        @Override
//        public void onClick(View v) {
//                listner.onClick(itemView, getAdapterPosition());
//        }

    }


    public interface OnItemClickListner {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setItemClickListner(OnItemClickListner listner){
        this.listner = listner;
    }


//    public interface RecyclerViewClickListner {
//        void onClick(View v, int position);
//    }




}