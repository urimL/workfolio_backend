package com.workfolio.workfolio_backend.config.oauth2;

import com.workfolio.workfolio_backend.config.jwt.TokenProvider;
import com.workfolio.workfolio_backend.member.domain.Member;
import com.workfolio.workfolio_backend.member.domain.RefreshToken;
import com.workfolio.workfolio_backend.member.repository.RefreshTokenRepository;
import com.workfolio.workfolio_backend.member.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final OAuth2MemberService oAuth2MemberService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Member member = oAuth2MemberService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        //리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(member, REFRESH_TOKEN_DURATION);
        saveRefreshToken(member.getId(), refreshToken); //refresh token DB에 저장
        addRefreshTokenToCookie(req, resp, refreshToken); //토큰 만료 시 재발급 요청하도록 쿠키에 리프레시 토큰 저장

        //엑세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.generateToken(member, ACCESS_TOKEN_DURATION); //액세스 토큰 생성
        String targetUrl = getTargetUrl(accessToken); //쿼리 파라미터에 액세스 토큰 추가

        //임시로 저장해둔 인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(req, resp);

        //redirect
        getRedirectStrategy().sendRedirect(req, resp, targetUrl);
    }

    //생성된 리프레시 토큰 전달받아 DB에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    //생성된 리프레시 토큰 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest req, HttpServletResponse resp, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(req, resp, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(resp, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    //인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest req, HttpServletResponse resp) {
        super.clearAuthenticationAttributes(req);
        authorizationRequestRepository.removeAuthorizationRequestCookies(req, resp);
    }

    //엑세스 토큰을 패스에 추가
    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build().toUriString();
    }
}