package mycat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import mycat.bean.CatInfo;
import mycat.bean.ProductInfo;
import mycat.bean.SingleFileAuthor;
import mycat.bean.SingleFileTotle;
import mycat.utils.DateUtil;
import mycat.utils.GitLogUtil;
import mycat.utils.SingletonSession;

/**
 * 分析git/svn 提交log
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月31日]
 * @since  [云服务/模块版本]
 */
public class AnalysisLog
{
    /** 提交日志路径**/
    private String logPath;
    
    private String productId;
    
    private int analysisId;
    
    private SqlSessionFactory sf;
    
    private String[] fileType;
    
    public AnalysisLog(String logPath, String productId, int analysisId, String[] fileType)
    {
        this.logPath = logPath;
        this.productId = productId;
        this.analysisId = analysisId;
        sf = SingletonSession.getInstance().getSf();
        this.fileType = fileType;
    }
    
    public void work()
        throws IOException
    {
        long startTime = System.currentTimeMillis();
        // 随机读取文件、解析
        randomReed(logPath, 0, productId, fileType);
        
        // 计算单次分析时间段类各个文件修改总数，被各个用户修改总数
        analysisCommitLog(analysisId);
        System.out.println("AnalysisLog toast time is:" + (System.currentTimeMillis() - startTime) / 1000 + " minutes.");
    }
    
    private void analysisCommitLog(int analysisId)
    {
        // 通过分析ID获取这次分析的时间段
        Date[] times = getDateByAnalysisId(analysisId);
        if (null != times && 2 == times.length)
        {
            
            // 通过时间段从cat_info_tb获取一个文件在此时间段内修改的总量信息并入库
            totalModify(analysisId, times[0], times[1]);
            
            // 通过时间段从cat_info_tb获取一个文件在此时间段内各个作者修改的总量信息并入库
            authorModify(analysisId, times[0], times[1]);
        }
    }
    
    /**
     * 分文件统计修改总量
     * <功能详细描述>
     * @param analysisId
     * @param begin
     * @param end
     */
    private void totalModify(int analysisId, Date begin, Date end)
    {
        SqlSession session = null;
        try
        {
            session = sf.openSession();
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("begintime", begin);
            para.put("endtime", end);
            
            // 查询数据
            List<SingleFileTotle> data = session.selectList("CatInfoMapper.selectFileTotalModify", para);
            
            // 数据入库
            if (null != data)
            {
                int i = 0;
                for (SingleFileTotle one : data)
                {
                    one.setId(analysisId);
                    session.insert("SingleFileTotleMapper.insertSelective", one);
                    if (i % 20 == 0)
                    {
                        session.commit();
                    }
                    i++;
                }
                session.commit();
            }
        }
        finally
        {
            if (null != session)
            {
                session.close();
            }
        }
    }
    
    /**
     * 分文件分用户统计修改总量
     * <功能详细描述>
     * @param analysisId
     * @param begin
     * @param end
     */
    private void authorModify(int analysisId, Date begin, Date end)
    {
        SqlSession session = null;
        try
        {
            session = sf.openSession();
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("begintime", begin);
            para.put("endtime", end);
            
            // 查询数据
            List<SingleFileAuthor> data = session.selectList("CatInfoMapper.selectFileTotalAuthor", para);
            
            // 数据入库
            if (null != data)
            {
                int i = 0;
                for (SingleFileAuthor one : data)
                {
                    one.setId(analysisId);
                    session.insert("SingleFileAuthorMapper.insertSelective", one);
                    if (i % 20 == 0)
                    {
                        session.commit();
                    }
                    i++;
                }
                session.commit();
            }
        }
        finally
        {
            if (null != session)
            {
                session.close();
            }
        }
    }
    
    /**
     * 根据分析ID获取时间段
     * <功能详细描述>
     * @param analysisId
     * @return
     */
    private Date[] getDateByAnalysisId(int analysisId)
    {
        SqlSession session = null;
        try
        {
            Date[] out = null;
            session = sf.openSession();
            ProductInfo one = session.selectOne("ProductInfoMapper.selectByPrimaryKey", analysisId);
            if (null != one)
            {
                out = new Date[2];
                out[0] = one.getBegintime();
                out[1] = one.getEndtime();
            }
            return out;
        }
        finally
        {
            if (null != session)
            {
                session.close();
            }
        }
    }
    
    /** 
     * 随机读取git log的方法 
     * @param path 文件路径 
     * @param pointe 指针位置 
     * @throws IOException 
     * **/
    private void randomReed(String path, int pointe, String productId, String[] fileType)
        throws IOException
    {
        RandomAccessFile raf = null;
        List<String> data = new ArrayList<String>();
        List<CatInfo> catInfos = new ArrayList<CatInfo>();
        SqlSession session = null;
        Date latestTime = getLastTime(productId);
        try
        {
            /** 
             * model各个参数详解 
             * r 代表以只读方式打开指定文件 
             * rw 以读写方式打开指定文件 
             * rws 读写方式打开，并对内容或元数据都同步写入底层存储设备 
             * rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备 
             *  
             * **/
            session = sf.openSession();
            
            raf = new RandomAccessFile(path, "r");
            
            // 获取RandomAccessFile对象文件指针的位置，初始位置是0  
            
            raf.seek(pointe);//移动文件指针位置  
            
            String temp = null;
            String[] splits = null;
            String[] splitstt = null;
            CatInfo oneInfo = null;
            
            //循环读取  
            while ((temp = raf.readLine()) != null)
            {
                
                // 遇到空行则开始分析
                if ("".equals(temp))
                {
                    // 第一行是作者、提交时间信息，后续是文件改动情况
                    splits = data.get(0).split("\\|");
                    
                    //如果解析到当前提交批次的时间在数据库中保存的最新时间之前则停止下面的解析
                    if (DateUtil.strToDate(splits[3]).before(latestTime))
                    {
                        break;
                    }
                    for (int i = 1; i < data.size(); i++)
                    {
                        if (FileType.isContain(GitLogUtil.suffix(data.get(i)), fileType))
                        {
                            splitstt = data.get(i).split("\\t");//GitLogUtil.split(data.get(i));
                            oneInfo = new CatInfo();
                            oneInfo.setProductid(productId);
                            oneInfo.setAuthorid(splits[2]);
                            oneInfo.setCommittime(DateUtil.strToDate(splits[3]));
                            oneInfo.setAddline(Integer.parseInt(splitstt[0]));
                            oneInfo.setDeleteline(Integer.parseInt(splitstt[1]));
                            oneInfo.setFilepath(splitstt[2].toLowerCase());
                            catInfos.add(oneInfo);
                        }
                    }
                    
                    data.clear();
                    
                    // 入库
                    for (CatInfo one : catInfos)
                    {
                        //                        System.out.println(JSON.toJSONString(one));
                        session.insert("CatInfoMapper.insertSelective", one);
                    }
                    catInfos.clear();
                    session.commit();
                    
                    continue;
                }
                
                data.add(temp);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != raf)
            {
                raf.close();
            }
            if (null != session)
            {
                session.close();
            }
        }
    }
    
    /**
     * 通过productId查询最新的gitlog分析时间
     * <功能详细描述>
     * @param productId
     * @return
     */
    private Date getLastTime(String productId)
    {
        CatInfo data = null;
        SqlSession session = null;
        try
        {
            session = sf.openSession();
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("productId", productId);
            data = session.selectOne("CatInfoMapper.getLatestCommitTime", para);
            Date out = DateUtil.strToDate("2000-01-01 00:00:00");
            if (null != data)
            {
                out = data.getCommittime();
            }
            return out;
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
