package com.example.dell.androidnote4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShareNoteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listview;
    // 模拟数据
    private List<NoteItemInfo> dataList = null;

    private View _lastClickView = null;

    //当前用户的userrid
    private  String userid;
    private  int lastClickId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_note);


        _lastClickView = null;
        dataList = new ArrayList<NoteItemInfo>();

        lastClickId = -1;
        userid = GlobalData.curUser.dic.get("id").toString();
        //查询我的笔记列表,获取分享表的全表数据
        SelectResInfo noteList = MySQLiteOpenHelper.getInstance().SelectTable("sharenote","1=1");
        if(noteList.list.size()==0)
        {
            NoteItemInfo nullInfo = new NoteItemInfo();
            nullInfo.title="";
            nullInfo.content ="暂无分享数据！";
            nullInfo.modify_time ="";
            nullInfo.docID =-1;
            nullInfo.noteType = NoteItemInfo.NoteType.ShareNote;
            dataList.add(nullInfo);
        }
        else{
            for(int index=0;index< noteList.list.size();index++)
            {
                SelectResItem item = noteList.list.get(index);
                NoteItemInfo noteInfo = new NoteItemInfo();

                noteInfo.docID =Integer.parseInt(item.dic.get("id").toString());
                noteInfo.title = item.dic.get("title").toString();
                noteInfo.content = item.dic.get("content").toString();
                noteInfo.modify_time = item.dic.get("modify_time").toString();
                noteInfo.tag = item.dic.get("tag").toString();
                noteInfo.noteType = NoteItemInfo.NoteType.ShareNote;
                noteInfo.username = item.dic.get("username").toString();
                //LogTool.prnit("我的笔记列表index="+index+",info="+item.dic);
                dataList.add(noteInfo);
            }
        }

        //为ListView对象赋值
        listview = (ListView) findViewById(R.id.listview);
        LayoutInflater inflater =getLayoutInflater();
        //创建自定义Adapter的对象
        ShareNoteItemAdapter adapter = new ShareNoteItemAdapter(inflater,dataList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

    //点击了某个分享元素
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // Toast.makeText(NoteListActivity.this, "点击了第" + position + "条数据", 0).show();
        lastClickId = dataList.get(position).docID;
        LogTool.prnit(this,"点击的元素下标="+position+",数据的ID="+lastClickId);
        if(lastClickId >=0)
        {
            if(_lastClickView!=view && _lastClickView!=null)
            {
                _lastClickView.findViewById(R.id.btnDelete).setVisibility(View.INVISIBLE);
                _lastClickView.findViewById(R.id.btnModify).setVisibility(View.INVISIBLE);
            }
            Button btnDelete = (Button) view.findViewById(R.id.btnDelete);
            Button btnModify = (Button) view.findViewById(R.id.btnModify);
            btnDelete.setVisibility(View.VISIBLE);
            btnModify.setVisibility(View.VISIBLE);
            _lastClickView = view;
        }
    }
}
