package com.hoperun.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hoperun.commons.domain.Account;

@Mapper
public interface AccountMapper {
	int deleteByPrimaryKey(String id);

	int insert(Account record);

	int insertSelective(Account record);

	Account selectByPrimaryKey(String id);

	Account selectOne(Account record);

	List<Account> selectAllByDepartmentId(@Param(value = "departmentId") Integer departmentId,
			@Param(value = "id") String id, @Param(value = "colAccountName") String colAccountName);

	Map<String, Object> selectBycondition(Account record);

	int updateByPrimaryKeySelective(Account record);

	int updateByPrimaryKey(Account record);

	int updatePassword(Account record);

	int resetPassword(Account record);

	int accountToDepartment(Map<String, Object> para);
}