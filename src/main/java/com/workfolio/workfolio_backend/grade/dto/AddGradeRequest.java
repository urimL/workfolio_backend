package com.workfolio.workfolio_backend.grade.dto;

import com.workfolio.workfolio_backend.grade.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddGradeRequest {

    private String totalGrade;
    private String majorGrade;

    public Grade toEntity(String email) {
        return Grade.builder()
                .userEmail(email)
                .totalGrade(totalGrade)
                .majorGrade(majorGrade)
                .build();
    }
}
