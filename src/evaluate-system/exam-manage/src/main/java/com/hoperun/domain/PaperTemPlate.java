/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月25日]
 * @since  [阿里运/模块1.0版本]
 */
package com.hoperun.domain;

import java.util.List;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月25日]
 * @since [马振勇/模块版本]
 */
public class PaperTemPlate {
	private Integer id;

	private String colTemplateName;

	private Integer colCreateTime;

	private String colCreator;
	private List<ExamPaperTemPlateDetail> details;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getColTemplateName() {
		return colTemplateName;
	}

	public void setColTemplateName(String colTemplateName) {
		this.colTemplateName = colTemplateName;
	}

	public Integer getColCreateTime() {
		return colCreateTime;
	}

	public void setColCreateTime(Integer colCreateTime) {
		this.colCreateTime = colCreateTime;
	}

	public String getColCreator() {
		return colCreator;
	}

	public void setColCreator(String colCreator) {
		this.colCreator = colCreator;
	}

	public List<ExamPaperTemPlateDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ExamPaperTemPlateDetail> details) {
		this.details = details;
	}

}
