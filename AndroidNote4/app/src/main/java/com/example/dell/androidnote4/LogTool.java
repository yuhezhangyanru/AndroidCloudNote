package com.example.dell.androidnote4;

import android.util.Log;

/**
 * Created by DELL on 2018/5/19.
 */

public class LogTool {

    public static void Log(String str)
    {
        Log.w("Info",str);
    }

    public static void prnit(String str)
    {
        System.out.println("yanruTODOdate="+System.nanoTime()+":"+str);
    }
}
