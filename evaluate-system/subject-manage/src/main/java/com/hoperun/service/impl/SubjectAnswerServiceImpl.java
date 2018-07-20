/*
 * 文 件 名:  SubjectAnswerServiceImpl.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.dao.SubjectAnswerMapper;
import com.hoperun.entity.SubjectAnswer;
import com.hoperun.service.AnswerService;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Service
public class SubjectAnswerServiceImpl implements AnswerService {

	@Autowired
	private SubjectAnswerMapper subjectAnswerMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return subjectAnswerMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SubjectAnswer record) {

		return subjectAnswerMapper.insert(record);

	}

	@Override
	public SubjectAnswer selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return subjectAnswerMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(SubjectAnswer record) {
		// TODO Auto-generated method stub
		return subjectAnswerMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteBySubjectId(Integer subjectId) throws Exception {
		// TODO Auto-generated method stub
		return subjectAnswerMapper.deleteBySubjectId(subjectId);
	}

	@Override
	public List<SubjectAnswer> selectBySubjectId(Integer subjectId) throws Exception {
		// TODO Auto-generated method stub
		return subjectAnswerMapper.selectBySubjectId(subjectId);
	}

}
