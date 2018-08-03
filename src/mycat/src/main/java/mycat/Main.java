package mycat;

import java.io.IOException;
import java.util.Map;

import mycat.bean.ProductInfo;
import mycat.pojo.PROP;
import mycat.service.ProductInfoService;
import mycat.utils.DateUtil;
import mycat.utils.PropertiesUtil;

/**
 * 工具入口
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月31日]
 * @since  [云服务/模块版本]
 */
public class Main
{
    
    public static void main(String[] args)
    {
        /**
         * 0、解析配置文件，初始化系统，确定产品、版本及分析ID
         * 1、一次分析初始化信息并入库
         * 2、解析git/svn 提交日志并入库
         * 3、分析单个文件新增、删除行总数
         * 4、cct工具扫描信息解析入库
         * 5、计算各个员工的各个文件代码贡献量
         */
        long startTime = System.currentTimeMillis();
        
        String rootPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        System.out.println(rootPath);
        rootPath = rootPath.substring(1);
        rootPath = rootPath.substring(0, rootPath.lastIndexOf("/")+1);
        System.out.println("Work path is:"+rootPath);
        
        // 步骤0
        Map<String, String> prop = PropertiesUtil.get(rootPath.concat("mycat.properties"));//"D:\\mycat\\mycat.properties"; 
        String cctReportPath = prop.get(PROP.cct_report_xls.getKey());
        String commitLogPath = prop.get(PROP.commit_log_path.getKey());
        String mycatReportPath = prop.get(PROP.mycat_report_xls_path.getKey());
        String productId = prop.get(PROP.product_id.getKey());
        String productName = prop.get(PROP.product_name.getKey());
        String branchPath = prop.get(PROP.branch_path.getKey());
        String analysisBeginTime = prop.get(PROP.analysis_begin_time.getKey());
        String analysisEndTime = prop.get(PROP.analysis_end_time.getKey());
        String replaceStr = prop.get(PROP.replace_str.getKey());
        String supportFileType = prop.get(PROP.support_file_type.getKey());
        String[] fileType = null;
        if(null != supportFileType)
        {
            fileType = supportFileType.split("\\|");
        }
        else
        {
            System.err.println("错误：支持分析文件类型为空，程序退出！！！");
            System.exit(0);
        }
        
        System.out.println("\n\n--------------------------\n\n");
        
        // 步骤1
        ProductInfo data = new ProductInfo();
        ProductInfoService service = new ProductInfoService();
        int newId = service.getNewId();
        if (-1 == newId)
        {
            System.err.println("错误：创建新的分析ID失败，程序退出！！！");
            System.exit(0);
        }
        
        data.setId(newId);
        data.setProductname(productName);
        data.setProductid(productId);
        data.setBranchpath(branchPath);
        data.setBegintime(DateUtil.strToDate(analysisBeginTime));
        data.setEndtime(DateUtil.strToDate(analysisEndTime));
        service.insert(data);
        
        // 步骤2、3
        try
        {
            new AnalysisLog(commitLogPath, productId, newId, fileType).work();
        }
        catch (IOException e)
        {
            System.err.println("错误：分析提交日志失败，程序退出！！！");
            e.printStackTrace();
            System.exit(0);
        }
        
        // 步骤4
        new AnalysisCctExcel(newId, cctReportPath, replaceStr).work();
        
        // 步骤5
        new ExportReport(newId, mycatReportPath).work();
        
        System.out.println("Total toast time is:"+(System.currentTimeMillis() - startTime)/1000+" minutes.");
    }
    
}
