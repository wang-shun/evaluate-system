package com.hoperun.dao;

import java.util.List;

import com.hoperun.entity.SubjectAnswer;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public interface SubjectAnswerMapper {
    /**
     * 删除
     * 
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * 
     * @param record
     * @return
     */
    int insert(SubjectAnswer record);

    /**
     * 查询
     * 
     * @param id
     * @return
     */
    SubjectAnswer selectByPrimaryKey(Integer id);

    /**
     * 查询
     * 
     * @param subjectId
     * @return
     */
    List<SubjectAnswer> selectBySubjectId(Integer subjectId);

    /**
     * 查询
     * 
     * @param subjectId
     * @return
     */
    int deleteBySubjectId(Integer subjectId);

    /**
     * 查询
     * 
     * @param subjectId
     * @return
     */
    SubjectAnswer queryBySubjectId(Integer subjectId);

    /**
     * 修改
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKey(SubjectAnswer record);
    
   
}