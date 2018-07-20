/*
 * 文 件 名:  AnswerService.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hoperun.entity.SubjectAnswer;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Transactional
public interface AnswerService {
	int deleteByPrimaryKey(Integer id) throws Exception;
	int deleteBySubjectId(Integer subjectId) throws Exception;

	int insert(SubjectAnswer record)throws Exception;

	SubjectAnswer selectByPrimaryKey(Integer id)throws Exception;
	List<SubjectAnswer> selectBySubjectId(Integer SubjectId)throws Exception;

	int updateByPrimaryKey(SubjectAnswer record)throws Exception;
}
