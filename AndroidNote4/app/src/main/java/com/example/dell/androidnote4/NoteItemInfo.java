package com.example.dell.androidnote4;

/**
 * Created by DELL on 2018/5/26.
 */

public class NoteItemInfo {
    public int docID;
    public String title;
    public String content;
    public String modify_time;
    public String tag;
    public String username;

    public NoteType noteType = NoteType.MyNote;

    public   enum NoteType
    {
        MyNote,
        ShareNote,
    }
}