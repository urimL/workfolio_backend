package com.workfolio.workfolio_backend.covoerletter.dto;

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
