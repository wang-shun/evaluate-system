package com.hoperun.dao;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Param;

import com.hoperun.entity.ExamPaperSubject;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public interface ExamPaperSubjectMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param record
     * @return
     */
    int insert(ExamPaperSubject record);

    /**
     * 查询
     * @param id
     * @return
     */
    ExamPaperSubject selectByPrimaryKey(Integer id);

    /**
     * 查询
     * @param subjectId
     * @param examPaperId
     * @return
     */
    ExamPaperSubject selectBySubjectIdAndPaperId(@Param("subjectId") Integer subjectId,
        @Param("examPaper") Integer examPaperId);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKey(ExamPaperSubject record);

    /**
     * 计算总分
     * @param examPaper
     * @return
     */
    int getSumScore(Integer examPaper);
}