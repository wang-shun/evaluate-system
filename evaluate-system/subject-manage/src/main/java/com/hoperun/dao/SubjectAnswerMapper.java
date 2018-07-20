package com.hoperun.dao;

import java.util.List;

import com.hoperun.entity.SubjectAnswer;

public interface SubjectAnswerMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SubjectAnswer record);

	int insertSelective(SubjectAnswer record);

	SubjectAnswer selectByPrimaryKey(Integer id);

	List<SubjectAnswer> selectBySubjectId(Integer SubjectId);

	int deleteBySubjectId(Integer subjectId);

	SubjectAnswer queryBySubjectId(Integer subjectId);

	int updateByPrimaryKey(SubjectAnswer record);
}