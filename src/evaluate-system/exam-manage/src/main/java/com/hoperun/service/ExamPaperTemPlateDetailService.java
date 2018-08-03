package com.hoperun.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.commons.domain.ResultCode;
import com.hoperun.domain.ExamPaperTemPlateDetail;
import com.hoperun.mapper.ExamPaperTemPlateDetailMapper;

@Service
public class ExamPaperTemPlateDetailService {
	private Logger log=LoggerFactory.getLogger(ExamPaperTemPlateDetailService.class);
	@Autowired
	private ExamPaperTemPlateDetailMapper examPaperTemPlateDetailMapper;

	public List<ExamPaperTemPlateDetail> selectAllTemPlate() {
		return examPaperTemPlateDetailMapper.selectAll();

	}

	public List<ExamPaperTemPlateDetail> ListTemPlateDetailBytemPlateId(Integer temPlateId) {
		return examPaperTemPlateDetailMapper.listByTemlateId(temPlateId);

	}
	/**
	 * 修改模板
	 * <功能详细描述>
	 * @param detail
	 * @return
	 */
	public String updateTemplate(ExamPaperTemPlateDetail detail) throws Exception{
		String resultCode=ResultCode.RESULTCODE_SUCESS;
		try {
			examPaperTemPlateDetailMapper.updateByPrimaryKey(detail);
		} catch (Exception e) {
			resultCode=ResultCode.RESULTCODE_FAIL;
			log.error("updateTemplate ExamPaperTemPlateDetail error "+e);
		}
		return resultCode;
		
	}
	public String insertTemplateDetail(ExamPaperTemPlateDetail detail) {
		String resultCode=ResultCode.RESULTCODE_SUCESS;
		try {
			examPaperTemPlateDetailMapper.insert(detail);
		} catch (Exception e) {
			resultCode=ResultCode.RESULTCODE_FAIL;
			log.error("insert ExamPaperTemPlateDetail error "+e);
		}
		
		return resultCode;
	}
}
