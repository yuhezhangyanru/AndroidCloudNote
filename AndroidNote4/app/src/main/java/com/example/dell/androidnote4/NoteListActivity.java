package com.example.dell.androidnote4;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//function:用户的笔记列表
public class NoteListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listview;
    // 模拟数据
    private List<NoteItemInfo> dataList = null;

    private  View _lastClickView = null;

    private Button btnCreate;
    private Button btnSetting;
    private Button btnShare;

    //当前用户的userrid
    private  String userid;
    private  int lastClickId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        //处理注册的消息
        GlobalData.handlerNoteList = new Handler() {
            public void handleMessage(Message msg) {
                String message = (String) msg.obj;//obj不一定是String类，可以是别的类，看用户具体的应用
                //根据message中的信息对主线程的UI进行改动
                LogTool.prnit(this,"笔记列表页面，接收消息message=" + message);
                if(message.contains("跳转到删除")) {
                    int messageId = msg.arg1;
                    LogTool.prnit(this,"即将删除笔记ID="+messageId);
                  //  noteItemView.setVisibility(View.INVISIBLE);
                    MySQLiteOpenHelper.getInstance().DeleteTable("note", "id" ,new String[]{messageId+""});

                    //刷新列表！
                    Intent intent = new Intent(NoteListActivity.this, NoteListActivity.class);
                    startActivity(intent);
                }
                else if(message.contains("跳转到修改"))
                {
                    //跳转到修改页面
                    int messageId = msg.arg1;
                     LogTool.prnit(this,"编辑的笔记ID="+messageId);
                     SelectResInfo noteInfo = MySQLiteOpenHelper.getInstance().SelectTable("note","id="+messageId+"");
                     GlobalData.curNote =noteInfo.list.get(0);

                      Intent intent = new Intent(NoteListActivity.this, WriteNoteActivity.class);
                      startActivity(intent);
                }
                else{
                    DialogTool.ShowTip(NoteListActivity.this, message);
                }
            }
        };

        //处理笔记列表
        _lastClickView = null;
        dataList = new ArrayList<NoteItemInfo>();

        lastClickId = -1;
        userid = GlobalData.curUser.dic.get("id").toString();
        //查询我的笔记列表
        SelectResInfo noteList = MySQLiteOpenHelper.getInstance().SelectTable("note","userid='"+userid+"'");
        if(noteList.list.size()==0)
        {
            NoteItemInfo nullInfo = new NoteItemInfo();
            nullInfo.title="";
            nullInfo.content ="笔记为空！点击即新建";
            nullInfo.modify_time ="";
            nullInfo.docID =-1;
            nullInfo.noteType = NoteItemInfo.NoteType.MyNote;
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
                noteInfo.noteType = NoteItemInfo.NoteType.MyNote;
               // LogTool.prnit("我的笔记列表index="+index+",info="+item.dic);
                dataList.add(noteInfo);
            }
        }

        //为ListView对象赋值
        listview = (ListView) findViewById(R.id.listview);
        LayoutInflater inflater =getLayoutInflater();
        //创建自定义Adapter的对象
        NoteItemAdapter adapter = new NoteItemAdapter(inflater,dataList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnSetting = (Button)findViewById(R.id.btnSettinng);
        btnShare = (Button)findViewById(R.id.btnShare);
        //阜南县笔记
        btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit(this,"点击跳转到分享笔记页面！");
              //  GlobalData.curNote = null;
                Intent intent = new Intent(NoteListActivity.this, ShareNoteActivity.class);
                startActivity(intent);
            }});

        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit(this,"点击了新建笔记！");
                GlobalData.curNote = null;
                Intent intent = new Intent(NoteListActivity.this, WriteNoteActivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit(this,"点击了设置！lastClickId="+lastClickId);
                Intent intent = new Intent(NoteListActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
      // yanruTODO屏蔽 btnShare.setOnClickListener(new View.OnClickListener() {
       //     public void onClick(View v) {
          //      LogTool.prnit("点击了分享笔记！lastClickId="+lastClickId);
           //     //TODO 群组分享页面
      //      }
     // /  });
    }

    // 这是实现OnItemClickListener接口必须实现的方法，在这里进行item的点击事件的处理，最常用的是position，可以根据position获得点击的item的数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
       // Toast.makeText(NoteListActivity.this, "点击了第" + position + "条数据", 0).show();
        lastClickId = dataList.get(position).docID;
        LogTool.prnit(this,"点击的元素下标="+position+",数据的ID="+lastClickId);
        if(lastClickId <0)
        {
            //点击了空元素，去新建
            GlobalData.curNote = null;
            Intent intent = new Intent(NoteListActivity.this, WriteNoteActivity.class);
            startActivity(intent);
        }
        else
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
