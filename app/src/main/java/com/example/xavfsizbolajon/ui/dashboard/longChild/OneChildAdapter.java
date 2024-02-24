package com.example.xavfsizbolajon.ui.dashboard.longChild;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.ui.dashboard.Activity2Adapter;

import java.util.List;

public class OneChildAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder>{

    private RecyclerViewClickListner listner;
    LongChildOne longChildOne;
    List<String> activityllist ;


    public OneChildAdapter(LongChildOne longChildOne, List<String> activityllist, RecyclerViewClickListner listner) {
        this.activityllist = activityllist;
        this.longChildOne = longChildOne;
        this.listner = listner;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash, parent, false);

        return new OneChildAdapter.OneChildAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView Url= ((OneChildAdapterViewHolder) holder).last_name;
        Url.setText(longChildOne.activityllist.get(position));


    }

    @Override
    public int getItemCount() {
        int dd =  longChildOne.activityllist.size();
        return dd;
    }



    public class OneChildAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        TextView Url, last_name;

        public OneChildAdapterViewHolder(View v) {
            super(v);
            view = v;

            Url = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.first_name);
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
}