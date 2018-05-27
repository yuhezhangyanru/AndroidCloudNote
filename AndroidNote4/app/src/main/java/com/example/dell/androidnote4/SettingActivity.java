package com.example.dell.androidnote4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {
    private TextView txtUserName;
    private TextView txtUserId;
    private TextView txtGroupID;
    private TextView txtEmail;
    private TextView txtPhoneNumber;
    private TextView txtGroupName;
    private ImageView imageHeadIcon;
    private Button btnModifyInfo;

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

    }
}
