//package com.workfolio.workfolio_backend.member;
//
//import com.workfolio.workfolio_backend.config.jwt.TokenProvider;
//import com.workfolio.workfolio_backend.config.oauth2.PrincipalDetails;
//import com.workfolio.workfolio_backend.member.repository.MemberRepository;
//import com.workfolio.workfolio_backend.member.util.CookieUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import jakarta.servlet.http.Cookie;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//@Log4j2
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    @Value("${jwt.refresh_cookie_key}")
//    private String cookieKey;
//
//    private final MemberRepository memberRepository;
//    private final TokenProvider tokenProvider;
//
//    public String refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {
//        // 1. Validation Refresh Token
//        String oldRefreshToken = CookieUtil.getCookie(request, cookieKey)
//                .map(Cookie::getValue).orElseThrow(() -> new RuntimeException("no Refresh Token Cookie"));
//
//        if (!tokenProvider.validateToken(oldRefreshToken)) {
//            throw new RuntimeException("Not Validated Refresh Token");
//        }
//
//        // 2. 유저정보 얻기
//        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
//        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
//
//        Long id = Long.valueOf(user.getName());
//
//        // 3. Match Refresh Token
//        String savedToken = memberRepository.getRefreshTokenById(id);
//
//        if (!savedToken.equals(oldRefreshToken)) {
//            throw new RuntimeException("Not Matched Refresh Token");
//        }
//
//        // 4. JWT 갱신
//        String accessToken = tokenProvider.createAccessToken(authentication);
//        tokenProvider.createRefreshToken(authentication, response);
//
//        return accessToken;
//    }
//}