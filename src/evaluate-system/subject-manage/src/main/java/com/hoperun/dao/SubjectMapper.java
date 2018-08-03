package com.hoperun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoperun.entity.Subject;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public interface SubjectMapper {
    /**
     * 
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 
     * @param record
     * @return
     */
    int insert(Subject record);

    /**
     * 
     * @param id
     * @return
     */
    Subject selectByPrimaryKey(Integer id);

    /**
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKey(Subject record);

    /**
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

//    /**
//     * 根据题目内容查询
//     * 
//     * @param subjectContent
//     * @param subjectDbId
//     * @param subjectType
//     * @param isOpen
//     * @param defficultyWeight
//     * @return
//     */
//    List<Subject> queySubjectByContent(@Param("subjectContent") String subjectContent,
//        @Param("subjectDbId") Integer subjectDbId, @Param("subjectType") Integer subjectType,
//        @Param("isOpen") Integer isOpen, @Param("defficultyWeight") Integer defficultyWeight);
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
    List<Subject> queySubjectByContent(Subject subject);

    /**
     * 查询题目
     * 
     * @param count
     * @param subjectDbId
     * @param subjectType
     * @param defficultyWeight
     * @return
     */
    List<Subject> querySubject(@Param("count") int count, @Param("subjectDbId") int subjectDbId,
        @Param("subjectType") int subjectType, @Param("defficultyWeight") int defficultyWeight);
}