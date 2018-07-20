/*
 * 文 件 名:  SubjectController.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月11日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hoperun.constant.SubjectResponse;
import com.hoperun.constant.SubjectResultCode;
import com.hoperun.entity.Subject;
import com.hoperun.papermodle.ExamSubject;
import com.hoperun.service.SubjectService;
import com.hoperun.service.impl.ExamPaperSubjectService;
import com.hoperun.util.ExcelUtil;
import com.hoperun.util.HttpUtil;

/**
 * 题目<功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月11日]
 * @since [云服务/模块版本]
 */
@RestController
public class SubjectController {
	private Logger log = LoggerFactory.getLogger(SubjectController.class);

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ExamPaperSubjectService examPaperSubjectService;

	@Resource(name = "excelUtil")
	private ExcelUtil excelUtil;

	/**
	 * 新增题目 <功能详细描述>
	 * 
	 * @param file
	 * @param subjectDbId
	 * @return
	 */
	@PostMapping("/importFile")
	public SubjectResponse importExcel(@RequestParam("file") MultipartFile file,
			@RequestParam("subjectDbId") Integer subjectDbId, HttpServletResponse httpServletResponse) {
		log.info("begin to importfile");
		HttpUtil.acessmhttpServletResponse(httpServletResponse);
		String resultcode = SubjectResultCode.RESULTCODE_FAIL;

		try {
			resultcode = excelUtil.importSubject(file, subjectDbId);
		} catch (Exception e) {
			log.error("importExcel error");
		}
		log.info("end to importfile resultcode is " + resultcode);
		return HttpUtil.getSubjectResonse(resultcode);

	}

	/**
	 * 删除 <功能详细描述>
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteSubject")
	public SubjectResponse deleteSubject(@RequestParam(value = "id") int id) {
		log.info("begin to deleteSubject");
		String resultcode = SubjectResultCode.RESULTCODE_FAIL;
		try {
			resultcode = subjectService.deleteByPrimaryKey(id);
		} catch (Exception e) {
			log.error("deleteSubject error");
		}
		log.info("end to deleteSubject resultcode is " + resultcode);
		return HttpUtil.getSubjectResonse(resultcode);
	}

	/**
	 * 
	 * <功能详细描述>
	 * 
	 * @return
	 */
	@GetMapping("/queryAllSubject")
	public List<Subject> queryAllSubject() {
		log.info("begin to queryAllSubject");
		return subjectService.queyAllSubject();

	}

	@GetMapping(value = "/queySubjectByContent")
	public List<Subject> queySubjectByContent(
			@RequestParam(value = "subjectContent", required = false) String subjectContent,
			@RequestParam(value = "subjectDbId", required = false) Integer subjectDbId,
			@RequestParam(value = "subjectType", required = false) Integer subjectType,
			@RequestParam(value = "isOpen", required = false) Integer isOpen) {
		log.info("begin to queySubjectByContent subejctContent is " + subjectContent + "subjectDbId is " + subjectDbId
				+ "subjectType is " + subjectType + "isOpen " + isOpen);
		return subjectService.queySubjectByContent(subjectContent, subjectDbId, subjectType, isOpen);

	}

	@GetMapping(value = "/examPaperSubjects")
	public List<ExamSubject> queySubject(@RequestParam(value = "subjectType", required = true) String subjectType,
			@RequestParam int subjectCount, @RequestParam int subjectDbId) {
		log.info("begin to examPaperSubjects subjectType is " + subjectType + "subjectCount is " + subjectCount
				+ "subjectDbId is " + subjectDbId);
		List<ExamSubject> examSubjects = examPaperSubjectService.acessmble(subjectType, subjectCount, subjectDbId);
		return examSubjects;

	}

}
