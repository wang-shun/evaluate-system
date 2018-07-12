package com.hoperun.dto;

import com.hoperun.domain.Paper;
import com.hoperun.domain.Question;

/**
 * Created by tangyi on 2017/3/30.
 */
public class DtoAnswerPaper {

    private Paper paper;

    private Question question;

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
