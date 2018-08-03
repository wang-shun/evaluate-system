package com.hoperun.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.hoperun.entity.ExamPaper;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
@Transactional(rollbackOn = Exception.class)
public interface ExamPaperMapper {
    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     * @param record
     * @return
     */
    int insert(ExamPaper record);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    ExamPaper selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */

    int updateByPrimaryKey(ExamPaper record);

    /**
     * 查询ExamAccount
     * @param accountId
     * @return
     */
    List<ExamPaper> getExamAccountList(String accountId);

    /**
     * 查询ExamAccount
     * @param examIdStr
     * @return
     */
    List<ExamPaper> getExamListByExamId(String examIdStr);
}