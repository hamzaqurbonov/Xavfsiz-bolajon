package com.example.xavfsizbolajon.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xavfsizbolajon.R;
import com.example.xavfsizbolajon.databinding.FragmentDashboardBinding;
import com.example.xavfsizbolajon.databinding.FragmentNotificationsBinding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    YouTubePlayerView youTubePlayerView;
    List<Model> modellist = new ArrayList<>();

    private Context context;
    private RecyclerView recyclerView;

    private CustomAdapter.RecyclerViewClickListner listner;

    private FragmentDashboardBinding binding;

    public View onCreateView( @NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView =  view.findViewById(R.id.recyclerView);
        initViews();
        List<Model> modellist = prepareMemerList();
        refreshAdapter(modellist);
    }


    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1 ));
    }
    private void refreshAdapter (List<Model> modellist) {
        setOnClickListner();
        CustomAdapter adapter = new CustomAdapter(this, modellist, listner);
        recyclerView.setAdapter(adapter);
    }
    private List<Model> prepareMemerList() {
        modellist.add(new Model("Kurbanov", "HXrETVPKWh0"));
        modellist.add(new Model("Kurbanov", "X3tr5ax78V4"));
        modellist.add(new Model("Kurbanov", "k_an7b4r1_Q"));
        return modellist;
    }

    private void setOnClickListner() {
        listner = new CustomAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra( "Kurbanov",modellist .get(position).getLastName());
                startActivity(intent);
            }
        };
    }

}