/*
 * 文 件 名:  SubjectDbController.java
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoperun.constant.SubjectResponse;
import com.hoperun.constant.SubjectResultCode;
import com.hoperun.entity.SubjectDb;
import com.hoperun.service.SubjectDbService;
import com.hoperun.util.HttpUtil;

/**
 * 题库<功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月11日]
 * @since [云服务/模块版本]
 */
@RestController
public class SubjectDbController {
	private Logger log = LoggerFactory.getLogger(SubjectController.class);
	@Resource(name = "subjectDbService")
	private SubjectDbService subjectDbService;

	/**
	 * 新增题库 <功能详细描述>
	 * 
	 * @param db
	 * @return
	 */
	@PostMapping(value = "/addsubjectDb")
	public SubjectResponse addSubjectDb(@RequestBody SubjectDb db, HttpServletResponse response) {
		HttpUtil.acessmhttpServletResponse(response);
		log.info("begin to addsubjectDb");
		String respcode = subjectDbService.insert(db);
		log.info("end addsubjectDb resultCode is " + respcode);
		return HttpUtil.getSubjectResonse(respcode);

	}

	/**
	 * 根据Id查询题库 <功能详细描述>
	 * 
	 * @param db
	 * @return
	 */
	@GetMapping(value = "/getSubjectDbById")
	public SubjectDb getSubjectDbById(@RequestParam(value = "id", required = true) int id) {
		log.info("begin to getSubjectDbById");
		SubjectDb subjectDb = subjectDbService.selectByPrimaryKey(id);
		return subjectDb;

	}

	/**
	 * 根据状态查询 <功能详细描述>
	 * 
	 * @param db
	 * @return
	 */
	@GetMapping(value = "/getsubjectDbByStatus")
	public List<SubjectDb> getsubjectDbByStatus(@RequestParam(value = "isOpen", required = true) int isOpen) {
		log.info("begin to getsubjectDbByStatus");
		List<SubjectDb> list = subjectDbService.querySubjectByOpenStatus(isOpen);
		return list;

	}

	/**
	 * 根据subjectName <功能详细描述>
	 * 
	 * @param db
	 * @return
	 */
	@GetMapping(value = "/getsubjectDbyName")
	public SubjectDb getSubjectDbByName(@RequestParam(value = "subjectDbName", required = true) String subjectDbName) {
		log.info("begin to getSubjectDbByName");
		SubjectDb subjectDb = subjectDbService.getbySubjectName(subjectDbName);
		return subjectDb;

	}

	/**
	 * 查询所有题库 <功能详细描述>
	 * 
	 * @param db
	 * @return
	 */
	@GetMapping(value = "/queryAllsubjectDb")
	public List<SubjectDb> queryAllsubjectDb() {
		log.info("begin to queryAllsubjectDb");
		List<SubjectDb> list = subjectDbService.queryAllSubjectDb();
		return list;

	}

	/**
	 * 删除题库 <功能详细描述>
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/deleteSubjectDbById")
	public SubjectResponse deleteSubjectDbById(@RequestParam(value = "id", required = true) int id) {
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
		resultCode = subjectDbService.deleteByPrimaryKey(id);
		return HttpUtil.getSubjectResonse(resultCode);

	}

	/**
	 * 删除题库 <功能详细描述>
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/updateSubjectDb")
	public SubjectResponse updateSubjectDb(@RequestBody SubjectDb db) {
		log.info("begin to updateSubjectDb");
		String respcode = SubjectResultCode.RESULTCODE_FAIL;
		respcode = subjectDbService.updateByPrimaryKey(db);
		log.info("end to updateSubjectDb resultCode is " + respcode);
		return HttpUtil.getSubjectResonse(respcode);

	}

}
