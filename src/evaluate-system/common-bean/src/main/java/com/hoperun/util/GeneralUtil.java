/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月25日]
 * @since  [阿里运/模块1.0版本]
 */
package com.hoperun.util;

import java.util.List;

/**
 * 常量工具类 <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月25日]
 * @since [马振勇/模块版本]
 */
public class GeneralUtil {
	/**
	 * 
	 * @param list
	 * @return
	 * 
	 */
	public static boolean isNotNullOrZeroSize(List list) {
	    
		boolean flag = false;
		System.out.println(list.size());
		if (null != list & 0 != list.size()) {
			flag = true;
		}
		return flag;

	}

}
