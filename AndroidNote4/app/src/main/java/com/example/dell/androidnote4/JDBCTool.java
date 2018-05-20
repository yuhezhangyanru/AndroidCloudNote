package com.example.dell.androidnote4;

import android.database.SQLException;

import com.example.dell.androidnote4.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCTool {

    /**
     * 插入新用户
     * @param username
     * @param password
     * @param email
     * @param headid
     * @param phonenum
     * @return
     */
    public static int AddUserInfo(String username,String password,String email,String phonenum,String headid,String groupid) {
        int i=0;
        String sql="insert into user() values (?,?,?,?,?,?,?)";
        DBConnection db = new DBConnection();
        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            preStmt.setString(1, username);
            preStmt.setString(2, password);
            preStmt.setString(3, email);
            preStmt.setString(5, phonenum);
            preStmt.setString(4, headid);
            preStmt.setString(6,groupid); //新建的用户是没有分组的
            preStmt.executeUpdate();
            //Statement statement = (Statement) db.conn.createStatement();
            //statement.executeUpdate(sql);
            preStmt.close();
            db.close();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }

    //查找操作
    public static void show(){
        String sql ="select * from employee";
        DBConnection db = new DBConnection();

        System.out.println("-----------------");
        System.out.println("姓名" +"\t"+ "邮箱" +"\t"+ "日期");
        System.out.println("-----------------");

        try {
            Statement stmt = (Statement)db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            while(rs.next()){
                String uname = rs.getString("name");
                String uemail = rs.getString("email");
                String uhiredate = rs.getString("hiredate");
                //可以将查找到的值写入类，然后返回相应的对象
                //这里 先用输出的端口显示一下
                System.out.println(uname +"\t"+ uemail +"\t"+ uhiredate);
            }
            rs.close();
            db.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    //更新操作
    public static int update(String uname,String uemail,String upwd) {
        int i =0;
        String sql="update employee set email=?,pwd=? where name=?";
        DBConnection db = new DBConnection();

        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            preStmt.setString(1, uemail);
            preStmt.setString(2, upwd);
            preStmt.setString(3, uname);
            preStmt.executeUpdate();

            preStmt.close();
            db.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }
    //删除操作
    public static int del(String uname) {
        int i=0;
        String sql="delete from employee where name=?";
        DBConnection db = new DBConnection();
        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            preStmt.setString(1, uname);
            preStmt.executeUpdate();

            preStmt.close();
            db.close();//关闭连接
        } catch (SQLException e){
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }
}