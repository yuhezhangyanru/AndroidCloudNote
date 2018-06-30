using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
public class LogTool
{
    public static bool writeLog = false;

    /// <summary>
    /// 服务器日志文件
    /// </summary>
    public static string serverInfo = "";
    public static string debugPath = ""; //输出报错文件

    /// <summary>
    /// 修改配置信息
    /// </summary>
    /// <param name="LogType"></param>
    /// <param name="logStr"></param>
    public static void WriteServerLog(string LogType, string logStr)
    {
         using (StreamWriter sw = new StreamWriter(serverInfo+LogType))
            {
                sw.WriteLine(logStr);
         }
    }

    /// <summary>
    /// 获取当前的配置信息
    /// </summary>
    /// <param name="LogType"></param>
    /// <param name="logStr"></param>
    /// <returns></returns>
    public static string   GetServerLog(string LogType)//,string logStr)
    {
        String oldContent = "";
        using (StreamReader sr = new StreamReader(serverInfo + LogType))
        {
            string line;
            // 从文件读取并显示行，直到文件的末尾 
            while ((line = sr.ReadLine()) != null)
            {
                oldContent += line + "\n";
            }
        }
        return oldContent;
    }

    public static void WriteLog(string logStr)
    {
        if (!writeLog)
            return;
        WriteLogToPath(debugPath, logStr);
    }

    private static void WriteLogToPath(string path, string logStr)
    {
        try
        {
            string oldContent = "";
            if (!File.Exists(path))
            {
                FileStream fs = new FileStream(path, FileMode.CreateNew);
                StreamWriter sw = new StreamWriter(fs);
                sw.Write("输出错误日志：");  //这里是写入的内容
                sw.Flush();
                sw.Close();
            }
            else
            {
                using (StreamReader sr = new StreamReader(path))
                {
                    string line;
                    // 从文件读取并显示行，直到文件的末尾 
                    while ((line = sr.ReadLine()) != null)
                    {
                        oldContent += line + "\n";
                    }
                }
            }
            oldContent += DateTime.Now.ToLongDateString() + "" + DateTime.Now.ToShortTimeString() + "[ERROR]:" + logStr + "\n";
            using (StreamWriter sw = new StreamWriter(path))
            {
                sw.WriteLine(oldContent);
            }
        }
        catch (Exception e)
        {
            Console.WriteLine("写入文件失败e=" + e.Message);
        }
    }
}
