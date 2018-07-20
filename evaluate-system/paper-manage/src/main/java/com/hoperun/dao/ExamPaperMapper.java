package com.hoperun.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.hoperun.entity.ExamPaper;
@Transactional
public interface ExamPaperMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamPaper record);

    ExamPaper selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ExamPaper record);
    
    List<ExamPaper> getExamAccountList(String accountId);
    
    List<ExamPaper> getExamListByExamId(String examIdStr);
}