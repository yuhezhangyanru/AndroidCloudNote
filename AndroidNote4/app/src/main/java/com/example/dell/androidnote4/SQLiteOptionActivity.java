package com.example.dell.androidnote4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SQLiteOptionActivity extends AppCompatActivity {
    private Button insert;
    private Button modify;
    private Button delete;
    private Button query;
    private Button delete_database;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_option);
        context = getApplicationContext();

        //绑定按钮
        //   instablish = (Button) findViewById(R.id.instablish);
        insert = (Button) findViewById(R.id.insert);
        modify = (Button) findViewById(R.id.modify);
        delete = (Button) findViewById(R.id.delete);
        query = (Button) findViewById(R.id.query);
        delete_database = (Button) findViewById(R.id.delete_database);

        //设置监听器


        //点击创建数据库库
        // instablish.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v) {
        // 创建SQLiteOpenHelper子类对象
        //  MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(context, ConstData.DBName);
        //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
        //  SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
        // SQLiteDatabase  sqliteDatabase = dbHelper.getReadbleDatabase();
        //   }
        // });

        //点击插入数据到数据库
        insert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("插入数据");
                // 创建SQLiteOpenHelper子类对象
                // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase sqlOption = MySQLiteOpenHelper.getInstance().getWritableDatabase();
                // 创建ContentValues对象
                ContentValues values1 = new ContentValues();
                // 向该对象中插入键值对
                values1.put("name", "carson");
                // 调用insert()方法将数据插入到数据库当中
                sqlOption.insert("user", null, values1);
                //关闭数据库
                sqlOption.close();
            }
        });

        //点击修改数据
        modify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("修改数据");

                // 创建一个DatabaseHelper对象
                // 将数据库的版本升级为2
                // 传入版本号为2，大于旧版本（1），所以会调用onUpgrade()升级数据库
                MySQLiteOpenHelper dbHelper2 = new MySQLiteOpenHelper(SQLiteOptionActivity.this, ConstData.DBName, 2);

                // 调用getWritableDatabase()得到一个可写的SQLiteDatabase对象
                SQLiteDatabase sqliteDatabase2 = dbHelper2.getWritableDatabase();

                // 创建一个ContentValues对象
                ContentValues values2 = new ContentValues();
                values2.put("name", "zhangsan");

                // 调用update方法修改数据库
                sqliteDatabase2.update("user", values2, "id=?", new String[]{"1"});

                //关闭数据库
                sqliteDatabase2.close();
            }
        });

        //点击删除数据
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("删除数据");
                // 创建DatabaseHelper对象
                MySQLiteOpenHelper dbHelper3 = new MySQLiteOpenHelper(SQLiteOptionActivity.this, ConstData.DBName, 2);

                // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase sqliteDatabase3 = dbHelper3.getWritableDatabase();
                //删除数据
                sqliteDatabase3.delete("user", "id=?", new String[]{"1"});
                //关闭数据库
                sqliteDatabase3.close();
            }
        });

        //点击查询数据库
        query.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("查询数据");
                // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase sqliteDatabase4 = MySQLiteOpenHelper.getInstance().getReadableDatabase();
                // 调用SQLiteDatabase对象的query方法进行查询
                // 返回一个Cursor对象：由数据库查询返回的结果集对象
                String whereID = "carson";
                Cursor cursor = sqliteDatabase4.query("user", new String[]{"id",
                        "name"}, "name=?", new String[]{whereID}, null, null, null);
                String id = null;
                String name = null;
                //将光标移动到下一行，从而判断该结果集是否还有下一条数据
                //如果有则返回true，没有则返回false
                while (cursor.moveToNext()) {
                    id = cursor.getString(cursor.getColumnIndex("id"));
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    //输出查询结果
                    System.out.println("查询到的数据是:" + "id: " + id + "  " + "name: " + name);
                }
                //关闭数据库
                sqliteDatabase4.close();
            }
        });

        //点击删除数据库
        delete_database.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogTool.prnit("删除数据库");
                // 调用getReadableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase sqliteDatabase5 = MySQLiteOpenHelper.getInstance().getReadableDatabase();
                //删除名为test.db数据库
                deleteDatabase(ConstData.DBName);
            }
        });
    }
}
