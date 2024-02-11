package com.workfolio.workfolio_backend.interview.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "nickname")
    private String nickname;

    @Column(name="question", nullable = false, columnDefinition = "LONGTEXT")
    private String question;

    @Column(name = "answer", columnDefinition = "LONGTEXT")
    private String answer;

    @Column(name = "cp", columnDefinition = "LONGTEXT")
    private String cp;

    @Builder
    public Interview(String question, String answer, String cp, String userEmail, String nickname) {
        this.question = question;
        this.answer = answer;
        this.cp = cp;
        this.userEmail = userEmail;
        this.nickname = nickname;
    }

    public void update(String question, String answer, String cp){
        this.question = question;
        this.answer = answer;
        this.cp = cp;
    }

}
