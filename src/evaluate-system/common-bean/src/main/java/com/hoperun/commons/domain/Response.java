/*
 * 文 件 名:  SubjectResponse.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月16日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.commons.domain;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月16日]
 * @since  [云服务/模块版本]
 */
public class Response {

	private String resultCode;
	
	private String resultDesc;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	
}
