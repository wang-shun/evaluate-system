package com.hoperun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hoperun.domain.ExamPaperTemPlateDetail;

@Mapper
public interface ExamPaperTemPlateDetailMapper {
	int deleteByPrimaryKey(Integer id);
    /**
     * 根据模板id删除模板详情
     * <功能详细描述>
     * @param colTemplateId
     * @return
     */
	int deleteByTemplateId(Integer colTemplateId);

	int insert(ExamPaperTemPlateDetail record);

	ExamPaperTemPlateDetail selectByPrimaryKey(Integer id);

	int updateByPrimaryKey(ExamPaperTemPlateDetail record);

	List<ExamPaperTemPlateDetail> selectAll();

	List<ExamPaperTemPlateDetail> listByTemlateId(Integer colTemplateId);

}