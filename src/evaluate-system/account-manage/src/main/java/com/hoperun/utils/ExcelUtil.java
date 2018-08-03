package com.hoperun.utils;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hoperun.commons.domain.Account;
import com.hoperun.commons.domain.FinalPojo;
import com.hoperun.domain.INFO;
/**
 * 导入题目模板 <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Component("excelUtil")
public class ExcelUtil {
    
    private Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    
    //	@Resource
    //	private ImportSubjectService importSubjectService;
    
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
    public final Map<INFO, List<Account>> readAccountFromExcel(InputStream is, String fileName, String creatorId, int departmentId)
        throws RuntimeException {
        Map<INFO, List<Account>> out = new HashMap<>();
        if (null == is) {
            throw new RuntimeException("read excel error!");
        }
        
        // 创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            
            // 获取excel文件的io流
            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith("xls")) {
                
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                
                // 2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("read excel error!");
        }
        
        getUserInfoFromSheet(workbook, 0, out, creatorId, departmentId);
        
        try {
            if (null != is) {
                is.close();
            }
        } catch (Exception e) {
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
    private void getUserInfoFromSheet(Workbook workbook, int sheetIndex, Map<INFO, List<Account>> map, String creatorId, int departmentId) {
        List<Account> tempError = new ArrayList<>();
        List<Account> tempRight = new ArrayList<>();
        if (null != workbook) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Account account = null;
            Row row = null;
            String cell0 = null, cell1 = null, cell2 = null, cell3 = null, cell4 = null, cell5 = null, cell6 = null;
            Integer int1 = null;
            boolean is = false;
            if (sheet != null) {
                int maxRow = sheet.getLastRowNum();
                log.info("account maxRow is:" + maxRow);
                
                //第一行是标题栏，不需要解析
                for (int u = 1; u <= maxRow; u++) {
                    is = false;
                    row = sheet.getRow(u);
                    cell0 = getCellValue(row.getCell(0));
                    cell1 = getCellValue(row.getCell(1));
                    cell2 = getCellValue(row.getCell(2));
                    cell3 = getCellValue(row.getCell(3));
                    cell4 = getCellValue(row.getCell(4));
                    cell5 = getCellValue(row.getCell(5));
                    
                    cell6 = getCellValue(row.getCell(6));
                    
                    if ("".equals(cell0)) {
                        account = new Account();
                        account.setColFullName("Error, sheet " + (sheetIndex + 1) + " row at " + (u + 1)
                            + " column at " + 1 + " content is empty!");
                        tempError.add(account);
                        is = true;
                    }
                    
                    if ("".equals(cell1)) {
                        account = new Account();
                        account.setColFullName("Error, sheet " + (sheetIndex + 1) + " row at " + (u + 1)
                            + " column at " + 2 + " content is empty!");
                        tempError.add(account);
                        is = true;
                    }
                    
                    try {
                        int1 = Integer.parseInt(cell2);
                    }catch(NumberFormatException e)
                    {
                        account = new Account();
                        account.setColFullName("Error, sheet " + (sheetIndex + 1) + " row at " + (u + 1)
                            + " column at " + 1 + " content is illegal!");
                        tempError.add(account);
                        is = true;
                    }
                    
                    if (is) {
                        continue;
                    }
                    
                    account = new Account();
                    
                    account.setId(cell0);
                    account.setColAccountName(cell1);
                    account.setColPassowrd(FinalPojo.INIT_PASSWORD);
                    account.setColRoleId(int1);
                    account.setColFullName(cell3);
                    account.setColMobile(cell4);
                    account.setColEmail(cell5);
                    account.setColDepartmentId(departmentId);
                    account.setColLevel(cell6);
                    account.setColCreateTime(System.currentTimeMillis());
                    account.setColCreatorId(creatorId);
                    tempRight.add(account);
                }
                
            } 
        }
        map.put(INFO.right, tempRight);
        map.put(INFO.error, tempError);
    }
    
    /**
     * 取一个cell的值
     * 
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
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
