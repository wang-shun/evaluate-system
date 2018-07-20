package com.hoperun.dao;


import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;

import com.hoperun.entity.ExamPaperSubject;

public interface ExamPaperSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamPaperSubject record);

    ExamPaperSubject selectByPrimaryKey(Integer id);
   ExamPaperSubject selectBySubjectIdAndPaperId(@Param("subjectId") Integer subjectId,@Param("examPaper") Integer examPaperId);

    int updateByPrimaryKey(ExamPaperSubject record);
    
    int getSumScore(Integer examPaper);
}