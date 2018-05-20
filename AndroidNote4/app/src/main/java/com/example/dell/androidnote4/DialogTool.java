package com.example.dell.androidnote4;

import android.app.Activity;
import android.app.AlertDialog;

/**
 * Created by DELL on 2018/5/20.
 */

public class DialogTool {

    ///单纯的弹出一个提示框，不带任何操作按钮，点背景即关闭
    public static  void ShowTip(Activity activity, String str){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("提示");
        dialog.setMessage(str);
        dialog.setCancelable(true);
        dialog.show();
    }

}
