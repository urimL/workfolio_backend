package com.workfolio.workfolio_backend.coverletter.dto.QuestionDto;

import com.workfolio.workfolio_backend.coverletter.domain.Question;
import lombok.Getter;

@Getter
public class QuestionResponse {
    private Long cl_id;
    private String question;
    private String answer;

    public QuestionResponse(Question question) {
        this.cl_id = question.getClId();
        this.question = question.getQuestion();
        this.answer = question.getAnswer();
    }
}
