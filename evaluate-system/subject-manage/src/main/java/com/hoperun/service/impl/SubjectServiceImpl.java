/*
 * 文 件 名:  SubjectServiceImpl.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.constant.SubjectResultCode;
import com.hoperun.dao.SubjectMapper;
import com.hoperun.entity.Subject;
import com.hoperun.service.SubjectService;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Service
public class SubjectServiceImpl implements SubjectService {
	private Logger log = LoggerFactory.getLogger(SubjectServiceImpl.class);
	@Autowired
	private SubjectMapper subjectMapper;

	@Override
	public String deleteByPrimaryKey(Integer id) {
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
		try {
			 subjectMapper.deleteByPrimaryKey(id);
			resultCode = SubjectResultCode.RESULTCODE_SUCESS;
		} catch (Exception e) {
			log.error("delete subject by id error " + e);
		}
		return resultCode;
	}

	@Override
	public int insert(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.insert(record);
	}

	@Override
	public Subject selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return subjectMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Subject> queyAllSubject()  {
		// TODO Auto-generated method stub
		return subjectMapper.queyAllSubject();
	}

	

	@Override
	public List<Subject> queySubjectByType(Integer subjectType) {
		// TODO Auto-generated method stub
		return subjectMapper.queySubjectByType(subjectType);
	}

	@Override
	public List<Subject> queySubjectByContent(String subjectContent,Integer subjectDbId,Integer subjectType,Integer isOpen) {
		// TODO Auto-generated method stub
		return subjectMapper.queySubjectByContent(subjectContent, subjectDbId, subjectType, isOpen);
	}

	@Override
	public List<Subject> queySubjectBysubjectDbId(Integer subjectDbId) {

			return subjectMapper.queySubjectBysubjectDbId(subjectDbId);
		}
	

}
