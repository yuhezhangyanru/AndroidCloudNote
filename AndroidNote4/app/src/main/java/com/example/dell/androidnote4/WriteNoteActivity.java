package com.example.dell.androidnote4;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.logging.Logger;

public class WriteNoteActivity extends AppCompatActivity {
    private TextView txtCreateType;
    private Button btnCreateConfirm;
    private EditText editTitle;
    private EditText editTag;
    private EditText editContent;
    private CheckBox checkBoxShare;

    private String groupid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        btnCreateConfirm = (Button)findViewById(R.id.btnCreateConfirm);
        checkBoxShare =(CheckBox)findViewById(R.id.checkBoxShare);
        editTitle = (EditText)findViewById(R.id.editTitle);
        editTag = (EditText)findViewById(R.id.editTag);
        editContent = (EditText)findViewById(R.id.editContent);
        txtCreateType = (TextView) findViewById(R.id.txtCreateType);

        final boolean isCreate = GlobalData.curNote ==null;

        txtCreateType.setText(isCreate?"新建笔记":"修改笔记");
        if(!isCreate)
        {
            editTitle.setText(GlobalData.curNote.dic.get("title").toString());
            editContent.setText(GlobalData.curNote.dic.get("content").toString());
            editTag.setText(GlobalData.curNote.dic.get("tag").toString());
            groupid = GlobalData.curNote.dic.get("groupid").toString();
        }
        checkBoxShare.setChecked(!groupid.isEmpty());

        btnCreateConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("点击了 btnCreateConfirm！当前模式是创建笔记？"+isCreate+",是否选分享？"+checkBoxShare.isChecked());
                if(editTitle.getText().toString().isEmpty())
                {
                    DialogTool.ShowTip(WriteNoteActivity.this,"标题不可以为空！");
                }
                else if(editContent.getText().toString().isEmpty())
                {
                    DialogTool.ShowTip(WriteNoteActivity.this,"笔记内容不可以为空！");
                }
                else
                {
                    if(checkBoxShare.isChecked()==true)
                    {
                        if(GlobalData.curUser.dic.get("groupid").isEmpty()) {
                            DialogTool.ShowTip(WriteNoteActivity.this,"你还没有群组！请先取消勾选分享！或去'与我分享'加入一个群组！");
                            return;
                        }
                    }

                    //插入或者更新数据库
                    if(isCreate)
                    {
                        ContentValues values = new ContentValues();
                        values.put("title",editTitle.getText().toString());
                        values.put("content",editContent.getText().toString());
                        values.put("tag",editTag.getText().toString());
                        values.put("create_time",GlobalData.GetDateStr());
                        values.put("modify_time",GlobalData.GetDateStr());
                        values.put("groupid",groupid);
                        values.put("userid",GlobalData.curUser.dic.get("id").toString());
                        MySQLiteOpenHelper.getInstance().InsertTable("note",values);
                    }
                    else
                    {
                        int curNoteId= Integer.parseInt(GlobalData.curNote.dic.get("id").toString());
                        //TODO 修改笔记内容
                        ContentValues values = new ContentValues();
                        values.put("title",editTitle.getText().toString());
                        values.put("content",editContent.getText().toString());
                        values.put("tag",editTag.getText().toString());
                        values.put("create_time",GlobalData.GetDateStr());
                        values.put("modify_time",GlobalData.GetDateStr());
                        values.put("groupid",groupid);
                        values.put("userid",GlobalData.curUser.dic.get("id").toString());
                        values.put("id",curNoteId);

                        MySQLiteOpenHelper.getInstance().UpdateTable("note","id="+curNoteId,values);
                    }

                    //更新我的笔记标签
                    String tag = editTag.getText().toString();
                    if(!tag.isEmpty())
                    {
                        String oldTag = GlobalData.curUser.dic.get("taglist")==null?"": GlobalData.curUser.dic.get("taglist").toString();
                        LogTool.prnit("旧的标签="+oldTag);
                        if(oldTag.contains(tag))
                        {
                            oldTag+=tag+"#";

                            ContentValues update = new ContentValues();
                            update.put("taglist",oldTag);
                            MySQLiteOpenHelper.getInstance().UpdateTable("user","id="+GlobalData.curUser.dic.get("id"),update);
                        }
                    }

                    //插入笔记或成功！跳转到笔记的主面板
                    Intent intent = new Intent(WriteNoteActivity.this, NoteListActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
