package com.workfolio.workfolio_backend.covoerletter.dto;

import com.workfolio.workfolio_backend.covoerletter.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddQuestionRequest {

    private String question;
    private String answer;

    public Question toEntity(Long cl_id) {
        return Question.builder()
                .clId(cl_id)
                .question(question)
                .answer(answer)
                .build();
    }
}
