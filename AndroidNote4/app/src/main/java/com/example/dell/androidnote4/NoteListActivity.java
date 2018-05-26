package com.example.dell.androidnote4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//function:用户的笔记列表
public class NoteListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listview;
    // 模拟数据
    private List<NoteItemInfo> dataList = null;

    private  View _lastClickView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        _lastClickView = null;
        dataList = new ArrayList<NoteItemInfo>();
        // 初始化数据
        for (int i = 0; i < 20; i++) {
            NoteItemInfo info = new NoteItemInfo();
            info.title ="第"+i+"笔记";
            info.modifyTime="tag:"+"生活"+"2018-5-26 22:53:31";
            info.description="测试测试描述。。。。。。。。。。发的房间的防静电服";

            //TODO 取正式数据
            dataList.add(info);
        }

        //为ListView对象赋值
        listview = (ListView) findViewById(R.id.listview);
        LayoutInflater inflater =getLayoutInflater();
        //初始化数据
        //initData();
        //创建自定义Adapter的对象
        NoteItemAdapter adapter = new NoteItemAdapter(inflater,dataList);
        //将布局添加到ListView中
        listview.setAdapter(adapter);


      //  listview = (ListView) findViewById(R.id.listview);
        //listview.setAdapter(adapter);
        // 绑定item点击事件
        listview.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }


    // 这是实现OnItemClickListener接口必须实现的方法，在这里进行item的点击事件的处理，最常用的是position，可以根据position获得点击的item的数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast.makeText(NoteListActivity.this, "点击了第" + position + "条数据", 0).show();
    //    ((NoteItemAdapter)view).btnModify.setVisibility(View.VISIBLE);

        if(_lastClickView!=view && _lastClickView!=null)
        {
            _lastClickView.findViewById(R.id.btnDelete).setVisibility(View.INVISIBLE);
            _lastClickView.findViewById(R.id.btnModify).setVisibility(View.INVISIBLE);
        }
        Button    btnDelete = (Button) view.findViewById(R.id.btnDelete);
        Button  btnModify = (Button) view.findViewById(R.id.btnModify);
        LogTool.prnit("点击了某个元素view="+view);
        btnDelete.setVisibility(View.VISIBLE);
        btnModify.setVisibility(View.VISIBLE);
        _lastClickView = view;
    }
}
