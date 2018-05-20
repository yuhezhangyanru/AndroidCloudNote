package com.example.dell.androidnote4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText editName;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //以下绑定自己的组件：
        editName = (EditText)findViewById(R.id.editName);
        editPassword =(EditText)findViewById((R.id.editPassword));

        //TODO 点击请求登录信息
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editName.getText().length()==0||editPassword.getText().length()==0)
                {
                    DialogTool.ShowTip(MainActivity.this,"用户名或密码不能为空！");
                }
            }
        });

        //点击跳转到注册
        Button  btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册页面
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                startActivity(intent);
            }
        });
    }
}
