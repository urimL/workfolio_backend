package com.workfolio.workfolio_backend.config.oauth2.handler;

import com.workfolio.workfolio_backend.config.jwt.TokenProvider;
import com.workfolio.workfolio_backend.config.oauth2.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.workfolio.workfolio_backend.member.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.workfolio.workfolio_backend.config.oauth2.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestBasedOnCookieRepository;
    // 로그인 성공해서 생성된 JWT를 authorizedRedirectUri로 client에게 전달
    @Value("${app.oauth2.authorizedRedirectUris}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(req, resp, authentication);

        if (resp.isCommitted()) {
            logger.debug("Response has already been committed");
            return;
        }

        clearAuthenticationAttributes(req, resp);
        getRedirectStrategy().sendRedirect(req, resp, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(req, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Redirect URIs are not matched");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        //create JWT
        String accessToken = tokenProvider.createAccessToken(authentication);
        tokenProvider.createRefreshToken(authentication, resp);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest req, HttpServletResponse resp) {
        super.clearAuthenticationAttributes(req);
        authorizationRequestBasedOnCookieRepository.removeAuthorizationRequestCookies(req, resp);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizedUri = URI.create(redirectUri);

        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
            && authorizedUri.getPort() == clientRedirectUri.getPort()) {
            return true;
        }

        return false;
    }



}