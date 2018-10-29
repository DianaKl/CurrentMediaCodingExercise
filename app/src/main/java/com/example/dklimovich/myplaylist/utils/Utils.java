package com.example.dklimovich.myplaylist.utils;

import android.os.Build;

public class Utils {

    public static int longToInt(long value){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Math.toIntExact(value);
        }else{
            return  (int) Math.max(Math.min(Integer.MAX_VALUE, value), Integer.MIN_VALUE);
        }

    }
}
