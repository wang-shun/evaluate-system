package com.hoperun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hoperun.domain.Exam;
@Mapper
public interface ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer id);
    
    List<Exam> selectAll();

    int updateByPrimaryKeySelective(Exam record);

    int updateByPrimaryKey(Exam record);
    
    List<Exam> selectCreateExamid(Exam record);
}