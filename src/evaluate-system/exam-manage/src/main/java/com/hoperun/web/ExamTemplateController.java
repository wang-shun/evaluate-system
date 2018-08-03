/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月24日]
 * @since  [阿里运/模块1.0版本]
 */
package com.hoperun.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.hoperun.commons.domain.Response;
import com.hoperun.commons.domain.ResultCode;
import com.hoperun.domain.ExamPaperTemPlate;
import com.hoperun.domain.ExamPaperTemPlateDetail;
import com.hoperun.domain.PaperTemPlate;
import com.hoperun.service.ExamPaperTemPlateDetailService;
import com.hoperun.service.ExamPaperTemPlateService;
import com.hoperun.service.PaperTemplateService;
import com.hoperun.util.ResopnseUtil;

/**
 * 考试模板 <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月24日]
 * @since [马振勇/模块版本]
 */
@RestController
public class ExamTemplateController {
	private Logger log = LoggerFactory.getLogger(ExamTemplateController.class);
	@Autowired
	private PaperTemplateService paperTemplateService;
	@Autowired
	private ExamPaperTemPlateService examPaperTemplateService;
	@Autowired
	private ExamPaperTemPlateDetailService examPaperTemPlateDetailService;

	/**
	 * 查询所有模板
	 * 
	 * @return
	 */
	@RequestMapping(value = "/examTemplate", method = RequestMethod.GET)
	public PageInfo<PaperTemPlate> queryAllTemplate(@RequestParam(value = "colTemplateName",required = false) String colTemplateName,
			@RequestParam(value = "colCreator",required = false) String colCreator,@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		log.info("begin to queryAllTemplate");
		return paperTemplateService.listPaperTemplate(colTemplateName,colCreator,pageNum,pageSize);

	}

	/**
	 * 
	 * <功能详细描述>
	 * 
	 * @param paperTemPlate
	 * @return
	 */
	@RequestMapping(value = "/examTemplates", method = RequestMethod.POST)
	public Response addTemPlate(@RequestBody PaperTemPlate paperTemPlate) {
		String resultCode = ResultCode.RESULTCODE_FAIL;
		log.info("begin to examTemplates");
		// 新增题目模板
		resultCode = paperTemplateService.addExamTemPlate(paperTemPlate);
		return ResopnseUtil.getResponse(resultCode);
	}

	/**
	 * 删除模板 <功能详细描述>
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteTemPlate", method = RequestMethod.DELETE)
	public Response deleteTemplate(@RequestParam(value = "id") Integer id) {
		String resultCode = ResultCode.RESULTCODE_SUCESS;
		log.info("begin to delete template");
		// 删除题目模板
		resultCode = examPaperTemplateService.deleteTemplate(id);
		return ResopnseUtil.getResponse(resultCode);

	}

	/**
	 * 更改模板 <功能详细描述>
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/updateTemplate", method = RequestMethod.PUT)
	public Response updateTemplate(@RequestBody PaperTemPlate paperTemPlate) {
		String resultCode = ResultCode.RESULTCODE_SUCESS;
		List<ExamPaperTemPlateDetail> details = paperTemPlate.getDetails();
		// 修改模板
		ExamPaperTemPlate examPaperTemPlate = new ExamPaperTemPlate();
		examPaperTemPlate.setId(paperTemPlate.getId());
		examPaperTemPlate.setColCreator(paperTemPlate.getColCreator());
		examPaperTemPlate.setColTemplateName(paperTemPlate.getColTemplateName());
		// 修改模板详情
		for (ExamPaperTemPlateDetail examPaperTemPlateDetail : details) {
			try {
				examPaperTemPlateDetailService.updateTemplate(examPaperTemPlateDetail);
			} catch (Exception e) {
				resultCode = ResultCode.RESULTCODE_FAIL;
				log.error("insert ExamPaperTemPlateDetail error " + e);
				return ResopnseUtil.getResponse(resultCode);
			}
		}
		return ResopnseUtil.getResponse(resultCode);

	}
}
