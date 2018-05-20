package com.example.dell.androidnote4;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by DELL on 2018/5/20.
 */

public class DBConnection {

    String driver = "com.mysql.jdbc.Driver";
    String url= "jdbc:mysql://localhost:3306/android_note_db"; //TODO modifyy database IP
    String user = "root";
    String password = "123456";

    public Connection conn;

    public DBConnection() {

        try {
            Class.forName(driver);// 加载驱动程序
            conn = (Connection) DriverManager.getConnection(url, user, password);// 连续数据库

            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
