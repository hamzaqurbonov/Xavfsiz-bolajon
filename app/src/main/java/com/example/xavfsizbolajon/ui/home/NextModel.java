package com.example.xavfsizbolajon.ui.home;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NextModel extends HomeFragment {

    static HashMap<Integer,String> mapPreviousNext = new HashMap<>();
    private static final Random random = new Random();
    static int previousNext;

    public static String getNextVideoId() {
        previousNext++;

        int size = nextArrayList.size();
        int randomNext = random.nextInt(size);
        String clon =  nextArrayList.get(previousNext);

        if (previousNext <= mapPreviousNext.size()) {
            String previous = mapPreviousNext.get(previousNext);
//            Log.d("demo22", String.valueOf( "NextPrevious" + " a=" + previousNext  +  " " + previous + " "+ mapPreviousNext));
            return previous;
        } else {
            mapPreviousNext.put(previousNext,clon);
//            Log.d("demo22", String.valueOf( "Next" + " a=" + previousNext +  " "+ clon + " "+ mapPreviousNext));
            Log.d("demo22", "arreyPreviousNext " + clon + " " + previousNext );
            return clon;
        }
    }

}

