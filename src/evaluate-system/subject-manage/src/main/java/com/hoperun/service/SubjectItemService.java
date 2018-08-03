/*
 * 文 件 名:  SubjectItemService.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月13日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service;

import org.springframework.transaction.annotation.Transactional;

import com.hoperun.entity.SubjectItem;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月13日]
 * @since [云服务/模块版本]
 */
@Transactional
public interface SubjectItemService {
	int deleteByPrimaryKey(Integer id);

	int insert(SubjectItem record);

	SubjectItem selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(SubjectItem record);
}
