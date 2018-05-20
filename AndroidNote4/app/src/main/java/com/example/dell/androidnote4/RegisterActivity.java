package com.example.dell.androidnote4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.w3c.dom.Text;

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

    private String chooseHeadId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //点击返回到登录页面
        ((Button) findViewById(R.id.btnBackLogin)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent it = new Intent();
                    it.setClass(RegisterActivity.this,MainActivity.class);
                    startActivity(it);
                    RegisterActivity.this.finish();
                }
        });

        editName= (EditText)findViewById(R.id.editName);
        editPassword= (EditText)findViewById(R.id.editPassword);
        editEmail= (EditText)findViewById(R.id.editEmail);
        btnHead1= (ImageButton) findViewById(R.id.headIcon1);
        btnHead2= (ImageButton) findViewById(R.id.headIcon2);
        btnHead3= (ImageButton) findViewById(R.id.headIcon3);
        btnHead4= (ImageButton) findViewById(R.id.headIcon4);
        btnHead5= (ImageButton) findViewById(R.id.headIcon5);
        btnHead6= (ImageButton) findViewById(R.id.headIcon6);
        btnHead7= (ImageButton) findViewById(R.id.headIcon7);
        editPhone= (EditText)findViewById(R.id.editPhone);
        editGroupID= (EditText)findViewById(R.id.editGroup);
        btnConfirmRegister =((Button) findViewById(R.id.btnConfirmRegister));

        //TODO 点击请求注册用户
        btnConfirmRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogTool.ShowTip(RegisterActivity.this, "yanruTODO点击请求注册用户");
                int nameLength = editName.getText().length();
                int passwordLength =editPassword.getText().length();
                int phoneLength =editPhone.getText().length();
                String errorTip = null;

                if(nameLength==0||passwordLength==0)
                {
                    errorTip="用户名或密码不能为空！";
                }
                else if(phoneLength!=11){
                    errorTip="电话号码长度不合规范！";
                }
                else if(nameLength>10){
                    errorTip="用户名过长！(最长10个字符)";
                }
                else if(passwordLength>10){
                    errorTip="密码过长！(最长10个字符)";
                }
                else if(editEmail.getText().length()>20){
                    errorTip="邮件长度过长！(最长20个字符)";
                }

                //TODO 检查数据库是否自己存在！

                if(errorTip!=null) {
                    DialogTool.ShowTip(RegisterActivity.this,errorTip);
                }
                else{
                    //TODO 插入数据
                    JDBCTool.AddUserInfo(editName.getText().toString(),editPassword.getText().toString(),
                            editEmail.getText().toString(),editPhone.getText().toString(),
                            chooseHeadId,editGroupID.getText().toString());
                }
            }
        });

        btnHead1.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {chooseHeadId="1";}});
        btnHead2.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {chooseHeadId="2";}});
        btnHead3.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {chooseHeadId="3";}});
        btnHead4.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {chooseHeadId="4";}});
        btnHead5.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {chooseHeadId="5";}});
        btnHead6.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {chooseHeadId="6";}});
        btnHead7.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {chooseHeadId="7";}});
    }

}
