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
    public static string debugPath = ""; //输出报错文件

    public static void WriteLog(string logStr)
    {
        try
        {
            string oldContent = "";
            if (!File.Exists(debugPath))
            {
                FileStream fs = new FileStream(debugPath, FileMode.CreateNew);
                StreamWriter sw = new StreamWriter(fs);
                sw.Write("输出错误日志：");  //这里是写入的内容
                sw.Flush();
                sw.Close();
            }
            else
            {
                using (StreamReader sr = new StreamReader(debugPath))
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
            using (StreamWriter sw = new StreamWriter(debugPath))
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
