package com.workfolio.workfolio_backend.interview.dto;

import com.workfolio.workfolio_backend.interview.domain.Interview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddInterviewRequest {

    private String question;
    private String answer;
    private String cp;

    public Interview toEntity(String nickname, String email) {
        return Interview.builder()
                .nickname(nickname)
                .userEmail(email)
                .question(question)
                .answer(answer)
                .cp(cp)
                .build();
    }
}
