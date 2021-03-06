package com.hoperun.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hoperun.commons.domain.Account;
import com.hoperun.commons.domain.CODE;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.domain.DepartmentTreeBean;
import com.hoperun.dto.Department;
import com.hoperun.service.DepartmentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门接口 <功能详细描述>
 * 
 * @author xWX483171
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@RestController
public class DepartmentController {

	private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;
	

	/**
	 * 根据父部门id查询其下所有子部门信息 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/department", method = RequestMethod.POST)
	public ResponseMsg sonDepartmentGet(@RequestBody String body) {
		logger.info("Enter controller method sonDepartmentGet!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		JSONObject json = JSON.parseObject(body);
		Map<String, Object> data = new HashMap<String, Object>();
		int colParentId = json.getIntValue("parentId");
		List<Integer> last = departmentService.selectLastParentId();
		List<Department> dataList = departmentService.findSonDepartment(colParentId);
		if (null != dataList && null != last) {
			for (Department one : dataList) {
				for (int i = 0; i < last.size(); i++) {
					if (last.get(i).equals(one.getId())) {
						one.setColParentId(-1);
						break;
					}
				}
			}
		}
		data.put("sonDepartmentInfos", dataList);
		resp.setData(data);

		return resp;
	}

	/**
	 * 部门增加 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/departmentadd", method = RequestMethod.POST)
	public ResponseMsg departmentAdd(@RequestBody Department body) {
		logger.info("Enter controller method departmentAdd!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		// TODO
		String is = departmentService.departmentAdd(body);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 部门删除 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE)
	public ResponseMsg departmentDel(@PathVariable Integer id) {
		logger.info("Enter controller method departmentDel!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		// TODO
		String is = departmentService.departmentDel(id);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 部门修改 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/department", method = RequestMethod.PUT)
	public ResponseMsg departmentUpdate(@RequestBody Department body) {
		logger.info("Enter controller method departmentUpdate!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		// TODO
		String is = departmentService.departmentUpdate(body);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 根据一个部门id查询其下所有子部门信息树结构 <功能详细描述>
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/departmenttree/{id}", method = RequestMethod.GET)
	public ResponseMsg getDepartmentTree(@PathVariable Integer id) {
		logger.info("Enter controller method getDepartmentTree!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		List<DepartmentTreeBean> dataList = departmentService.getDepartmentTree(id);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("infos", dataList);
		resp.setData(data);
		return resp;
	}

	/**
	 * 通过部门id获取属于该部门下所有的员工 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	// @RequestMapping(value="/department/{departmentId}", method=
	// RequestMethod.GET)
	// public ResponseMsg accountByDepartment(@PathVariable Integer departmentId) {
	// logger.info("Enter controller method accountByDepartment!");
	// ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
	// Map<String, Object> data = new HashMap<String, Object>();
	// data.put("accountInfos",
	// departmentService.findAccountByDepartmentId(departmentId));
	// resp.setData(data);
	// return resp;
	// }
	/**
	 * 通过部门id获取属于该部门下所有的员工 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/department", method = RequestMethod.GET)
	public PageInfo<Account> accountByDepartment(
			@RequestParam(value = "departmentId", required = false) Integer departmentId,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "accountName", required = false) String accountName,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		PageInfo<Account>info=null;
		logger.info("Enter controller method accountByDepartment!");
		System.out.println("=======================");
		try {
			info	=	departmentService.findAccount(departmentId, id, accountName, pageNum, pageSize);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		 return info;
	}
}
