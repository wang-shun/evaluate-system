package mycat.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import mycat.bean.CctInfo;
import mycat.pojo.INFO;

/**
 * 导入题目模板 <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
public class ExcelUtil
{
    
    /**
     * 用户信息导入
     * <功能详细描述>
     * @param is 输入流
     * @param fileName 文件名
     * @param creatorId 导入者ID
     * @param departmentId 部门ID
     * @return
     * @throws RuntimeException
     */
    public static final Map<INFO, List<CctInfo>> readCctInfoFromExcel(String filePath, int analysisId, String replaceStr)
        throws RuntimeException
    {
        Map<INFO, List<CctInfo>> out = new HashMap<INFO, List<CctInfo>>();
        
        String fileName = GitLogUtil.suffix(filePath).toUpperCase();
        InputStream is = null;
        
        // 创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try
        {
            is = new FileInputStream(filePath);
            
            // 获取excel文件的io流
            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if ("XLS".equals(fileName))
            {
                
                // 2003
                workbook = new HSSFWorkbook(is);
            }
            else if ("XLSX".equals(fileName))
            {
                
                // 2007
                workbook = new XSSFWorkbook(is);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("read excel error!");
        }
        
        /** 解析第三个sheet页**/
        getUserInfoFromSheet(workbook, 2, out, analysisId, replaceStr);
        
        try
        {
            if (null != is)
            {
                is.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return out;
    }
    
    /**
     * 用户基础资料解析
     * @param workbook
     * @param sheetIndex
     * @param type
     * @param map
     * @param creatorId 导入者ID
     */
    private static void getUserInfoFromSheet(Workbook workbook, int sheetIndex, Map<INFO, List<CctInfo>> map, int analysisId, String replaceStr)
    {
        List<CctInfo> tempError = new ArrayList<CctInfo>();
        List<CctInfo> tempRight = new ArrayList<CctInfo>();
        if (null != workbook)
        {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            CctInfo CctInfo = null;
            Row row = null;
            String cell8 = null;
            Integer cell2 = null, cell3 = null, cell4 = null;
            boolean is = false;
            if (sheet != null)
            {
                int maxRow = sheet.getLastRowNum();
//                System.out.println("CctInfo maxRow is:" + maxRow);
                
                //第一行是标题栏，不需要解析
                for (int u = 1; u <= maxRow; u++)
                {
                    is = false;
                    row = sheet.getRow(u);
                    try
                    {
                        cell2 = Integer.parseInt(getCellValue(row.getCell(2)));
                    }
                    catch (NumberFormatException e)
                    {
                        CctInfo = new CctInfo();
                        CctInfo.setFilepath("Error, sheet " + (sheetIndex + 1) + " row at " + (u + 1) + " column at "
                            + 3 + " content is illegal!");
                        tempError.add(CctInfo);
                        is = true;
                    }
                    try
                    {
                        cell3 = Integer.parseInt(getCellValue(row.getCell(3)));
                    }
                    catch (NumberFormatException e)
                    {
                        CctInfo = new CctInfo();
                        CctInfo.setFilepath("Error, sheet " + (sheetIndex + 1) + " row at " + (u + 1) + " column at "
                            + 4 + " content is illegal!");
                        tempError.add(CctInfo);
                        is = true;
                    }
                    try
                    {
                        cell4 = Integer.parseInt(getCellValue(row.getCell(4)));
                    }
                    catch (NumberFormatException e)
                    {
                        CctInfo = new CctInfo();
                        CctInfo.setFilepath("Error, sheet " + (sheetIndex + 1) + " row at " + (u + 1) + " column at "
                            + 5 + " content is illegal!");
                        tempError.add(CctInfo);
                        is = true;
                    }
                    cell8 = dealWithStr(getCellValue(row.getCell(8)), replaceStr);
                    
                    /*if ("".equals(cell2)) {
                        CctInfo = new CctInfo();
                        CctInfo.setFilepath("Error, sheet " + (sheetIndex + 1) + " row at " + (u + 1)
                            + " column at " + 1 + " content is empty!");
                        tempError.add(CctInfo);
                        is = true;
                    }*/
                    
                    if (is)
                    {
                        continue;
                    }
                    
                    CctInfo = new CctInfo();
                    
                    CctInfo.setId(analysisId);
                    CctInfo.setFilepath(cell8);
                    CctInfo.setAddline(cell2);
                    CctInfo.setModifyline(cell3);
                    CctInfo.setDeleteline(cell4);
                    tempRight.add(CctInfo);
                }
                
            }
        }
        map.put(INFO.right, tempRight);
        map.put(INFO.error, tempError);
    }
    
    /**
     * filepath处理
     * <功能详细描述>
     * @param str
     * @return
     */
    public static String dealWithStr(String str, String replaceStr)
    {
        String out = "";
        if(null != str && null != replaceStr)
        {
//            System.out.println(str +"\n"+ replaceStr);
            replaceStr = replaceStr.replaceAll("\\\\", "\\\\\\\\");
            out = str.replaceFirst(replaceStr, "").replace('\\', '/');
        }
        return out;
    }
    
    /**
     * 取一个cell的值
     * 
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell)
    {
        String cellValue = "";
        if (cell == null)
        {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
        {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        // 判断数据的类型
        switch (cell.getCellType())
        {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
    
}
