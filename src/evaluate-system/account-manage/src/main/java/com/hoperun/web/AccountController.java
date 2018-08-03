package com.hoperun.web;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoperun.commons.domain.Account;
import com.hoperun.commons.domain.CODE;
import com.hoperun.commons.domain.FinalPojo;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.constant.UserImportConstant;
import com.hoperun.service.AccountService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账户接口 <功能详细描述>
 * 
 * @author xWX483171
 * @version [版本号, 2018年7月12日]
 * @since [云服务/模块版本]
 */
@RestController
public class AccountController {

	private static Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;
	@Resource
	private ResourceLoader resourceLoader;
	// @Autowired
	// private CacheService cacheService;

	/**
	 * 登录验证 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accessvalidat", method = RequestMethod.POST)
	public ResponseMsg accessValidat(@RequestBody String smsReceiptJSON, HttpServletResponse response) {
		logger.info("Enter controller method accessValidat!");

		JSONObject json = JSON.parseObject(smsReceiptJSON);
		ResponseMsg resp = null;
		String roleType = json.getString("roleType");
		String name = json.getString("name");
		String id = json.getString("id");
		String pwd = json.getString("pwd");
		Account account = new Account();
		account.setColRoleId(Integer.parseInt(roleType));
		account.setColAccountName(name);
		account.setId(id);
		account.setColPassowrd(pwd);
		boolean is = accountService.isAccessValidat(account);
		resp = is ? new ResponseMsg(CODE.RESP_SUC) : new ResponseMsg(CODE.UNEXP_ERR);
		if (FinalPojo.INIT_PASSWORD.equals(pwd) && is) {
			resp = new ResponseMsg(CODE.FIRST_LOGIN);
		}

		// 登录成功后即生成access token，并放置缓存后返回到响应头中
		if (!resp.getCode().equals(CODE.UNEXP_ERR)) {
			String orignal = UUID.randomUUID().toString().concat(String.valueOf(System.currentTimeMillis()));
			String accessToken = DigestUtils.md5DigestAsHex(orignal.getBytes());
			logger.info("AccountController make new at: {}", accessToken);
			// cacheService.expire(id.concat(FinalPojo.AT_KEY_PRE), accessToken,
			// FinalPojo.FIFTEEN_MINUTES);
			// cacheService.expire(id.concat(FinalPojo.AT_KEY_AFTER), accessToken,
			// FinalPojo.THIRTY_MINUTES);
			response.setHeader(FinalPojo.RESP_HEADER_ACCESSTOKEN, accessToken);
		}
		return resp;
	}

	/**
	 * 用户增加 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	public ResponseMsg accountAdd(@RequestBody Account body) {
		logger.info("Enter controller method accountAdd!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		// TODO
		String is = accountService.accountAdd(body);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 用户删除 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.DELETE)
	public ResponseMsg accountDel(@RequestBody List<String> id) {
		logger.info("Enter controller method accountDel!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		// TODO
		String is = accountService.accountDel(id);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 用户修改 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.PUT)
	public ResponseMsg accountUpdate(@RequestBody Account body) {
		logger.info("Enter controller method departmentUpdate!");
		ResponseMsg resp = new ResponseMsg(CODE.RESP_SUC);
		// TODO
		String is = accountService.accountUpdate(body);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	public static void accessIgnore(HttpServletResponse response) {
		/**
		 * header('Access-Control-Allow-Origin:*');//允许所有来源访问
		 * header('Access-Control-Allow-Method:POST,GET,PUT,DELETE,OPTIONS);//允许访问的方式
		 */
		response.setHeader("Access-Control-Allow-Method", "POST,GET,PUT,DELETE,OPTIONS");
	}

	/**
	 * 查询单个账户 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ResponseMsg getOneAccount(HttpServletRequest request, @RequestParam(required = false) String id,
			@RequestParam(required = false) String accountName) {
		logger.info("Enter controller method getOneAccount!");
		ResponseMsg resp = null;
		Account account = new Account();
		account.setColAccountName(accountName);
		account.setId(id);
		resp = new ResponseMsg(CODE.RESP_SUC);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("oneAccount", accountService.findOneAccount(account));
		resp.setData(data);

		return resp;
	}

	/**
	 * 修改个人密码 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public ResponseMsg updatePassword(@RequestBody String jsonData) {
		logger.info("Enter controller method updatePassword!");
		JSONObject json = JSON.parseObject(jsonData);
		ResponseMsg resp = null;
		String roleType = json.getString("roleid");
		String id = json.getString("id");
		String oldpwd = json.getString("oldpassword");
		String newpwd = json.getString("newpassword");
		Account account = new Account();
		account.setColRoleId(Integer.parseInt(roleType));
		account.setId(id);
		account.setColPassowrd(oldpwd);

		// 通过全名称字段保存新密码
		account.setColFullName(newpwd);
		String is = accountService.updatePassword(account);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 管理员重置个人密码 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/password/{id}", method = RequestMethod.PUT)
	public ResponseMsg resetPassword(@PathVariable String id) {
		logger.info("Enter controller method resetPassword!");
		Account account = new Account();
		account.setId(id);

		ResponseMsg resp = null;
		String is = accountService.resetPassword(account);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 批量修改用户的部门信息 <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accounttodepartment", method = RequestMethod.PUT)
	public ResponseMsg accountToDepartment(@RequestBody String jsonData) {
		logger.info("Enter controller method accountToDepartment!");
		JSONObject json = JSON.parseObject(jsonData);
		ResponseMsg resp = null;
		List<String> ids = JSON.parseArray(json.getJSONArray("ids").toJSONString(), String.class);
		int departmentId = json.getIntValue("departmentid");

		String is = accountService.accountToDepartment(ids, departmentId);
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * 账户批量导入 <功能详细描述>
	 * 
	 * @param file
	 * @param departmentId
	 * @param creatorId
	 * @return
	 */
	@PostMapping("/importAccount")
	public ResponseMsg importAccount(@RequestParam("file") MultipartFile file,
			@RequestParam("departmentId") Integer departmentId, @RequestParam("creatorId") String creatorId) {
		logger.info("Enter controller method importAccount!");
		ResponseMsg resp = null;
		String is = null;
		try {
			is = accountService.importAccount(file, departmentId, creatorId);
		} catch (Exception e) {
			logger.error("importAccount error.{}", e);
			is = e.getMessage();
		}
		if (StringUtils.isEmpty(is)) {
			resp = new ResponseMsg(CODE.RESP_SUC);
		} else {
			resp = new ResponseMsg(CODE.UNEXP_ERR);
			resp.setMessage(is);
		}
		return resp;
	}

	/**
	 * test <功能详细描述>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testinfoshow", method = RequestMethod.GET)
	public ResponseMsg testinfoshow(HttpServletRequest request) {
		logger.info("Enter controller method testinfoshow!");
		ResponseMsg resp = null;
		resp = new ResponseMsg(CODE.RESP_SUC);

		return resp;
	}

	@GetMapping("/download")
	public Map<String, Object> downloadTemplate(HttpServletResponse response) {
		logger.info("begin to download userTempalte");
		String fileName = "试题模板.xls";
		File file;
		InputStream inputStream = null;
		ServletOutputStream servletOutputStream = null;
		Map<String, Object> maps = new HashMap<>();
		try {
			org.springframework.core.io.Resource resource = resourceLoader
					.getResource(UserImportConstant.USER_TEMPLATE_PATH);
			file = ResourceUtils.getFile(UserImportConstant.USER_TEMPLATE_PATH);
			if (!file.exists()) {
				maps.put(UserImportConstant.USERTEMPLATE_IS_NOTEXISTS,
						UserImportConstant.USERTEMPLATE_IS_NOTEXISTS_DESC);
				return maps;
			}
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.addHeader("charset", "utf-8");
			response.addHeader("Pragma", "no-cache");
			String encodeName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

			inputStream = resource.getInputStream();
			servletOutputStream = response.getOutputStream();
			IOUtils.copy(inputStream, servletOutputStream);
			response.flushBuffer();

		} catch (IOException e) {
			maps.put(UserImportConstant.USERTEMPLATE_IS_NOTEXISTS, UserImportConstant.USERTEMPLATE_IS_NOTEXISTS_DESC);
			logger.error("template file is not exist" + e);
			return maps;
		} finally {
			try {
				if (servletOutputStream != null) {
					servletOutputStream.close();
					servletOutputStream = null;
				}
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
				// 召唤jvm的垃圾回收器
				System.gc();
			} catch (Exception e) {

				maps.put(UserImportConstant.USERTEMPLATE_IS_NOTEXISTS,
						UserImportConstant.USERTEMPLATE_IS_NOTEXISTS_DESC);
				logger.error("close steam error");
				return maps;
			}
		}

		maps.put(UserImportConstant.USERTEMPLATE_DOWNLOAD_SUCESS, UserImportConstant.USERTEMPLATE_DOWNLOAD_SUCESS_DESC);
		return maps;

	}

}
