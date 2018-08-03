package com.hoperun.dao;

import javax.transaction.Transactional;

import com.hoperun.entity.ExamAccount;
/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
@Transactional(rollbackOn=Exception.class)
public interface ExamAccountMapper {
    /**
     * 根据主键删除ExamAccount
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);
    /**
     * 新增ExamAccount
     * @param record
     * @return
     */
    int insert(ExamAccount record);
    /**
     * 根据主键查询ExamAccount
     * @param id
     * @return
     */
    ExamAccount selectByPrimaryKey(Integer id);
    /**
     * 修改ExamAccount
     * @param record
     * @return
     */
    int updateByPrimaryKey(ExamAccount record);
}