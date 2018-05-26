package com.example.dell.androidnote4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * Created by Carson_Ho on 16/11/18.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    //数据库版本号
    private static Integer Version = 1;

    //在SQLiteOpenHelper的子类当中，必须有该构造函数
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);
    }

    private static MySQLiteOpenHelper _instance = null;
    public static MySQLiteOpenHelper getInstance()
    {
        return  _instance;
    }
    ///创建全局访问实例
    public static void CreateInstance(Context context,String name,int version) {
        _instance = new MySQLiteOpenHelper(context,name,version);
    }

    //参数说明
    //context:上下文对象
    //name:数据库名称
    //param:factory
    //version:当前数据库的版本，值必须是整数并且是递增的状态

    public MySQLiteOpenHelper(Context context,String name,int version)
    {
        this(context,name,null,version);
        LogTool.prnit("创建MySQLiteOpenHelper 数据库名="+name+",version="+version);
    }


    public MySQLiteOpenHelper(Context context,String name)
    {
        this(context, name, Version);
    }

    //当数据库创建的时候被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        LogTool.prnit("创建数据库和表path="+db.getPath());
        //创建了数据库并创建一个叫records的表
        //SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型

        user u = new user();
        String sqlUser = "create table user(id INTEGER primary key AUTOINCREMENT";
        sqlUser+=",username varchar(200)";
        sqlUser+=",password varchar(200)";
        sqlUser+=",email varchar(200)";
        sqlUser+=",phonenumber varchar(200)";
        sqlUser+=",headid varchar(200)";
        sqlUser+=",groupid varchar(200))";

        CreateTable(db,sqlUser);

        //execSQL用于执行SQL语句
        //完成数据库的创建
        //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
    }

    ///初始化表结构
    private  void CreateTable(SQLiteDatabase db,String sql)
    {
        LogTool.prnit("创建表结构sql="+sql);
        db.execSQL(sql);
    }

    //数据库升级时调用
    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade（）方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogTool.prnit("更新数据库版本为:"+newVersion);
    }


    //获得满足某个条件元素的个数
    public int GetItemCount(String tableName,String columnName,String columnValue)
    {
        SQLiteDatabase sqliteDatabase4 = MySQLiteOpenHelper.getInstance().getReadableDatabase();
        // 调用SQLiteDatabase对象的query方法进行查询
        // 返回一个Cursor对象：由数据库查询返回的结果集对象
        Cursor cursor = sqliteDatabase4.query(tableName, new String[]{"id"}, columnName+"=?", new String[]{columnValue}, null, null, null);
        int count =0;
        //将光标移动到下一行，从而判断该结果集是否还有下一条数据
        //如果有则返回true，没有则返回false
        while (cursor.moveToNext()) {
            count ++;
        }
        //关闭数据库
        sqliteDatabase4.close();
        return count;
    }

    //插入数据
    public String InsertTable(String tableName,ContentValues values)
    {
        try{
            SQLiteDatabase sqlOption = getWritableDatabase();
//            values1.put("name", "carson");
            sqlOption.insert(tableName, null, values);
            LogTool.prnit("成功插入表="+tableName+"，数据内容="+values);
            sqlOption.close();
            return "";
        }
        catch (Exception e)
        {
           LogTool.prnit("插入表="+tableName+"的数据="+values+"失败！失败原因="+e);
           return "插入数据失败！";
        }
    }

    ///分别指定表名 和 where条件获取查询结果
    public SelectResInfo SelectTable(String tableName,String sqlWhere)
    {
        SQLiteDatabase dbOption = MySQLiteOpenHelper.getInstance().getReadableDatabase();
        String sql = "select * from "+tableName +" "+sqlWhere;
        LogTool.prnit("执行查询sql="+sql);
        Cursor cursor = dbOption.rawQuery(sql,null);
        SelectResInfo info = new SelectResInfo();
        int readIndex=0;
        while (cursor.moveToNext()) {
            for (int index = 0; index < cursor.getColumnCount(); index++) {
                String columnName = cursor.getColumnName(index);
                String columValue = cursor.getString(index);
                LogTool.prnit("查询表=" + tableName + "元素["+readIndex+"]结果列名=" + columnName + ",值=" + columValue);
                info.dic.put(columnName,columValue);
            }
            readIndex++;
        }
        //关闭数据库
        dbOption.close();
        return info;
    }

}
