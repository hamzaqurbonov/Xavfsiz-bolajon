package com.example.xavfsizbolajon.ui.home;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PreviousHashMap {
    static HomeViewModel homeViewModel;

    public static String getPreviousVideoId() {

//        Log.d("demo22", String.valueOf(homeViewModel.a + " a + b " + homeViewModel.b));
            if (homeViewModel.j ==  homeViewModel.b) {
             int d =  homeViewModel.j -1;
                String previousD = homeViewModel.hashMap.get(d);
                Log.d("demo22", String.valueOf(d + " previousD " + previousD));
                return previousD;
            } else {
                int c = homeViewModel.a--;
                String previous = homeViewModel.hashMap.get(c);

                Log.d("demo22", String.valueOf( "previous" + " a=" + c + " b=" + homeViewModel.b + " " + previous));
                return previous;
            }


    }
}