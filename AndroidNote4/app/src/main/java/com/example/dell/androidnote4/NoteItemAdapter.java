package com.example.dell.androidnote4;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 2018/5/26.
 */

public class NoteItemAdapter extends BaseAdapter {

    private List<NoteItemInfo> mData;//定义数据。
    private LayoutInflater mInflater;//定义Inflater,加载我们自定义的布局。
    public TextView txtTitle;
    public TextView txtContent;
    public TextView txtModifyTime;
    public Button btnDelete;
    public Button btnModify;

    /*
    定义构造器，在Activity创建对象Adapter的时候将数据data和Inflater传入自定义的Adapter中进行处理。
    */
    public NoteItemAdapter(LayoutInflater inflater, List<NoteItemInfo> data) {
        mInflater = inflater;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("WrongConstant")
    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        //获得ListView中的view
        View noteItemView = mInflater.inflate(R.layout.item_simpleadapter, null);
        //获得学生对象
        NoteItemInfo student = mData.get(position);
        //获得自定义布局中每一个控件的对象。
        txtTitle = (TextView) noteItemView.findViewById(R.id.txtTitle);
        txtContent = (TextView) noteItemView.findViewById(R.id.txtContent);
        txtModifyTime = (TextView) noteItemView.findViewById(R.id.txtModifyTime);
        btnDelete = (Button) noteItemView.findViewById(R.id.btnDelete);
        btnModify = (Button) noteItemView.findViewById(R.id.btnModify);

        //将数据一一添加到自定义的布局中。
        txtTitle.setText(student.title);
        txtContent.setText(student.description);
        txtModifyTime.setText(student.modifyTime);
        //  txtTitle.setImageResource(student.getImag());
        btnDelete.setVisibility(View.INVISIBLE);
        btnModify.setVisibility(View.INVISIBLE);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               LogTool.prnit("点击删除按钮");
               //TODO 点击删除按钮
            }
        });
        btnModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("点击修改按钮");
                //TODO 点击修改按钮
            }
        });
        return noteItemView;
    }
}