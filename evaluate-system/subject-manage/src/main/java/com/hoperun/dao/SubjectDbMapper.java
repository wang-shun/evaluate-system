package com.hoperun.dao;

import java.util.List;

import com.hoperun.entity.SubjectDb;

public interface SubjectDbMapper {
	int deleteByPrimaryKey(Integer id) throws Exception;

	int insert(SubjectDb record)throws Exception;

	SubjectDb selectByPrimaryKey(Integer id)throws Exception;

	int updateByPrimaryKey(SubjectDb record)throws Exception;

	// 查询所有题库
	List<SubjectDb> queryAllSubjectDb()throws Exception;

	// 根据题库名称查询
	SubjectDb getbySubjectName(String subjectName)throws Exception;

	// 根据是否开启查询
	List<SubjectDb> querySubjectByOpenStatus(int staus)throws Exception;
}