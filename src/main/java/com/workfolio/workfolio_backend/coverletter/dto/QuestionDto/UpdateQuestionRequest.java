package com.workfolio.workfolio_backend.coverletter.dto.QuestionDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateQuestionRequest {
    private String question;
    private String answer;
}
