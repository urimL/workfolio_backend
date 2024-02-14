package com.workfolio.workfolio_backend.portfolio.grade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateGradeRequest {
    private String totalGrade;
    private String majorGrade;
}
