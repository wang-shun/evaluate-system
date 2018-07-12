package com.hoperun.service;

import com.hoperun.domain.AnswerPaperQuestion;
import com.hoperun.domain.AnswerQuestion;
import com.hoperun.mapper.AnswerPaperQuestionMapper;
import com.hoperun.mapper.AnswerQuestionMapper;
import com.hoperun.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tangyi on 2017/3/29.
 */
@Service
@Transactional(readOnly = true)
public class AnswerPaperQuestionService {

    private static Logger logger = LoggerFactory.getLogger(AnswerPaperQuestionService.class);

    @Autowired
    AnswerPaperQuestionMapper answerPaperQuestionMapper;


    public List<AnswerPaperQuestion> getByPaperId(String id) {
        return answerPaperQuestionMapper.findByPaperId(id);
    }

    public List<AnswerPaperQuestion> getByQuestionId(String id) {
        return answerPaperQuestionMapper.findByQuestionId(id);
    }

    @Transactional(readOnly = false)
    public int saveAnswerPaperQuestion(AnswerPaperQuestion question) {
        return answerPaperQuestionMapper.insert(question);
    }
}
