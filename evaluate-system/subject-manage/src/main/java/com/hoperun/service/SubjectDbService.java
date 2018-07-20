package com.hoperun.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hoperun.entity.SubjectDb;

/**
 * 
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月11日]
 * @since [云服务/模块版本]
 */

@Transactional
public interface SubjectDbService {
	String deleteByPrimaryKey(Integer id);

	String insert(SubjectDb record);

	//查询所有题库
	List<SubjectDb> queryAllSubjectDb();
	
	//根据题库名称查询
    SubjectDb getbySubjectName(String subjectName);
    
    //根据是否开启查询
    List<SubjectDb>querySubjectByOpenStatus(int status);
	SubjectDb selectByPrimaryKey(Integer id);
	String updateByPrimaryKey(SubjectDb record);
}
