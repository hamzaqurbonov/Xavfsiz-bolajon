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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    private MainActivity activity;
    private RecyclerViewClickListner listner;

        private DashboardFragment context;
    public List<Model> modellist;


    public CustomAdapter(DashboardFragment context, List<Model> modellist, RecyclerViewClickListner listner) {
        this.context=context;
        this.modellist=modellist;
//        this.activity=activity;
        this.listner=listner;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Model member = modellist.get(position);

        if(holder instanceof CustomViewHolder) {

            if (position % 3 == 0) {
                ((CustomViewHolder) holder).itemImage.setImageResource(R.drawable.image18);
            } else if (position % 4 == 0) {
                ((CustomViewHolder) holder).itemImage.setImageResource(R.drawable.image21);
            } else {
                ((CustomViewHolder) holder).itemImage.setImageResource(R.drawable.image19);
            }
        }

//            LinearLayout lay_click = ((CustomViewHolder) holder).lay_click;
//            TextView fist_name = ((CustomViewHolder) holder).first_name;
//            TextView last_name = ((CustomViewHolder) holder).last_name;
//
//            fist_name.setText(member.getFirstName());
//            last_name.setText(member.getLastName());

//            lay_click.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    activity.openItem(member);
//                    activity.initYouTubePlayerView();
//                    Intent intent = new Intent(v.getContext(), MainActivity2.class);
//                    v.getContext().startActivity(intent);

//                    ((CustomViewHolder) holder).youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                        @Override
//                        public void onReady(@NonNull YouTubePlayer youTubePlayer)  {
//
//                            String videoId = "HXrETVPKWh0";
//                            youTubePlayer.cueVideo(videoId, 0);
////                Log.d("demo20", String.valueOf(2));
////
//                        }
//
//                    });
//                }
//            });


    }

    @Override
    public int getItemCount() {
        return modellist.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View view;
        private LinearLayout lay_click;
        YouTubePlayerView youTubePlayerView;
        ImageView itemImage;
        public TextView first_name, last_name, nameTxt;
        public CustomViewHolder(View v) {
            super(v);
            view = v;
            itemImage = view.findViewById(R.id.item_image);
//            lay_click = view.findViewById(R.id.lay_click    );
//            first_name = view.findViewById(R.id.first_name);
//            last_name = view.findViewById(R.id.last_name);
            youTubePlayerView = view.findViewById(R.id.youtube_player_view);
//            nameTxt = view.findViewById(R.id.nameTextView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onClick(view, getAdapterPosition());
        }
    }


    public interface RecyclerViewClickListner {
        void onClick(View v, int position);
    }



//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        private TextView nameTxt;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//        }
//    }


}