/*
 * 文 件 名:  SubjectController.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月11日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hoperun.commons.domain.ResultCode;
import com.hoperun.constant.SubjectConstant;
import com.hoperun.constant.SubjectResponse;
import com.hoperun.constant.SubjectResultCode;
import com.hoperun.entity.Subject;
import com.hoperun.papermodle.ExamSubject;
import com.hoperun.papermodle.UpdateSubject;
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
	@Resource
	private ResourceLoader resourceLoader;

	/**
	 * 通过文件新增题目 <功能详细描述>
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
	 * 通过页面新增题目 <功能详细描述>
	 * 
	 * @param file
	 * @param subjectDbId
	 * @return
	 */
	@PostMapping("/subject")
	public SubjectResponse addSubject(HttpServletRequest httpServletRequest) {
		String resultcode = SubjectResultCode.RESULTCODE_FAIL;
		ServletInputStream inputStream;
		try {
			inputStream = httpServletRequest.getInputStream();
			String msg = IOUtils.toString(inputStream, "UTF-8");
			JSONObject object = JSONObject.parseObject(msg);
			Subject insertSubjectModle = JSONObject.toJavaObject(object, Subject.class);
			// 新增题目题目详情答案
			resultcode = subjectService.addSubject(insertSubjectModle);
		} catch (IOException e) {
			log.error("add subject error{}" + e);
		}
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

	@PutMapping("/updatesubjects")
	public SubjectResponse updateSubject(@RequestBody UpdateSubject updateSubject) {
		log.info("begin to updateSubject");
		String resultCode = ResultCode.RESULTCODE_FAIL;
		try {
			Subject subject = subjectService.selectByPrimaryKey(updateSubject.getId());
			subject.setIsOpen(updateSubject.getIsOpen());
			resultCode = subjectService.updateByPrimaryKey(subject);
		} catch (Exception e) {
			log.error("update subject error");
		}
		return HttpUtil.getSubjectResonse(resultCode);

	}

	@GetMapping(value = "/queySubjectByContent")
	public PageInfo<Subject> queySubjectByContent(
			@RequestParam(value = "subjectContent", required = false) String subjectContent,
			@RequestParam(value = "subjectDbId", required = false) Integer subjectDbId,
			@RequestParam(value = "subjectType", required = false) Integer subjectType,
			@RequestParam(value = "isOpen", required = false) Integer isOpen,
			@RequestParam(value = "defficultyWeight", required = false) Integer defficultyWeight,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		log.info("begin to queySubjectByContent subejctContent is " + subjectContent + "subjectDbId is " + subjectDbId
				+ "subjectType is " + subjectType + "isOpen " + isOpen + "defficultyWeight" + defficultyWeight);
		PageInfo<Subject> info = subjectService.queySubjectByContent(subjectContent, subjectDbId, subjectType, isOpen,
				defficultyWeight, pageNum, pageSize);
		return info;

	}

	@GetMapping(value = "/examPaperSubjects")
	public List<ExamSubject> queySubject(@RequestParam(value = "subjectType", required = true) String subjectType,
			@RequestParam int subjectCount, @RequestParam int subjectDbId, @RequestParam int defficultyWeight) {
		log.info("begin to examPaperSubjects subjectType is " + subjectType + "subjectCount is " + subjectCount
				+ "subjectDbId is " + subjectDbId);
		List<ExamSubject> examSubjects = examPaperSubjectService.acessmble(subjectType, subjectCount, subjectDbId,
				defficultyWeight);
		return examSubjects;

	}

	/**
	 * 试题文件模板下载 <功能详细描述>
	 * 
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/downloadTemplate")
	public Map<String, Object> downloadTemplate(HttpServletResponse response) {
		Map<String, Object> maps = new HashMap<>();
		log.info("begin to download subjectTempalte");
		String fileName = "试题模板.xls";
		File file;
		InputStream inputStream = null;
		ServletOutputStream servletOutputStream = null;
		try {
			org.springframework.core.io.Resource resource = resourceLoader
					.getResource(SubjectConstant.SUBJECT_TEMPLATE_PATH);
			file = ResourceUtils.getFile(SubjectConstant.SUBJECT_TEMPLATE_PATH);
			if (!file.exists()) {
				log.error("file is not exist");
				maps.put(SubjectResultCode.QUESTIONPLATE_IS_NOTEXISTS,
						SubjectResultCode.QUESTIONPLATE_IS_NOTEXISTS_DESC);
				return maps;
			}
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.addHeader("charset", "utf-8");
			response.addHeader("Pragma", "no-cache");
			String encodeName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

			inputStream = resource.getInputStream();
			servletOutputStream = response.getOutputStream();
			IOUtils.copy(inputStream, servletOutputStream);
			response.flushBuffer();

		} catch (IOException e) {
			log.error("template file is not exist" + e);
			maps.put(SubjectResultCode.QUESTIONTEMPLATE_DOWNLOAD_FAIL,
					SubjectResultCode.QUESTIONTEMPLATE_DOWNLOAD_FAIL_DESC);
			return maps;

		} finally {
			try {
				if (servletOutputStream != null) {
					servletOutputStream.close();
					servletOutputStream = null;
				}
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				// 召唤jvm的垃圾回收器
				System.gc();
			} catch (Exception e) {
				maps.put(SubjectResultCode.QUESTIONTEMPLATE_DOWNLOAD_FAIL,
						SubjectResultCode.QUESTIONTEMPLATE_DOWNLOAD_FAIL_DESC);
				log.error("close steam error");
				return maps;

			}
		}
		maps.put(SubjectResultCode.QUESTIONPLATE_DOWNLOAD_SUCESS, SubjectResultCode.QUESTIONPLATE_DOWNLOAD_SUCESS_DESC);

		return maps;

	}

}
