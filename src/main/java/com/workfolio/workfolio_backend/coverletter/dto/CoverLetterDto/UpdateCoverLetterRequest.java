package com.workfolio.workfolio_backend.coverletter.dto.CoverLetterDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCoverLetterRequest {
    private String cp;
    private String objective;
}
