package com.workfolio.workfolio_backend.coverletter.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoverLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cp")
    private String cp;

    @Column(name = "objective", columnDefinition = "LONGTEXT")
    private String objective;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "nickname")
    private String nickname;

    @Builder
    public CoverLetter(String cp, String objective, String userEmail, String nickname) {
        this.nickname = nickname;
        this.userEmail = userEmail;
        this.cp = cp;
        this.objective = objective;
    }

    public void update(String cp, String objective) {
        this.cp = cp;
        this.objective = objective;
    }
}
