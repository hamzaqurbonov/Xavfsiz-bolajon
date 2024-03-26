package com.example.xavfsizbolajon.ui.home;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PreviousModel {
    static NextModel nextModel;

    public static String getPreviousVideoId() {

//        Log.d("demo22", String.valueOf(homeViewModel.a + " a + b " + homeViewModel.b));
            if (nextModel.previousNext == 1) {
                String previousNull = nextModel.mapPreviousNext.get(1);
//                Log.d("demo22", String.valueOf("previousD " + " a=" + nextModel.previousNext + " " + previousNull));
                return previousNull;
            } else {
                int num = --nextModel.previousNext;
                String previous = nextModel.mapPreviousNext.get(num);
//                Log.d("demo22", String.valueOf( "previous" + " a=" + num + " " + previous));
                return previous;
            }


    }
}