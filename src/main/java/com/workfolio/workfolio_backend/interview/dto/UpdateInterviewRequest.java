package com.workfolio.workfolio_backend.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateInterviewRequest {
    private String question;
    private String answer;
    private String cp;
}
