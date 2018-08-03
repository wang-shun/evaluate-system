package com.hoperun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hoperun.domain.ExamPaperTemPlate;
@Mapper
public interface ExamPaperTemPlateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamPaperTemPlate record);

  
    ExamPaperTemPlate selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(ExamPaperTemPlate record);
    List<ExamPaperTemPlate>selectAll(@Param(value="colTemplateName")String colTemplateName,@Param(value="colCreator")String colCreator);
}