package com.hoperun.dao;

import javax.transaction.Transactional;

import com.hoperun.entity.ExamAccount;
@Transactional
public interface ExamAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamAccount record);

    ExamAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ExamAccount record);
}