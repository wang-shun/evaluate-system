/*
 * 文 件 名:  HttpUtil.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月16日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hoperun.constant.SubjectResponse;
import com.hoperun.constant.SubjectResultCode;


/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月16日]
 * @since [云服务/模块版本]
 */
public class HttpUtil {

	/**
	 * 返回响应
	 * <功能详细描述>
	 * @param resultCode
	 * @return
	 */
	public static SubjectResponse getSubjectResonse(String resultCode) {
		SubjectResponse subject = new SubjectResponse();
		if (SubjectResultCode.RESULTCODE_SUCESS.equals(resultCode)) {
			subject.setResultDesc(SubjectResultCode.RESULTCODE_SUCESS_DESC);
		} else {
			subject.setResultDesc(SubjectResultCode.RESULTCODE_FAIL_DESC);
		}
		subject.setResultCode(resultCode);
		return subject;
	}
	/**
	 * 
	 * <功能详细描述>
	 * @param httpServletResponse
	 * @param httpServletRequest
	 * @return
	 */
	public static HttpServletResponse acessmhttpServletResponse(HttpServletResponse httpServletResponse) {
	
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Method:POST","GET,PUT,DELETE,OPTIONS");
		return httpServletResponse;
	}
}
