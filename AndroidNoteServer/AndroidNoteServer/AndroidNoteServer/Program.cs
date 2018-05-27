using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System;
using System.IO;
using System;
using System.Collections.Generic;
using System;
using System.IO;

public class Program
{
    public const int portNumber = 8088;//内网需要监听8088，外网通过 48708这个访问;// 10002;

    public static void Main(string[] args)
    {
        string ServerIp = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
        ServerIp = ServerIp.Substring(0, ServerIp.LastIndexOf(@"\")) + @"\";
        ServerIp += "ServerIp.txt";

        //仅客户端测试时有用
        string serverAddress =   File.ReadAllText(ServerIp);
        string debugPath = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
        debugPath = debugPath.Substring(0, debugPath.LastIndexOf(@"\")) + @"\";
        debugPath += "Log.txt";

        LogTool.debugPath = debugPath;

        Console.WriteLine("输出日志路径=" + LogTool.debugPath);
        bool useServerMode = true;//为false的时候只是为了本地测试发送！
        ////暂时有效！！
        if (useServerMode)
        {
            Console.WriteLine("我是服务器");
            SocketTest.instance.Listen(portNumber); //服务器新的监听
        }
        else
        {
            Console.WriteLine("我是客户端");
            //客户端发送
            string testStr = "Client#" + "jasfakfjadkfjdksafshfahdfhfhds";
            string testTemp = testStr;
            System.Console.WriteLine("客户端启动！输入换行发送数据！");

            int interval = 0;
            while (true)
            {
                interval++;
                if (interval > 1000000000)
                {
                    interval = 0;

                    //客户端发送
                    Console.WriteLine("准备发送消息s=" + testStr);
                    try
                    {
                        SocketTest.instance.Send(serverAddress, portNumber, testStr);
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("连接服务器失败！");
                    }
                } 
            }
        }


        Console.ReadLine();//不关闭对话框
    }
}