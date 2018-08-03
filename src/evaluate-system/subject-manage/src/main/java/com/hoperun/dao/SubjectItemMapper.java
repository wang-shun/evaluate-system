package com.hoperun.dao;

import java.util.List;

import com.hoperun.entity.SubjectItem;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public interface SubjectItemMapper {
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
    int insert(SubjectItem record);

    /**
     * 查询
     * 
     * @param id
     * @return
     */
    SubjectItem selectByPrimaryKey(Integer id);

    /**
     * 查询
     * 
     * @param id
     * @return
     */
    List<com.hoperun.papermodle.SubjectItem> selectBySubjectId(Integer id);

    /**
     * 修改
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKey(SubjectItem record);
    /**
     * 根据题目id删除题目详情
     * @param subjectId
     * @return
     */
    int deleteBySubjectId(Integer subjectId);

}