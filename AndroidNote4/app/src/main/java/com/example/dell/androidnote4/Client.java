package com.example.dell.androidnote4;

/**
 * Created by DELL on 2018/5/27.
 */

import android.content.ContentValues;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Client {
    private static Client _client = null;

    public static Client instance() throws IOException {
        if (_client == null) {
            _client = new Client();
            _client.Update();
        }
        return _client;

    }

    //阿里云服务器的IP地址和监听端口
    public static int portNum = 8088;
    public static String ServerIP ="119.23.54.163";//"192.168.0.104";//"119.23.54.163";////

    private  Socket _socket = null;

    //发送消息，成功执行了
    public  void SendMessage(final String message)
    {
        //2018-5-28 暂时去掉线程
        new Thread(new Runnable() {
             @Override
             public void run() {
                LogTool.prnit(this,"客户端：Connectin开始连接服务器");
                    //IP地址和端口号（对应服务端），我这的IP是本地路由器的IP地址
                 Socket  socket = null;
                try {
                    socket = new Socket(Client.ServerIP, Client.portNum);
                    _socket = socket;
                    LogTool.prnit(this,"客户端：构造socket成功 time"+GlobalData.GetDateStr());
                } catch (IOException e) {
                    e.printStackTrace();
                    LogTool.prnit(this,"构造socket失败！e="+e);
                }
                //发送给服务端的消息
                    //成功发给服务器了！
                    try {
                        LogTool.prnit(this,"客户端发送：" + message + "'");
                        //第二个参数为True则为自动flush

                        PrintWriter out = new PrintWriter(
                                new BufferedWriter(new OutputStreamWriter( _socket.getOutputStream())), true);
                        out.println(message);
//                      out.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogTool.prnit(this,"客户端：发送消息异常e="+e);
                    }


             }
         }).start();
    }

    //处理等待接收
    private void Update() throws IOException {
        new Thread(new Runnable() {      //开启线程连接服务端
            @Override
            public void run() {
                LogTool.prnit(this,"客户端开始：监听处理来自服务器的数据！");
                while (true)
                {
                    try {
                        InputStream input = _socket.getInputStream();
                        List<Byte> data = new ArrayList<>();
                        byte[] buffer = new byte[1024];
                        int length = 0;
                        try
                        {
                            while ((length = input.read(buffer)) > 0)
                            {
                                for (int j = 0; j < length; j++)
                                {
                                    data.add(buffer[j]);
                                }
                                if (length < buffer.length)
                                {
                                    break;
                                }
                            }
                        }
                        catch (Exception e){
                            LogTool.prnit(this,"处理消息异常！e+"+e);
                        }
                        if (data.size() > 0)
                        {
                            byte [] byteArray = new byte[data.size()];
                            for(int index=0;index<data.size();index++)
                            {
                                byteArray[index] = data.get(index);
                            }
                            //收到的消息体
                            String str = new String(byteArray,"UTF-8");
                            LogTool.prnit(this,"收到服务器："+str+"@");
                            //通知其他模块，去处理这个分享消息

                            //更新并插入到数据库啊！
                            if(str.startsWith(ConstData.ACTION_SHARE+ConstData.SUB_SIGN))
                            {
                                ContentValues values= new ContentValues();
                                String content = str.substring((ConstData.ACTION_SHARE+ConstData.SUB_SIGN).length());
                                LogTool.prnit(this,"消息体="+content);


                            }

                            //最后通知需要特别处理的activity
                            Message message = Message.obtain();
                            message.obj = str;
                            GlobalData.handerGroupEventListSetting.sendMessage(message); //发送给设置页面
                        }
                        //else {
                        //    LogTool.prnit("收到长度为0的包了");
                       // }

                    } catch (Exception e) {
                        System.out.println("监听socket异常！！e=" + e);
                        //错误
                    }
                }

            }
        }
        ).start();
    }

    //占位函数
    public  void TestNUll()
    {

    }
    //  public static void main(String[] args) {
    //     Client c = new Client();
    //   try {
    //    c.send("123456789,nimabinijiehunlema");

    //        System.out.println("发送成功");
    //   } catch (IOException e) {
    //     System.out.println("IO异常");
    //     e.printStackTrace();
    //   }}
}