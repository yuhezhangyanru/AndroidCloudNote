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
    public static int portNumber = 0;//8;//内网需要监听8088，外网通过 48708这个访问;// 10002;

    public static void Main(string[] args)
    {
        //仅客户端测试时有用
        bool ClientSend = false;//本客户端是否需要发数据
        string serverAddress = "";
        bool useServerMode = false;//为false的时候只是为了本地测试发送！

        string ServerIp = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
        ServerIp = ServerIp.Substring(0, ServerIp.LastIndexOf(@"\")) + @"\";
        ServerIp += "ServerIp.txt";
        string settingContent = File.ReadAllText(ServerIp);

        Console.WriteLine("配置内容=" + settingContent);

        string[] lines = settingContent.Split('\n');
        for (int index = 0; index < lines.Length; index++)
        {
            string [] item = lines[index].Split('=');
            string itemKey = item[0];
            string itemValue = item[1];
            Console.WriteLine("配置项key="+itemKey+",itemValue="+itemValue);

            if (itemKey == "ServerIP")
            {
                serverAddress = itemValue;
                serverAddress = serverAddress.TrimEnd('\r');
                serverAddress = serverAddress.TrimEnd('\n');
            }
            else if (itemKey == "Port")
            {
                portNumber = int.Parse(itemValue);
            }
            else if (itemKey == "IsServer")
            {
                useServerMode = itemValue.Contains( "true");
            }
            else if (itemKey == "WriteLog")
            {
                LogTool.writeLog = itemValue == "true";
            }
            else if (itemKey == "ClientSend")
            {
                ClientSend = itemValue == "true";
            }
        }

        string serverInfo = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
        serverInfo = serverInfo.Substring(0, serverInfo.LastIndexOf(@"\")) + @"\";
        serverInfo += "ServerInfo.txt";
    
        string debugPath = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
        debugPath = debugPath.Substring(0, debugPath.LastIndexOf(@"\")) + @"\";
        debugPath += "Log.txt";

        LogTool.debugPath = debugPath;

        Console.WriteLine("输出日志路径=" + LogTool.debugPath);
        Console.WriteLine("(服务器或客户端)请输入你的身份：");
        string line =  Console.ReadLine();
        if (line.Contains("服务器"))
        {
            useServerMode = true;
        }
        else if(line.Contains("客户端"))
        {
            useServerMode = false;
        }
        Console.WriteLine("你是 " + (useServerMode ? "服务器!请输入你的IP：" : "客户端!请输入服务器的IP(有意义)："));
        if (useServerMode == false)
        {
            string readIP = Console.ReadLine();
            if (readIP != "")
            {
                serverAddress = readIP;
            }
        }

        Console.WriteLine("*****最终服务器IP=" + serverAddress + "#");
        Console.WriteLine("*****最终端口号:" + portNumber + "#");
        // Console.WriteLine("*****是否服务器模式:" + useServerMode + "#");
        Console.WriteLine("*****是否输出日志:" + LogTool.writeLog + "#");
        Console.WriteLine("*****本客户端是否会发送数据:" + ClientSend + "#");
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
            string testStr = "Client#123#" + "jasfakfjadkfjdksafshfahdfhfhds";
            string testTemp = testStr;
            System.Console.WriteLine("客户端启动！输入换行发送数据！");

          //、  if (ClientSend) //客户端模拟收发
          //  {
                long interval = 0;
                while (true)
                {
                    interval++;
                    if (interval > 600000000)
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
         //   }
          //  else
         //   {
          //  }
            
        }


        Console.ReadLine();//不关闭对话框
    }
}