/*
 * 文 件 名:  SubjectItemServiceImpl.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月13日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.dao.SubjectItemMapper;
import com.hoperun.entity.SubjectItem;
import com.hoperun.service.SubjectItemService;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月13日]
 * @since  [云服务/模块版本]
 */
@Service
public class SubjectItemServiceImpl implements SubjectItemService{

	@Autowired
	private SubjectItemMapper subjectItemMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(SubjectItem record) {
		// TODO Auto-generated method stub
		return subjectItemMapper.insert(record);
	}

	@Override
	public SubjectItem selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(SubjectItem record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
