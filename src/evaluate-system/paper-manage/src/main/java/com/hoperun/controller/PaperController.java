/*
 * 文 件 名: PaperController.java 版 权: Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved. 描 述: <描述> 修 改 人:
 * mWX576942 修改时间: 2018年7月17日 修改版本: <跟踪单号>
 */
package com.hoperun.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hoperun.commons.domain.ExamResponse;
import com.hoperun.commons.domain.Response;
import com.hoperun.commons.domain.ResponseMsg;
import com.hoperun.commons.domain.ResultCode;
import com.hoperun.constant.PaperResultCode;
import com.hoperun.constant.PapersatusConstant;
import com.hoperun.dao.ExamPaperSubjectMapper;
import com.hoperun.entity.ExamPaper;
import com.hoperun.modle.ExamPaperEntity;
import com.hoperun.modle.ExamStruct;
import com.hoperun.modle.SubjectSubmitModle;
import com.hoperun.papermodle.ExamPaperSubject;
import com.hoperun.papermodle.ExamSubject;
import com.hoperun.papermodle.SubjectAnswer;
import com.hoperun.service.ExamPaperService;
import com.hoperun.service.impl.Examutil;
import com.hoperun.util.ResopnseUtil;

/**
 * <功能详细描述>
 * 
 * @author mWX576942
 * @version [版本号, 2018年7月17日]
 * @since [云服务/模块版本]
 */
@RestController
public class PaperController {
    private Logger logger = LoggerFactory.getLogger(PaperController.class);

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private ExamPaperSubjectMapper examPaperSubjectMapper;

    @Autowired
    private Examutil examutil;

    /**
     * 生成试卷 <功能详细描述>
     * 
     * @param httpServletRequest
     * @param response
     * @return
     */
    @PostMapping("/examPapers")
    public ExamPaperSubject createPaper(HttpServletRequest httpServletRequest, HttpServletResponse response) {

        logger.info("begin to examPapers");
        ExamPaperSubject examPaperSubject = new ExamPaperSubject();
        try {
            ServletInputStream inputStream = httpServletRequest.getInputStream();
            String msg = IOUtils.toString(inputStream, "UTF-8");
            JSONObject object = JSONObject.parseObject(msg);
            ExamPaperEntity entity = JSONObject.toJavaObject(object, ExamPaperEntity.class);
            if (examutil.validateEntity(entity)) {
                examPaperSubject = this.insertPaper(entity, Integer.valueOf(entity.getSubjectDbId()));
            } else {
                logger.error("validate error");
                return null;
            }

        } catch (Exception e) {
            logger.error("createPaper error" + e);
        }

        return examPaperSubject;

    }

    /**
     * 提交试卷 <功能详细描述>
     * 
     * @param httpServletRequest
     * @return
     */
    @PutMapping(value = "submitExamPaper")
    public ExamResponse submitExamPaper(HttpServletRequest httpServletRequest) {
        logger.info("begin to submitExamPaper");
        String resultCode = ResultCode.RESULTCODE_FAIL;
        int score = 0;
        int isPass = 99;
        try {
            ServletInputStream inputStream = httpServletRequest.getInputStream();
            String msg = IOUtils.toString(inputStream, "UTF-8");
            logger.info("================msg" + msg);
            JSONObject object = JSONObject.parseObject(msg);
            // 提交内容
            SubjectSubmitModle entity = JSONObject.toJavaObject(object, SubjectSubmitModle.class);
            // 更新考试答题表
            // 试卷id
            int examPaperId = entity.getExamPaperId();
            Long beginTime = entity.getBeginTime();
            Long endTime = entity.getEndTime();
            List<SubjectAnswer> answers = entity.getAnswers();
            for (SubjectAnswer subjectAnswer : answers) {
                int subjectId = subjectAnswer.getSubjectId();
                // 根据id查询答案
                com.hoperun.entity.ExamPaperSubject examPaperSubject =
                    examPaperSubjectMapper.selectBySubjectIdAndPaperId(subjectId, examPaperId);
                if ("".equals(examPaperSubject) || null == examPaperSubject) {
                    logger.error("examPaperSubjects is null");
                    return ResopnseUtil.getExamResponse(resultCode, score, isPass);
                }
                examPaperSubject.setSubmitAnswer(subjectAnswer.getSubmitAnswer());
                // 判断是否正确
                int trueAnswer = examPaperSubject.getSubjectAnswer();
                // 提交的答案
                int submitAnswer = subjectAnswer.getSubmitAnswer();
                // 正确
                if (trueAnswer == submitAnswer) {
                    examPaperSubject.setIsCorrect(1);
                } else {
                    examPaperSubject.setIsCorrect(0);
                }
                // 更新提交答案表
                examPaperSubjectMapper.updateByPrimaryKey(examPaperSubject);
            }
            // 计算总分数
            score = examPaperSubjectMapper.getSumScore(examPaperId);

            //
            // 更新表TB_EXAM_PAPER
            ExamPaper examPaper = examPaperService.selectByPrimaryKey(examPaperId);
            examPaper.setSubmitTime(endTime);
            examPaper.setScore(score);
            examPaper.setStartTime(beginTime);
            logger.info("endtime " + endTime + "score" + score + "begintime" + beginTime);
            // 调用rest服务查看考试是否通过
            ResponseMsg rspmsg =
                examutil.queryExamPass(examPaper.getPaperId(), Integer.valueOf(examPaper.getExamId()), score);
            logger.info("queryExamPass resultcode " + rspmsg.getCode());
            // 通过
            if (ResultCode.RESULTCODE_SUCESS_ISPASS.equals(rspmsg.getCode())) {
                examPaper.setPaperStatus(PapersatusConstant.PASS);
                examPaper.setPaperStatusDesc("通过");
                isPass = PapersatusConstant.PASS;
            } else {
                examPaper.setPaperStatus(PapersatusConstant.DONTPASS);
                examPaper.setPaperStatusDesc("未通过");
            }
            examPaperService.updateByPrimaryKey(examPaper);
            resultCode = ResultCode.RESULTCODE_SUCESS;
        }

        catch (Exception e) {
            logger.error("createPaper error" + e);
        }
        logger.info("end to submitExamPaper resultCode is " + resultCode);
        return ResopnseUtil.getExamResponse(resultCode, score, isPass);

    }

    /**
     * 生成试卷 <功能详细描述>
     * 
     * @param entity
     * @return
     */
    public ExamPaperSubject insertPaper(ExamPaperEntity entity, Integer subjectDbId) {
        logger.info("begin to insertPaper ");
        ExamPaperSubject examPaperSubject = null;

        List<ExamStruct> list = entity.getExamStructs();

        ExamPaper examPaper = examutil.generateExamPaper(entity);
        com.hoperun.entity.ExamPaperSubject examPaperSubjectinsert = null;
        // 新增
        try {
            examPaperService.insert(examPaper);
        } catch (Exception e) {
            logger.error("insert examPaper error" + e);
        }

        examPaperSubject = new ExamPaperSubject();
        List<ExamSubject> singleSubjectsList = null;
        List<ExamSubject> multiSubjectsList = null;
        logger.info("ExamStructlist size=====" + list.size());
        // 考试规则
        for (int i = 0; i < list.size(); i++) {
            // 试题数
            int subjectCount = list.get(i).getColSubjectCount();
            // 试题分数
            int subjectPoint = list.get(i).getColSubjectPoint();
            // 试题类型
            int subjectType = list.get(i).getColSubjectType();
            // 试题难度
            int defficuteType = list.get(i).getColDifficultLevel();
            ExamSubject[] examSubject =
                examutil.createPaper(String.valueOf(subjectType), subjectDbId, subjectCount, defficuteType);
            logger.info("examSubject size=====" + examSubject.length);
            // 新增试题
            for (int j = 0; j < examSubject.length; j++) {
                ExamSubject examPapersubject = examSubject[j];
                examPaperSubjectinsert = new com.hoperun.entity.ExamPaperSubject();
                examPaperSubjectinsert.setPoint(subjectPoint);
                examPaperSubjectinsert.setSubjectAnswer(examPapersubject.getAnswer().getSubjectAnswer());
                examPaperSubjectinsert.setExamPaper(examPaper.getId());
                examPaperSubjectinsert.setSubjectId(examPapersubject.getId());
                examPaperSubjectinsert.setSubjectType(list.get(i).getColSubjectType());
                // 清空正确答案
                examPapersubject.setAnswer(null);
                examPaperSubject.setPaperId(examPaper.getId());
                try {
                    examPaperSubjectMapper.insert(examPaperSubjectinsert);
                    
                } catch (Exception e) {
                    logger.error("examPaperSubjectinsert error" + e);
                }
            }
            switch (Integer.valueOf(subjectType)) {
                case 0:
                    // examPaperSubject.setSingleSubjects(examSubject);
                    singleSubjectsList = examutil.initExamSubject(examSubject, singleSubjectsList);
                    break;
                case 1:
                    // examPaperSubject.setMultiSubjects(examSubject);
                    // multiSubjects[i]=examSubject[0];
                    multiSubjectsList = examutil.initExamSubject(examSubject, multiSubjectsList);
                    break;

                default:
                    break;
            }

        }
        examPaperSubject.setSingleSubjects(singleSubjectsList);
        examPaperSubject.setMultiSubjects(multiSubjectsList);
        // 新增题目详情
        examPaperSubjectinsert.setSubjectDetail(JSONObject.toJSONString(examPaperSubject));
        examPaperSubjectMapper.updateByPrimaryKey(examPaperSubjectinsert);
        return examPaperSubject;

    }

}
