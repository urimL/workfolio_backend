package com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto;

import com.workfolio.workfolio_backend.coverletter.dto.QuestionDto.QuestionResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class CoverLetterDetailResponse {
    private String cp;
    private String objective;
    private List<QuestionResponse> questions;

    public CoverLetterDetailResponse(String cp, String objective, List<QuestionResponse> questions) {
        this.cp = cp;
        this.objective = objective;
        this.questions = questions;
    }
}
