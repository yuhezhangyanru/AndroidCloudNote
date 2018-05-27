package com.example.dell.androidnote4;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.logging.Logger;

public class RegisterActivity extends AppCompatActivity {

    EditText editName;
    EditText editPassword;
    EditText editEmail;
    ImageButton btnHead1;
    ImageButton btnHead2;
    ImageButton btnHead3;
    ImageButton btnHead4;
    ImageButton btnHead5;
    ImageButton btnHead6;
    ImageButton btnHead7;
    EditText editPhone;
    EditText editGroupID;
    Button btnConfirmRegister;
    TextView txtErrorLog;
    TextView txtTopTip;

    private ImageButton lastClickHead= null;

    private String chooseHeadId = "1";

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtTopTip = (TextView) findViewById(R.id.txtTopTip);
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editEmail = (EditText) findViewById(R.id.editEmail);
        btnHead1 = (ImageButton) findViewById(R.id.headIcon1);
        btnHead2 = (ImageButton) findViewById(R.id.headIcon2);
        btnHead3 = (ImageButton) findViewById(R.id.headIcon3);
        btnHead4 = (ImageButton) findViewById(R.id.headIcon4);
        btnHead5 = (ImageButton) findViewById(R.id.headIcon5);
        btnHead6 = (ImageButton) findViewById(R.id.headIcon6);
        btnHead7 = (ImageButton) findViewById(R.id.headIcon7);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editGroupID = (EditText) findViewById(R.id.editGroup);
        btnConfirmRegister = ((Button) findViewById(R.id.btnConfirmRegister));
        txtErrorLog = (TextView) findViewById(R.id.txtErrorLog);

        //TODO 点击请求注册用户
        //没有用户的时候是注册内容
        final boolean isRegiser = GlobalData.curUser == null;
        if (isRegiser) {
            btnConfirmRegister.setText("完成注册");
            txtTopTip.setText("注册新用户");
        } else {
            btnConfirmRegister.setText("确定修改");
            txtTopTip.setText("修改用户信息");

            SelectResItem userInfo = GlobalData.curUser;
            editName.setText(userInfo.dic.get("username").toString());
            editPassword.setText(userInfo.dic.get("password").toString());
            editGroupID.setText(userInfo.dic.get("groupid").toString());
            editEmail.setText(userInfo.dic.get("email").toString());
            editPhone.setText(userInfo.dic.get("phonenumber").toString());
        }

        //取消按钮
        ((Button) findViewById(R.id.btnBackLogin)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isRegiser)
                {
                    Intent it = new Intent();
                    it.setClass(RegisterActivity.this, MainActivity.class);
                    startActivity(it);
                }
                else
                {
                    Intent it = new Intent();
                    it.setClass(RegisterActivity.this, SettingActivity.class);
                    startActivity(it);
                }
            }
        });

        btnConfirmRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("点击了确认注册");
                int nameLength = editName.getText().length();
                int passwordLength = editPassword.getText().length();
                String errorTip = null;

                if (nameLength == 0 || passwordLength == 0) {
                    errorTip = "用户名或密码不能为空！";
                } else if (nameLength > 10) {
                    errorTip = "用户名过长！(最长10个字符)";
                } else if (passwordLength > 10) {
                    errorTip = "密码过长！(最长10个字符)";
                } else if (editEmail.getText().length() > 20) {
                    errorTip = "邮件长度过长！(最长20个字符)";
                }

                if (errorTip != null) {
                    DialogTool.ShowTip(RegisterActivity.this, errorTip);
                } else {
                    txtErrorLog.setText("");

                    if (isRegiser) //检查注册信息
                    {
                        GetDataCount(editName.getText().toString(), txtErrorLog);
                    } else {
                        ConfirmModifyInfo(editName.getText().toString());
                    }
                }
            }
        });

        btnHead1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "1";
                if(lastClickHead!=null)
                    lastClickHead.setAlpha(1f);
                btnHead1.setAlpha(0.6f);
                lastClickHead = btnHead1;
            }
        });
        btnHead2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "2";
                if(lastClickHead!=null)
                    lastClickHead.setAlpha(1f);
                btnHead2.setAlpha(0.6f);
                lastClickHead = btnHead2;
            }
        });
        btnHead3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "3";
                if(lastClickHead!=null)
                    lastClickHead.setAlpha(1f);
                btnHead3.setAlpha(0.6f);
                lastClickHead = btnHead3;
            }
        });
        btnHead4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "4";
                if(lastClickHead!=null)
                    lastClickHead.setAlpha(1f);
                btnHead4.setAlpha(0.6f);
                lastClickHead = btnHead4;
            }
        });
        btnHead5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "5";
                if(lastClickHead!=null)
                    lastClickHead.setAlpha(1f);
                btnHead5.setAlpha(0.6f);
                lastClickHead = btnHead5;
            }
        });
        btnHead6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "6";
                if(lastClickHead!=null)
                    lastClickHead.setAlpha(1f);
                btnHead6.setAlpha(0.6f);
                lastClickHead = btnHead6;
            }
        });
        btnHead7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "7";
                if(lastClickHead!=null)
                    lastClickHead.setAlpha(1f);
                btnHead7.setAlpha(0.6f);
                lastClickHead = btnHead7;
            }
        });

        //处理注册的消息
        handler = new Handler() {
            public void handleMessage(Message msg) {
                String message = (String) msg.obj;//obj不一定是String类，可以是别的类，看用户具体的应用
                //根据message中的信息对主线程的UI进行改动
                LogTool.prnit("接收消息message=" + message);
                DialogTool.ShowTip(RegisterActivity.this, message);
            }
        };
    }

    private void ConfirmModifyInfo(final String username) {
        ContentValues item = new ContentValues();
        item.put("username", username);
        item.put("password", editPassword.getText().toString());
        item.put("email", editEmail.getText().toString());
        item.put("phonenumber", editPhone.getText().toString());
        item.put("headid", chooseHeadId);
        item.put("groupid", editGroupID.getText().toString());
        LogTool.prnit("旧的用户名=" + GlobalData.curUser.dic.get("username") + ",新的用户名=" + username);
        int resCode = 0;
        if (!username.equals(GlobalData.curUser.dic.get("username"))) {
            resCode = getCurUserCount(username);
        }
        if (resCode == 0) {
            MySQLiteOpenHelper.getInstance().UpdateTable("user", "id=" + GlobalData.curUser.dic.get("id"), item);

            //更新下本地用户缓存
            SelectResInfo newInfo = MySQLiteOpenHelper.getInstance().SelectTable("user", "username='" + username + "'");
          //  LogTool.prnit("注册完查询用户个数="+ newInfo.list.size());
            GlobalData.curUser = newInfo.list.get(0);


            //用户注册成功！跳转到笔记的主面板
            Intent intent = new Intent(RegisterActivity.this, NoteListActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
            startActivity(intent);
        }
    }

    private int getCurUserCount(final String username)
    {
        int count = MySQLiteOpenHelper.getInstance().GetItemCount("user", "username", username);
        LogTool.prnit("查询用户名="+username+",count=" + count);
        if (count > 0) {
            //先验证该用户名是否存在
            Message message = Message.obtain();
            message.obj = "该用户已经存在！请输入新的用户名";
            handler.sendMessage(message);
        }
        return count;
    }

    ///统计某个查询结果的个数
    public int GetDataCount(final String username, final TextView errorLog) {
        int resultCode = 0;
        int count = 0;
     //   try {
            count = getCurUserCount(username);
            if (count == 0) {
                ContentValues item = new ContentValues();
                item.put("username", username);
                item.put("password", editPassword.getText().toString());
                item.put("email", editEmail.getText().toString());
                item.put("phonenumber", editPhone.getText().toString());
                item.put("headid", chooseHeadId);
                item.put("groupid", editGroupID.getText().toString());
                item.put("taglist","");

                String errorCode = MySQLiteOpenHelper.getInstance().InsertTable("user", item);
                if (errorCode != "") {
                    LogTool.prnit("查询失败!错误原因");
                    Message message = Message.obtain();
                    message.obj = "注册用户信息：" + errorCode;
                    handler.sendMessage(message);
                } else {

                    //查询当前用户信息并更新当前用户信息，主要为了更新到用户id
                    SelectResInfo newInfo = MySQLiteOpenHelper.getInstance().SelectTable("user", "username='" + username + "'");
                    LogTool.prnit("注册完查询用户个数="+ newInfo.list.size());
                    GlobalData.curUser = newInfo.list.get(0);
                    LogTool.prnit("注册完查询用户信息dic=" + GlobalData.curUser.dic);

                    //用户注册成功！跳转到笔记的主面板
                    Intent intent = new Intent(RegisterActivity.this, NoteListActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                    startActivity(intent);
                }
            }
      //  } catch (Exception e) {
       //     LogTool.prnit("查询失败!错误原因=" + e.getMessage());
      //      Message message = Message.obtain();
        //    message.obj = "注册验证失败！服务器连接异常！";
        //    handler.sendMessage(message);
     //   }
        return 0;
    }
}
