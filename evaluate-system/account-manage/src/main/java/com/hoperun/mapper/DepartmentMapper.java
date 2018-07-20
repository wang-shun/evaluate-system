package com.hoperun.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.hoperun.dto.Department;
@Mapper
public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);
    
    List<Department> selectByParentId(Integer colParentId);
    
    List<Department> selectAll();
    
    List<Integer> selectLastParentId();

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}