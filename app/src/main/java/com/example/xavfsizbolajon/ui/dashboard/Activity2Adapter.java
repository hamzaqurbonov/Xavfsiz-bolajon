package com.example.xavfsizbolajon.ui.dashboard;

import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class Activity2Adapter extends RecyclerView.Adapter< RecyclerView.ViewHolder> {


//    private static final String[] VideoIds = {"pcbGX4JcILA", "asdsd"};

    MainActivity2 activity2;
    List<String> activityllist ;
    List<String> modellist2;
    public ArrayList<String> list2;


    public Activity2Adapter(MainActivity2 activity2, List<String> activityllist) {
        this.activityllist = activityllist;
        this.activity2 = activity2;
//        Log.d("demo16","4 "+ activity2.activityllist.toString());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dash, parent, false);


        return new Activity2Adapter.Activity2AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        TextView Url= ((Activity2AdapterViewHolder) holder).last_name;
        Url.setText(activity2.activityllist.get(position));

    }

    @Override
    public int getItemCount() {
        int dd =  activity2.activityllist.size();
//        Log.d("demo16","5 "+ dd);
//           String[] dd = new String[]{activity2.activityllist.toString()};

        return dd;
    }

    public class Activity2AdapterViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView Url, last_name;

        public Activity2AdapterViewHolder(View v) {
            super(v);
            view = v;

            Url = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.first_name);

        }


    }
}
