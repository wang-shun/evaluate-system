package com.hoperun.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.hoperun.dto.Account;
@Mapper
public interface AccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(String id);
    
    Account selectOne(Account record);
    
    List<Account> selectAllByDepartmentId(Integer departmentId);
    
    Map<String, Object> selectBycondition(Account record);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}