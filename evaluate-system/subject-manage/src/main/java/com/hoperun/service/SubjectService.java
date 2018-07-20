/*
 * 文 件 名:  SubjectService.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月12日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hoperun.entity.Subject;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月12日]
 * @since  [云服务/模块版本]
 */
@Transactional
public interface SubjectService {
    String deleteByPrimaryKey(Integer id) throws Exception;

    int insert(Subject record) throws Exception;
  
    Subject selectByPrimaryKey(Integer id)throws Exception;

    int updateByPrimaryKey(Subject record)throws Exception;
    List<Subject>queyAllSubject();
    //根据题库查询
    List<Subject>queySubjectBysubjectDbId(Integer subjectDbId);
    //根据题目类型查询
    List<Subject>queySubjectByType(Integer subjectType);
    //根据题目内容查询
    List<Subject>queySubjectByContent(String subjectContent,Integer subjectDbId,Integer subjectType,Integer isOpen);
}
