package com.example.dell.androidnote4;

/**
 * Created by DELL on 2018/5/27.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() throws IOException{
        //服务端socket对象
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1",1234));

        while(true) {
            Socket socket = serverSocket.accept();
            //接收客户端发来的数据
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            if( (str = br.readLine()) != null) {
                System.out.println(str);
            }
            socket.close();
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        System.out.println("服务器端开启");
        try {
            s.run();
        } catch (IOException e) {
            System.out.println("IO异常");
            e.printStackTrace();
        }
    }
}