/*
 * 文 件 名: SubjectService.java 版 权: Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved. 描 述: <描述> 修 改 人:
 * mWX576942 修改时间: 2018年7月12日 修改版本: <跟踪单号>
 */
package com.hoperun.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.hoperun.entity.Subject;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@Transactional
public interface SubjectService {
    /**
     * 删除
     * 
     * @param id
     * @return
     * @throws Exception
     */
    String deleteByPrimaryKey(Integer id) throws Exception;

    /**
     * 新增
     * 
     * @param record
     * @return
     * @throws Exception
     */
    int insert(Subject record) throws Exception;

    /**
     * 查询
     * 
     * @param id
     * @return
     * @throws Exception
     */
    Subject selectByPrimaryKey(Integer id) throws Exception;

    /**
     * 修改
     * 
     * @param record
     * @return
     * @throws Exception
     */
    String updateByPrimaryKey(Subject record) throws Exception;

    /**
     * 查询所有题目
     * 
     * @return
     */
    List<Subject> queyAllSubject();

    /**
     * 根据题库查询
     * 
     * @param subjectDbId
     * @return
     */
    List<Subject> queySubjectBysubjectDbId(Integer subjectDbId);

    /**
     * 根据题目类型查询
     * 
     * @param subjectType
     * @return
     */
    List<Subject> queySubjectByType(Integer subjectType);

    /**
     * 根据题目内容查询
     * 
     * @param subjectContent
     * @param subjectDbId
     * @param subjectType
     * @param isOpen
     * @param defficultyWeight
     * @return
     */
    PageInfo<Subject> queySubjectByContent(String subjectContent, Integer subjectDbId, Integer subjectType, Integer isOpen,
        Integer defficultyWeight,Integer pageNum,Integer pageSize);
    
    public String addSubject(Subject insertSubjectModle);

}
