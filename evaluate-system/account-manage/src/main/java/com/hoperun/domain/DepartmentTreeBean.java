package com.hoperun.domain;

import java.util.List;

import com.hoperun.dto.Department;
/**
 * 部门的树结构
 * <功能详细描述>
 * 
 * @author  xWX483171
 * @version  [版本号, 2018年7月18日]
 * @since  [云服务/模块版本]
 */
public class DepartmentTreeBean extends Department
{
    
    private List<DepartmentTreeBean> sonList;

    public List<DepartmentTreeBean> getSonList()
    {
        return sonList;
    }

    public void setSonList(List<DepartmentTreeBean> sonList)
    {
        this.sonList = sonList;
    }
    
}
