package com.hoperun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoperun.entity.SubjectDb;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
public interface SubjectDbMapper {
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int deleteByPrimaryKey(Integer id) throws Exception;

	/**
	 * 新增
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	int insert(SubjectDb record) throws Exception;

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SubjectDb selectByPrimaryKey(Integer id) throws Exception;

	/**
	 * 修改
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	int updateByPrimaryKey(SubjectDb record) throws Exception;

	/**
	 * 查询所有题库
	 * 
	 * @return
	 * @throws Exception
	 */
	List<SubjectDb> queryAllSubjectDb(@Param(value = "subjectDbName") String subjectDbName,
			@Param(value = "isOpen") Integer isOpen,@Param(value="creatorId")String creatorId) throws Exception;

	/**
	 * 根据题库名称查询
	 * 
	 * @param subjectName
	 * @return
	 * @throws Exception
	 */
	SubjectDb getbySubjectName(String subjectName) throws Exception;

	/**
	 * 根据是否开启查询
	 * 
	 * @param staus
	 * @return List<SubjectDb>
	 * @throws Exception
	 *             抛出异常
	 */
	List<SubjectDb> querySubjectByOpenStatus(int staus) throws Exception;
}