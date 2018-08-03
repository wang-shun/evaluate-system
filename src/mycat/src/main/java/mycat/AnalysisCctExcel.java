package mycat;

//import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mycat.bean.CctInfo;
import mycat.pojo.INFO;
import mycat.utils.ExcelUtil;
import mycat.utils.SingletonSession;

/**
 * 解析cct工具生成的excel数据并入库
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月1日]
 * @since  [云服务/模块版本]
 */
public class AnalysisCctExcel
{
    
    /*private Date begin;
    
    private Date end;*/
    private int analysisId;
    
    private String filePath;
    
    private SqlSessionFactory sf;
    
    private String replaceStr;
    
    public AnalysisCctExcel(int analysisId, String filePath, String replaceStr)//(Date begin, Date end)
    {
        /*this.begin = begin;
        this.end = end;*/
        this.analysisId = analysisId;
        this.filePath = filePath;
        sf = SingletonSession.getInstance().getSf();
        this.replaceStr = replaceStr;
    }
    
    public void work()
    {
        long startTime = System.currentTimeMillis();
        // 解析excel，得到时间段内各个文件的增删改量
        Map<INFO, List<CctInfo>> data = ExcelUtil.readCctInfoFromExcel(filePath, analysisId, replaceStr);
        List<CctInfo> error = data.get(INFO.error);
        if (null != error && error.size() > 0)
        {
            System.err.println("CCT excel data analysis error!!!");
            return;
        }
        
        List<CctInfo> right = data.get(INFO.right);
        
        // 数据入库
        dataToDB(right);
        System.out.println("AnalysisCctExcel toast time is:" + (System.currentTimeMillis() - startTime) / 1000 + " minutes.");
    }
    
    /**
     * 解析数据入库
     * <功能详细描述>
     * @param data
     */
    private void dataToDB(List<CctInfo> data)
    {
        if (null != data)
        {
            SqlSession session = null;
            try
            {
                session = sf.openSession();
                int i = 0;
                for (CctInfo one : data)
                {
                    session.insert("CctInfoMapper.insertSelective", one);
                    if (i % 20 == 0)
                    {
                        session.commit();
                    }
                    i++;
                }
                session.commit();
            }
            finally
            {
                if (null != session)
                {
                    session.close();
                }
            }
        }
    }
}
