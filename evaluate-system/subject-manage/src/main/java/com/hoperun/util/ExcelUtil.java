package com.hoperun.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hoperun.constant.SubjectConstant;
import com.hoperun.constant.SubjectResultCode;
import com.hoperun.excelmodel.ExcelSubject;
import com.hoperun.service.ImportSubjectService;

/**
 * 导入题目模板 <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Component("excelUtil")
@Transactional
public class ExcelUtil {

	private Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	@Resource
	private ImportSubjectService importSubjectService;

	/**
	 * 
	 * <功能详细描述>
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String importSubject(MultipartFile file, Integer subjectDbId) throws Exception {
		log.info("begin to importSubject");
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
		String fileName = file.getOriginalFilename();
		Workbook hssfWorkbook = null;
		if (fileName.endsWith(SubjectConstant.XLSX_TYPE)) {
			hssfWorkbook = new XSSFWorkbook(file.getInputStream());// Excel 2007
		} else if (fileName.endsWith(SubjectConstant.XLS_TYPE)) {
			hssfWorkbook = new HSSFWorkbook(file.getInputStream());// Excel 2003
		} else {
			// 不支持的类型
			log.error("the file Type is not support");
			return resultCode;
		}
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				// HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				Row hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					ExcelSubject excelSubject = new ExcelSubject();
					List<String> subjectAnswerList = new ArrayList<>();
					// List<String> subjectRadiolist = new ArrayList<>();
					// 试题内容
					Cell subjectContent = hssfRow.getCell(1);

					// 选项内容
					Cell subjectAnswer = hssfRow.getCell(3);
					// 试题答案
					Cell rightAnswer = hssfRow.getCell(4);
					// 试题类型
					Cell subjectType = hssfRow.getCell(5);

					// 是否有效
					Cell isUse = hssfRow.getCell(6);
					// 试题难度
					Cell diffcutType = hssfRow.getCell(7);
					subjectAnswerList.add(subjectAnswer.toString());
					excelSubject.setContent(subjectContent.toString());
					excelSubject = ExcelUtil.getExcelSubject(hssfSheet, rowNum, excelSubject, subjectAnswerList);
					String rightAnwer = rightAnswer.getStringCellValue();
					String[] rightAnwerColection = rightAnwer.split(",");
					excelSubject.setSubjectType(new Double(subjectType.getNumericCellValue()).intValue());
					excelSubject.setIsUse(new Double(isUse.getNumericCellValue()).intValue());
					excelSubject.setRightAnswer(rightAnwerColection);
					excelSubject.setDiffcutType(new Double(diffcutType.getNumericCellValue()).intValue());
					excelSubject.setSubjectDbId(subjectDbId);
					resultCode = this.insertSubject(excelSubject);
					rowNum = excelSubject.getIndex();

				}

			}
		}
		log.info("end to importSubject");
		return resultCode;

	}

	/**
	 * 
	 * <功能详细描述>
	 * 
	 * @param hssfSheet
	 * @param num
	 * @param excelSubject
	 * @param answerlList
	 * @return
	 */
	public static ExcelSubject getExcelSubject(Sheet hssfSheet, int num, ExcelSubject excelSubject,
			List<String> answerlList) {
		int index = num + 1;
		// 最后一行
		if (index > hssfSheet.getLastRowNum()) {
			return excelSubject;
		}
		while (null == hssfSheet.getRow(index).getCell(1).toString()
				|| "".equals(hssfSheet.getRow(index).getCell(1).toString())) {
			answerlList.add(hssfSheet.getRow(index).getCell(3).toString());
			excelSubject.setAnswer(answerlList);
			index++;
			if (index > hssfSheet.getLastRowNum()) {
				break;
			}
			continue;
		}
		excelSubject.setIndex(index - 1);
		return excelSubject;
	}

	/**
	 * 新增题目 <功能详细描述>
	 * 
	 * @param excelSubject
	 * @return
	 */
	public String insertSubject(ExcelSubject excelSubject) {
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
		try {
			return importSubjectService.InsertSubjectAndAnswer(excelSubject);
		} catch (Exception e) {
			log.error("insertSubject error" + e);
			return resultCode;
		}

	}
}
