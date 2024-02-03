package com.workfolio.workfolio_backend.member.service;

import com.workfolio.workfolio_backend.config.jwt.TokenProvider;
import com.workfolio.workfolio_backend.config.oauth2.OAuth2MemberService;
import com.workfolio.workfolio_backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
//    private final MemberService memberService;
    private final OAuth2MemberService oAuth2MemberService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected user");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        Member member = oAuth2MemberService.findById(userId);
        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }
}