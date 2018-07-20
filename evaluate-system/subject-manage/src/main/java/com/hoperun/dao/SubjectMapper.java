package com.hoperun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoperun.entity.Subject;

public interface SubjectMapper
{
    int deleteByPrimaryKey(Integer id);
    
    int insert(Subject record);
    
    Subject selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKey(Subject record);
    
    List<Subject> queyAllSubject();
    
    //根据题库查询
    List<Subject> queySubjectBysubjectDbId(Integer subjectDbId);
    
    //根据题目类型查询
    List<Subject> queySubjectByType(Integer subjectType);
    
    // 根据题目内容查询
    List<Subject> queySubjectByContent(@Param("subjectContent") String subjectContent,
        @Param("subjectDbId") Integer subjectDbId, @Param("subjectType") Integer subjectType,
        @Param("isOpen") Integer isOpen);
    
    List<Subject> querySubject(@Param("count") int count, @Param("subjectDbId") int subjectDbId,@Param("subjectType") int subjectType);
}