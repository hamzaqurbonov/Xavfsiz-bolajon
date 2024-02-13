package com.example.xavfsizbolajon.ui.home;

import static com.example.xavfsizbolajon.ui.home.HomeFragment.nextArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Random;

public class HomeViewModel extends HomeFragment {

    public static ArrayList<String> previousArrayList = new ArrayList<>();
    private static final Random random = new Random();

    public static String getPreviousVideoId() {
        int size = previousArrayList.size();
        int randomNext = random.nextInt(size);
        String clon =  previousArrayList.get(randomNext);
//        Log.d("demo21", String.valueOf(previousArrayList));
//        Log.d("demo21", String.valueOf(previousArrayList.size()));
        return clon;
    }
    public static String getNextVideoId() {
        int size = HomeFragment.nextArrayList.size();
        int randomNext = random.nextInt(size);
        String clon =  nextArrayList.get(randomNext);
        previousArrayList.add(clon);
//        Log.d("demo21", String.valueOf(size));
//        Log.d("demo21", String.valueOf(nextArrayList));
        return clon;
    }
   }