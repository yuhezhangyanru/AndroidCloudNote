using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

public class SocketTest
{
    private static SocketTest _instance = null;
    public static SocketTest instance
    {
        get
        {
            if (_instance == null)
                _instance = new SocketTest();
            return _instance;
        }
    }

    //为了去掉等号符号
    public const string DATE_TIME_OLD_FLAG = "CP=&&DataTime";
    public const string DATE_TIME_NEW_FLAG = "DataTime";

    private static Encoding encode = Encoding.UTF8;//.Default;

    private Socket listenSocket = null;

    /// <summary>
    /// 监听请求
    /// </summary>
    /// <param name="port"></param>
    public void Listen(int port)
    {
        try
        {
            listenSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            listenSocket.Bind(new IPEndPoint(IPAddress.Any, port));//2018-04-18屏蔽
            listenSocket.Listen(100);
            Console.WriteLine("Listen Port:" + port + ",date=" + DateTime.Now.ToLongDateString() + "," + DateTime.Now.ToShortTimeString());

            Thread m_thread = new Thread((obj) => { WaitingGetData(listenSocket); });
            m_thread.Start();

        }
        catch (Exception ex)
        {
            Console.WriteLine("端口绑定失败！请检查端口=" + port + "状态！error=" + ex.Message);
            Console.Read();
        }
    }

    /// <summary>
    /// 等待接收数据
    /// </summary>
    private void WaitingGetData(object obj)
    {
        while (true)
        {
            Socket listenSocket = obj as Socket;

            Socket acceptSocket = listenSocket.Accept();

            string receiveData = Receive(acceptSocket, 5000); //5 seconds timeout.

            Console.WriteLine("time=" + DateTime.Now.ToLongTimeString() +",s="+DateTime.Now.Second+ ",Receive：" + receiveData + "#");
            //ParseDatafromBytes(byteArray);
            // ParseDataAsStr(receiveData);
            //服务器转发出去

            string resend = receiveData;
            //yanruTODO需要的日志：
            //分组列表
           // if (resend != "") //服务器纯转发，指令由客户端去定。
         //   {
                Console.WriteLine("服务器发送:" + resend);
                // LogTool.WriteServerLog(resend);
            //    LogTool.WriteLog("服务器发送:" + resend);
                acceptSocket.Send(encode.GetBytes(resend));//encode.GetBytes("ok"));
          //  }
            DestroySocket(acceptSocket); //import
        }
    }

    

    /// <summary>
    /// 发送数据.模拟第三方给我发数据
    /// </summary>
    /// <param name="host"></param>
    /// <param name="port"></param>
    /// <param name="data"></param>
    /// <returns></returns>
    public string Send(string host, int port, string data)
    {
        string result = string.Empty;
        try
        {
            Socket clientSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            clientSocket.Connect(host, port);
            clientSocket.Send(encode.GetBytes(data));
            Console.WriteLine("Send：" + data);
            result = Receive(clientSocket, 5000 * 2); //5*2 seconds timeout.
           

            Console.WriteLine("来自服务器 Receive：" + result);
            DestroySocket(clientSocket);
        }
        catch (Exception e)
        {
            Console.WriteLine("发送不了数据e=" + e);
        }
        return result;
    }

    public static void OnlyWaitServerData()
    { 
    }

    /// <summary>
    /// 接收数据
    /// </summary>
    /// <param name="socket"></param>
    /// <param name="timeout"></param>
    /// <returns></returns>
    private static string Receive(Socket socket, int timeout)
    {
        string result = string.Empty;
        socket.ReceiveTimeout = timeout;
        List<byte> data = new List<byte>();
        byte[] buffer = new byte[1024];
        int length = 0;
        try
        {
            while ((length = socket.Receive(buffer)) > 0)
            {
                for (int j = 0; j < length; j++)
                {
                    data.Add(buffer[j]);
                }
                if (length < buffer.Length)
                {
                    break;
                }
            }
        }
        catch { }
        if (data.Count > 0)
        {
            //、、 byteArray = data.ToArray();
            result = encode.GetString(data.ToArray(), 0, data.Count);
        }
        return result;
    }
    /// <summary>
    /// 销毁Socket对象
    /// </summary>
    /// <param name="socket"></param>
    private static void DestroySocket(Socket socket)
    {
        if (socket.Connected)
        {
            socket.Shutdown(SocketShutdown.Both);
        }
        socket.Close();
    }
}