package com.hoperun.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hoperun.domain.ExamAccount;
import com.hoperun.domain.ExamAll;
@Mapper
public interface ExamAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamAccount record);

    int insertSelective(ExamAccount record);

    ExamAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExamAccount record);
    
    int updateByPrimaryKey(ExamAccount record);
    
    List<ExamAll> selectByAccountId(Map<String, Object> obj);
    
    List<ExamAccount> examNotAccess(Integer examId);
    
    List<ExamAccount> selectByExamId(Integer examId);
    
    List<ExamAccount> examNeedDo(Integer examId);
    
}