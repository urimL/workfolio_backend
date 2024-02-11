package com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto;

import com.workfolio.workfolio_backend.coverletter.domain.CoverLetter;
import lombok.Getter;

@Getter
public class CoverLetterListResponse {
    private String cp;
    private String objective;

    public CoverLetterListResponse(CoverLetter coverLetter) {
        this.cp = coverLetter.getCp();
        this.objective = coverLetter.getObjective();
    }
}
