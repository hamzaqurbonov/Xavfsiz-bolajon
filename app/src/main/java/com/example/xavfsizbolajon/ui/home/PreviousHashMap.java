package com.example.xavfsizbolajon.ui.home;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PreviousHashMap {
    static HomeViewModel homeViewModel;

    public static String getPreviousVideoId() {
        int b = homeViewModel.a--;

        String previous = homeViewModel.hashMap.get(b);

//        Log.d("demo21", String.valueOf(hashMap));
        Log.d("demo21", String.valueOf(b + "=" +previous));
        return previous;
    }
}
