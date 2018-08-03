package mycat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

import mycat.bean.StatisticalResults;
import mycat.utils.SingletonSession;

/**
 * 导出个人工作量报告
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年8月2日]
 * @since  [云服务/模块版本]
 */
public class ExportReport
{
    
    private int analysisId;
    
    private String createFilePath;
    
    public ExportReport(int analysisId, String createFilePath)
    {
        this.analysisId = analysisId;
        this.createFilePath = createFilePath;
    }
    
    public void work()
    {
        long startTime = System.currentTimeMillis();
        // 执行SQL查询数据
        List<StatisticalResults> data = getData(analysisId);
        
        // 将数据导出
        makeReport(createFilePath, data);
        System.out.println("ExportReport toast time is:" + (System.currentTimeMillis() - startTime) / 1000 + " minutes.");
    }
    
    /**
     * 根据分析ID查询这次下所有作者的工作量数据
     * <功能详细描述>
     * @param analysisId
     * @return
     */
    private List<StatisticalResults> getData(int analysisId)
    {
        List<StatisticalResults> out = new ArrayList<StatisticalResults>();
        SqlSessionFactory sf = SingletonSession.getInstance().getSf();
        SqlSession session = null;
        try
        {
            session = sf.openSession();
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("analysisId", analysisId);
            out = session.selectList("CatInfoMapper.getWorkloadData", para);
        }
        finally
        {
            if (null != session)
            {
                session.close();
            }
        }
        return out;
    }
    
    /**
     * 生成xls报告
     * <功能详细描述>
     * @param createFilePath
     * @param data
     */
    private void makeReport(String createFilePath, List<StatisticalResults> data)
    {
        if (data != null && data.size() > 0)
        {
            try
            {
                HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
                HSSFSheet sheet = workbook.createSheet("个人工作量"); // 创建工作表
                sheet.setColumnWidth(0, 9000); // 第一列列宽加长
                sheet.setColumnWidth(1, 4500); // 第二列列宽加长
                sheet.setColumnWidth(2, 4500); // 第三列列宽加长
                sheet.setColumnWidth(3, 4500); // 第四列列宽加长
                
                HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
                HSSFCellStyle style = this.getStyle(workbook);
                
                // 定义所需列数
                String[] rowName = {"作者", "新增行数", "修改行数", "删除行数"};
                int columnNum = rowName.length;
                HSSFRow rowRowName = sheet.createRow(0); // 在索引1的位置创建行(最顶端的行开始的第一行)
                
                rowRowName.setHeight((short)(25 * 30)); //设置高度
                
                // 将列头设置到sheet的单元格中
                for (int n = 0; n < columnNum; n++)
                {
                    HSSFCell cellRowName = rowRowName.createCell(n); //创建列头对应个数的单元格
                    cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); //设置列头单元格的数据类型
                    HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                    cellRowName.setCellValue(text); //设置列头单元格的值
                    cellRowName.setCellStyle(columnTopStyle); //设置列头单元格样式
                }
                
                //将查询出的数据设置到sheet对应的单元格中
                StatisticalResults obj = null;
                HSSFCell cell = null;
                for (int i = 0; i < data.size(); i++)
                {
                    obj = data.get(i);//遍历每个对象
                    HSSFRow row = sheet.createRow(i + 1);//创建所需的行数
                    
                    row.setHeight((short)(25 * 20)); //设置高度
                    cell = row.createCell(0, HSSFCell.CELL_TYPE_STRING);
                    if (obj.getAuthorid() != null && !"".equals(obj.getAuthorid()))
                    {
                        cell.setCellValue(obj.getAuthorid()); //设置单元格的值
                    }
                    cell.setCellStyle(style);
                    
                    cell = row.createCell(1, HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(obj.getAddsum());
                    cell.setCellStyle(style);
                    
                    cell = row.createCell(2, HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(obj.getModifysum());
                    cell.setCellStyle(style);
                    
                    cell = row.createCell(3, HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(obj.getDeletesum());
                    cell.setCellStyle(style);
                }
                
                if (workbook != null)
                {
                    FileOutputStream out = null;
                    try
                    {
                        out = new FileOutputStream(createFilePath + "/" + "mycat.xls");
                        workbook.write(out);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (null != out)
                        {
                            out.close();
                        }
                    }
                }
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 列头单元格样式    
     * <功能详细描述>
     * @param workbook
     * @return
     */
    private HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook)
    {
        
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字 
        font.setFontName("Courier New");
        //设置样式; 
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框; 
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;  
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;   
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色; 
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框; 
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色; 
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框; 
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;  
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;  
        style.setFont(font);
        //设置自动换行; 
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐; 
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        //设置单元格背景颜色
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        return style;
        
    }
    
    /**
     * 列数据信息单元格样式 
     * <功能详细描述>
     * @param workbook
     * @return
     */
    private HSSFCellStyle getStyle(HSSFWorkbook workbook)
    {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字 
        font.setFontName("Courier New");
        //设置样式; 
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框; 
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;  
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;   
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色; 
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框; 
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色; 
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框; 
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;  
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;  
        style.setFont(font);
        //设置自动换行; 
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐; 
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        return style;
        
    }
}
