package com.workfolio.workfolio_backend.covoerletter.dto;

import com.workfolio.workfolio_backend.covoerletter.domain.CoverLetter;
import lombok.Getter;


@Getter
public class CoverLetterResponse {
    private String cp;
    private String objective;
    private String nickname;

    public CoverLetterResponse(CoverLetter coverLetter) {
        this.cp = coverLetter.getCp();
        this.objective = coverLetter.getObjective();
        this.nickname = coverLetter.getNickname();
    }
}
