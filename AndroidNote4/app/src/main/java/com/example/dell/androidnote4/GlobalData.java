package com.example.dell.androidnote4;

import android.os.Handler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2018/5/26.
 */

public class GlobalData {
    //当前登录的用户名
    public static SelectResItem curUser = new SelectResItem();

    //当前选中要编辑的笔记
    public static SelectResItem curNote = new SelectResItem();

    //我当前的群组信息
    public static SelectResItem curGroup = new SelectResItem();

    //笔记列表页面的事件注册
    public static Handler handlerNoteList;

    //处理群组分享的事件
    public static Handler handerGroupEventListSetting;

    ///获得当前系统时间
    public static String GetDateStr()
    {
        Date d = new Date();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = df.format(d);
        return str;
    }

    //不可包含非法字符，服务器要用
    public static String checkStringOK(String str)
    {
        if(str.contains("#")||str.contains("%")||str.contains("*"))//||str.contains(" "))
        {
            return "不可包含#,%,*等特殊字符！";
        }
        return "";
    }
}
