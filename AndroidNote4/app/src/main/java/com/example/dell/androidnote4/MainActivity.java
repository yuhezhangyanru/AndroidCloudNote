package com.example.dell.androidnote4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText editName;
    private EditText editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2018-5-26 初始化SQLite数据库，并指定数据库可读写
        LogTool.prnit("启动应用后初始化数据库=" + ConstData.DBName);
        MySQLiteOpenHelper.CreateInstance(getApplicationContext(), ConstData.DBName, 2);
        MySQLiteOpenHelper.getInstance().getWritableDatabase();

        //以下绑定自己的组件：
        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById((R.id.editPassword));

        //TODO 点击请求登录信息
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().length() == 0 || editPassword.getText().length() == 0) {
                    DialogTool.ShowTip(MainActivity.this, "用户名或密码不能为空！");
                } else {
                    SelectResInfo info = MySQLiteOpenHelper.getInstance().SelectTable("user", "where username='" +
                            editName.getText().toString() + "'");
                    if(info.dic.get("password")!=null)
                    {
                        LogTool.prnit("查询的password=" + info.dic.get("password").toString()
                                + "#,输入的密码="+editPassword.getText().toString()
                        +",值相同？"+(info.dic.get("password").toString().equals( editPassword.getText().toString())));
                    }

                    if (info.dic.size() == 0) {
                        DialogTool.ShowTip(MainActivity.this, "用户名不存在！！");
                    } else if (!info.dic.get("password").toString().equals( editPassword.getText().toString())) {
                        DialogTool.ShowTip(MainActivity.this, "输入密码错误！！！");
                    }
                    else //用户验证通过，将进入主页面
                    {
                        GlobalData.curUser.dic = info.dic;
                        Intent intent = new Intent(MainActivity.this, NoteListActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        //点击跳转到注册
        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                boolean useTest = false;//TODO 测试跳转
                if (useTest) {
                    Intent intent = new Intent(MainActivity.this, SQLiteOptionActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                    startActivity(intent);
                }
            }
        });


        //TODO 测试查询表格
    }

}