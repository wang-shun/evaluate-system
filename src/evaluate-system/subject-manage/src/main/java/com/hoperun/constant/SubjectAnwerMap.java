/*
 * 文 件 名:  SubjectAnwerMap.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月13日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月13日]
 * @since [云服务/模块版本]
 */
public class SubjectAnwerMap {
	public static Map<String, Integer> SUBJECTAWERMAP=new HashMap<>();
	static {
		SUBJECTAWERMAP.put(SubjectConstant.ANSWER_A, 1);
		SUBJECTAWERMAP.put(SubjectConstant.ANSWER_B, 2);
		SUBJECTAWERMAP.put(SubjectConstant.ANSWER_C, 4);
		SUBJECTAWERMAP.put(SubjectConstant.ANSWER_D, 8);
		SUBJECTAWERMAP.put(SubjectConstant.ANSWER_E, 16);
		SUBJECTAWERMAP.put(SubjectConstant.ANSWER_F, 32);
		
	}
}
