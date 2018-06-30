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

    public static void prnit(Object obj,String str)
    {
      //  System.out.println("yanruTODOdate="+System.nanoTime()+":"+str);
      //可以打印   Log.e("异常","异常哈哈哈");
        Log.e( obj.getClass().getName().toString(),System.nanoTime()+":"+str);
    }
}
