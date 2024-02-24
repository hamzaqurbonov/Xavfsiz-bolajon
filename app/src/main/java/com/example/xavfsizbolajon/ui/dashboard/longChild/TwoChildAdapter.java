package com.example.xavfsizbolajon.ui.dashboard.longChild;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xavfsizbolajon.R;

import java.util.List;

public class TwoChildAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder>{
    private TwoChildAdapter.RecyclerViewClickListner listner;
    LongChildTwo longChildTwo;
    List<String> activityllist ;


    public TwoChildAdapter(LongChildTwo longChildTwo, List<String> activityllist, TwoChildAdapter.RecyclerViewClickListner listner) {
        this.activityllist = activityllist;
        this.longChildTwo = longChildTwo;
        this.listner = listner;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash, parent, false);

        return new TwoChildAdapter.TwoChildAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView Url= ((TwoChildAdapter.TwoChildAdapterViewHolder) holder).last_name;
        Url.setText(longChildTwo.activityllist.get(position));

    }

    @Override
    public int getItemCount() {
        int dd =  longChildTwo.activityllist.size();
        return dd;
    }



    public class TwoChildAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        TextView Url, last_name;

        public TwoChildAdapterViewHolder(View v) {
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
