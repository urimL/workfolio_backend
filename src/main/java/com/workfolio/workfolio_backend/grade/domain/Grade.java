package com.workfolio.workfolio_backend.grade.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "totalGrade")
    private String totalGrade;

    @Column(name = "majorGrade")
    private String majorGrade;

    @Builder
    public Grade(String userEmail, String totalGrade, String majorGrade) {
        this.userEmail = userEmail;
        this.totalGrade = totalGrade;
        this.majorGrade = majorGrade;
    }

    public void update(String totalGrade, String majorGrade) {
        this.totalGrade = totalGrade;
        this.majorGrade = majorGrade;
    }
}
