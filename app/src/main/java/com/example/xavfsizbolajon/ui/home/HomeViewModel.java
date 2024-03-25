package com.example.xavfsizbolajon.ui.home;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HomeViewModel extends HomeFragment {

    public static ArrayList<String> previousArrayList = new ArrayList<>();
//    public static ArrayList<Integer> integer = new ArrayList<Integer>();
    static Map<Integer,String> hashMap = new HashMap<Integer,String>();
    private static final Random random = new Random();
    static int a = 0;
    static int b;
    static int j;

    public static String getNextVideoId() {
        int size = HomeFragment.nextArrayList.size();
        int randomNext = random.nextInt(size);
        String clon =  nextArrayList.get(randomNext);
        previousArrayList.add(clon);
        a++;
        b++;
        j++;



        if (a <= hashMap.size()) {

            String previous = hashMap.get(a);

            Log.d("demo22", String.valueOf( "NextPrevious" + " a=" + a + " b=" + b +  " " + previous + " "+ hashMap));
            return previous;
        } else {

            Integer key = a;
            hashMap.put(key,clon);

            Log.d("demo22", String.valueOf( "Next" + " a=" + a + " b=" + b +  " "+ clon + " "+ hashMap));
            return clon;
        }

    }



}

//
//
//
//    public static String getPreviousVideoId() {
//        int size = previousArrayList.size();
//        int randomNext = random.nextInt(size);
//        String clon =  previousArrayList.get(randomNext);
//
//        Log.d("demo6", String.valueOf(previousArrayList));
//        return clon;
//    }
//    public static String getNextVideoId() {
//        int size = HomeFragment.nextArrayList.size();
//        int randomNext = random.nextInt(size);
//        String clon =  nextArrayList.get(randomNext);
//        previousArrayList.add(clon);
////        Log.d("demo21", String.valueOf(size));
////        Log.d("demo21", String.valueOf(nextArrayList));
//        return clon;
//    }