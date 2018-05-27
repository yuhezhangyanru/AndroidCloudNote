package com.example.dell.androidnote4;

/**
 * Created by DELL on 2018/5/27.
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/*
 * 必须在服务器端开启的时候才能扫描成功
 */
public class ScanPorts {
    private int minPort, maxPort;

    public ScanPorts(int minPort, int maxPort) {
        this.minPort = minPort;
        this.maxPort = maxPort;
    }
    public void run() throws IOException {
        for(int i = minPort; i <= maxPort; i++) {
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(Client.ServerIP, i);
            socket.connect(socketAddress, 5000);
            LogTool.prnit("扫描服务器成功！"+i + ":ok");
            socket.close();
        }
    }

    public static void main(String[] args) {
        ScanPorts sc = new ScanPorts(Client.portNum, Client.portNum);
        try {
            sc.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}