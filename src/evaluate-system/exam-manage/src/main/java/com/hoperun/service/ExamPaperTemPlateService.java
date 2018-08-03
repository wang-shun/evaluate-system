/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月24日]
 * @since  [阿里运/模块1.0版本]
 */
package com.hoperun.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.commons.domain.ResultCode;
import com.hoperun.domain.ExamPaperTemPlate;
import com.hoperun.mapper.ExamPaperTemPlateDetailMapper;
import com.hoperun.mapper.ExamPaperTemPlateMapper;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月24日]
 * @since [马振勇/模块版本]
 */
@Service
public class ExamPaperTemPlateService {
	
	
	private Logger log=LoggerFactory.getLogger(ExamPaperTemPlateService.class);
	@Autowired
	private ExamPaperTemPlateMapper examPaperTemPlateMapper;
	@Autowired
	private ExamPaperTemPlateDetailMapper detailMapper;

	public List<ExamPaperTemPlate> selectAllTemPlate(String colTemplateName,String colCreator) {
		return examPaperTemPlateMapper.selectAll(colTemplateName,colCreator);

	}
	
	public String insertTemPlate(ExamPaperTemPlate examPaperTemPlate) {
		String resultCode=ResultCode.RESULTCODE_SUCESS;
		try {
			examPaperTemPlateMapper.insert(examPaperTemPlate);
		} catch (Exception e) {
			resultCode=ResultCode.RESULTCODE_FAIL;
			log.error("insert examPaperTemPlate error "+e);
		}
		
		return resultCode;
	}
	
	public String deleteTemplate(Integer id) {
		String resultCode=ResultCode.RESULTCODE_SUCESS;
		try {
			examPaperTemPlateMapper.deleteByPrimaryKey(id);
			detailMapper.deleteByTemplateId(id);
		} catch (Exception e) {
			resultCode=ResultCode.RESULTCODE_FAIL;
			log.error("delete templater error "+e);
		}
		return resultCode;
	}
	
}
