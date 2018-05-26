package com.example.dell.androidnote4;
/**
 * Created by liyanzhen on 16/7/20.
 */
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class UtilMe {
    private static UtilMe _instance = null;
    public static UtilMe instance () {
        if (_instance == null)
            _instance = new UtilMe();
        return _instance;
    }

    public Connection conn;
    final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
    static String driver = "com.mysql.jdbc.Driver";
    static String URL= "jdbc:mysql://192.168.0.104:3306/android_note_db"; //TODO modifyy database IP
    static String USER = "root";
    static String PASSWORD = "123456";

    public static Connection openConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            conn = null;
            LogTool.prnit("连接异常ClassNotFoundException="+e);
        } catch (SQLException e) {
            conn = null;
            LogTool.prnit("连接异常SQLException="+e);
        }
        LogTool.prnit("初始化连接！"+conn+",旧连接="+instance().conn);
        return conn;
    }

    public  int query(String sql) {
        instance().conn=  openConnection();
        LogTool.prnit("query 连接为空么?"+(conn==null));
        if (conn == null) {
            return 2;
        }
        int resultCount = 0;
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            LogTool.Log("查询结果result=" + result);
            if (result != null && result.first()) {
                int idColumnIndex = result.findColumn("id");
                int nameColumnIndex = result.findColumn("name");
                LogTool.Log("查询结果id\t\t" + "name");
                while (!result.isAfterLast()) {
                    LogTool.Log("查询结果column=" + result.getString(nameColumnIndex) + ",content=" + result.getString(idColumnIndex) + "\t\t");
                    result.next();
                    resultCount++;
                }
            }
            return resultCount;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return resultCount;
    }

    public static boolean execSQL(Connection conn, String sql) {
        boolean execResult = false;
        if (conn == null) {
            return execResult;
        }
        Statement statement = null;
        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
        }
        return execResult;
    }

    //以下全部为应用层接口
    //插入新用户
    public int InsertData(final String sql)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    execSQL(conn, sql);
                    LogTool.prnit("成功插入!数据SQL="+sql);
                }
                catch (Exception e){
                    LogTool.prnit("插入失败!错误原因="+e.getMessage()+",sql="+sql);
                }
            }
        }).start();
        return 0;
    }

}