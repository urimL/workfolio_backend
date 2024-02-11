package com.workfolio.workfolio_backend.covoerletter.dto;

import com.workfolio.workfolio_backend.covoerletter.domain.CoverLetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCoverLetterRequest {

    private String cp;
    private String objective;

    public CoverLetter toEntity(String nickname, String email) {
        return CoverLetter.builder()
                .nickname(nickname)
                .userEmail(email)
                .cp(cp)
                .objective(objective)
                .build();
    }

}
