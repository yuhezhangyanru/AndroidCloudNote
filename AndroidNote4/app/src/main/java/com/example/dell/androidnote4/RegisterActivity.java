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

import org.w3c.dom.Text;

import java.util.Dictionary;
import java.util.Hashtable;

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

    private String chooseHeadId = "1";

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //点击返回到登录页面
        ((Button) findViewById(R.id.btnBackLogin)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(RegisterActivity.this, MainActivity.class);
                startActivity(it);
                RegisterActivity.this.finish();
            }
        });

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
                    GetDataCount(editName.getText().toString(), txtErrorLog);
                }
            }
        });

        btnHead1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "1";
            }
        });
        btnHead2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "2";
            }
        });
        btnHead3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "3";
            }
        });
        btnHead4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "4";
            }
        });
        btnHead5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "5";
            }
        });
        btnHead6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "6";
            }
        });
        btnHead7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chooseHeadId = "7";
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


    ///统计某个查询结果的个数
    public int GetDataCount(final String username, final TextView errorLog) {
        int resultCode = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                String sql = "SELECT * FROM USER WHERE username = '" + username + "'";
                try {
                    count = MySQLiteOpenHelper.getInstance().GetItemCount("user", "username", username);
                    LogTool.prnit("查询完毕!sql=" + sql + ",count=" + count);
                    if (count > 0) {
                        //先验证该用户名是否存在
                        Message message = Message.obtain();
                        message.obj = "该用户已经过存在！请输入新的用户名";
                        handler.sendMessage(message);
                    } else {
                        ContentValues item = new ContentValues();
                        item.put("username", username);
                        item.put("password", editPassword.getText().toString());
                        item.put("email", editEmail.getText().toString());
                        item.put("phonenumber", editPhone.getText().toString());
                        item.put("headid", chooseHeadId);
                        item.put("groupid", editGroupID.getText().toString());

                        String errorCode = MySQLiteOpenHelper.getInstance().InsertTable("user", item);
                        if (errorCode != "") {
                            LogTool.prnit("查询失败!错误原因");
                            Message message = Message.obtain();
                            message.obj = "注册用户信息：" + errorCode;
                            handler.sendMessage(message);
                        } else {
                            Dictionary<String, String> dic = new Hashtable<String, String>();
                            dic.put("username", username);
                            dic.put("password", editPassword.getText().toString());
                            dic.put("email", editEmail.getText().toString());
                            dic.put("phonenumber", editPhone.getText().toString());
                            dic.put("headdid", chooseHeadId);
                            dic.put("groupid", editGroupID.getText().toString());
                            GlobalData.curUser.dic = dic;

                            //用户注册成功！跳转到笔记的主面板
                            Intent intent = new Intent(RegisterActivity.this, NoteListActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    LogTool.prnit("查询失败!错误原因=" + e.getMessage() + ",sql=" + sql);
                    Message message = Message.obtain();
                    message.obj = "注册验证失败！服务器连接异常！";
                    handler.sendMessage(message);
                }
            }
        }).start();
        return 0;
    }
}
