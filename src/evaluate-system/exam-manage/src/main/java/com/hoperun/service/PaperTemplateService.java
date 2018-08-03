/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月25日]
 * @since  [阿里运/模块1.0版本]
 */
package com.hoperun.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hoperun.commons.domain.ResultCode;
import com.hoperun.domain.ExamPaperTemPlate;
import com.hoperun.domain.ExamPaperTemPlateDetail;
import com.hoperun.domain.PaperTemPlate;
import com.hoperun.util.GeneralUtil;
import com.hoperun.util.page.PageUtil;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月25日]
 * @since [马振勇/模块版本]
 */
@Service
public class PaperTemplateService {

	@Autowired
	private ExamPaperTemPlateService examPaperTemPlateService;
	@Autowired
	private ExamPaperTemPlateDetailService examPaperTemPlateDetailService;

	public PageInfo<PaperTemPlate> listPaperTemplate(String colTemplateName,String colCreator,Integer pageNum,Integer pageSize) {
		List<PaperTemPlate> list = new ArrayList<>();
		pageNum = pageNum == null ? PageUtil.DEFULT_PAGENUM : pageNum;
		pageSize = pageSize == null ? PageUtil.DEFULT_PAGESIZE : pageSize;
		PageHelper.startPage(pageNum, pageSize);
		
		// 查询所有模板
		List<ExamPaperTemPlate> examPaperTemPlates = examPaperTemPlateService.selectAllTemPlate(colTemplateName,colCreator);
		
		PageInfo<ExamPaperTemPlate> infos = new PageInfo<>(examPaperTemPlates);
		if (GeneralUtil.isNotNullOrZeroSize(examPaperTemPlates)) {
			for (ExamPaperTemPlate examPaperTemPlate : examPaperTemPlates) {
				PaperTemPlate paperTemPlate = new PaperTemPlate();
				List<ExamPaperTemPlateDetail> examPaperTemPlateDetails = examPaperTemPlateDetailService
						.ListTemPlateDetailBytemPlateId(examPaperTemPlate.getId());
				paperTemPlate.setDetails(examPaperTemPlateDetails);
				paperTemPlate.setId(examPaperTemPlate.getId());
				paperTemPlate.setColCreateTime(examPaperTemPlate.getColCreateTime());
				paperTemPlate.setColCreator(examPaperTemPlate.getColCreator());
				paperTemPlate.setColTemplateName(examPaperTemPlate.getColTemplateName());
				list.add(paperTemPlate);
			}
			PageInfo<PaperTemPlate> info = new PageInfo<>(list);
			info.setTotal(infos.getTotal());
			return info;
		}
		return null;

	}

	// 新增试卷模板
	public String addExamTemPlate(PaperTemPlate paperTemPlate) {
		String resultCode = ResultCode.RESULTCODE_FAIL;
		// 模板详情
		List<ExamPaperTemPlateDetail> details = paperTemPlate.getDetails();
		// 试卷模板
		String templateName = paperTemPlate.getColTemplateName();
		// 试卷创建人
		String creatorId = paperTemPlate.getColCreator();
		// 创建时间
		Integer createTime = (int) System.currentTimeMillis();

		ExamPaperTemPlate examPaperTemPlate = new ExamPaperTemPlate();

		examPaperTemPlate.setColCreateTime(createTime);
		examPaperTemPlate.setColCreator(creatorId);
		examPaperTemPlate.setColTemplateName(templateName);
		resultCode = examPaperTemPlateService.insertTemPlate(examPaperTemPlate);
		int templateId = examPaperTemPlate.getId();
		for (ExamPaperTemPlateDetail examPaperTemPlateDetail : details) {
			examPaperTemPlateDetail.setColTemplateId(templateId);
			resultCode = examPaperTemPlateDetailService.insertTemplateDetail(examPaperTemPlateDetail);
		}
		return resultCode;
	}

	// 删除试卷模板

	public String deleteExamTemPlate(String templateId) {
		
		
		
		return templateId;

	}

}
