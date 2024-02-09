package com.workfolio.workfolio_backend.interview.dto;

import com.workfolio.workfolio_backend.interview.domain.Interview;
import lombok.Getter;

@Getter
public class InterviewResponse {

    private String question;
    private String answer;
    private String cp;

    private String userEmail;

    public InterviewResponse(Interview interview) {
        this.question = interview.getQuestion();
        this.answer = interview.getAnswer();
        this.cp = interview.getCp();
        this.userEmail = interview.getUserEmail();
    }
}
