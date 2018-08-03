package mycat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * java执行cmd命令工具
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月3日]
 * @since  [云服务/模块版本]
 */
public class CmdUtil
{
    
    public void executeCMDconsole(String cmd)
    {
        System.out.println("在cmd里面输入" + cmd);
        Process p;
        try
        {
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader bReader=new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
            String line=null;
            while((line=bReader.readLine())!=null)
                System.out.println(line);
            p.destroy();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public String executeCMDfile(String[] cmmands, String logToFile, String dirTodoCMD)
        throws IOException
    {
        try
        {
            ProcessBuilder builder = new ProcessBuilder(cmmands);
            if (dirTodoCMD != null)
                builder.directory(new File(dirTodoCMD));
            builder.redirectErrorStream(true);
            builder.redirectOutput(new File(logToFile));
            Process process = builder.start();
            process.waitFor();
            
            // 得到命令执行后的结果
            InputStream is = process.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "gbk"));
            String line = null;
            StringBuffer sbBuffer = new StringBuffer();
            while ((line = buffer.readLine()) != null)
            {
                sbBuffer.append(line);
            }
            
            is.close();
            return sbBuffer.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args)
        throws IOException
    {
        CmdUtil adbc = new CmdUtil();
        adbc.executeCMDconsole("cmd.exe /k start D:\\mycat\\mycat.bat");
    }
    
}
