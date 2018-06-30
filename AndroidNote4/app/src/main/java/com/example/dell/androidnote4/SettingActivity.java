package com.example.dell.androidnote4;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TextView txtUserName;
    private TextView txtUserId;
    private TextView txtGroupID;
    private TextView txtEmail;
    private TextView txtPhoneNumber;
    private TextView txtGroupName;
    private ImageView imageHeadIcon;
    private Button btnModifyInfo;
    private Button btnQuitGroup;
    private ListView listViewNearGroup;
    // 模拟数据
    private List<String> dataList = null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserId = (TextView) findViewById(R.id.txtUserID);
        txtGroupID = (TextView) findViewById(R.id.txtGroupID);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        txtGroupName = (TextView) findViewById(R.id.txtGroupName);
        imageHeadIcon = (ImageView) findViewById(R.id.imageMyHead);
         btnModifyInfo = (Button) findViewById(R.id.btnModifyInfo);
      //  btnQuitGroup = (Button) findViewById(R.id.btnLeaveGroup);

        SelectResItem userInfo = GlobalData.curUser;
        txtUserName.setText("用户名：" + userInfo.dic.get("username").toString());
        txtUserId.setText("用户ID：" + userInfo.dic.get("id").toString());

        if(userInfo.dic.get("email").isEmpty())
        {
            txtEmail.setText("暂无邮件信息");
        }
        else {
            txtEmail.setText("电子邮箱："+userInfo.dic.get("email").toString());
        }

        if (userInfo.dic.get("groupid").isEmpty())
        {
            txtGroupID.setText("暂未加入群组");
            txtGroupName.setText("");
        }
        else {
            txtGroupID.setText("群组ID：" + userInfo.dic.get("groupid").toString());
            txtGroupName.setText(""); //TODO 如果有群组iD，就把名字显示上去
        }

        if(userInfo.dic.get("phonenumber").isEmpty())
        {
            txtPhoneNumber.setText("暂无电话号码");
        }
        else {
            txtPhoneNumber.setText("电话号码：" + userInfo.dic.get("phonenumber").toString());
        }

        switch (userInfo.dic.get("headid").toString()){
            case "1":
                imageHeadIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_icon_1));
                break;
            case "2":
                imageHeadIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_icon_2));
                break;
            case "3":
                imageHeadIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_icon_3));
                break;
            case "4":
                imageHeadIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_icon_4));
                break;
            case "5":
                imageHeadIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_icon_5));
                break;
            case "6":
                imageHeadIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_icon_6));
                break;
            case "7":
                imageHeadIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_icon_7));
                break;
        }
//        imageHeadIcon.setImageURI(Uri.parse("@drawable/head_icon_" + userInfo.dic.get("headid").toString()));

        btnModifyInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        return;//yanruTODO屏蔽群组相关的
        //推出群组
     //   btnQuitGroup.setOnClickListener(new View.OnClickListener() {
          //  public void onClick(View v) {
           //     if(GlobalData.curUser.dic.get("groupid").isEmpty()) {
             //   DialogTool.ShowTip(SettingActivity.this,"你还没有群组！");
             //   }
              //  else {
               //     ContentValues values= new ContentValues();
               //     values.put("groupid","");
               //     MySQLiteOpenHelper.getInstance().UpdateTable("user","id="+GlobalData.curUser.dic.get("id").toString(),values);
              //  }
           // }
      //  });

        //请求分享分组

        //绑定处理分组按钮
       // dataList = new ArrayList<String>();
        // 初始化数据
     //   for (int i = 0; i < 20; i++) {
      //      dataList.add("第" + i + "条数据");//yanruTODO测试数据
     //   }
        // 设置adapter(所在的activity,使用的显示样式,数据源)
     //   ListAdapter adapter = new ArrayAdapter<String>(SettingActivity.this,
      //          android.R.layout.simple_list_item_1, dataList);
     //   listViewNearGroup = (ListView) findViewById(R.id.listCurrentGroups);
      //  listViewNearGroup.setAdapter(adapter);
        // 绑定item点击事件
     //   listViewNearGroup.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        //TODO 向服务器请求当前的群组列表
      //  try {
      //      Client.instance().RequestGetGroups(GlobalData.curUser.dic.get("username").toString());
     //   } catch (IOException e) {
      //      e.printStackTrace();
     //   }

      //  GlobalData.handerGroupEventListSetting= new Handler() {
         //   public void handleMessage(Message msg) {
         //       String message = (String) msg.obj;//obj不一定是String类，可以是别的类，看用户具体的应用
        //        //根据message中的信息对主线程的UI进行改动
        //        LogTool.prnit("笔记列表页面，接收消息message=" + message);
         //       String []values = message.split("#");
         //       for(int index=0;index<values.length;index++)
         //       {
         //           LogTool.prnit("收到服务器log index"+index+",value="+values[index]);
        //        }
      //      }
      //  };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

       LogTool.prnit(this,"点击了position="+position);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
