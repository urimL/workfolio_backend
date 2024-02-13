package com.workfolio.workfolio_backend.grade.dto;

import com.workfolio.workfolio_backend.grade.domain.Grade;
import lombok.Getter;

@Getter
public class GradeResponse {
    private String nickname;
    private String totalGrade;
    private String majorGrade;


    public GradeResponse(Grade grade, String nickname) {
        this.nickname = nickname;
        this.totalGrade = grade.getTotalGrade();
        this.majorGrade = grade.getMajorGrade();
    }
}
