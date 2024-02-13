package com.example.xavfsizbolajon.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.xavfsizbolajon.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class Activity2Adapter extends FirestoreRecyclerAdapter<Activity2Model, Activity2Adapter.Activity2Holder> {
    private final FirestoreRecyclerOptions<Activity2Model> options2;
    public Activity2Adapter(@NonNull FirestoreRecyclerOptions<Activity2Model> options2) {
        super(options2);
        this.options2=options2;
    }

    @NonNull
    @Override
    public Activity2Adapter.Activity2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash,
                parent, false);
        return new Activity2Holder(v);
    }
    @Override
    protected void onBindViewHolder(@NonNull Activity2Adapter.Activity2Holder holder, int position, @NonNull Activity2Model model) {
        holder.Url.setText(model.getTitle());
        holder.videoName.setText(String.valueOf(model.getTitle()));


        Glide.with(holder.itemView.getContext()) // get context from view
                .load(model.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.image18)
                .into(holder.imgChildItem);

    }



    public class Activity2Holder extends RecyclerView.ViewHolder {

        TextView Url, videoName;
        ImageView imgChildItem;
        public Activity2Holder(@NonNull View itemView) {
            super(itemView);
            videoName = itemView.findViewById(R.id.first_name);
            Url = itemView.findViewById(R.id.first_name);
            imgChildItem = itemView.findViewById(R.id.img_child_item);

        }
    }
}
