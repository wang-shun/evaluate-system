/*
 * 文 件 名:  SubjectDbserviceImpl.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月11日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoperun.constant.SubjectResultCode;
import com.hoperun.dao.SubjectDbMapper;
import com.hoperun.entity.SubjectDb;
import com.hoperun.service.SubjectDbService;
import com.hoperun.util.TimeUtil;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月11日]
 * @since [云服务/模块版本]
 */
@Service("subjectDbService")
public class SubjectDbserviceImpl implements SubjectDbService {
	private Logger log = LoggerFactory.getLogger(SubjectDbserviceImpl.class);
	@Autowired
	private SubjectDbMapper subjectDbMapper;

	@Override
	public String deleteByPrimaryKey(Integer id) {
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
		try {
			subjectDbMapper.deleteByPrimaryKey(id);
			resultCode = SubjectResultCode.RESULTCODE_SUCESS;
		} catch (Exception e) {
			log.error("delete subjectDb by id error " + e);
		}
		return resultCode;
	}

	@Override
	public String insert(SubjectDb record) {
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
        record.setCreateTime(TimeUtil.getCurrentTime());
        record.setIsOpen(1);
       
		try {
			int i = subjectDbMapper.insert(record);
			if (i == 1) {
				log.info("inser SubjectDb success");
				resultCode = SubjectResultCode.RESULTCODE_SUCESS;
			}
		} catch (Exception e) {
			log.error("inser SubjectDb error" + e);
		}
		return resultCode;
	}

	@Override
	public SubjectDb selectByPrimaryKey(Integer id) {
		SubjectDb db = new SubjectDb();
		try {
			db = subjectDbMapper.selectByPrimaryKey(id);

		} catch (Exception e) {
			log.error("selectByPrimaryKey error " + e);
		}
		return db;
	}

	@Override
	public String updateByPrimaryKey(SubjectDb record) {
		String resultCode = SubjectResultCode.RESULTCODE_FAIL;
		try {
			subjectDbMapper.updateByPrimaryKey(record);
			resultCode = SubjectResultCode.RESULTCODE_SUCESS;
		} catch (Exception e) {
			log.error("updateByPrimaryKey error " + e);
		}
		return resultCode;
	}

	@Override
	public List<SubjectDb> queryAllSubjectDb() {

		try {
			return subjectDbMapper.queryAllSubjectDb();
		} catch (Exception e) {
			log.error("queryAllSubjectDb error" + e);
		}
		return null;
	}

	@Override
	public SubjectDb getbySubjectName(String subjectName) {

		try {
			return subjectDbMapper.getbySubjectName(subjectName);
		} catch (Exception e) {
			log.error("getbySubjectName error" + e);
		}
		return null;
	}

	@Override
	public List<SubjectDb> querySubjectByOpenStatus(int status) {
		
		try {
			return subjectDbMapper.querySubjectByOpenStatus(status);
		} catch (Exception e) {
			log.error("querySubjectByOpenStatus error" + e);
		}
		return null;
	}

}
