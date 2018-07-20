package com.hoperun.dao;

import java.util.List;

import com.hoperun.entity.SubjectItem;

public interface SubjectItemMapper {
    int deleteByPrimaryKey(Integer id);

	int insert(SubjectItem record);

	SubjectItem selectByPrimaryKey(Integer id);
	List<com.hoperun.papermodle.SubjectItem> selectBySubjectId(Integer id);

	int updateByPrimaryKey(SubjectItem record);

	
}