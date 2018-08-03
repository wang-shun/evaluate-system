package com.hoperun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hoperun.domain.ExamStruct;
@Mapper
public interface ExamStructMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamStruct record);

    int insertSelective(ExamStruct record);

    ExamStruct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExamStruct record);

    int updateByPrimaryKey(ExamStruct record);
    
    List<ExamStruct> getExamStructPojo(int colExamId);
}